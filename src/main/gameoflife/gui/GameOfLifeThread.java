package main.gameoflife.gui;

import java.util.ArrayList;

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
						break;
					case 1:		// status 1: paused
						synchronized (this) {
							while (gold.getStatus() == 1) {wait();}
						}
						break;
					case 2:		// status 2: start and resume
						long startTime = System.currentTimeMillis();
						for (ArrayList<CellPanel> row : gold.getCellsGrid()) {
							for (CellPanel cell : row) {
								cell.countNeighbors();
							}
						}
						for (ArrayList<CellPanel> row : gold.getCellsGrid()) {
							for (CellPanel cell : row) {
								cell.updateGeneration();
							}
						}
						long endTime = System.currentTimeMillis() - startTime;
						System.out.println(endTime);
						synchronized (this) {							
							wait(gold.getTimeLapse() - endTime);
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
		gold.setLastChange(0);
		super.stop();
		gold.update();
	}
	
	// OVERRIDE METHOD 'resume()' //
	
	@Override
	public synchronized void resume() {
		super.resume();
	}

}
