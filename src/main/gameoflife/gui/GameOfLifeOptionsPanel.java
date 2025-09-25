package main.gameoflife.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Gui;
import main.utilities.Item;
import main.utilities.OptionsPanel;

public class GameOfLifeOptionsPanel extends OptionsPanel{
	
	// SERIAL VERSION IDENTIFIER //

	private static final long serialVersionUID = 7388218801567522611L;
	
	// CUSTOM CLASSES FOR COMBOBOX ITEMS //
	
	private class GridRangeItem extends Item {
		// attributes
		private int gridRange;
		// getters and setters
		public int getGridRange() {return gridRange;}
		// constructor
		public GridRangeItem(int gridRange) {
			this.id = String.valueOf(gridRange);
			this.name = String.valueOf(gridRange) + " X " + String.valueOf(gridRange);
			this.gridRange = gridRange;
		}
	}
	
	private class TimeLapseItem extends Item {
		// attributes
		private int timeLapse;
		// getters and setters
		public int getTimeLapse() {return timeLapse;}
		// constructor
		public TimeLapseItem(int timeLapse, String name) {
			this.id = String.valueOf(timeLapse);
			this.name = name;
			this.timeLapse = timeLapse;
		}
	}
	
	private class CellColorItem extends Item {
		// attributes
		private Color cellColor;
		// getters and setters
		public Color getCellColor() {return cellColor;}
		public void setName(String name) {this.name = name;}
		// constructor
		public CellColorItem(Color cellColor) {
			this.id = String.valueOf(cellColor);
			this.name = String.valueOf(cellColor);
			this.cellColor = cellColor;
		}
	}
	
	// INHERITED //
	
	///protected GridBagConstraints gbcCentered = null;

	///protected List<Dimension> buttonSize = null;
	///protected List<Dimension> optionsPanelSize = null;
	///protected List<Dimension> optionsTitleSize = null;
	///protected List<Dimension> optionTitlePanelSize = null;
	///protected List<Dimension> optionControllerPanelSize = null;
	///protected List<Dimension> optionGapPanelSize = null;
	
	///protected Gui gui = null;
	
	///protected JPanel centralPanel = null;
	///protected JPanel optionsPanel = null;
	///protected JPanel optionsTopPanel = null;
	///protected JPanel optionsCenterPanel = null;
	///protected JPanel optionsCenterCenterPanel = null;
	///protected JPanel optionsCenterBottomPanel = null;
	///protected JPanel optionsBottomPanel = null;
	
	///protected JLabel jlabel_title = null;
	
	///protected JButton jbutton_back = null;
	
	// VARIABLES //
	
	private Item[] gridRangeList = {
		new GridRangeItem(24),
		new GridRangeItem(48),
		new GridRangeItem(60)
	};
	
	private Item[] timeLapseList = {
		new TimeLapseItem(3, "0.003 s"),
		new TimeLapseItem(100, "0.10 s"),
		new TimeLapseItem(250, "0.25 s"),
		new TimeLapseItem(500, "0.50 s"),
		new TimeLapseItem(1000, "1 s"),
		new TimeLapseItem(2000, "2 s")
	};
	
	private Item[] cellColorList = {
		new CellColorItem(Color.WHITE),
		new CellColorItem(Color.YELLOW),
		new CellColorItem(Color.CYAN),
		new CellColorItem(Color.GREEN),
		new CellColorItem(Color.MAGENTA),
		new CellColorItem(Color.ORANGE),
		new CellColorItem(Color.RED)
	};
	
	private Item[] initialItemsList = {
		gridRangeList[1],
		timeLapseList[2],
		cellColorList[0]
	};
	
	// UI COMPONENTS //
	
	private GameOfLifeDisplayPanel displayPanel = null;
	
	// CONSTRUCTOR //

	public GameOfLifeOptionsPanel(Gui gui) {
		// initialize attributes from parent class
		super(gui);
		
		// with gui already initialized, give a name to each color
		String[] translatedColors = {
			gui.getMessages().getString("color_White"),
			gui.getMessages().getString("color_Yellow"),
			gui.getMessages().getString("color_Cyan"),
			gui.getMessages().getString("color_Green"),
			gui.getMessages().getString("color_Magenta"),
			gui.getMessages().getString("color_Orange"),
			gui.getMessages().getString("color_Red")
		};
		
		for (int i = 0; i < translatedColors.length; i++) {
			CellColorItem ceci = (CellColorItem) cellColorList[i];
			ceci.setName(translatedColors[i]);
		}
		
		// initialize 'displayPanel'
		buildupDisplay();
		
		// add components to 'optionsPanel'
		buildupOptions();
	}
	
	// OVERRIDE METHOD 'buildupOptions' //
	
	@Override
	protected void buildupOptions() {
		// initialize and compose panels of both title and back button
		super.buildupOptions();
		
		// add controllers to 'optionsCenterCenterPanel'
		/**
		 * This might blow most people's minds (I still haven't found all the fragments
		 * of my head) but child components can be modified after being added to the
		 * parent component. This even allows programmers to add some grandson components
		 * within the child component, and the parent component would show everything
		 * fine. Knowing this, I could copy-paste like 90% of this method in the parent
		 * class, save tens (or even hundreds) of redundant lines of code, and feel like
		 * a supergenius gigachad. I wish I had a horny girl to snog right now :(
		 */
		generateComboBox(
			new JComboBox<Item>(),
			new JLabel(gui.getMessages().getString("gameoflife_GridRange_Label")),
			gridRangeList,
			1
		);
		
		generateComboBox(
			new JComboBox<Item>(),
			new JLabel(gui.getMessages().getString("gameoflife_TimeLapse_Label")),
			timeLapseList,
			2
		);

		generateComboBox(
			new JComboBox<Item>(),
			new JLabel(gui.getMessages().getString("gameoflife_CellColor_Label")),
			cellColorList,
			3
		);
		
		// add a pause menu to 'optionsCenterBottomPanel'
		buildupPauseMenu();
	}
	
