import java.util.ArrayList;

public class HeartbeatMonitor extends Thread {

	private Boolean running = false;
	private static ArrayList<Long> timers = new ArrayList<Long>(10);
	
	public HeartbeatMonitor() {}
	
	public void run() {
		
		running = false;
		
		while (running) {
			
			for (int i = 0; i < OnlineUsersList.arrayLength(); i++) { // Must fix!
				
				if (System.nanoTime() - timers.get(i) >= 15000) {
					
					new Packet09(OnlineUsersList.onlineUsers.get(i));
					
				}
				
			}
			
		}
		
	}
	
	public void stopThread() {
		
		running = false;
		
	}
	
}
