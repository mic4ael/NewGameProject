package server.gui;

import helperclasses.WrongPortException;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import server.logic.ServerLogic;
import server.logic.ServerType;

public class ServerWindow extends JFrame {
	private static final long serialVersionUID = 8983584014894262271L;
	private Dimension windowSize;
	private Container pane;
	private JButton createServerButton;
	private JRadioButton pServer;
	private JRadioButton lServer;
	private ButtonGroup servers;
	private JLabel serverState;
	private ServerLogic server;
	private ServerType chosenServerType = null;
	private JTextField port;
	private JButton stopServer;
	
	public ServerWindow() {
		this(ServerParameters.SERVER_WINDOW_WIDTH, ServerParameters.SERVER_WINDOW_HEIGHT);
	}
	
	public ServerWindow(int width, int height) {
		// initialize variables and listeners
		windowSize = new Dimension(width, height);
		pane = getContentPane();
		serverState = new JLabel(ServerParameters.SERVER_STATE + " stopped");
		serverState.setHorizontalAlignment(JLabel.CENTER);
		serverState.setBorder(BorderFactory.createEtchedBorder());
		port = new JTextField("Port Number");
		servers = new ButtonGroup();
		stopServer = new JButton("Stop Server");
		
		stopServer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (server != null && server.isRunning()) {
					try {
						server.stopServer();
						setServerState();
						server = null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		pServer = new JRadioButton("Public Server");
		pServer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				chosenServerType = ServerType.PUBLIC;
			}
		});
		
		lServer = new JRadioButton("Local Server");
		lServer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				chosenServerType = ServerType.LOCAL;
			}
		});
		
		createServerButton = new JButton(ServerParameters.CREATE_SERVER);
		createServerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (chosenServerType == null)
					JOptionPane.showMessageDialog(null, new String(ServerParameters.NO_TYPE_CHOSEN));
				else if (port.getText() == null || port.getText().equals("")) {
					JOptionPane.showMessageDialog(null, new String(ServerParameters.NO_PORT_SPECIFIED));
				} else {
					try {
						Integer portValue = Integer.valueOf(port.getText());
						
						if (portValue < ServerParameters.MIN_PORT_NUMBER || portValue > ServerParameters.MAX_PORT_NUMBER)
							throw new WrongPortException("Port must be between 1024 and 65535");
						
						if (server == null) {
							server = new ServerLogic(chosenServerType, portValue);
						}
						
						if (!server.isRunning()) {
							server.start();
							server.setIsRunning(true);
						} else {
							JOptionPane.showMessageDialog(null, ServerParameters.ALREADY_STARTED);
						}
						
						setServerState();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (WrongPortException | NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, ServerParameters.WRONG_PORT);
					}
					
				}
			}
		});
		
		servers.add(pServer);
		servers.add(lServer);
		
		// set primary settings for the frame
		initUI();
		
		setVisible(true);
	}
	
	private void setServerState() {
		String state = ServerParameters.SERVER_STATE;
		serverState.setText(state + (server.isRunning() == true ? "running on port " + server.getPortNumber() : "stopped"));
		serverState.repaint();
	}
	
	private void initUI() {
		setSize(windowSize);
		setTitle(ServerParameters.WINDOW_TITLE);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(ServerParameters.IS_RESIZABLE);
		setResizable(false);
		pane.setLayout(new BorderLayout());
		
		JPanel buttons = new JPanel();
		buttons.add(pServer);
		buttons.add(lServer);
		buttons.add(port);
		buttons.add(stopServer);
		
		pane.add(serverState, BorderLayout.NORTH);
		pane.add(buttons, BorderLayout.CENTER);
		pane.add(createServerButton, BorderLayout.SOUTH);
	}
}
