package main.gameoflife.gui;

import main.utilities.GameDisplay;
import main.utilities.GameThread;

public class GameOfLifeThread extends GameThread {
	
	// SHARED OBJECT //
	
	GameOfLifeDisplay gold;
	
	// CONSTRUCTOR //
	
	public GameOfLifeThread(GameDisplay display) {
		super(display);
		this.gold = (GameOfLifeDisplay) display;
	}
	
	// OVERRIDE METHOD 'run()' //

	@Override
	public void run() {
		Thread current = Thread.currentThread();
		System.out.println("BEGINNING");
		
		// run loop while 'thread' matches with currently executed thread
		while (thread != null && thread.equals(current)) {
			try {
				switch (gold.getStatus()) {
					case 0:		// status 0: stop
						/**
						 * This wait() does nothing, but the least I need at this moment
						 * is to hear eclipse cry for some unused exception...
						 */
						wait();
						break;
					case 1:		// status 1: paused
						/* pausar mientras 'status' == 1 */
						break;
					case 2:		// status 2: start and resume
						for (CellPanel cell : gold.getCellsList()) {
							cell.countNeighbors();
						}
						Thread.sleep(gold.getTimeLapse());
						for (CellPanel cell : gold.getCellsList()) {
							cell.updateGeneration();
						}
						break;
					default:
						break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("DEATH");
	}
	
	// OVERRIDE METHOD 'start()' //
	
	@Override
	public void start() {
		super.start();
	}
	
	// OVERRIDE METHOD 'pause()' //
	
	@Override
	public synchronized void pause() {
		super.pause();
	}
	
	// OVERRIDE METHOD 'stop()' //
	
	@Override
	public synchronized void stop() {
		super.stop();
	}
	
	// OVERRIDE METHOD 'resume()' //
	
	@Override
	public synchronized void resume() {
		super.resume();
	}

}
