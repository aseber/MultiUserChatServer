import java.io.IOException;

public class Packet06 {

	static private final byte ID = 6;
	private User client;
	
	public Packet06 (User user) {
		
		this.client = user;
		
	}
	
	public void write(User user) {
		
		try {
		
			client.out.writeByte(ID);
			client.out.writeUTF(user.username);
			client.out.writeByte(user.permission.getByteValue());
			client.out.flush();
		
		} catch (IOException e) {
		
			DisconnectClient.lostConnection(client);
		
		}
		
	}
	
	public byte getID() {
		
		return ID;
		
	}
	
}
