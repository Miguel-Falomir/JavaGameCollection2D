package main.gameoflife.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import main.Gui;
import main.utilities.GameDisplay;

public class GameOfLifeDisplay extends GameDisplay {
	
	// SERIAL VERSION IDENTIFIER //

	private static final long serialVersionUID = -8049480235816106864L;
	
	// CUSTOM CLASS FOR GAME OF LIFE CELLS //
	
	private class CellPanel extends JPanel implements MouseListener {
		// attributes
		private GameOfLifeDisplay display;
		private boolean life;
		private Color liveColor;
		private Color deadColor;
		// getters and setters
		public boolean getLife() {return life;}
		public void setLife(boolean life) {
			this.life = life;
		}
		public void setLiveColor(Color liveColor) {
			this.liveColor = liveColor;
			this.setBackground((life) ? liveColor : deadColor);
		}
		public void setCurrentColor() {
			life = !life;
			this.setBackground((life) ? liveColor : deadColor);
		}
		// constructor
		public CellPanel(GameOfLifeDisplay display, Color lifeColor) {
			this.display = display;
			this.life = false;
			this.liveColor = lifeColor;
			this.deadColor = Color.BLACK;
			this.addMouseListener(this);
		}
		// action listener(s)
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
			if (status < 2) {
				setCurrentColor();
			}
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			///System.out.println("Suelta el botón");
		}

	}
	
	// VARIABLES //
	
	private Dimension[] panelSize;
	private int gridRange;
	private int timeLapse;
	private Color cellColor;
	private int status; /* 0: stop / 1: pause / 2: resume */
	
	// UI COMPONENTS //
	
	private Gui gui = null;
	
	private List<CellPanel> cellsList = null;
	
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
			if (cell.getLife()) {
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
	
	// CONSTRUCTOR //

	public GameOfLifeDisplay(Gui gui, int gridRange, int timeLapse, Color cellColor) {
		// set ui components
		this.gui = gui;
		
		// set variables
		this.panelSize = gui.getStandardSize(2);
		this.gridRange = gridRange;
		this.timeLapse = timeLapse;
		this.cellColor = cellColor;
		this.cellsList = new ArrayList<CellPanel>();
		this.status = 0;
		
		// fulfill 'cellsList' separately
		int side = (int) (panelSize[1].getWidth() / gridRange);
		for (int i = 0; i < Math.pow(gridRange, 2); i++) {
			// set cell border separately
			MatteBorder border;
			if (i == 0) {
				border = new MatteBorder(1, 1, 1, 1, Color.WHITE);
			} else if (i < gridRange) {
				border = new MatteBorder(1, 0, 1, 1, Color.WHITE);
			} else if (i % gridRange == 0) {
				border = new MatteBorder(0, 1, 1, 1, Color.WHITE);
			} else {
				border = new MatteBorder(0, 0, 1, 1, Color.WHITE);
			}
			// intialize cell
			CellPanel cell = new CellPanel(this, cellColor);
			gui.setComponentSize(
				cell,
				new Dimension(side,side),
				new Dimension(side,side),
				new Dimension(side,side)
			);
			cell.setBackground(Color.black);
			cell.setBorder(border);
			cellsList.add(cell);
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
		for (CellPanel cell : cellsList) {			
			this.add(cell);
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
		if (cellsList.size() == Math.pow(gridRange, 2)) {
			// declare new list
			List<CellPanel> newList = new ArrayList<CellPanel>();
			// save cells of 'cellsList' in new list
			for (CellPanel cell: cellsList) {newList.add(cell);}
			// clear 'cellsList'
			cellsList.clear();
			// update 'cellsList' with new list cells
			for (CellPanel cell : newList) {cellsList.add(cell);}
		} else {
			// calculate length (px) of cell side
			int side = (int) (panelSize[1].getWidth() / gridRange);
			// clear 'cellsList'
			cellsList.clear();
			// for each new cell:
			for (int i = 0; i < Math.pow(gridRange, 2); i++) {
				// set cell border separately
				MatteBorder border;
				if (i == 0) {
					border = new MatteBorder(1, 1, 1, 1, Color.WHITE);
				} else if (i < gridRange) {
					border = new MatteBorder(1, 0, 1, 1, Color.WHITE);
				} else if (i % gridRange == 0) {
					border = new MatteBorder(0, 1, 1, 1, Color.WHITE);
				} else {
					border = new MatteBorder(0, 0, 1, 1, Color.WHITE);
				}
				// intialize cell
				CellPanel cell = new CellPanel(this, cellColor);
				gui.setComponentSize(
					cell,
					new Dimension(side,side),
					new Dimension(side,side),
					new Dimension(side,side)
				);
				cell.setBackground(Color.black);
				cell.setBorder(border);
				cellsList.add(cell);
			}
		}
		
		// rebuild JPanel
		buildup();
		
		// refresh container
		this.revalidate();
		this.repaint();
	}
	
}
