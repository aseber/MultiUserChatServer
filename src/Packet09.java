import java.io.IOException;

public class Packet09 {

	static private final byte ID = 9;
	private User client;
	
	public Packet09 (User user) {
		
		this.client = user;
		
	}
	
	public void read() {}
	
	public void write() {
		
		try {
			
			client.out.writeByte(ID);
			client.out.flush();
		
		} catch (IOException e) {
		
			DisconnectClient.lostConnection(client);
		
		}
		
		System.out.println("Heatbeat for " + client.username);
		
	}
	
	public byte getID() {
		
		return ID;
		
	}
	
}
