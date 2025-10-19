package main.gameoflife;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.SwingUtilities;

import main.utilities.GameDisplay;
import main.utilities.GameThread;

public class GameOfLifeThread extends GameThread {
	
	// SHARED OBJECT //
	
	private GameOfLifeDisplay gold;
	
	// CUSTOM CLASS FOR ROW THREAD //
	
	private class RowRunnable implements Runnable{
		// attributes
		private GameOfLifeDisplay gold;
		private ArrayList<CellPanel> row;
		// constructor
		public RowRunnable(GameOfLifeDisplay gold, ArrayList<CellPanel> row) {
			this.gold = gold;
			this.row = row;
		}
		// override method 'run'
		@Override
		public void run() {
			for (CellPanel cell : row) {cell.countNeighbors();}
		}
	}
	
	// CONSTRUCTOR //
	
	public GameOfLifeThread(GameDisplay display) {
		super(display);
		this.gold = (GameOfLifeDisplay) display;
	}
	
	// OVERRIDE METHOD 'run()' //

	@Override
	public void run() {
		System.out.println("BEGINNING");
		
		boolean sentinel = true;
		
		// run execution loop until sentinel == false
		do {
			try {
				switch (gold.getStatus()) {
					case 0:		// shutdown
						// set sentinel to false
						sentinel = false;
						// clear all cells
						SwingUtilities.invokeLater(() -> {
							for (ArrayList<CellPanel> row : gold.getCellsGrid()) {								
								for (CellPanel cell : row) {cell.clear();}
							}
						});
						break;
					case 1:		// pause
						System.out.println("PAUSE");
						synchronized (this) {
							while (gold.getStatus() == 1) {wait();}
						}
						if (gold.getStatus() == 2) {System.out.println("RESUME");}
					case 2:		// execution cycle
						// start all threads
						for (ArrayList<CellPanel> row : gold.getCellsGrid()) {
							RowRunnable rowRun = new RowRunnable(gold, row);
							Thread thrd = new Thread(rowRun);
							thrd.start();
						}
						// force main thread to wait during a specific time lapse
						synchronized (this) {
							wait(gold.getTimeLapse());
						}
						// update all cells one by one in the same thread
						SwingUtilities.invokeLater(() -> {
							for (ArrayList<CellPanel> row : gold.getCellsGrid()) {								
								for (CellPanel cell : row) {
									cell.updateGeneration();
								}
							}
						});
						break;
					default:
						throw new IllegalArgumentException("Unexpected value: " + gold.getStatus());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (sentinel == true);
		
		System.out.println("DEATH");
	}
	
}
