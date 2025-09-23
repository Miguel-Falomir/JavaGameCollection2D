package main.gameoflife.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import main.Gui;
import main.utilities.DisplayPanel;

public class GameOfLifeDisplayPanel extends DisplayPanel {
	
	// SERIAL VERSION IDENTIFIER //

	private static final long serialVersionUID = -8049480235816106864L;
	
	// VARIABLES //
	
	private List<Dimension> panelSize;
	private int gridRange;
	private int timeLapse;
	private int maxLife;
	
	// UI COMPONENTS //
	
	Gui gui = null;
	
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
	
	public int getMaxLife() {
		return maxLife;
	}
	
	public void setMaxLife(int maxLife) {
		this.maxLife = maxLife;
	}
	
	// CONSTRUCTOR //

	public GameOfLifeDisplayPanel(Gui gui) {
		// set ui components
		this.gui = gui;
		
		// set variables
		panelSize = gui.getStandardSize(2);
		gridRange = 60;
		timeLapse = 250;
		maxLife = 1;
		
		// build UI components
		buildup();
	}
	
	// METHOD BUILD PANEL //
	
	@Override
	protected void buildup() {
		// set layout
		this.setLayout(new GridLayout(gridRange, gridRange));
		
		// set properties
		///this.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
		this.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		gui.setComponentSize(
			this,
			panelSize.get(0),
			panelSize.get(1),
			panelSize.get(2)
		);
		
		this.setBackground(Color.GREEN);
		
		// fulfill with labels
		int side = (int) (panelSize.get(1).getWidth() / gridRange);
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
			JPanel cell = new JPanel();
			gui.setComponentSize(
				cell,
				new Dimension(side,side),
				new Dimension(side,side),
				new Dimension(side,side)
			);
			cell.setBackground(Color.black);
			cell.setBorder(border);
			this.add(cell);
		}
		
		// show
		this.setVisible(true);
	}
	
	// METHOD UPDATE PANEL //
	
	@Override
	protected void update() {
		// clear container
		this.removeAll();
		
		// rebuild JPanel
		buildup();
		
		// refresh container
		this.revalidate();
		this.repaint();
	}

}
