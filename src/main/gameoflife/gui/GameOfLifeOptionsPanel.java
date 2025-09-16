package main.gameoflife.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import main.Gui;
import main.utilities.MenuPanel;
import main.utilities.OptionsPanel;

public class GameOfLifeOptionsPanel extends OptionsPanel{
	
	// SERIAL VERSION IDENTIFIER //

	private static final long serialVersionUID = 7388218801567522611L;
	
	/**
	 * Note: Adidas-like (///) comments refer to protected objects from parent class
	 */
	
	// VARIABLES //
	
	///protected GridBagConstraints gbcCentered = null;

	///protected List<Dimension> buttonSize = null;
	///protected List<Dimension> optionsPanelSize = null;
	///protected List<Dimension> optionsTitleSize = null;
	
	// UI COMPONENTS //
	
	///protected Gui gui = null;
	
	///protected JPanel centralPanel = null;
	///protected JPanel optionsPanel = null;
	///protected JPanel optionsTopPanel = null;
	///protected JPanel optionsCenterPanel = null;
	///protected JPanel optionsCenterCenterPanel = null;
	///protected JPanel optionsCenterBottomPanel = null;
	///protected JPanel optionsBottomPanel = null;
	
	///protected JLabel jlabel_title = null;
	private JLabel gridRangeValue = null;
	
	///protected JButton jbuton_back = null;

	private JSlider gridRangeSlider = null;
	
	private GameOfLifeDisplayPanel displayPanel = null;
	
	// CONSTRUCTOR //

	public GameOfLifeOptionsPanel(Gui gui) {
		// initialize attributes from parent class
		super(gui);
		
		// set private panels
		optionsCenterCenterPanel = new JPanel();
		optionsCenterCenterPanel.setLayout(
			new BoxLayout(optionsCenterCenterPanel, BoxLayout.Y_AXIS)
		);
		optionsCenterBottomPanel = new JPanel(new GridBagLayout());
		
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
			new JSlider(),
			new JLabel(gui.getMessages().getString("gameoflife_Title") + " :"),
			new JLabel(String.valueOf(displayPanel.getNumber())),
			1
		);
	}
	
	// METHOD GENERATE OPTION WITH SLIDER //
	
	@Override
	protected void generateSlider(JSlider slider, JLabel title, JLabel value, int attributeIndex) {
		// initialize
		super.generateSlider(slider, title, value, attributeIndex);
		
		// set slider properties
		slider.setValue(displayPanel.getNumber());
		
		// implement switch-case within change listener as an action selector
		/**
		 * This is the simplest solution I could come with. I wish I knew more about
		 * lambda expressions, but even in that case all StackOverflow solution seemed
		 * to be too much verbose for me. For those who doesn't know (I googled it 10
		 * minutes before writing this comment) verbosity is the abundance or excess of
		 * word, for instance:
		 * (normal)  Karen drives a big car.
		 * (verbose) The woman named karen happens to get herself to places by driving a car of big proportions.
		 */
		slider.addChangeListener( event -> {
			int number = slider.getValue();
			value.setText(String.valueOf(number));
			switch (attributeIndex) {
				case 1: {
					displayPanel.setNumber(number);
					break;
				}
				default:
					throw new IllegalArgumentException("Unexpected value: " + attributeIndex);
			}
		});
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
