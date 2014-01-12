package server.logic;

import java.io.Serializable;

public class Message implements Serializable {
	private MessageType msgType;
	private String word;
	private int x;
	private int y;
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
	
	public Message(MessageType msgType, int x, int y) {
		this(msgType, "");
		this.x = x;
		this.y = y;
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
