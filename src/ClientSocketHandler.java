import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientSocketHandler extends Thread {

	ServerSocket server;
	boolean running = true;
	
	public ClientSocketHandler(int port) {
		
		super("Client Socket Handler");
		
		while (true) { // Should be runing and not always true?
			
			try {
				
				server = new ServerSocket(port);
				break;
				
			} catch (IOException e) {
				
				ServerBase.gui.printToLog("Could not bind server to port " + port);
				
				try {
					
					sleep(1000);
					
				} catch (InterruptedException e1) {
					
					e1.printStackTrace();
					
				}
				
			}
			
		}
		
		ServerBase.gui.printToLog("Successfully bound server to port " + port);
		
	}
	
	@Override
	public void run() {
		
		while (running) {
			
			if (!ServerBase.serverLocked && OnlineUsersList.arrayLength() < ServerBase.maxClients) {
				
				User client = null;
				
				try {
					
					Socket clientSocket = server.accept(); // Will still allow one user in after the server locks...
					client = new User(clientSocket.getInetAddress().toString(), "", new Permission(Permission.INVISIBLE_BYTE)); //Creates client without a socket or session
					client.setSocket(clientSocket); //Sets socket to the clientSocket, creates input/output streams.
					OnlineUsersList.addUser(client);
					
				} catch (IOException e) {
					
					DisconnectClient.lostConnection(client); //Could give out a null pointer exception IF the serverSocket dies somehow...
					
				}
				
			} else {
				
				try {
					
					synchronized (this) {
					
						this.wait(1000);
						System.out.println("Server is locked");
			
					}
					
				} catch (InterruptedException e) {}
				
			}
			
		}
			
	}
	
	public void closeServerSocketThread() {
		
		ServerBase.gui.printToLog("Shutting down server socket thread");
		running = false;
		
	}
	
}