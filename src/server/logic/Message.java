package server.logic;

import java.io.Serializable;

public class Message implements Serializable {
	private MessageType msgType;
	private String word;
	private int x;
	private int y;
	private int oldX;
	private int oldY;
	private boolean isRightClicked;
	
	public Message(MessageType msgType) {
		this.msgType = msgType;
	}
	
	public Message(MessageType msgType, String word) {
		this.msgType = msgType;
		this.word = word;
		this.x = -1;
		this.y = -1;
	}
	
	public Message(MessageType msgType, int x, int y, int oldX, int oldY) {
		this(msgType, "");
		this.x = x;
		this.y = y;
		this.oldX = oldX;
		this.oldY = oldY;
	}
	
	public String getWord() {
		return word;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getOldX() {
		return oldX;
	}
	
	public int getOldY() {
		return oldY;
	}
	
	public MessageType getMessageType() {
		return msgType;
	}

	public boolean isRightClicked() {
		return isRightClicked;
	}

	public void setRightClicked(boolean isRightClicked) {
		this.isRightClicked = isRightClicked;
	}
}
