import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class UserPopupMenu extends JPopupMenu {

	private static final long serialVersionUID = 1L;
	JPopupMenu popUp = new JPopupMenu("");
	JMenuItem profile;
	JMenuItem kick;
	JMenuItem ban;
	User user;
	
	public UserPopupMenu(User user) {
		
		this.user = user;
		this.profile = new JMenuItem(user.username + "'s Profile");
		this.kick = new JMenuItem("Kick " + user.username);
		this.ban = new JMenuItem("Ban " + user.username);
		this.profile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				profileActionPerformed();
				
			}
			
		});
		this.kick.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				kickActionPerformed();
				
			}
			
		});
		this.ban.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				banActionPerformed();
				
			}
			
		});
		this.popUp.add(profile);
		this.popUp.add(kick);
		this.popUp.add(ban);
		
	}
	
	public void show(Component component, int x, int y) {
		
		popUp.show(component, x, y);
		
	}
	
	private void profileActionPerformed() {
		
		//Accesses User Profile with more information.
		
	}
	
	private void kickActionPerformed() {
		
		DisconnectClient.kick(user);
		
		
	}

	private void banActionPerformed() {
		
		DisconnectClient.ban(user);
		
		
	}
	
}
