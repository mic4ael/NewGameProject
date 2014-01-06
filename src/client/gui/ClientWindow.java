package client.gui;

import helperclasses.NoValueProvidedException;
import helperclasses.WrongPortException;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import client.logic.ClientLogic;

public class ClientWindow extends JFrame {
	private Container pane;
	private JTextField host;
	private JTextField portNumber;
	private JButton connectButton;
	private String hostValue = null;
	private Integer portValue = null;
	private ClientLogic clientLogic;
	
	public ClientWindow() {
		this(ClientParameters.WINDOW_WIDTH, ClientParameters.WINDOW_HEIGHT);
	}
	
	public ClientWindow(int width, int height) {
		// initialize components
		pane = getContentPane();
		host = new JTextField(ClientParameters.HOST_TEXT_FIELD);
		portNumber = new JTextField(ClientParameters.PORT_NUMBER_TEXT_FIELD);
		
		connectButton = new JButton(ClientParameters.CONNECT_BUTTON);
		connectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					validateInput();
					
					clientLogic = new ClientLogic(hostValue, portValue);
					clientLogic.start();
					
				} catch (NoValueProvidedException e) {
					JOptionPane.showMessageDialog(null, ClientParameters.NO_VALUES);
				} catch (WrongPortException | NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, ClientParameters.WRONG_PORT);
				} catch (UnknownHostException e) {
					JOptionPane.showMessageDialog(null, ClientParameters.UNKNOWN_HOST);
				} catch (IOException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, ClientParameters.CONNECTION_ERROR);
				} 
			}
		});
		
		
		// add components and general settings
		initUI(width, height);
		
		setVisible(true);
	}
	
	private void validateInput() throws NoValueProvidedException, WrongPortException {
		validateHost();
		validatePort();
	}
	
	private void validateHost() throws NoValueProvidedException {
		String address = host.getText();
		
		if (address == null || "".equals(address) || ClientParameters.HOST_TEXT_FIELD.equals(address))
			throw new NoValueProvidedException();
		
		hostValue = address.trim();
	}
	
	private void validatePort() throws WrongPortException {
		Integer port = null;
		port = Integer.parseInt(portNumber.getText(), 10);
		
		if (port < ClientParameters.MIN_PORT_NUMBER || port > ClientParameters.MAX_PORT_NUMBER)
			throw new WrongPortException();
		
		portValue = port;
	}
	
	private void initUI(int width, int height) {
		// basic settings for the JFrame
		setSize(width, height);
		setTitle(ClientParameters.WINDOW_TITLE);
		setLocationRelativeTo(null);
		setResizable(ClientParameters.IS_RESIZABLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// adding components
		pane.setLayout(new BorderLayout());
		pane.add(host, BorderLayout.NORTH);
		pane.add(portNumber, BorderLayout.CENTER);
		pane.add(connectButton, BorderLayout.SOUTH);
	}
}
