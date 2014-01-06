package game;

import java.awt.Container;

import javax.swing.JFrame;

import client.logic.ClientLogic;

public class GameWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private Container pane;
	private DrawingPanel canvas;
	private ClientLogic clientLogic;
	
	public GameWindow(ClientLogic clientLogic) {
		this.clientLogic = clientLogic;
		
		// initialize components
		pane = getContentPane();
		canvas = new DrawingPanel(clientLogic);
		clientLogic.setDrawingPanel(canvas);
		initUI(GameParameters.WINDOW_WIDTH, GameParameters.WINDOW_HEIGHT);
		
		setVisible(true);
		clientLogic.start();
	}
	
	private void initUI(int width, int height) {
		// general settings and adding components
		setResizable(GameParameters.IS_RESIZABLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		setTitle(GameParameters.GAME_WINDOW_TITLE);
		setLocationRelativeTo(null);
		
		pane.add(canvas);
	}
}
