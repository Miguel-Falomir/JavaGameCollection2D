package main.gameoflife.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

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
		public void setName(String name) {this.name = name;}
		public int getGridRange() {return gridRange;}
		
		// constructor
		public GridRangeItem(int gridRange) {
			this.id = String.valueOf(gridRange);
			this.name = String.valueOf(gridRange) + " X " + String.valueOf(gridRange);
			this.gridRange = gridRange;
		}
	}
	
	private class CheckBoxItem extends Item {
		// getters and setters
		public String getId() {return id;}
		public String getName() {return name;}
		
		// constructor
		public CheckBoxItem (String id, String name) {
			this.id = id;
			this.name = name;
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
	
	private List<Item> gridRangeList = Arrays.asList(
		new GridRangeItem(24),
		new GridRangeItem(48),
		new GridRangeItem(60)
	);
	
	// UI COMPONENTS //
	
	private GameOfLifeDisplayPanel displayPanel = null;
	
	// CONSTRUCTOR //

	public GameOfLifeOptionsPanel(Gui gui) {
		// initialize attributes from parent class
		super(gui);
		
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
		
		generateSlider(
			new JSlider(3, 1000, displayPanel.getTimeLapse()),
			new JLabel(gui.getMessages().getString("gameoflife_TimeLapse_Label")),
			new JLabel(String.valueOf(displayPanel.getTimeLapse()) + " ms"),
			false,
			1
		);

		generateSlider(
			new JSlider(1, 5, displayPanel.getMaxLife()),
			new JLabel(gui.getMessages().getString("gameoflife_MaxLife_Label")),
			new JLabel(String.valueOf(displayPanel.getMaxLife())),
			true,
			2
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
			new JButton("<<"),
			new JButton("[]"),
			new JButton("||"),
			new JButton("!>"),
			new JButton(">>")
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
		
		// add 'pauseMenuPanel' to 'optionsCenterBottomPanel'
		optionsCenterBottomPanel.add(pauseMenuPanel, gbcCentered);
	}
	
	// OVERRIDE METHOD 'buildupDisplay' //
	
	@Override
	protected void buildupDisplay() {
		// initialize (despite this method is supposed to be empty...)
		super.buildupDisplay();
		
		// set properties (coming soon...)
		displayPanel = new GameOfLifeDisplayPanel(gui);
		
		// add 'displayPanel' to 'centralPanel'
		centralPanel.add(displayPanel, gbcCentered);
		
		// add 'centralPanel' to screen
		this.add(centralPanel, BorderLayout.CENTER);
	}
	
	// OVERRIDE METHOD 'generateTextArea' //
	
	@Override
	protected void generateTextField(JTextField textfield, JLabel title, JButton button, int attributeIndex) {
		// initialize
		super.generateTextField(textfield, title, button, attributeIndex);
		
		// set textfield properties
		textfield.setText("value");
		
		// use switch-case as a selector to add one or other action listener
		switch (attributeIndex) {
			case 1: {
				button.addActionListener( event -> {
					String value = textfield.getText();
					System.out.println(value);
				});
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + attributeIndex);
		}
	}
	
	// OVERRIDE METHOD 'generateSlider' //
	
	@Override
	protected void generateSlider(JSlider slider, JLabel title, JLabel value, boolean useTicks, int attributeIndex) {
		// initialize
		super.generateSlider(slider, title, value, useTicks, attributeIndex);
		
		// use switch-case as a selector of one or other configuration
		/**
		 * This is the simplest solution I could come with. I wish I knew more about
		 * lambda expressions, but even in that case all StackOverflow solutions seemed
		 * to be too much verbose for me. For those who doesn't know (I googled it 10
		 * minutes before writing this comment) verbosity is the abundance or excess of
		 * word, for instance:
		 * (normal)  Karen drives a big car.
		 * (verbose) The woman named Karen happens to get herself to places by driving a car of big proportions.
		 */
		switch (attributeIndex) {
			case 1:	// time lapse slider
				slider.addChangeListener( event -> {
					int number = slider.getValue();
					value.setText(String.valueOf(number) + " ms");
					displayPanel.setTimeLapse(number);
				});
				if (useTicks) {			
					slider.setMinorTickSpacing(50);
					slider.setMajorTickSpacing(250);
					slider.setPaintTicks(true);
					slider.setPaintLabels(true);
				}
				break;
			case 2:	// max life slider
				slider.addChangeListener( event -> {
					int number = slider.getValue();
					value.setText(String.valueOf(number));
					displayPanel.setMaxLife(number);
				});
				if (useTicks) {			
					slider.setMajorTickSpacing(1);
					slider.setPaintTicks(true);
					slider.setPaintLabels(true);
				}
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + attributeIndex);
		}
	}
	
	// OVERRIDE METHOD 'generateComboBox' //
	
	@Override
	protected void generateComboBox(JComboBox<Item> combo, JLabel title, List<Item> list, int attributeIndex) {
		// initialize
		super.generateComboBox(combo, title, list, attributeIndex);
		
		// set combobox properties
		combo.setSelectedItem(list.get(2));
		
		// add action listener
		/**
		 * why would I bundle various listeners within one switch-case, if I only need
		 * to use one combobox for this very case? And what the hell took me this long
		 * to realize? >:(
		 */
		combo.addActionListener( event -> {
			GridRangeItem selected = (GridRangeItem) combo.getSelectedItem();
			int newGridRange = selected.getGridRange();
			displayPanel.setGridRange(newGridRange);
		});
	}
	
	// OVERRIDE METHOD 'generateCheckBoxesList' //
	
	@Override
	protected void generateCheckBoxesList(ArrayList<JCheckBox> check, JLabel title, List<Item> list, int attributeIndex) {
		// initialize
		super.generateCheckBoxesList(check, title, list, attributeIndex);
		
		// set each check box properties
		/* just in case... */
		
		// use switch-case as a selector to add one or other action listener
		switch (attributeIndex) {
			case 1:
				for (JCheckBox box : check) {
					box.addActionListener( event -> {
						for (int i = 0; i < check.size(); i++) {						
							if (check.get(i).isSelected()) {
								CheckBoxItem item = (CheckBoxItem) list.get(i);
								System.out.println(item.getName());
							}
						}
					});
				}
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + attributeIndex);
		}
	}

}
