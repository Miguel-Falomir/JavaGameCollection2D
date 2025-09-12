package main.gameoflife.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class DisplayPanel extends JPanel {
	
	// SERIAL VERSION IDENTIFIER //

	private static final long serialVersionUID = -8049480235816106864L;
	
	// VARIABLES //
	
	private Dimension[] displaySize = {
		new Dimension(480, 480),
		new Dimension(480, 480),
		new Dimension(480, 480)
	};
	private int gridRange = 12;
	private int gensPerSecond = 4;
	private int lifeMaximum = 1;
	private int gridlineWidth = 1;
	private int borderWidth = gridlineWidth * 2;
	private Color gridlineColor = Color.CYAN;
	
	// UI COMPONENTS //
	
	ScreenPanel main = null;
	
	// GETTERS AND SETTERS //
	
	public int getGridRange() {
		return gridRange;
	}
	
	public void setGridRange(int gridRange) {
		this.gridRange = gridRange;
		update();
	}

	public int getGensPerSecond() {
		return gensPerSecond;
	}

	public void setGensPerSecond(int gensPerSecond) {
		this.gensPerSecond = gensPerSecond;
		update();
	}
	
	public int getLifeMaximum() {
		return lifeMaximum;
	}

	public void setLifeMaximum(int lifeMaximum) {
		this.lifeMaximum = lifeMaximum;
		update();
	}

	public int getGridlineWidth() {
		return gridlineWidth;
	}

	public void setGridlineWidth(int gridlineWidth) {
		this.gridlineWidth = gridlineWidth;
		update();
	}

	public int getBorderWidth() {
		return borderWidth;
	}

	public void setBorderWidth(int borderWidth) {
		this.borderWidth = borderWidth;
	}
	
	// CONSTRUCTOR //
	
	public DisplayPanel (ScreenPanel main) {
		// set ui components
		this.main = main;
		
		// set properties
		this.setLayout(new GridLayout(gridRange, gridRange, gridlineWidth, gridlineWidth));
		this.setBackground(gridlineColor);
		this.setMinimumSize(displaySize[0]);
		this.setPreferredSize(displaySize[1]);
		this.setMaximumSize(displaySize[2]);
		
		// build-up JPanel components
		buildup();
	}
	
	// METHOD BUILD PANEL //
	
	public void buildup() {
		for (int row = 0; row < gridRange; row++) {
			for (int col = 0; col < gridRange; col++) {
				JPanel stuff = new JPanel();
				if (row % 2 == 0 && col % 2 == 1 || row % 2 == 1 && col % 2 == 0) {
					stuff.setBackground(Color.WHITE);
				} else {
					stuff.setBackground(Color.black);
				}
		        this.add(stuff);
		    }
		}
	}
	
	// METHOD UPDATE PANEL //
	
	private void update() {
		// clear container
		this.removeAll();
		
		// rebuild JPanel
		buildup();
		
		// refresh container
		this.revalidate();
		this.repaint();
	}

}
