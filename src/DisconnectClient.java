import java.io.IOException;

public class DisconnectClient {

	public static void lostConnection(User client) {
		
		ServerBase.gui.printToLog("User " + client.username + " has lost connection to the server.");
		cleanupClient(client);
		
	}
	
	public static void kick(User client) {
		
		ServerBase.gui.printToLog("User " + client.username + " was kicked from the server.");
		cleanupClient(client);
		
	}
	
	public static void ban(User client) {
		
		ServerBase.gui.printToLog("User " + client.username + " was banned from the server.");
		client.setBanned(true);
		cleanupClient(client);
		
	}
	
	public static void shutDownServer() {
		
		ServerBase.gui.printToLog("Shutting server down. Kicking all users.");
		try {ServerBase.serverSocket.closeServerSocketThread();} catch (NullPointerException e) {}
		User client;
		
		for (int i = 0; i < OnlineUsersList.arrayLength(); i++) {
			
			client = OnlineUsersList.getUser(i);
			
			if (!client.permission.equals(Permission.CONSOLE)) {
				
				new Packet10(client).write("Server is shutting down...");
				cleanupClient(client);
			
			}
			
		}
		
		System.exit(1);
		
	}
	
	private static void cleanupClient(User client) {
		
		if (!(client.permission.equals(Permission.CONSOLE))) {
		
			OnlineUsersList.removeUser(client);
			try {client.inComms.close();} catch (NullPointerException e) {}
			try {client.out.close();} catch (IOException e) {} // Will change later as needed.
			try {client.socket.close();} catch (IOException e) {} // Should be finished with cleanup operation at this point!
			
		}
	
	}
	
}
