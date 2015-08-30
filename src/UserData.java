import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class UserData {
	
	public static void newUser(User user) {
		
		File saveData = new File(ServerBase.savedUsersFolder + File.separator + user.username + ".dat");
		
		try {
		
			if (!saveData.exists()) {
		
				saveData.createNewFile();	
				saveUser(user);
			
			}
				
		}
		
		catch (FileNotFoundException e) {}
		
		catch (IOException e) {}
		
	}
	
	public static boolean checkUserCredentials(String username, String password) {
		
		for (File file : ServerBase.savedUsersFolder.listFiles()) {
			
			if (file.getName().equals(username + ".dat")) {
				
				User user = openUser(username);
				
				if (user.checkPassword(password)) {
					
					return true;
					
				}
				
				break;
				
			}
			
		}
		
		return false;
		
	}
	
	public static User openUser(String username) {
		
		try {
			
			DataInputStream input = new DataInputStream(new FileInputStream(ServerBase.savedUsersFolder + File.separator + username + ".dat"));
			User user =  new User(input.readUTF(), input.readUTF(), new Permission(input.readByte()), input.readBoolean());
			input.close();
			return user;
			
		}
		
		catch (IOException e) {}
		
		return null;
		
	}
	
	public static void saveUser(User user) {

		try {
					
			DataOutputStream output = new DataOutputStream(new FileOutputStream(ServerBase.savedUsersFolder + File.separator + user.username + ".dat"));
			output.writeUTF(user.username);
			output.writeUTF(user.getPassword());
			output.writeByte(user.permission.getByteValue());
			output.writeBoolean(user.isBanned());
			output.flush();
			output.close();
			ServerBase.gui.printToLog("Saving user: " + user);
			
		}
			
		catch (IOException e) {}
		
	}
	
}