package main.gameoflife.gui;

import main.Gui;
import main.utilities.GameDisplay;
import main.utilities.GameMenu;
import main.utilities.GameThread;

public class GameOfLifeThread extends GameThread {
	
	// VARIABLES //
	private GameOfLifeMenu menu;
	private GameOfLifeDisplay display;
	
	// CONSTRUCTOR //
	
	public GameOfLifeThread(Gui gui) {
		super(gui);
		menu = new GameOfLifeMenu(gui);
		display = menu.getDisplay();
		gui.mockup(menu);
	}
	
	// OVERRIDE METHOD 'run()' //

	@Override
	public void run() {
		while (true) {
			try {
				if (display.getStatus() == 2) {
					System.out.println("braka");
					Thread.sleep(display.getTimeLapse());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
