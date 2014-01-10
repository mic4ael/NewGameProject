package client.logic;

import game.DrawingPanel;
import game.GameWindow;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import server.logic.Message;
import server.logic.MessageType;

public class ClientLogic extends Thread {
	private String host;
	private int portNumber;
	private Socket socket;
	private volatile boolean isRunning = false;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private DrawingPanel panel;
	private GameWindow game;
	
	public ClientLogic(String host, int portNumber) throws UnknownHostException, IOException {
		this.host = host;
		this.portNumber = portNumber;
		this.socket = new Socket(host, portNumber);
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
	}
	
	public void setDrawingPanel(DrawingPanel panel) {
		this.panel = panel;
	}
	
	public void sendMessage(MessageType msgType, int x, int y) throws IOException {
		out.writeObject(new Message(msgType, x, y));
	}
	
	public void sendMessage(MessageType msgType, String word) throws IOException {
		out.writeObject(new Message(msgType, word));
	}
	
	@Override
	public void run() {
		isRunning = true;
		System.out.println("Client Started");
		Message msg = null;
		
		while (isRunning) {
			try {
				msg = (Message)in.readObject();
				
				switch(msg.getMessageType()) {
				case DRAW:
					panel.setXY(msg.getX(), msg.getY());
					break;
				case DRAWING_TURN:
					game.setDrawing(msg.getWord());
					break;
				case DISABLE:
					game.disableDrawing();
					break;
				case ANSWER:
				}
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Client Stopped");
	}
	
	public void setWindowGame(GameWindow game) {
		this.game = game;
	}
	
	public String getHost() {
		return host;
	}
	
	public int portNumber() {
		return portNumber;
	}
	
	public Socket getSocket() {
		return socket;
	}
}
