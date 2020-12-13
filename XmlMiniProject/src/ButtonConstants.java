import java.awt.Color;

public class ButtonConstants {
	private final static Color hoverColor = new Color(160, 196, 207);
	private final static Color btnColor = new Color(163, 184, 204);
	
	public final static String[] MENU = { "LOAD", "MAKE", "FIND",
			"SAVE", "PRINT", "INSERT", "UPDATE", "DELETE", "EXIT"};

	public final static String[] TYPE = { "Element", "Attribute", "Text", "Comment"};
	
	public final static Color[] MENU_COLOR = { btnColor, Color.black,
			hoverColor, Color.white };

	public final static int LOAD = 0, MAKE = 1, FIND = 2, SAVE = 3, PRINT = 4,
			INSERT = 5, UPDATE = 6, DELETE = 7, EXIT = 8;
	
	public final static int ELEMENT = 0, ATTRIBUTE = 1, TEXT = 2, COMMENT = 3;
}
