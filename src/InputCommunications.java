import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

public class InputCommunications extends Thread {

	private User client;
	private DataInputStream in;
	private boolean running = true;
	
	public InputCommunications(DataInputStream in, User user) {
	
		super("InputCommunications: " + user.username);
		this.client = user;
		this.in = in;
		
	}
	
	@Override
	public void run() {
		
		byte packetID;
		
		while (running) {
			
			packetID = 0;
			
			try {
				
				packetID = in.readByte();
				
			} catch (EOFException e) { //Under some circumstances, the EOF exception will not be found and the Input thread will not shut down.
				
				DisconnectClient.lostConnection(client);
				
			} catch (IOException e) {}
			
			switch(packetID) {
			
			case 1:
				new Packet01(client).read();
				break;
				
			case 2:
				break;
				
			case 3:
				new Packet03(client).read();
				break;
				
			case 4:
				break;
				
			case 5:
				break;
			
			case 6:
				break;
			
			case 7:
				new Packet07(client).read();
				break;
				
			case 8:
				break;
				
			case 9:
				break;
				
			case 10:
				break;
				
			default:
				break;
			
			}
			
		}
		
		try {
			
			in.close();
		
		}
			
		catch (IOException e) {}
		
	}
	
	public void close() {
		
		running = false;
		
	}
	
	public int readInt() {
		
		try {
			
			return this.in.readInt();
			
		} catch (IOException e) {
			
			DisconnectClient.lostConnection(client);
			return 0;
			
		}

	}
	
	public String readUTF() {
		
		try {
			
			return this.in.readUTF();
			
		} catch (IOException e) {
			
			DisconnectClient.lostConnection(client);
			return null;
			
		}

	}
	
}
