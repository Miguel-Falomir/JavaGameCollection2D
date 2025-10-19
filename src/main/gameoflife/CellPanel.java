package main.gameoflife;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class CellPanel extends JPanel implements MouseListener {
	
	// SERIAL VERSION IDENTIFIER //
	
	private static final long serialVersionUID = 4881069824330811434L;
	
	// SHARED OBJECTS //
	
	private GameOfLifeDisplay display;
	
	// ATTRIBUTES //
	
	private boolean oldLife;
	private boolean newLife;
	private int[] position = {0,0};
	private ArrayList<int[]> neighborPositions;
	private int axis_x;
	private int axis_y;
	private int neighbors;
	private Color liveColor;
	private Color deadColor;
	
	// GETTERS AND SETTERS //
	
	public boolean getOldLife() {return oldLife;}
	
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
		this.axis_x = position[1];
		this.axis_y = position[0];
		this.neighborPositions = new ArrayList<int[]>();
		// fulfill 'neighborPositions'
		this.neighborPositions.add(new int[] {axis_x - 1, axis_y - 1});	// top-left
		this.neighborPositions.add(new int[] {axis_x, axis_y - 1});		// top-middle
		this.neighborPositions.add(new int[] {axis_x + 1, axis_y - 1});	// top-right
		this.neighborPositions.add(new int[] {axis_x - 1, axis_y});		// mid-left
		this.neighborPositions.add(new int[] {axis_x + 1, axis_y});		// mid-right
		this.neighborPositions.add(new int[] {axis_x - 1, axis_y + 1});	// bot-left
		this.neighborPositions.add(new int[] {axis_x, axis_y + 1});		// bot-middle
		this.neighborPositions.add(new int[] {axis_x + 1, axis_y + 1});	// bot-right
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
		neighbors = 0;
		
		// count how many neighbors are alive (oldLife == true)
		for (int[] pos : neighborPositions) {
			try {
				CellPanel cell = display.getCellsGrid().get(pos[0]).get(pos[1]);
				if (cell.getOldLife()) {neighbors++;}
			} catch (IndexOutOfBoundsException e) {
				/**
				 * This exception is expected to happen. As this program checks all 8
				 * potential neighbors, corners would have 3, and sides would have 5.
				 * As brute as it may sound, this way saved me around 9 conditionals
				 * (4 corners + 4 sides + middle)
				 */
			}
		}
		
		// set if this cell lives for the next generation or dies
		if ((oldLife == true)) {
			newLife = (neighbors == 2 || neighbors == 3) ? true : false;
		} else {
			newLife = (neighbors == 3) ? true : false;
		}	
	}
	
	// METHOD UPDATE GENERATION COLOR //
	
	public void updateGeneration() {
		this.setBackground((newLife) ? liveColor : deadColor);
		oldLife = newLife;
	}
	
	// METHOD CLEAR CELL //
	
	public void clear() {
		oldLife = false;
		newLife = false;
		this.setBackground(deadColor);
	}

}