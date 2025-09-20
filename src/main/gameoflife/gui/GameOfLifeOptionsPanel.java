package main.gameoflife.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import main.Gui;
import main.utilities.Item;
import main.utilities.OptionsPanel;

public class GameOfLifeOptionsPanel extends OptionsPanel{
	
	// SERIAL VERSION IDENTIFIER //

	private static final long serialVersionUID = 7388218801567522611L;
	
	// CUSTOM CLASSES FOR COMBOBOX ITEMS //
	
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
	
	///protected JButton jbuton_back = null;
	
	// VARIABLES //
	

	
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
	protected void buildupOptions() { /* todavia falta agregar controladores de ajustes */
		// initialize and compose panels of both title and back button
		super.buildupOptions();
		
		// add controllers to the options panel
		/**
		 * This might blow most people's minds (I still haven't found all the fragments
		 * of my head) but child components can be modified after being added to the
		 * parent component. This even allows programmers to add some grandson components
		 * within the child component, and the parent component would show everything
		 * fine. Knowing this, I could copy-paste like 90% of this method in the parent
		 * class, save tens (or even hundreds) of redundant lines of code, and feel like
		 * a supergenius gigachad. I wish I had a horny girl to snog right now :(
		 */
		generateSlider(
			new JSlider(0, 50, displayPanel.getNumber()),
			new JLabel("Slider:"),
			new JLabel(String.valueOf(displayPanel.getNumber())),
			true,
			1
		);
		
		generateTextField(
			new JTextField(),
			new JLabel("Text Field:"),
			new JButton(),
			1
		);
		/**
		generateComboBox(
			new JComboBox<Item>(),
			new JLabel("Combo Box:"),
			Arrays.asList(
				new comboItem("id_1", "option 1"),
				new comboItem("id_2", "option 2"),
				new comboItem("id_3", "option 3")
			),
			1
		);*/
		
		generateCheckBoxesList(
			new ArrayList<JCheckBox>(),
			new JLabel("Toggle Buttons:"),
			Arrays.asList(
				new CheckBoxItem("id_1", "option 1"),
				new CheckBoxItem("id_2", "option 2"),
				new CheckBoxItem("id_3", "option 3")
			),
			1
		);
		
		
	}
	
	// OVERRIDE METHOD 'generateTextArea' //
	
	@Override
	protected void generateTextField(JTextField textfield, JLabel title, JButton buton, int attributeIndex) {
		// initialize
		super.generateTextField(textfield, title, buton, attributeIndex);
		
		// set textfield properties
		textfield.setText("value");
		
		// use switch-case as a selector to add one or other action listener
		switch (attributeIndex) {
			case 1: {
				buton.addActionListener( event -> {
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
		
		// show ticks
		if (useTicks) {			
			slider.setMinorTickSpacing(5);
			slider.setMajorTickSpacing(10);
			slider.setPaintTicks(true);
			slider.setPaintLabels(true);
		}
	    
	    // set slider properties
		slider.setValue(displayPanel.getNumber());
		
		// use switch-case as a selector to add one or other action listener
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
			case 1:
				slider.addChangeListener( event -> {
					int number = slider.getValue();
					value.setText(String.valueOf(number));
					displayPanel.setNumber(number);
				});
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
		/* just in case... */
		
		// use switch-case as a selector to add one or other action listener
		
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
	
	// OVERRIDE METHOD 'buildupDisplay' //
	
	@Override
	protected void buildupDisplay() {
		// set properties (coming soon...)
		displayPanel = new GameOfLifeDisplayPanel();
		
		// add panels
		this.add(centralPanel, BorderLayout.CENTER);
	}

}
