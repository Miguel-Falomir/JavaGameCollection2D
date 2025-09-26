package main.utilities;

public interface GameRunnable extends Runnable {
	
	@Override
	default void run() {}
	
	default void start() {}
	
	default void pause() {}
	
	default void stop() {}
	
	default void resume() {}

}
