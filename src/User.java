import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class User {

	protected static User consoleUser;
	protected volatile String username;
	protected Permission permission;
	protected Socket socket;
	protected DataInputStream in;
	protected DataOutputStream out;
	protected InputCommunications inComms;
	private String password;
	private boolean banned;
	
	public User(String username, String password, Permission permission, Boolean banned) {
		
		this.username = new String(username);
		this.password = new String(password);
		this.permission = permission;
		this.banned = banned;
		
	}
	
	public User(String username, String password, Permission permission) {
		
		this.username = new String(username);
		this.password = new String(password);
		this.permission = permission;
		this.banned = false;
		
	}
	
	public User(String username, String password) {
		
		this.username = new String(username);
		this.password = new String(password);
		this.permission = new Permission(Permission.USER_BYTE);
		this.banned = false;
		
	}
	
	// Fix online users to inclue users who are not logged in but that still need to have a heartbeat monitor on (ALL USERS MUST HAVE A HEARTBEAT! LOGGED IN OR NOT!)
	// Make saved users a imprint, so they will only be used to find username/password/permission, online user gets their name and can do stuff to the imprint (change password, maybe username)
	// Must fix packet02 issue for client (NullPointerException now? WTF?) and fix heartbeat (Both sides must check integrity of connection every 15 seconds.
	// All Console users are invisible, invisible class too, and muted class stuff
	// Need to find a way to make saved users synonymous with online users, so you are "switched" to the user saved (socket, etc.) and when you log out you're
	// removed from it.
	// Need to also pull apart large classes to make them smaller and easier to manage. (Like what was done with cleanup on DisconnectClient)
	// Reform Users, files etc.
	// Logging in permission will kick people if server locked, it needs to check for them and selectively kick.
	// "LOGGING_IN", "INVISIBLE", "CONSOLE" permissions = no visibility outside of server
	
	public void setConsoleUser() {
		
		this.in = new DataInputStream(System.in); // Will change
		this.out = new DataOutputStream(System.out); // Will change
		this.permission = new Permission(Permission.CONSOLE_BYTE);
		consoleUser = this;
		
	}
	
	public void setSessionUsername(String username) {
		
		this.username = new String(username);
		
	}
	
	public void setPermission(Permission permission) {
		
		this.permission = permission;
		UserData.saveUser(this);
		
	}
	
	public void setBanned(boolean banned) {
		
		this.banned = banned;
		UserData.saveUser(this);
		
	}
	
	public boolean isBanned() {
		
		return this.banned;
		
	}
	
	public boolean checkPassword(String password) {
		
		if (this.password.equals(password)) {
			
			return true;
			
		}
		
		return false;
		
	}
	
	public String getPassword() {
		
		return this.password;
		
	}
	
	public void setPassword(String password) {
		
		this.password = password;
		UserData.saveUser(this);
		
	}
	
	public void setSocket(Socket socket) {
		
		this.socket = socket;
		
		try {
			
			this.socket.setSoTimeout(5000);
			this.in = new DataInputStream(socket.getInputStream());
			this.inComms = new InputCommunications(in, this);
			this.inComms.start();
			this.out = new DataOutputStream(socket.getOutputStream());
			
		} catch (IOException e) {
			
			DisconnectClient.lostConnection(this);
			
		}
		
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (super.equals(obj)) {
			
			User user2 = (User) obj;
			
			if (this.username.equals(user2.username) && this.password.equals(user2.password) && this.permission.equals(user2.permission)) {
				
				return true;
				
			}
			
		}
		
		return false;
		
	}
	
	@Override
	public String toString() {
		
		if (this.username != null && this.password != null) {
			
			return username + " | " + permission;
			
		}
		
		return null;
		
	}
	
}
