package client.logic;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientLogic extends Thread {
	private String host;
	private int portNumber;
	private Socket socket;
	
	public ClientLogic(String host, int portNumber) throws UnknownHostException, IOException {
		this.host = host;
		this.portNumber = portNumber;
		this.socket = new Socket(host, portNumber);
	}
}
