package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import server.logic.MessageType;
import client.logic.ClientLogic;

public class DrawingPanel extends JPanel implements MouseMotionListener {
	private static final long serialVersionUID = 1L;
	private Image buffer;
	private Graphics offscreenG;
	private int curX = -1; // on first repaint there is a dot at (0,0), so this is to prevent it
	private int curY = -1;
	private ClientLogic clientLogic;
	private boolean buttonClicked;
	
	public DrawingPanel(final ClientLogic clientLogic) {
		this.clientLogic = clientLogic;
		setBorder(BorderFactory.createEtchedBorder());
		// add drawing capability
		//addMouseMotionListener(this);
	}
	
	public void enableCanvas(boolean isEnabled) {
		if(isEnabled) addMouseMotionListener(this);
		else removeMouseMotionListener(this);
		buffer = null;
		repaint();
	}
	
	public void setXY(int x, int y, boolean isRight) {
		curX = x;
		curY = y;
		this.buttonClicked = isRight;
		
		repaint();
	}
	
	@Override
	public void update(Graphics g) {
		paint(g);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		// apply double buffering to obtain smooth animations
		if (buffer == null) {
			buffer = createImage(this.getWidth(), this.getHeight());
			offscreenG = buffer.getGraphics();
		}
		
		if (curX != -1 && curY != -1) {
			if (buttonClicked) {
				offscreenG.setColor(this.getBackground());
				offscreenG.fillRect(curX, curY, 10, 10);
				offscreenG.setColor(Color.BLACK);
			} else {
				offscreenG.drawRect(curX, curY, 1, 1);
			}
		}
		
		g.drawImage(buffer, 0, 0, null);
	}

	@Override
	public void mouseDragged(MouseEvent evt) {
		curX = evt.getX();
		curY = evt.getY();
		buttonClicked = SwingUtilities.isRightMouseButton(evt);
		
		try {
			clientLogic.sendMessage(MessageType.DRAW, evt.getX(), evt.getY(), buttonClicked);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		repaint();
		
	}

	@Override
	public void mouseMoved(MouseEvent evt) {
	}
}
