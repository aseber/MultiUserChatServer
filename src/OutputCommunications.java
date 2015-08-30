public class OutputCommunications {

	public static void addNewUser(User user) {
		
		synchronized (OnlineUsersList.onlineUsers) {
			
			for (int i = 0; i < OnlineUsersList.arrayLength(); i++) {
				
				User client = OnlineUsersList.getUser(i);
				
				if (checkPermission(client)) {
				
					new Packet05(user).write(client);
					
				}
				
			}
			
		}
		
	}
	
	public static void addUser(User user) {
		
		if (checkPermission(user)) {
		
			synchronized (OnlineUsersList.onlineUsers) {
				
				for (int i = 0; i < OnlineUsersList.arrayLength(); i++) {
					
					User client = OnlineUsersList.getUser(i);
					
					if (!client.equals(user) && checkPermission(client)) {
					
						new Packet05(OnlineUsersList.getUser(i)).write(user);
						new Packet08(OnlineUsersList.getUser(i)).write(Packet08.USER_LOGGED_ON, user);
						
					}
					
				}
				
			}
		
		}
			
	}
	
	public static void removeUser(User user) {
		
		ServerBase.gui.updateServerTitle();
		ServerBase.gui.removeUserFromList(user);
		ServerBase.gui.printError("\"" + user.username + "\" has disconnected from the server.");
		
		if (checkPermission(user)) {
			
			synchronized (OnlineUsersList.onlineUsers) {
			
				for (int i = 0; i < OnlineUsersList.arrayLength(); i++) {
				
					User client = OnlineUsersList.getUser(i);
					
					if (!client.equals(user) && checkPermission(client)) {
						
						new Packet06(OnlineUsersList.getUser(i)).write(user);
						new Packet08(OnlineUsersList.getUser(i)).write(Packet08.USER_LOGGED_OFF, user);
						
					}
						
				}
				
			}
		
		}
			
	}
		
	public static void sendMessageToAll(User user, String message) { // Depreciated
		
		ServerBase.gui.printMessage(user.username + ": " + message);
		
		synchronized (OnlineUsersList.onlineUsers) {
			
			for (int i = 0; i < OnlineUsersList.arrayLength(); i++) {
				
				User client = OnlineUsersList.getUser(i);
				
				if (checkPermission(client)) {
					
					new Packet07(client).write(user, message);
					
				}
				
			}
			
		}
		
	}
	
	public static void sendMessageToGroup() {
		
		// Future class for group specific chat functions. Need group constructor etc.
		
	}
	
	private static boolean checkPermission(User user) {
	
		if (user.permission.equals(Permission.NO_PERMISSION) || user.permission.equals(Permission.INVISIBLE) || user.permission.equals(Permission.CONSOLE)) {
			
			return false;
			
		}
		
		return true;
		
	}
	
}
