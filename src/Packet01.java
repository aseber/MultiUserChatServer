public class Packet01 {

	static private final byte ID = 1;
	private User client;
		
	public Packet01 (User user) {
		
		this.client = user;
		
	}
	
	public void read() {
		
		if (client.inComms.readUTF().equals("Chat Program; Version: " + ServerBase.VERSION)) {
			
			ServerBase.gui.printToLog("Client \"" + client.username + "\" is running program version " + ServerBase.VERSION);
			new Packet02(client).write(true);
			
		}
		
		else {
			
			new Packet02(client).write(false);
			DisconnectClient.kick(client);
			
		}
		
	}
	
	public byte getID() {
		
		return ID;
		
	}
	
}
