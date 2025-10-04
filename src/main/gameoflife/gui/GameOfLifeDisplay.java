package main.gameoflife.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.border.MatteBorder;

import main.Gui;
import main.utilities.GameDisplay;

public class GameOfLifeDisplay extends GameDisplay {
	
	// SERIAL VERSION IDENTIFIER //

	private static final long serialVersionUID = -8049480235816106864L;
	
	// VARIABLES //
	
	private Dimension[] panelSize;
	private int gridRange;
	private int timeLapse;
	private int status; /* 0: stop / 1: pause / 2: resume */
	private Color cellColor;
	private boolean timeLapseFinished;
	
	// UI COMPONENTS //
	
	private Gui gui = null;
	
	private ArrayList<ArrayList<CellPanel>> cellsGrid = null;
	
	// GETTERS AND SETTERS //
	
	public int getGridRange() {
		return gridRange;
	}
	
	public void setGridRange(int gridRange) {
		this.gridRange = gridRange;
		update();
	}	
	
	public int getTimeLapse() {
		return timeLapse;
	}
	
	public void setTimeLapse(int timeLapse) {
		this.timeLapse = timeLapse;
	}
	
	public Color getCellColor() {
		return cellColor;
	}
	
	public void setCellColor(Color cellColor) {
		this.cellColor = cellColor;
		for (Component comp : this.getComponents()) {
			CellPanel cell = (CellPanel) comp;
			if (cell.getOldLife()) {
				cell.setLiveColor(this.cellColor);
			}
		}
		update();
	}
	
	public synchronized int getStatus() {
		return status;
	}
	
	public synchronized void setStatus(int status) {
		this.status = status;
	}
	
	public synchronized boolean getTimeLapseFinished() {
		return timeLapseFinished;
	}
	
	public synchronized void setTimeLapseFinished(boolean timeLapseFinished) {
		this.timeLapseFinished = timeLapseFinished;
	}
	
	public synchronized ArrayList<ArrayList<CellPanel>> getCellsGrid() {
		return cellsGrid;
	}
	
	// CONSTRUCTOR //

	public GameOfLifeDisplay(Gui gui, int gridRange, int timeLapse, Color cellColor) {
		// set ui components
		this.gui = gui;
		
		// set variables
		this.panelSize = gui.getStandardSize(2);
		this.gridRange = gridRange;
		this.timeLapse = timeLapse;
		this.cellColor = cellColor;
		this.cellsGrid = new ArrayList<ArrayList<CellPanel>>();
		this.status = 0;
		this.timeLapseFinished = false;
		
		// fulfill 'cellsGrid' separately
		int side = (int) (panelSize[1].getWidth() / gridRange);
		for (int i = 0; i < gridRange; i++) {
			// create new arraylist
			ArrayList<CellPanel> list = new ArrayList<CellPanel>();
			// fulfill each new list
			for (int j = 0; j < gridRange; j++) {
				// set cell border separately
				MatteBorder border;
				if (i == 0 && j == 0) {
					border = new MatteBorder(1, 1, 1, 1, Color.WHITE);
				} else if (i == 0 && j > 0) {
					border = new MatteBorder(1, 0, 1, 1, Color.WHITE);
				} else if (i > 0 && j == 0) {
					border = new MatteBorder(0, 1, 1, 1, Color.WHITE);
				} else {
					border = new MatteBorder(0, 0, 1, 1, Color.WHITE);
				}
				// intialize cell
				CellPanel cell = new CellPanel(this, cellColor, new int[] {j, i});
				gui.setComponentSize(
					cell,
					new Dimension(side,side),
					new Dimension(side,side),
					new Dimension(side,side)
				);
				cell.setBackground(Color.black);
				cell.setBorder(border);
				list.add(cell);
			}
			// add new arraylist to grid as a new row
			cellsGrid.add(list);
		}
		
		// build UI components
		buildup();
	}
	
	// OVERRIDE METHOD 'buildup' //
	
	@Override
	protected void buildup() {
		// set layout
		this.setLayout(new GridLayout(gridRange, gridRange));
		
		// set properties
		this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		gui.setComponentSize(
			this,
			panelSize[0],
			panelSize[1],
			panelSize[2]
		);
		
		// add 'cellsList' to main panel one by one
		for (ArrayList<CellPanel> row : cellsGrid) {			
			for (CellPanel cell : row) {
				this.add(cell);
			}
		}
		
		// show
		this.setVisible(true);
	}
	
	// OVERRIDE METHOD 'update' //
	
	@Override
	protected void update() {
		// clear container
		this.removeAll();
		
		// refill 'cellsList' separately
		if (cellsGrid.size() == Math.pow(gridRange, 2)) {
			// declare new list
			ArrayList<ArrayList<CellPanel>> newGrid = new ArrayList<ArrayList<CellPanel>>();
			// save cells of 'cellsList' in new list
			for (ArrayList<CellPanel> row : cellsGrid) {newGrid.add(row);}
			// clear 'cellsList'
			cellsGrid.clear();
			// update 'cellsList' with new list cells
			for (ArrayList<CellPanel> row : newGrid) {cellsGrid.add(row);}
		} else {
			// calculate length (px) of cell side
			int side = (int) (panelSize[1].getWidth() / gridRange);
			// clear 'cellsList'
			cellsGrid.clear();
			// for each new cell:
			for (int i = 0; i < gridRange; i++) {
				// create new arraylist
				ArrayList<CellPanel> list = new ArrayList<CellPanel>();
				// fulfill each new list
				for (int j = 0; j < gridRange; j++) {				
					// set cell border separately
					MatteBorder border;
					if (i == 0 && j == 0) {
						border = new MatteBorder(1, 1, 1, 1, Color.WHITE);
					} else if (i == 0 && j > 0) {
						border = new MatteBorder(1, 0, 1, 1, Color.WHITE);
					} else if (i > 0 && j == 0) {
						border = new MatteBorder(0, 1, 1, 1, Color.WHITE);
					} else {
						border = new MatteBorder(0, 0, 1, 1, Color.WHITE);
					}
					// intialize cell
					CellPanel cell = new CellPanel(this, cellColor, new int[] {i, j});
					gui.setComponentSize(
						cell,
						new Dimension(side,side),
						new Dimension(side,side),
						new Dimension(side,side)
					);
					cell.setBackground(Color.black);
					cell.setBorder(border);
					list.add(cell);
				}
				// add new arraylist to grid as a new row
				cellsGrid.add(list);
			}
		}
		
		// rebuild JPanel
		buildup();
		
		// refresh container
		this.revalidate();
		this.repaint();
	}
	
}
