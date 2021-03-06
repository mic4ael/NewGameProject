package server.logic;

import game.WordGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public final class ServerLogic extends Thread {
	private volatile List<ClientWorker> clients;
	private ServerSocket serverSocket;
	private String ipAddress; // in case player wants to create a server available to the outside world
	private int portNumber;
	private boolean isRunning = false;
	private String currentWord;
	
	private class ClientWorker extends Thread {
		private Socket clientSocket;
		private ObjectInputStream inObject;
		private ObjectOutputStream outObject;
		private boolean isRunning = true;
		
		private ClientWorker(Socket clientSocket) throws IOException {
			this.clientSocket = clientSocket;
			inObject = new ObjectInputStream(clientSocket.getInputStream());
			outObject = new ObjectOutputStream(clientSocket.getOutputStream());
		}
		
		private void sendToClient(Message msg) {
			try {
				this.outObject.writeObject(msg);
			} catch (IOException e) {
				clients.remove(this);
			}
		}
		
		private void send(Message msg) {
			for(ClientWorker cl : clients)
				cl.sendToClient(msg);
		}
		
		public void stopJob() {
			isRunning = false;
			clients.remove(this);
		}
		
		private boolean checkIfCorrect(String word) {
			return word.equals(currentWord);
		}
		
		private synchronized void changeDrawingPlayer() {
			// enable drawing at the client who guessed the answer
			currentWord = WordGenerator.nextWord();
			this.sendToClient(new Message(MessageType.DRAWING_TURN, currentWord));
			
			for(ClientWorker i : clients) {
				if (i != this)
					i.sendToClient(new Message(MessageType.DISABLE));
			}
		}
		
		@Override
		public void run() {
			System.out.println("Client Worker started");
			Message msg = null;
			
			while(isRunning) {
				try {
					msg = (Message)inObject.readObject();
					
					switch(msg.getMessageType()) {
					case DRAW:
						send(msg);
						break;
					case ANSWER:
						if (checkIfCorrect(msg.getWord()))
							changeDrawingPlayer();
					}
					
					
				} catch (ClassNotFoundException | IOException e) {
					try {
						clientSocket.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					break;
				}
			}
			
			clients.remove(this);
			System.out.println("Client Worker stopped");
		}
	}
	
	public ServerLogic(ServerType type, int portNumber) throws IOException {
		this.portNumber = portNumber;
		clients = new ArrayList<ClientWorker>();
		
		switch(type) {
			case LOCAL:
				getLocalIp();
				break;
			case PUBLIC:
				getPublicIp();
				break;
		}
		
		serverSocket = new ServerSocket(portNumber);
		
		/*try {
			serverSocket.bind(new InetSocketAddress(ipAddress, portNumber));
		} catch  (BindException ex) {
			ex.printStackTrace();
		}*/
	}
	
	@Override
	public void run() {
		synchronized(this) {
			isRunning = true;
		}
		
		System.out.println("Server Started! at " + ipAddress);
		
		while(isRunning) {
			try {
				ClientWorker connectedClient = new ClientWorker(serverSocket.accept());
				clients.add(connectedClient);
				connectedClient.start();
				
				if (clients.size() == 2) {
					int randomClient = (int)Math.round(Math.random());
					currentWord = WordGenerator.nextWord();
					clients.get(randomClient).sendToClient(new Message(MessageType.DRAWING_TURN, currentWord));
				}
				
				System.out.println("Client Connected");
			} catch (IOException e) {
				break;
			}
		}
		
		System.out.println("Server Stopped!");
	}
	
	private void getPublicIp() throws IOException {
		URL whatismyip = new URL("http://checkip.amazonaws.com/");
		BufferedReader in = new BufferedReader(new InputStreamReader(
		                whatismyip.openStream()));
		ipAddress = in.readLine();
	}
	
	private void getLocalIp() throws UnknownHostException {
		ipAddress = "192.168.40.252";
	}
	
	public ServerSocket getServerSocket() {
		return serverSocket;
	}
	
	public int getPortNumber() {
		return portNumber;
	}
	
	public List<ClientWorker> getRooms() {
		return clients;
	}
	
	public void stopServer() throws IOException {
		isRunning = false;
		
		// stop all workers from communicating with the clients
		for(ClientWorker cl : clients)
			cl.stopJob();
		
		serverSocket.close();
	}
	
	public synchronized boolean isRunning() {
		return isRunning;
	}
	
	public synchronized void setIsRunning(boolean is) {
		isRunning = is;
	}
}
