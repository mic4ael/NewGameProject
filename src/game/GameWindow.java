package game;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import server.logic.MessageType;
import client.logic.ClientLogic;

public class GameWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private Container pane;
	private DrawingPanel canvas;
	private ClientLogic clientLogic;
	private JTextField message;
	private JLabel wordToDraw;
	
	public GameWindow(final ClientLogic clientLogic) {
		this.clientLogic = clientLogic;
		message = new JTextField();
		message.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent evt) {
			}
			
			@Override
			public void keyReleased(KeyEvent evt) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						clientLogic.sendMessage(MessageType.ANSWER, message.getText().trim());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		wordToDraw = new JLabel(GameParameters.WORD_TO_DRAW);
		wordToDraw.setBorder(BorderFactory.createEtchedBorder());
		wordToDraw.setHorizontalAlignment(JLabel.CENTER);
		wordToDraw.setVisible(false);
		clientLogic.setWindowGame(this);
		
		// initialize components
		pane = getContentPane();
		canvas = new DrawingPanel(clientLogic);
		clientLogic.setDrawingPanel(canvas);
		initUI(GameParameters.WINDOW_WIDTH, GameParameters.WINDOW_HEIGHT);
		
		setVisible(true);
		clientLogic.start();
	}
	
	public void disableDrawing() {
		this.canvas.enableCanvas(false);
		wordToDraw.setVisible(false);
		message.setVisible(true);
	}
	
	public void setDrawing(String wordToDraw) {
		this.wordToDraw.setText(GameParameters.WORD_TO_DRAW + wordToDraw);
		this.wordToDraw.setVisible(true);
		this.canvas.enableCanvas(true);
		message.setVisible(false);
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
