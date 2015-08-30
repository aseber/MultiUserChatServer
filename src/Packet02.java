import java.io.IOException;

public class Packet02 {

	static private final byte ID = 2;
	private User client;
	
	public Packet02 (User user) {
		
		this.client = user;
		
	}
	
	public void write(boolean accepted) {
		
		try {
		
			client.out.writeByte(ID);
			client.out.writeBoolean(accepted);
			
			if (accepted) {
				
				client.out.writeUTF(ServerBase.SERVER_NAME);
				
			}
			
			client.out.flush();
		
		} catch (IOException e) {
			
			DisconnectClient.lostConnection(client);
			
		}
		
	}
	
	public byte getID() {
		
		return ID;
		
	}
	
}
