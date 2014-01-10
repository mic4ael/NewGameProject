package game;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import client.logic.ClientLogic;

public class GameWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private Container pane;
	private DrawingPanel canvas;
	private ClientLogic clientLogic;
	private JTextField message;
	private JLabel wordToDraw;
	
	public GameWindow(ClientLogic clientLogic) {
		this.clientLogic = clientLogic;
		message = new JTextField();
		wordToDraw = new JLabel(GameParameters.WORD_TO_DRAW);
		wordToDraw.setBorder(BorderFactory.createEtchedBorder());
		wordToDraw.setHorizontalAlignment(JLabel.CENTER);
		wordToDraw.setVisible(false);
		
		// initialize components
		pane = getContentPane();
		canvas = new DrawingPanel(clientLogic);
		clientLogic.setDrawingPanel(canvas);
		initUI(GameParameters.WINDOW_WIDTH, GameParameters.WINDOW_HEIGHT);
		
		setVisible(true);
		clientLogic.start();
	}
	
	public void setDrawing(String wordToDraw) {
		this.wordToDraw.setText(GameParameters.WORD_TO_DRAW + wordToDraw);
		this.wordToDraw.setVisible(true);
	}
	
	private void initUI(int width, int height) {
		// general settings and adding components
		setResizable(GameParameters.IS_RESIZABLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		setTitle(GameParameters.GAME_WINDOW_TITLE);
		setLocationRelativeTo(null);
		
		pane.add(wordToDraw, BorderLayout.NORTH);
		pane.add(canvas, BorderLayout.CENTER);
		pane.add(message, BorderLayout.SOUTH);
	}
}
