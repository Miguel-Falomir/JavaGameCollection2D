package main.utilities;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import main.Gui;
import main.gameoflife.gui.GameOfLifeOptionsPanel;
//import main.gameoflife.gui.ScreenPanel;

public class MenuPanel extends JPanel {
	
	// SERIAL VERSION IDENTIFIER //

	private static final long serialVersionUID = -8336684578723717833L;
	
	// VARIABLES //
	
	private List<Dimension> gameButtonSize = new ArrayList<Dimension>();
	private List<Dimension> optionButtonSize = new ArrayList<Dimension>();
	
	private GridBagConstraints gbcCentered = null;
	
	// UI COMPONENTS //
	
	Gui gui = null;
	
	JPanel jpanel_top = new JPanel();
	JPanel jpanel_center = new JPanel();
	JPanel jpanel_bottom = new JPanel();
	
	JLabel jlabel_page_title = new JLabel();
	
	JButton jbutton_settings = new JButton();
	JButton jbutton_quit = new JButton();
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
	
	// CONSTRUCTOR //
	
	public MenuPanel (Gui gui) {
		// set main frame
		this.gui = gui;
		
		// set variables
		gameButtonSize = gui.getStandardSize(5);
		optionButtonSize = gui.getStandardSize(4);
		gbcCentered = gui.getGridBagConstraintsCentered();
		
		// build-up JPanel components
		this.buildup();
		
		// mock-up JPanel in JFrame
		this.gui.mockup(this);
	}
	
	// METHOD BUILD GUI COMPONENTS //
	
	private void buildup() {
		// set JPanel properties
		this.setLayout(new BorderLayout());
		
		// declare panels
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel centerPanel = new JPanel(new GridLayout(2, 4));
		JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
		
		// top
		// set properties
		/* or not */
		// set components
		JLabel title = new JLabel(gui.getMessages().getString("main_Title"));
		title.setFont(title.getFont().deriveFont(24.0f));
		title.setBorder(new EmptyBorder(20, 20, 20, 20));
		//add components
		topPanel.add(title);
		
		// center
		// set properties
		/* or not */
		// set ActionListener for each game button
		ActionListener[] gameStarterList = {
			new ActionListener() { // solitaire
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println(gui.getMessages().getString("solitaire_Title"));
				}
			},
			new ActionListener() { // game of life (J.H.Conway)
				@Override
				public void actionPerformed(ActionEvent e) {
					gui.mockup(new GameOfLifeOptionsPanel(gui));
				}
			},
			new ActionListener() { // snake
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println(gui.getMessages().getString("snakegame_Title"));
				}
			},
			new ActionListener() { // pong
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println(gui.getMessages().getString("pong_Title"));
				}
			},
			new ActionListener() { // breakout
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println(gui.getMessages().getString("breakout_Title"));
				}
			},
			new ActionListener() { // invaders
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println(gui.getMessages().getString("invaders_Title"));
				}
			},
			new ActionListener() { // tetris
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println(gui.getMessages().getString("tetris_Title"));
				}
			},
			new ActionListener() { // pacman
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println(gui.getMessages().getString("pacman_Title"));
				}
			},
		};
		// set components
		JButton[] gameButtonList = {
			new JButton(gui.getMessages().getString("solitaire_Title")),
			new JButton(gui.getMessages().getString("gameoflife_Title")),
			new JButton(gui.getMessages().getString("snakegame_Title")),
			new JButton(gui.getMessages().getString("pong_Title")),
			new JButton(gui.getMessages().getString("breakout_Title")),
			new JButton(gui.getMessages().getString("invaders_Title")),
			new JButton(gui.getMessages().getString("tetris_Title")),
			new JButton(gui.getMessages().getString("pacman_Title"))
		};
		for (int i = 0; i < gameButtonList.length; i++) {
			gui.setComponentSize(
				gameButtonList[i],
				gameButtonSize.get(0),
				gameButtonSize.get(1),
				gameButtonSize.get(2)
			);
			gameButtonList[i].addActionListener(gameStarterList[i]);
		}
		// add components
		for (JButton btn : gameButtonList) {
			JPanel pan = new JPanel(new GridBagLayout());
			pan.add(btn, gbcCentered);
			centerPanel.add(pan);
		}
		
		// bottom
		// set properties
		/* or not */
		bottomPanel.setPreferredSize(new Dimension(100, 100));
		// set components
		JButton[] optionButtonList = {
			new JButton(gui.getMessages().getString("settings_Buton")),
			new JButton(gui.getMessages().getString("quit_Buton"))
		};
		for (JButton btn : optionButtonList) {
			gui.setComponentSize(
				btn,
				optionButtonSize.get(0),
				optionButtonSize.get(1),
				optionButtonSize.get(2)
			);
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
			pan.add(btn, gbcCentered);
			bottomPanel.add(pan);
		}
		
		// add panels to container
		this.add(topPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);
	}
	
}
