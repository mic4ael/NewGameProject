package server.logic;

import helperclasses.Room;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.BindException;
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
	private volatile boolean isRunning = false;
	
	public ServerLogic(ServerType type, int portNumber) throws IOException {
		this.portNumber = portNumber;
		
		switch(type) {
			case LOCAL:
				getLocalIp();
				break;
			case PUBLIC:
				getPublicIp();
				break;
		}
		
		System.out.println(ipAddress);
		serverSocket = new ServerSocket();
		
		try {
			serverSocket.bind(new InetSocketAddress(ipAddress, portNumber));
		} catch  (BindException ex) {
			
		}
	}
	
	@Override
	public void run() {
		isRunning = true;
		System.out.println("Server Started!");
		
		while(isRunning) {
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
	
	public void stopServer() throws IOException {
		isRunning = false;
		serverSocket.close();
	}
	
	public boolean isRunning() {
		return isRunning;
	}
}
