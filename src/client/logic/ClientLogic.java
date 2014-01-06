package client.logic;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientLogic extends Thread {
	private String host;
	private int portNumber;
	private Socket socket;
	private volatile boolean isRunning = false;
	
	public ClientLogic(String host, int portNumber) throws UnknownHostException, IOException {
		this.host = host;
		this.portNumber = portNumber;
		this.socket = new Socket(host, portNumber);
	}
	
	@Override
	public void run() {
		isRunning = true;
		
		while (isRunning) {
			
		}
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
