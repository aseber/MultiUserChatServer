import java.awt.Color;

public class Permission {

	protected static final byte LOGGING_IN_BYTE = -2;
	protected static final byte NO_PERMISSION_BYTE = -1;
	protected static final byte INVISIBLE_BYTE = 0;
	protected static final byte USER_BYTE = 1;
	protected static final byte MODERATOR_BYTE = 2;
	protected static final byte ADMINISTRATOR_BYTE = 3;
	protected static final byte CONSOLE_BYTE = 4;
	protected static final Permission LOGGING_IN = new Permission(LOGGING_IN_BYTE);
	protected static final Permission NO_PERMISSION = new Permission(NO_PERMISSION_BYTE);
	protected static final Permission INVISIBLE = new Permission(INVISIBLE_BYTE);
	protected static final Permission USER = new Permission(USER_BYTE);
	protected static final Permission MODERATOR = new Permission(MODERATOR_BYTE);
	protected static final Permission ADMINISTRATOR = new Permission(ADMINISTRATOR_BYTE);
	protected static final Permission CONSOLE = new Permission(CONSOLE_BYTE);
	private static final Color LOGGING_IN_COLOR = new Color(0, 0, 0);
	private static final Color INVISIBLE_COLOR = new Color(0, 0, 0);
	private static final Color USER_COLOR = new Color(100, 100, 100);
	private static final Color MODERATOR_COLOR = new Color(0, 0, 255);
	private static final Color ADMINISTRATOR_COLOR = new Color(255, 0, 0);
	private static final Color CONSOLE_COLOR = new Color(255, 0, 255);
	protected byte permission;
	
	public Permission(byte PermissionValue) {
		
		switch (PermissionValue) {
		
			case LOGGING_IN_BYTE:
				permission = LOGGING_IN_BYTE;
				break;
				
			case NO_PERMISSION_BYTE:
				permission = NO_PERMISSION_BYTE;
				break;
		
			case INVISIBLE_BYTE:
				permission = INVISIBLE_BYTE;
				break;
				
			case USER_BYTE:
				permission = USER_BYTE;
				break;
			
			case MODERATOR_BYTE:
				permission = MODERATOR_BYTE;
				break;
			
			case ADMINISTRATOR_BYTE:
				permission = ADMINISTRATOR_BYTE;
				break;
				
			case CONSOLE_BYTE:
				permission = CONSOLE_BYTE;
				break;
		
			default:
				break;
				
		}
		
	}
	
	public Color getColorAttribute() {
		
		byte PermissionValue = this.permission;
		
		switch (PermissionValue) {
		
			case LOGGING_IN_BYTE:
				return LOGGING_IN_COLOR;
		
			case INVISIBLE_BYTE:
				return INVISIBLE_COLOR;
				
			case USER_BYTE:
				return USER_COLOR;
			
			case MODERATOR_BYTE:
				return MODERATOR_COLOR;
			
			case ADMINISTRATOR_BYTE:
				return ADMINISTRATOR_COLOR;
			
			case CONSOLE_BYTE:
				return CONSOLE_COLOR;
			
			default:
				return null;
		
		}
				
	}
	
	public byte getByteValue() {
		
		return this.permission;
		
	}
	
	@Override
	public String toString() {
		
		byte PermissionValue = this.permission;
	
		switch (PermissionValue) {
			
			case LOGGING_IN_BYTE:
				return "Logging in";
		
			case INVISIBLE_BYTE:
				return "Invisible";
		
			case USER_BYTE:
				return "User";
			
			case MODERATOR_BYTE:
				return "Moderator";
			
			case ADMINISTRATOR_BYTE:
				return "Administrator";
			
			case CONSOLE_BYTE:
				return "Console";
			
			default:
				return null;
	
		}
		
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj instanceof Permission) {
			
			Permission permission2 = (Permission) obj;
			
			if (this.getByteValue() == permission2.getByteValue()) {
				
				return true;
				
			}
			
		}
		
		return false;
		
	}
	
}