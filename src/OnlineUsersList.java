import java.util.ArrayList;

public class OnlineUsersList {
	
	static ArrayList<User> onlineUsers = new ArrayList<User>(10);
	
	public static void addUser(User user) {
		
		onlineUsers.add(user);
		ServerBase.gui.updateServerTitle();
		ServerBase.gui.addUserToList(user);
		
		if (!user.equals(User.consoleUser)) {
		
			ServerBase.gui.printCommand("\"" + user.username + "\" has connected to the server.");

		}
		
	}
	
	public static void loginUser(User user) {
		
		ServerBase.gui.updateUserList();
		ServerBase.gui.printCommand("\"" + user.socket.getLocalAddress() + "\" has been renamed to \"" + user.username + "\".");
		OutputCommunications.addNewUser(user);
		OutputCommunications.addUser(user);
		
	}
	
	public static User getUser(int index) {
		
		synchronized (onlineUsers) {
		
			return onlineUsers.get(index);
		
		}
			
	}
	
	public static User getUser(String username) {
		
		synchronized (onlineUsers) {
		
			for (int i = 0; i < arrayLength(); i++) {
			
				if (onlineUsers.get(i).username.equals(username)) {
				
					return onlineUsers.get(i);
				
				}
			
			}
		
		}
			
		return null;
		
	}
	
	public static void removeUser(User user) {
		
		synchronized (onlineUsers) {
			
			for (int i = 0; i < arrayLength(); i++) {
			
				if (onlineUsers.get(i).equals(user)) {
				
					User removedUser = onlineUsers.get(i);
					onlineUsers.remove(i);
					OutputCommunications.removeUser(removedUser);
					return;
				
				}
				
			}
			
			ServerBase.gui.printToLog("Failed to remove user from online users list");
			
		}
		
	}
	
	public static int arrayLength() {
		
		synchronized (onlineUsers) {
		
			return onlineUsers.size();
		
		}
		
	}
	
}