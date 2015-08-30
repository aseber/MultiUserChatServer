public class Packet03 {

	static private final byte ID = 3;
	private User client;
	
	public Packet03 (User user) {
		
		this.client = user;
		
	}
	
	public void read() {
		
		String username = client.inComms.readUTF();
		String password = client.inComms.readUTF();
		
		if (!username.isEmpty()) {
			
			User user = UserData.openUser(username);
			
			if (user != null) {
				
				if (!user.isBanned()) {
				
					if (user.checkPassword(password) && !user.permission.equals(Permission.CONSOLE)) {
						
						User user2 = OnlineUsersList.getUser(user.username);
						
						if (user2 != null) { 
							
							DisconnectClient.kick(user2);
							client.setSessionUsername(user.username);
							client.setPermission(user.permission);
							client.inComms.setName("InputCommunications: " + user.username); // Possible hacked account?
							new Packet04(client).write(true, "Successfully logged into \"" + ServerBase.SERVER_NAME + "\" under the name \"" + client.username + "\"");
							OnlineUsersList.loginUser(client);
							
						} else {
							
							client.setSessionUsername(user.username);
							client.setPermission(user.permission);
							client.inComms.setName("InputCommunications: " + user.username);
							new Packet04(client).write(true, "Successfully logged into \"" + ServerBase.SERVER_NAME + "\" under the name \"" + client.username + "\"");
							OnlineUsersList.loginUser(client);
							
						}
					
					} else {
						
						new Packet04(client).write(false, "Invalid username/password combination."); // Need to add ability to check for users who are currently online (Another OccupiedUsers type class) As well as check for banned names and IP's
						
					}
						
				} else {
					
					new Packet04(client).write(false, "User is banned.");
					
				}
				
			} else {
				
				user = new User(username, password);
				UserData.newUser(user); // Create a new user and log in
				client.setSessionUsername(user.username);
				client.setPermission(user.permission);
				client.inComms.setName("InputCommunications: " + user.username);
				new Packet04(client).write(true, "Successfully logged into \"" + ServerBase.SERVER_NAME + "\" under the new username \"" + user.username + "\" with password \"" + user.getPassword() + "\"");
				//OnlineUsersList. // Must rename the user here, not sure how yet, but it must be done
				OnlineUsersList.loginUser(client);
				
			}
			
		} else {
			
			new Packet04(client).write(false, "Username and/or Password fields are empty.");
			
		}
		
	}
	
	public byte getID() {
		
		return ID;
		
	}
	
}
