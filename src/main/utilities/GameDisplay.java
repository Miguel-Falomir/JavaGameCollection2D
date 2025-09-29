package main.utilities;

import javax.swing.JPanel;

public abstract class GameDisplay extends JPanel {
	
	// SERIAL VERSION IDENTIFIER //

	private static final long serialVersionUID = -5010227029194781824L;
	
	// VARIABLES //
	
	protected int status;
	
	// GETTERS AND SETTERS //
	
	public synchronized int getStatus() {return status;}
	
	public synchronized void setStatus(int status) {
		this.status = status;
	}
	
	// METHOD BUILD PANEL //
	
	protected void buildup() {}
	
	// METHOD UPDATE PANEL //
	
	protected void update() {}

}
