package main.utilities;

import java.util.ArrayList;

public class GameThread implements GameRunnable {
	
	// SHARED OBJECT //
	
	private GameDisplay display;
	
	// VARIABLES //
	
	protected volatile Thread thread;
	
	// CONSTRUCTOR //
	
	public GameThread(GameDisplay display) {
		this.display = display;
		this.thread = new Thread(this);
	}

	// METHOD EXECUTE THREAD //
	
	@Override
	public void run() {}
	
	@Override
	public void start() {
		display.setStatus(2);
		if (thread == null) {thread = new Thread(this);}
		thread.start();
	}
	
	@Override
	public synchronized void pause() {
		display.setStatus(1);
		notifyAll();
	}
	
	@Override
	public synchronized void stop() {
		display.setStatus(0);
		thread = null;
		notifyAll();
	}
	
	@Override
	public synchronized void resume() {
		display.setStatus(2);
		notifyAll();
	}
}
