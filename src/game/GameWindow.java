package game;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GameWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private Container pane;
	private DrawingPanel canvas;
	
	public GameWindow() {
		// initialize components
		pane = getContentPane();
		canvas = new DrawingPanel();
		
		initUI(GameParameters.WINDOW_WIDTH, GameParameters.WINDOW_HEIGHT);
		
		setVisible(true);
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
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new GameWindow();
			}
		});
	}
}
