public abstract class Packet {

	static private final byte ID = 00;
	private User client;
	
	public Packet (User user) {
		
		try {
			
			this.client = user;
			
		}
		
		catch (NullPointerException e) {
			
			ServerBase.gui.printError("An error was encountered when accessing a client for a packet.");
			
		}
			
	}
	
	public void read(User user) {}
	
	public void write(User user) {}
	
	public byte getID() {
		
		return ID;
		
	}
	
}