package main.gameoflife.gui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class CellPanel extends JPanel implements MouseListener, Runnable {
	
	// SERIAL VERSION IDENTIFIER //
	
	private static final long serialVersionUID = 4881069824330811434L;
	
	// SHARED OBJECTS //
	
	private GameOfLifeDisplay display;
	private Thread mainThread;
	
	// ATTRIBUTES //
	
	private boolean oldLife;
	private boolean newLife;
	private int position;
	private Color liveColor;
	private Color deadColor;
	
	// GETTERS AND SETTERS //
	
	public boolean getOldLife() {return oldLife;}
	
	public void setOldLife(boolean life) {
		this.oldLife = life;
	}
	
	public boolean getNewLife() {return newLife;}
	
	public void setNewLife(boolean life) {
		this.newLife = life;
	}
	
	public void setLiveColor(Color liveColor) {
		this.liveColor = liveColor;
		this.setBackground((oldLife) ? liveColor : deadColor);
	}
	
	public void setMainThread(Thread thread) {
		this.mainThread = thread;
	}
	
	// CONSTRUCTOR //
	
	public CellPanel(GameOfLifeDisplay display, Color lifeColor, int position) {
		this.display = display;
		this.oldLife = false;
		this.position = position;
		this.liveColor = lifeColor;
		this.deadColor = Color.BLACK;
		this.addMouseListener(this);
	}
	
	// ACTION LISTENERS //
	
	@Override
	public void mouseClicked(MouseEvent e) {
		///System.out.println("hace click");
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		///System.out.println("Pone el ratón encima");
	}
	@Override
	public void mouseExited(MouseEvent e) {
		///System.out.println("Quita el ratón");
	}
	@Override
	public void mousePressed(MouseEvent e) {
		///System.out.println("Empieza a presionar el botón");
		int status = display.getStatus();
		if (status < 2) {clickColor();}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		///System.out.println("Suelta el botón");
	}
	
	// METHOD CHANGE COLOR ON CLICK //
	
	private void clickColor() {
		oldLife = !oldLife;
		this.setBackground((oldLife) ? liveColor : deadColor);
	}
	
	// METHOD COUNT LIVING NEIGHBOURS //
	
	private void setNextGen() {
		// calculate the index of each neighbor
		int side = display.getGridRange();
		int[] positions = {
			position - side - 1,	// top-left
			position - side,		// top-middle
			position - side + 1,	// top-right
			position + 1,			// mid-right
			position + side + 1,	// bot-right
			position + side,		// bot-middle
			position + side - 1,	// bot-left
			position - 1			// mid-left
		};
		int count = 0;
		
		// count how many neighbors are alive (oldLife == true)
		for (int pos : positions) {			
			try {
				CellPanel cell = display.getCellsList().get(pos);
				if (cell.getOldLife()) {count++;}
			} catch (IndexOutOfBoundsException e) {
				/**
				 * This for-each loop is meant to look for all 8 neighbors.
				 * Neighbors that corners and sides are not expected to adjoin,
				 * hence the catch exception, to cancel the entire process when
				 * trying to reach a non-existent cell.
				 */
				System.out.println("that cell doesn't exist");
			}
		}
		
		// set if this cell lives for the next generation...
		if (
			(oldLife == true)  && (count == 2 || count == 3) ||
			(oldLife == false) && (count == 3)
		) {
			newLife = true;
		} else {// or dies
			newLife = false;
		}
		
		// update 'oldLife'
		oldLife = newLife;
	}
	
	// OVERRIDE METHOD 'run()' //
	
	@Override
	public void run() {
		while (mainThread != null && display.getStatus() > 0) {
			try {
				switch (display.getStatus()) {
					case 0:		// status 0: stop
						/**
						 * This wait() does nothing, but the least I need at this moment
						 * is to hear eclipse cry for some unused exception...
						 */
						wait();
					case 1:		// status 1: paused
						break;
					case 2:		// status 2: start and resume
						break;
					default:
						System.out.println(display.getStatus());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.setBackground(deadColor);
		System.out.println(Thread.currentThread().getName() + " DIES");
	}

}