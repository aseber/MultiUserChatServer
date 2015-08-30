import java.io.IOException;

public class Packet08 {

	static private final byte ID = 8;
	static public final byte USER_LOGGED_ON = 1;
	static public final byte USER_LOGGED_OFF = 2;
	private User client;
	
	public Packet08 (User user) {
		
		this.client = user;
		
	}
	
	public void write(Byte loginType, User user) {
		
		try {
		
			client.out.writeByte(ID);
			client.out.writeByte(loginType);
			client.out.writeUTF(user.username);
			client.out.flush();
		
		} catch (IOException e) {
		
			DisconnectClient.lostConnection(client);
		
		}
		
	}
	
	public byte getID() {
		
		return ID;
		
	}
	
}
