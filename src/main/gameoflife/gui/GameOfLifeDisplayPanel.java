package main.gameoflife.gui;

import main.Gui;
import main.utilities.DisplayPanel;

public class GameOfLifeDisplayPanel extends DisplayPanel {
	
	// SERIAL VERSION IDENTIFIER //

	private static final long serialVersionUID = -8049480235816106864L;
	
	// VARIABLES //
	
	private int number = 20;
	
	// UI COMPONENTS //
	
	Gui gui = null;
	
	// GETTERS AND SETTERS //
	
	public int getNumber() {return number;}
	public void setNumber(int number) {this.number = number;}
	
	// CONSTRUCTOR //
	
	public GameOfLifeDisplayPanel () {
		
	}
	
	// METHOD BUILD PANEL //
	
	private void buildup() {
		
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
