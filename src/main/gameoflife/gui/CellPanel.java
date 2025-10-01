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
	private int position;
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
	
	public void countNeighbors() {
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
		neighbors = 0;
		
		// count how many neighbors are alive (oldLife == true)
		for (int pos : positions) {			
			try {
				CellPanel cell = display.getCellsList().get(pos);
				if (cell.getOldLife()) {neighbors++;}
			} catch (IndexOutOfBoundsException e) {
				break;
			}
		}		
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