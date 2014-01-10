package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import server.logic.MessageType;
import client.logic.ClientLogic;

public class DrawingPanel extends JPanel implements MouseMotionListener {
	private static final long serialVersionUID = 1L;
	private Image buffer;
	private Graphics offscreenG;
	private int curX = -1; // on first repaint there is a dot at (0,0), so this is to prevent it
	private int curY = -1;
	private ClientLogic clientLogic;
	
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
	
	public synchronized void setXY(int x, int y) {
		curX = x;
		curY = y;
		
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
		
		if (curX != -1 && curY != -1)
			offscreenG.drawRect(curX, curY, 1, 1);
		
		g.drawImage(buffer, 0, 0, null);
	}

	@Override
	public void mouseDragged(MouseEvent evt) {
		curX = evt.getX();
		curY = evt.getY();
		
		try {
			clientLogic.sendMessage(MessageType.DRAW, evt.getX(), evt.getY());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		repaint();
		
	}

	@Override
	public void mouseMoved(MouseEvent evt) {
	}
}
