package main.gameoflife.gui;

import main.utilities.GameDisplay;
import main.utilities.GameThread;

public class GameOfLifeThread extends GameThread {
	
	// CONSTRUCTOR //
	
	public GameOfLifeThread(GameDisplay display) {
		super(display);
	}
	
	// OVERRIDE METHOD 'run()' //

	@Override
	public void run() {
		// cast game display to match child class
		GameOfLifeDisplay gold = (GameOfLifeDisplay) display;
		
		// run loop while 'thread' matches with currently executed thread
		Thread current = Thread.currentThread();
		System.out.println("BEGINNING");
		while (thread != null && thread.equals(current)) {
			try {
				switch (getStatus()) {
					case 0:		// status 0: stop
						break;
					case 1:		// status 1: paused
						System.out.println("---------- pause ----------");
						synchronized(this) {
							while (getStatus() == 1) {wait();}
		                }
						break;
					case 2:		// status 2: start and resume
						System.out.println("braka");
						Thread.sleep(gold.getTimeLapse());
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
