package main.jpanels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import main.Gui;

public class MenuPanel extends JPanel {
	
	// SERIAL VERSION IDENTIFIER //

	private static final long serialVersionUID = -8336684578723717833L;
	
	// VARIABLES //
	
	
	
	// MAIN FRAME //
	
	Gui gui = null;
	
	// UI COMPONENTS //
	
	
	
	// CONSTRUCTOR //
	
	public MenuPanel (Gui gui) {
		// set main frame
		this.gui = gui;
		
		// build-up JPanel components
		this.buildup();
		
		// mock-up JPanel in JFrame
		this.gui.mockup(this);
	}
	
	// METHOD BUILD GUI COMPONENTS //
	
	private void buildup() {
		// set JPanel properties
		this.setLayout(new BorderLayout());
		
		// declare variables
		Dimension[] gameBtnSize = {
			new Dimension(100, 100),
			new Dimension(100, 100),
			new Dimension(200, 200)
		};
		Dimension[] optionBtnSize = {
			new Dimension(100, 50),
			new Dimension(100, 50),
			new Dimension(100, 50)
		};
		GridBagConstraints gbcCenter = new GridBagConstraints(
			0,							// gridX (fila posicion)
			0,							// gridY (columna posicion)
			1,							// gridwidth (cantidad filas ocupadas)
			1,							// gridheight (cantidad columnas ocupadas)
			0.0,						// weightX (peso del componente respecto al ancho libre)
			0.0,						// weightY (peso del componente respecto a la altura disponible)
			GridBagConstraints.CENTER,	// anchor (lugar de la celda donde colocar el componente, si este es mas pequenyo)
			GridBagConstraints.NONE,	// fill (metodo para reescalar el componente, si este es mas pequenyo que la celda)
			new Insets(0, 0, 0, 0),		// insets (margenes externos para dejar espacio libre fuera de la celda)
			0,							// ipadX (margen interno respecto al eje X, espacio libre dentro de la celda)
			0							// ipadY (margen interno respecto al eje Y, espacio libre dentro de la celda)
		);
		
		// declare panels
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel centerPanel = new JPanel(new GridLayout(2, 4));
		JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
		
		// top
		// set properties
		//topPanel.setBackground(Color.RED);
		// set components
		JLabel title = new JLabel(gui.getMessages().getString("mainTitle"));
		title.setFont(title.getFont().deriveFont(24.0f));
		title.setBorder(new EmptyBorder(20, 20, 20, 20));
		//add components
		topPanel.add(title);
		
		// center
		// set properties
		
		// set components
		JButton[] gameButtonList = {
			new JButton("Solitaire"),
			new JButton("Game of life"),
			new JButton("Snake"),
			new JButton("Pong"),
			new JButton("Breakout"),
			new JButton("Invaders"),
			new JButton("Tetris"),
			new JButton("Pacman")
		};
		for (JButton btn : gameButtonList) {
			btn.setMinimumSize(gameBtnSize[0]);
			btn.setPreferredSize(gameBtnSize[1]);
			btn.setMaximumSize(gameBtnSize[2]);
		}
		// add components
		for (JButton btn : gameButtonList) {
			JPanel pan = new JPanel(new GridBagLayout());
			pan.add(btn, gbcCenter);
			centerPanel.add(pan);
		}
		
		// bottom
		// set properties
		//bottomPanel.setBackground(Color.GREEN);
		bottomPanel.setPreferredSize(new Dimension(100, 100));
		// set components
		JButton[] optionButtonList = {
			new JButton(gui.getMessages().getString("settings")),
			new JButton(gui.getMessages().getString("quit"))
		};
		for (JButton btn : optionButtonList) {
			btn.setMinimumSize(optionBtnSize[0]);
			btn.setPreferredSize(optionBtnSize[1]);
			btn.setMaximumSize(optionBtnSize[2]);
		}
		// disable settings button until better implementation
		optionButtonList[0].setEnabled(true);
		// add events
		optionButtonList[0].addActionListener(event -> {
			gui.mockup(new SettingsPanel(gui));
		});
		optionButtonList[1].addActionListener(event -> {
			setVisible(false);
			gui.dispose();
			SwingUtilities.invokeLater(new Runnable() {
		  		public void run() {
		  			System.exit(0);
		  		}
		  	});
		});
		// add components
		for (JButton btn : optionButtonList) {
			JPanel pan = new JPanel(new GridBagLayout());
			pan.add(btn, gbcCenter);
			bottomPanel.add(pan);
		}
		
		// add panels to container
		this.add(topPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);
	}
	
}
