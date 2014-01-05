package helperclasses;

import java.util.List;

public class Room {
	private List<Player> players; // each room has the list of current players
	private Player currentlyDrawing; //self-explanatory
	private Player roomOwner;
	
	// room can be created only with an owner
	public Room(Player roomOwner) {
		this.roomOwner = roomOwner;
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public Player getCurrentlyDrawing() {
		return currentlyDrawing;
	}
	
	public Player getOwner() {
		return roomOwner;
	}
}
