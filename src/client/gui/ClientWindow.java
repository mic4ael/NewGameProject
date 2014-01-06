package client.gui;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;

public class ClientWindow extends JFrame {
	private Container pane;
	
	public ClientWindow() {
		this(ClientParameters.WINDOW_WIDTH, ClientParameters.WINDOW_HEIGHT);
	}
	
	public ClientWindow(int width, int height) {
		// initialize components
		pane = getContentPane();

		// add components
		initUI(width, height);
		
		setVisible(true);
	}
	
	private void initUI(int width, int height) {
		setSize(width, height);
		setTitle(ClientParameters.WINDOW_TITLE);
		setLocationRelativeTo(null);
		setResizable(ClientParameters.IS_RESIZABLE);
		setLayout(new BorderLayout());
	}
}
