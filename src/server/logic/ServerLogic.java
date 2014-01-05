package server.logic;

import helperclasses.Room;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;

public final class ServerLogic extends Thread {
	private List<Room> rooms;
	private ServerSocket serverSocket;
	private String ipAddress; // in case player wants to create a server available to the outside world
	private int portNumber;
	private boolean isRunning = true;
	
	public ServerLogic(ServerType type, int portNumber) throws IOException {
		this.portNumber = portNumber;
		
		switch(type) {
			case LOCAL:
				getLocalIp();
				break;
			case PUBLIC:
				getPublicIp();
		}
		
		System.out.println(ipAddress);
		serverSocket = new ServerSocket();
		serverSocket.bind(new InetSocketAddress(ipAddress, portNumber));
	}
	
	@Override
	public void run() {
		while(isRunning) {
			
		}
	}
	
	private void getPublicIp() throws IOException {
		URL whatismyip = new URL("http://checkip.amazonaws.com/");
		BufferedReader in = new BufferedReader(new InputStreamReader(
		                whatismyip.openStream()));
		ipAddress = in.readLine();
	}
	
	private void getLocalIp() throws UnknownHostException {
		ipAddress = Inet4Address.getLocalHost().getHostAddress();
	}
	
	public ServerSocket getServerSocket() {
		return serverSocket;
	}
	
	public int getPortNumber() {
		return portNumber;
	}
	
	public List<Room> getRooms() {
		return rooms;
	}
	
	public void stopServer() {
		isRunning = false;
	}
	
	public boolean isRunning() {
		return isRunning;
	}
}
