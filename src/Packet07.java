import java.io.IOException;

public class Packet07 {

	static private final byte ID = 7;
	private User client;
	
	public Packet07 (User user) {
		
		this.client = user;
		
	}
	
	public void read() {
		
		OutputCommunications.sendMessageToAll(client, client.inComms.readUTF());
		
	}
	
	public void write(User user, String message) {
		
		try {
		
			client.out.writeByte(ID);
			client.out.writeUTF(user.username + ": " + message);
			client.out.flush();
		
		} catch (IOException e) {
		
			DisconnectClient.lostConnection(client);
		
		}
		
	}
	
	public byte getID() {
		
		return ID;
		
	}
	
}
