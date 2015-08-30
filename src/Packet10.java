import java.io.IOException;

public class Packet10 {

	static private final byte ID = 10;
	private User client;
	
	public Packet10 (User user) {
		
		this.client = user;
		
	}
	
	public void read() {}
	
	public void write(String message) {
		
		try {
		
			client.out.writeByte(ID);
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
