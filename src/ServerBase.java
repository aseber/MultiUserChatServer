import java.io.File;

public class ServerBase {

	static final String VERSION = "3.25";
	static final String SERVER_NAME = "Austin's Server";
	static int maxClients = 20;
	static final File savedUsersFolder = new File("Saved Users Data");
	static ServerGUI gui;
	static ClientSocketHandler serverSocket;
	static HeartbeatMonitor heartbeatMonitor;
	static boolean serverLocked = false;
	
	public static void main(String[] args) {
	
		if (!savedUsersFolder.exists()) {
			
			savedUsersFolder.mkdir();
			
		}
		
		gui = new ServerGUI();
		gui.setVisible(true);
		
		User defaultUser = new User("Console", "", new Permission(Permission.CONSOLE_BYTE));
		defaultUser.setConsoleUser();
		OnlineUsersList.addUser(defaultUser);
		ServerBase.gui.printToLog("Successfully Created Default User.");
		//heartbeatMonitor = new HeartbeatMonitor();
		
		// REMOVE
		//OnlineUsersList.addUser(new User("Derp", "asdf", new Permission(Permission.ADMINISTRATOR_BYTE)));
		//OnlineUsersList.addUser(new User("Derp22", "asdf", new Permission(Permission.CONSOLE_BYTE)));
		// REMOVE
		
		//heartbeatMonitor.start();
		serverSocket = new ClientSocketHandler(25566);
		serverSocket.start();
		
	}
	
}
