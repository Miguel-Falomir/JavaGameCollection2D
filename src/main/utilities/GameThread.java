package main.utilities;

import main.Gui;

public class GameThread implements Runnable {
	
	// SHARED OBJECT //
	
	protected Gui gui;
	
	// CONSTRUCTOR //
	
	public GameThread(Gui gui) {
		this.gui = gui;
	}

	// METHOD EXECUTE THREAD //
	
	@Override
	public void run() {}
}
