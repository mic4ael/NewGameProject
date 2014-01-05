package server;

import java.io.IOException;
import javax.swing.SwingUtilities;
import server.gui.ServerWindow;

public class RunServer {
	public static void main(String[] args) throws IOException {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new ServerWindow();
			}
		});
	}
}
