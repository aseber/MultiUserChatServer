import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;

public class ServerGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private static String DEFAULT_SERVER_TITLE = ServerBase.SERVER_NAME + " - " + (OnlineUsersList.arrayLength()) + "/" + ServerBase.maxClients + " Connections - Version: " + ServerBase.VERSION;
	private static final String DEFAULT_MESSAGE_INPUT_STRING = "Type message here...";
	private static final String EMPTY_STRING = "";
	private Document document;

	public ServerGUI() {
		
		initializeGUI();
		
	}
	
	public void updateServerTitle() {
		
		if (ServerBase.serverLocked) {
			
			DEFAULT_SERVER_TITLE = ServerBase.SERVER_NAME + " - " + (OnlineUsersList.arrayLength() - 1) + "/" + ServerBase.maxClients + " Connections - Server Locked - Version: " + ServerBase.VERSION;
			setTitle(DEFAULT_SERVER_TITLE);
			
		} else {
			
			DEFAULT_SERVER_TITLE = ServerBase.SERVER_NAME + " - " + (OnlineUsersList.arrayLength() - 1) + "/" + ServerBase.maxClients + " Connections - Version: " + ServerBase.VERSION;
			setTitle(DEFAULT_SERVER_TITLE);
			
		}
		
	}
	
	public void updateUserList() {
		
		userList.updateUI();
		
	}
	
	public void printMessage(String message) {
		
		document = chatWindow.getStyledDocument();
		
		try {
			
			document.insertString(document.getLength(), message + "\n", defaultSet);
			
		}
		
		catch (BadLocationException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public void printCommand(String message) {
		
		document = chatWindow.getStyledDocument();
		
		try {
			
			document.insertString(document.getLength(), message + "\n", commandSet);
			
		}
		
		catch (BadLocationException e) {
			
			e.printStackTrace();
			
		}
		
	}

	public void printError(String message) {
	
		document = chatWindow.getStyledDocument();
	
		try {
		
			document.insertString(document.getLength(), message + "\n", errorSet);
		
		}
		
		catch (BadLocationException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public void printToLog(String message) {
		
		logWindow.setText(logWindow.getText() + "Log: " + message + "\n");
		
	}
	
	public void addUserToList(User user) {
		
		synchronized (model) {
		
			model.add(model.getSize(), user);
	
		}
		
	}
	
	public void removeUserFromList(User user) {
		
		synchronized (model) {
		
			for (int i = 0; i < model.getSize(); i++) {
		
				if (model.getElementAt(i).equals(user)) {
					
					model.remove(i);
					return;
					
				}
				
			}	
			
			ServerBase.gui.printToLog("Could not find user to remove from user list.");
			
		}
		
	}
	
	public void removeUserFromList(int index) {
		
		synchronized (model) {
		
			model.remove(index);
	
		}
		
	}
	
	private void lockServerActionPerformed() {
		
		ServerBase.serverLocked = !ServerBase.serverLocked;
		updateServerTitle();
		
		if (ServerBase.serverLocked) {
			
			lockServer.setText("Unlock server");
			
		} else {
			
			lockServer.setText("Lock server");
			
		}
		
	}
	
	private void maxClientsActionPerformed() {
		
		int value = ServerBase.maxClients;
		
		try {
			
			value = Integer.valueOf((String) JOptionPane.showInputDialog(this,null,"Please enter the maximum amount of clients to allow on the server",JOptionPane.PLAIN_MESSAGE,null,null,ServerBase.maxClients));
		
		} catch (NumberFormatException e) {
			
			System.out.println("Invalid number.");
			
		}
		
		ServerBase.maxClients = value;
		updateServerTitle();
		
	}
	
	private void shutDownServerActionPerformed() {
		
		DisconnectClient.shutDownServer();
		
	}
	
	private void userListMouseClicked(MouseEvent e) {
		
		if (e.getButton() == MouseEvent.BUTTON3) {
			
			userList.setSelectedIndex(userList.locationToIndex(e.getPoint()));
			UserPopupMenu popUpMenu = new UserPopupMenu(userList.getSelectedValue());
			popUpMenu.show(userList, e.getX(), e.getY());
			
		}
		
	}
	
	private void messageInputFocusGained() {
		
		if (messageInput.getText().equals(DEFAULT_MESSAGE_INPUT_STRING)) {
			
			messageInput.setText(EMPTY_STRING);
			messageInput.setForeground(standardColor);
			
		}
		
	}
	
	private void messageInputFocusLost() {
		
		if (messageInput.getText().equals(EMPTY_STRING)) {
			
			messageInput.setText(DEFAULT_MESSAGE_INPUT_STRING);
			messageInput.setForeground(shadedColor);
			
		}
		
	}
	
	private void messageInputKeyPressed(KeyEvent e) {
		
		if (e.getKeyChar() == KeyEvent.VK_ENTER) {
			
			String message = messageInput.getText();
			
			if (!message.equals(EMPTY_STRING)) {
				
				OutputCommunications.sendMessageToAll(User.consoleUser, message);
				messageInput.setText("");
				
			}
			
		}
		
	}
	
	private void sendButtonMouseClicked() {
		
		String message = messageInput.getText();
		
		if (!message.equals(EMPTY_STRING)) {
		
			OutputCommunications.sendMessageToAll(User.consoleUser, message);
			messageInput.setText(EMPTY_STRING);
		
		}
		
	}
	
	class CellRenderer extends JLabel implements ListCellRenderer<Object> {

		private static final long serialVersionUID = 1L;

		@Override
		public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index, boolean selected, boolean cellHasFocus) {

			User user = (User) value;
			
			String username = user.toString();
			Color permissionColor = user.permission.getColorAttribute();
			setText(username);
			
			if (selected) {
				
				setBackground(list.getSelectionBackground());
				setForeground(new Color(0, 0, 0));
				
			} else if (!selected) {
				
				if (true) {
				
					setBackground(list.getBackground());
					setForeground(permissionColor);
				
				}
					
			}
			
			setEnabled(list.isEnabled());
			setFocusable(false);
			setFont(list.getFont());
			setOpaque(true);
			return this;

		}
		
	}

	private void initializeGUI() {
		
		creatorPlaque = new JLabel();
		menuBar = new JMenuBar();
		connectionsMenu = new JMenu();
		lockServer = new JMenuItem();
		maxClients = new JMenuItem();
		shutDownServer = new JMenuItem();
		userListScrollPane = new JScrollPane();
		model = new DefaultListModel<User>();
		userList = new JList<User>(model);
		chatWindowScrollPane = new JScrollPane();
		chatWindow = new JTextPane();
		logWindowScrollPane = new JScrollPane();
		logWindow = new JTextPane();
		messageInput = new JTextField();
		sendButton = new JButton();
		standardColor = Color.black;
		shadedColor = new Color(125, 125, 125);
		defaultSet = new SimpleAttributeSet();
		commandSet = new SimpleAttributeSet();
		errorSet = new SimpleAttributeSet();
		StyleConstants.setForeground(defaultSet, Color.black);
		StyleConstants.setForeground(commandSet, Color.green);
		StyleConstants.setForeground(errorSet, Color.red);
		
		addWindowListener(new WindowListener() {

			@Override
			public void windowClosing(WindowEvent arg0) {

				DisconnectClient.shutDownServer();

			}

			@Override
			public void windowActivated(WindowEvent arg0) {}
			@Override
			public void windowClosed(WindowEvent arg0) {}			
			@Override
			public void windowDeactivated(WindowEvent arg0) {}
			@Override
			public void windowDeiconified(WindowEvent arg0) {}
			@Override
			public void windowIconified(WindowEvent arg0) {}
			@Override
			public void windowOpened(WindowEvent arg0) {}

		});
		
		setLayout(new FormLayout(
		// Layout is broken between the chatBox and the send button
		// Also learn about asynchronous keys and cryptology and make a program of it, and powdertoy remake!
				"5px, 200px:grow, 5px, 325px:grow, 5px, 80px, 63px, 5px, 75px, 5px",
				"25px, 5px, 339px:grow, 5px, 25px, 5px"
				
		));
		
		userList.setCellRenderer(new CellRenderer());
		userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setTitle(DEFAULT_SERVER_TITLE);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocation(100, 100);
		
		add(creatorPlaque, CC.xywh(7, 1, 3, 1));
			creatorPlaque.setText("Created by Austin Seber");
			creatorPlaque.setEnabled(false);
		
		add(menuBar, CC.xywh(1, 1, 10, 1, CC.FILL, CC.DEFAULT));
			menuBar.add(connectionsMenu);
				connectionsMenu.setText("Connections");
				connectionsMenu.add(lockServer);
					lockServer.setText("Lock Server");
					lockServer.setToolTipText("Locking the server will not allow new clients to connect, even if the max amount of clients has not been met");
					lockServer.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
						
							lockServerActionPerformed();
							
						}
						
					});
					
				connectionsMenu.add(maxClients);
					maxClients.setText("Set maximum number of client connections");
					maxClients.setToolTipText("Set the maximum number of client connections that the server will allow");
					maxClients.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
						
							maxClientsActionPerformed();
							
						}
						
					});
					
					connectionsMenu.add(shutDownServer);
					shutDownServer.setText("Shut Down Server");
					shutDownServer.setToolTipText("Shutting down the server will disconnect all clients");
					shutDownServer.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							
							shutDownServerActionPerformed();
							
						}
						
					});
					
		add(userList, CC.xy(2, 3, CC.DEFAULT, CC.FILL));
			userList.setAutoscrolls(false);
			//userList.setComponentPopupMenu(new UserPopupMenu());
			userList.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					
					userListMouseClicked(e);

				}

				@Override
				public void mouseEntered(MouseEvent arg0) {}

				@Override
				public void mouseExited(MouseEvent arg0) {}

				@Override
				public void mousePressed(MouseEvent arg0) {}

				@Override
				public void mouseReleased(MouseEvent arg0) {}
				
			});
			
		add(userListScrollPane, CC.xy(2, 3, CC.FILL, CC.FILL));
			userListScrollPane.setViewportView(userList);
			
		add(chatWindow, CC.xy(4, 3, CC.FILL, CC.FILL));
			chatWindow.setEditable(false);
		
		add(chatWindowScrollPane, CC.xy(4, 3, CC.FILL, CC.FILL));
			chatWindowScrollPane.setViewportView(chatWindow);
			chatWindowScrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {

				@Override
				public void adjustmentValueChanged(AdjustmentEvent e) {

					JScrollBar scrollBar = chatWindowScrollPane.getVerticalScrollBar();
					
					scrollBar.setValue(scrollBar.getMaximum());
					/*if (((float) e.getValue() / (scrollBar.getMaximum() - scrollBar.getVisibleAmount() - 16)) == 1.0f) {
						
						scrollBar.setValue(scrollBar.getMaximum());
						
					}*/
				}
				
				
			});
			
		add(logWindow, CC.xywh(6, 3, 4, 1, CC.FILL, CC.FILL));
			logWindow.setEditable(false);
			logWindow.setToolTipText("The log file displays more information about what is occuring in the server");
			
		add(logWindowScrollPane, CC.xywh(6, 3, 4, 1, CC.FILL, CC.FILL));
			logWindowScrollPane.setViewportView(logWindow);
			logWindowScrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {

				@Override
				public void adjustmentValueChanged(AdjustmentEvent e) {

					JScrollBar scrollBar = logWindowScrollPane.getVerticalScrollBar();
					
					scrollBar.setValue(scrollBar.getMaximum());
					/*if (((float) e.getValue() / (scrollBar.getMaximum() - scrollBar.getVisibleAmount() - 16)) == 1.0f) {
						
						scrollBar.setValue(scrollBar.getMaximum());
						
					}*/
				}
				
				
			});
			
		add(messageInput, CC.xywh(2, 5, 6, 1, CC.FILL, CC.FILL));
			messageInput.setForeground(shadedColor);
			messageInput.setText(DEFAULT_MESSAGE_INPUT_STRING);
			messageInput.addFocusListener(new FocusAdapter() {
				
				@Override
				public void focusGained(FocusEvent e) {
					
					messageInputFocusGained();
					
				}
				
				@Override
				public void focusLost(FocusEvent e) {
					
					messageInputFocusLost();
					
				}
				
			});
			messageInput.addKeyListener(new KeyAdapter() {

				@Override
				public void keyTyped(KeyEvent e) {
					
					messageInputKeyPressed(e);
					
				}
				
			});
			
		add(sendButton, CC.xy(9, 5, CC.FILL, CC.FILL));
			sendButton.setText("Send");
			sendButton.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent e) {
					
					sendButtonMouseClicked();
				
				}
				
			});
			
		pack();
		setMinimumSize(this.getSize());
		
	}
	
	private JLabel creatorPlaque;
	private JMenuBar menuBar;
	private JMenu connectionsMenu;
	private JMenuItem lockServer;
	private JMenuItem maxClients;
	private JMenuItem shutDownServer;
	private JScrollPane userListScrollPane;
	private JList<User> userList;
	private JScrollPane chatWindowScrollPane;
	private JTextPane chatWindow;
	private JScrollPane logWindowScrollPane;
	private JTextPane logWindow;
	private JTextField messageInput;
	private JButton sendButton;
	private DefaultListModel<User> model;
	private Color standardColor;
	private Color shadedColor;
	private SimpleAttributeSet defaultSet;
	private SimpleAttributeSet commandSet;
	private SimpleAttributeSet errorSet;
	
}