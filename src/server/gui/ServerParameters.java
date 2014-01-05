package server.gui;

public interface ServerParameters {
	
	// ints
	public static final int SERVER_WINDOW_WIDTH = 400;
	public static final int SERVER_WINDOW_HEIGHT = 140;
	public static final int MAX_ROOMS = 20;
	public static final int MAX_PLAYERS_PER_ROOM = 15;
	public static final int MAX_PORT_NUMBER = 65535;
	public static final int MIN_PORT_NUMBER = 1024;
	
	// booleans
	public static final boolean IS_RESIZABLE = false;
	
	// Strings
	public static final String WINDOW_TITLE = "Server";
	public static final String CREATE_SERVER = "Create Server";
	public static final String NO_TYPE_CHOSEN = "You have not chosen the type of the server!";
	public static final String WRONG_PORT = "Wrong value for the port!";
	public static final String NO_PORT_SPECIFIED = "No port has been specified!";
}
