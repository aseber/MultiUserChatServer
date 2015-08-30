import java.io.IOException;

public class Packet04 {

	static private final byte ID = 4;
	private User client;
	
	public Packet04 (User user) {
		
		this.client = user;
		
	}
	
	public void write(boolean accepted, String message) {
		
		try {
		
			client.out.writeByte(ID);
			client.out.writeBoolean(accepted);
			client.out.writeUTF(message);
			client.out.flush();
		
		} catch (IOException e) {
		
			DisconnectClient.lostConnection(client);
		
		}
		
	}
	
	public byte getID() {
		
		return ID;
		
	}
	
}