	// OVERRIDE METHOD 'buildupPauseMenu' //
	
	@Override
	protected void buildupPauseMenu() {
		// initialize (despite this method is supposed to be empty...)
		super.buildupPauseMenu();
		
		// declare components
		JPanel pauseMenuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
		JButton[] buttonArray = {
			new JButton("[]"),
			new JButton("||"),
			new JButton("!>")
		};
		
		// set panel properties
		gui.setComponentSize(
			pauseMenuPanel,
			new Dimension(300, 40),
			new Dimension(300, 40),
			new Dimension(300, 40)
		);
		
		// add buttons to 'pauseMenuPanel'
		for (JButton button : buttonArray) {
			// button properties
			gui.setComponentSize(
				button,
				new Dimension(40, 40),
				new Dimension(40, 40),
				new Dimension(40, 40)
			);
			button.setMargin(new Insets(1,1,1,1));
			// add within GridBagLayout to center
			JPanel pan = new JPanel(new GridBagLayout());
			pan.add(button, gbcCentered);
			pauseMenuPanel.add(pan);
		}
		
		// add action listener for each button separately
		/**
		 * As each index has the same number as their desired status,
		 * I tried to implement all of this in the foreach (after turning
		 * it a for loop, of course).
		 * Result:
		 * 'i' counter throws exception because it is supposed to be some
		 * kind of "final" variable. I didn't understand the error, only
		 * that it would force me to bungle this workaround.
		 */
		buttonArray[0].addActionListener( event -> {	// stop
			displayPanel.setStatus(0);
			/**
			 * These bidimensional loops happen because each controller is actually
			 * a group of components within their own JPanel(s). That is, for each
			 * component, the route is JPanel -> JPanel -> Component
			 */
			for (Component comp_1 : optionsCenterCenterPanel.getComponents()) {
				JPanel pan = (JPanel) comp_1;
				for (Component comp_2 : pan.getComponents()) {
					comp_2.setEnabled(true);
				}
			}
		});
		buttonArray[1].addActionListener( event -> {	// pause
			displayPanel.setStatus(1);
			for (Component comp_1 : optionsCenterCenterPanel.getComponents()) {
				JPanel pan = (JPanel) comp_1;
				for (Component comp_2 : pan.getComponents()) {
					comp_2.setEnabled(true);
				}
			}
		});
		buttonArray[2].addActionListener( event -> {	// resume
			displayPanel.setStatus(2);
			for (Component comp_1 : optionsCenterCenterPanel.getComponents()) {
				JPanel pan = (JPanel) comp_1;
				for (Component comp_2 : pan.getComponents()) {
					comp_2.setEnabled(false);
				}
			}
		});
		
		// add 'pauseMenuPanel' to 'optionsCenterBottomPanel'
		optionsCenterBottomPanel.add(pauseMenuPanel, gbcCentered);
	}
	
	// OVERRIDE METHOD 'buildupDisplay' //
	
	@Override
	protected void buildupDisplay() {
		// initialize (despite this method is supposed to be empty...)
		super.buildupDisplay();
		
		// set properties (coming soon...)
		displayPanel = new GameOfLifeDisplayPanel(
			gui,
			((GridRangeItem) initialItemsList[0]).getGridRange(),
			((TimeLapseItem) initialItemsList[1]).getTimeLapse(),
			((CellColorItem) initialItemsList[2]).getCellColor()
		);
		
		// add 'displayPanel' to 'centralPanel'
		centralPanel.add(displayPanel, gbcCentered);
		
		// add 'centralPanel' to screen
		this.add(centralPanel, BorderLayout.CENTER);
	}
	
	// OVERRIDE METHOD 'generateComboBox' //
	
	@Override
	protected void generateComboBox(JComboBox<Item> combo, JLabel title, Item[] list, int attributeIndex) {
		// initialize
		super.generateComboBox(combo, title, list, attributeIndex);
		
		// set combobox properties
		switch (attributeIndex) {
			case 1:	// grid range
				combo.setSelectedItem((GridRangeItem) initialItemsList[0]);
				combo.addActionListener( event -> {
					GridRangeItem selected = (GridRangeItem) combo.getSelectedItem();
					int newGridRange = selected.getGridRange();
					displayPanel.setGridRange(newGridRange);
				});
				break;
			case 2:	// time lapse
				combo.setSelectedItem((TimeLapseItem) initialItemsList[1]);
				combo.addActionListener( event -> {
					TimeLapseItem selected = (TimeLapseItem) combo.getSelectedItem();
					int newTimeLapse = selected.getTimeLapse();
					displayPanel.setTimeLapse(newTimeLapse);
				});
				break;
			case 3:	// cell color
				combo.setSelectedItem((CellColorItem) initialItemsList[2]);
				combo.addActionListener( event -> {
					CellColorItem selected = (CellColorItem) combo.getSelectedItem();
					Color newCellColor = selected.getCellColor();
					displayPanel.setCellColor(newCellColor);
				});
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + attributeIndex);
		}

	}
}
