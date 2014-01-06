package client;

import javax.swing.SwingUtilities;
import client.gui.ClientWindow;

public class RunClient {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new ClientWindow();
			}
		});
	}
}
