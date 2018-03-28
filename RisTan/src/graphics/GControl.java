package graphics;

import java.awt.Point;


public class GControl {
	private GUI gui;

	public GControl() {
	}

	public void setGUI(GUI g) {
		gui = g;
	}

	void startServer() {

	}

	void startClient() {

	}

	void sendClick(Point p) {

	}

	void clickReceived(Point p) {
		if (gui == null)
			return;
		gui.addPoint(p);
	}
}
