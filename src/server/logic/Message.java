package server.logic;

import java.io.Serializable;

public class Message implements Serializable {
	private MessageType msgType;
	private int x;
	private int y;
	
	public Message(MessageType msgType) {
		this.msgType = msgType;
		this.x = -1;
		this.y = -1;
	}
	
	public Message(MessageType msgType, int x, int y) {
		this(msgType);
		this.x = x;
		this.y = y;
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
}
