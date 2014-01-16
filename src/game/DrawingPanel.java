package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
	private Graphics2D offscreenG;
	private int curX = -1; // on first repaint there is a dot at (0,0), so this is to prevent it
	private int curY = -1;
	private int oldX = 0;
	private int oldY = 0;
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
		else {
			removeMouseMotionListener(this);
			curX = -1;
			curY = -1;
			oldX = -1;
			oldY = -1;
		}
		
		buffer = null;
		repaint();
	}
	
	public void setXY(int x, int y, int oldX, int oldY, boolean isRight) {
		this.oldX = oldX;
		this.oldY = oldY;
		curX = x;
		curY = y;
		this.buttonClicked = isRight;
		
		repaint();
	}
	
	@Override
	public void update(Graphics g) {
		paint(g);
	}
	
	// TODO: make much smoother drawing by drawing lines instead of dots
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		// apply double buffering to obtain smooth animations
		if (buffer == null) {
			buffer = createImage(this.getWidth(), this.getHeight());
			offscreenG = (Graphics2D)buffer.getGraphics();
		}
		
		if (curX != -1 && curY != -1) {
			if (buttonClicked) {
				offscreenG.setColor(this.getBackground());
				offscreenG.fillRect(curX, curY, 10, 10);
				offscreenG.setColor(Color.BLACK);
			} else {
				offscreenG.setStroke(new BasicStroke(3));
				
				if (oldX == -1 && oldY == -1) {
					oldX = curX + 1;
					oldY = curY + 1;
				}
				
				offscreenG.drawLine(oldX, oldY, curX, curY);
			}
		}
		
		g.drawImage(buffer, 0, 0, null);
	}

	@Override
	public void mouseDragged(MouseEvent evt) {
		oldX = curX;
		oldY = curY;
		curX = evt.getX();
		curY = evt.getY();
		
		buttonClicked = SwingUtilities.isRightMouseButton(evt);
		
		try {
			clientLogic.sendMessage(MessageType.DRAW, evt.getX(), evt.getY(), oldX, oldY, buttonClicked);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent evt) {
		curX = evt.getX();
		curY = evt.getY();
	}
}
