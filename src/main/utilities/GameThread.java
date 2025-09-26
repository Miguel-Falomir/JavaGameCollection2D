package main.utilities;

public class GameThread implements GameRunnable {
	
	// SHARED OBJECT //
	
	protected GameDisplay display;
	
	// VARIABLES //
	
	protected volatile Thread thread;
	
	protected int status;
	
	// GETTERS AND SETTERS //
	
	public synchronized int getStatus() {return status;}
	
	public synchronized void setStatus(int status) {
		this.status = status;
	}
	
	// CONSTRUCTOR //
	
	public GameThread(GameDisplay display) {
		this.display = display;
		this.thread = new Thread(this);
		this.status = 0;
	}

	// METHOD EXECUTE THREAD //
	
	@Override
	public void run() {}
	
	@Override
	public void start() {
		setStatus(2);
		if (thread == null) {
			thread = new Thread(this);
		}
		thread.start();
	}
	
	@Override
	public synchronized void pause() {
		setStatus(1);
	}
	
	@Override
	public synchronized void stop() {
		setStatus(0);
		thread = null;
		notify();
	}
	
	@Override
	public synchronized void resume() {
		setStatus(2);
		notify();
	}
}
