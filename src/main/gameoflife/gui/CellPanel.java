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
	
	// ATTRIBUTES //
	
	private boolean oldLife;
	private boolean newLife;
	private int[] position = {0,0};
	private int neighbors;
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
	
	// CONSTRUCTOR //
	
	public CellPanel(GameOfLifeDisplay display, Color lifeColor, int[] position) {
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
	
	public void countNeighbors() {
		// calculate the index of each neighbor
		int axis_X = position[0];
		int axis_Y = position[1];
		int[][] neighborPositions = {
			new int[] {axis_X - 1, axis_Y - 1},	// top-left
			new int[] {axis_X, axis_Y - 1},		// top-middle
			new int[] {axis_X + 1, axis_Y - 1},	// top-right
			new int[] {axis_X - 1, axis_Y},		// mid-left
			new int[] {axis_X + 1, axis_Y},		// mid-right
			new int[] {axis_X - 1, axis_Y + 1},	// bot-left
			new int[] {axis_X, axis_Y + 1},		// bot-middle
			new int[] {axis_X + 1, axis_Y + 1}	// bot-right
		};
		neighbors = 0;
		
		// count how many neighbors are alive (oldLife == true)
		for (int[] pos : neighborPositions) {			
			try {
				CellPanel cell = display.getCellsGrid().get(pos[0]).get(pos[1]);
				if (cell.getOldLife()) {
					neighbors++;
				}
			} catch (IndexOutOfBoundsException e) {
				/**
				 * This exception is expected to happen. As this program checks all 8
				 * potential neighbors, corners would have 3, and sides would have 5.
				 * As brute as it may sound, this way saved me around 9 conditionals
				 * (4 corners + 4 sides + middle)
				 */
				e.printStackTrace();
				/**
				 * I have discovered that putting a break in the try-catch of a loop
				 * actually breaks the entire loop, not just current iteration (Or at
				 * least that's what happened to me).
				 * The break below is kept as a reminder for me, and a warning for you.
				 */
				///break;
			}
		}
		
		// print: nothing this time
		/**neighbors count
		System.out.println("I am cell [" + this.position[0] + ", " + this.position[1] + "]");
		System.out.println("I have " + neighbors + " neighbors");
		 */
		/**neighbors' positions
		System.out.println("I am cell [" + this.position[0] + ", " + this.position[1] + "]");
		System.out.println("My neighbors are:");
		System.out.println("["+neighborPositions[0][0]+","+neighborPositions[0][1]+"]" + "["+neighborPositions[1][0]+","+neighborPositions[1][1]+"]" + "["+neighborPositions[2][0]+","+neighborPositions[2][1]+"]");
		System.out.println("["+neighborPositions[3][0]+","+neighborPositions[3][1]+"]" + "[X.X]" + "["+neighborPositions[4][0]+","+neighborPositions[4][1]+"]");
		System.out.println("["+neighborPositions[5][0]+","+neighborPositions[5][1]+"]" + "["+neighborPositions[6][0]+","+neighborPositions[6][1]+"]" + "["+neighborPositions[7][0]+","+neighborPositions[7][1]+"]");
		 */
	}
	
	// METHOD UPDATE TO NEXT GENERATION //
	
	public void updateGeneration() {
		// set if this cell lives for the next generation...
		if ((oldLife == true)  && (neighbors == 2 || neighbors == 3) ||
			(oldLife == false) && (neighbors == 3)
		) {
			newLife = true;
		} else {// or dies
			newLife = false;
		}
		
		this.setBackground((newLife) ? liveColor : deadColor);
		
		// update 'oldLife'
		oldLife = newLife;
	}
	
	// OVERRIDE METHOD 'run()' //
	
	@Override
	public void run() {
		countNeighbors();
		System.out.println(Thread.currentThread().getName() + " DIES");
	}

}