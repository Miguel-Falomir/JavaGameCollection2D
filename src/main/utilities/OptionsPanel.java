package main.utilities;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import main.Gui;

public abstract class OptionsPanel extends JPanel {
	
	// SERIAL VERSION IDENTIFIER //

	private static final long serialVersionUID = -195480968851890393L;
	
	// VARIABLES //
	
	protected GridBagConstraints gbcCentered = null;
	
	protected List<Dimension> buttonSize = null;
	protected List<Dimension> optionsPanelSize = null;
	protected List<Dimension> optionsTitleSize = null;
	
	// UI COMPONENTS //
	
	protected Gui gui = null;
	
	protected JPanel centralPanel = null;
	protected JPanel optionsPanel = null;
	protected JPanel optionsTopPanel = null;
	protected JPanel optionsCenterPanel = null;
	protected JPanel optionsCenterCenterPanel = null;
	protected JPanel optionsCenterBottomPanel = null;
	protected JPanel optionsBottomPanel = null;
	
	protected JLabel jlabel_title = null;
	
	protected JButton jbuton_back = null;
	
	// CONSTRUCTOR //
	
	public OptionsPanel(Gui gui) {
		// set ui components
		this.gui = gui;
		
		// set variables
		gbcCentered = gui.getGridBagConstraintsCentered();
		buttonSize = gui.getStandardSize(4);
		optionsPanelSize = gui.getStandardSize(1);
		optionsTitleSize = gui.getStandardSize(5);
		
		// set panel layouts
		this.setLayout(new BorderLayout());
		centralPanel = new JPanel(new BorderLayout());
		optionsPanel = new JPanel(new BorderLayout());
		optionsTopPanel = new JPanel(new GridBagLayout());
		optionsCenterPanel = new JPanel(new GridBagLayout());
		optionsBottomPanel = new JPanel(new GridBagLayout());
	}
	
	// METHOD BUILDUP OPTIONS PANEL //
	
	protected void buildupOptions() {
		// main panel
		// set properties
		gui.setComponentSize(
			optionsPanel,
			optionsPanelSize.get(0),
			optionsPanelSize.get(1),
			optionsPanelSize.get(2)
		);
		
		// top panel
		// set properties
		gui.setComponentSize(
			optionsTopPanel,
			optionsTitleSize.get(0),
			optionsTitleSize.get(1),
			optionsTitleSize.get(2)
		);
		// set components
		jlabel_title = new JLabel(gui.getMessages().getString("gameoflife_Title"));
		jlabel_title.setFont(jlabel_title.getFont().deriveFont(24.0f));
		// add components
		optionsTopPanel.add(jlabel_title, gbcCentered);
		
		// center panel
		// set properties
		gui.setComponentSize(
			optionsCenterCenterPanel,
			new Dimension(200, 600),
			new Dimension(200, 600),
			new Dimension(200, 600)
		);
		optionsCenterPanel.add(optionsCenterCenterPanel, gbcCentered);
		
		// bottom panel
		// set properties
		gui.setComponentSize(
			optionsBottomPanel,
			optionsTitleSize.get(0),
			optionsTitleSize.get(1),
			optionsTitleSize.get(2)
		);
		// set components
		jbuton_back = new JButton(gui.getMessages().getString("back_Buton"));
		gui.setComponentSize(
			jbuton_back,
			buttonSize.get(0),
			buttonSize.get(1),
			buttonSize.get(2)
		);
		jbuton_back.addActionListener( event -> {
			gui.mockup(new MenuPanel(gui));
		});
		// add components
		optionsBottomPanel.add(jbuton_back);
		
		// add panels
		optionsPanel.add(optionsTopPanel, BorderLayout.NORTH);
		optionsPanel.add(optionsCenterPanel, BorderLayout.CENTER);
		optionsPanel.add(optionsBottomPanel, BorderLayout.SOUTH);
		this.add(optionsPanel, BorderLayout.WEST);
	}
	
	// METHOD GENERATE SLIDER COMPONENT //
	
	protected void generateSlider(JSlider slider, JLabel title, JLabel value, int attributeIndex) {
		// declare panels
		JPanel titlePanel = new JPanel(new BorderLayout());
		JPanel sliderPanel = new JPanel();
		
		// set all components
		title.setFont(title.getFont().deriveFont(18.0f));
		value.setFont(value.getFont().deriveFont(18.0f));
		
		// title panel
		// set properties
		gui.setComponentSize(
			titlePanel,
			new Dimension(200, 40),
			new Dimension(200, 40),
			new Dimension(200, 40)
		);
		// add components
		titlePanel.add(title, BorderLayout.WEST);
		titlePanel.add(new JPanel(), BorderLayout.CENTER); // this is just a spacer
		titlePanel.add(value, BorderLayout.EAST);
		
		// slider panel
		// set properties
		gui.setComponentSize(
			sliderPanel,
			new Dimension(200, 40),
			new Dimension(200, 40),
			new Dimension(200, 40)
		);
		// add components
		sliderPanel.add(slider);
		
		// add panels to main panel
		optionsCenterCenterPanel.add(titlePanel);
		optionsCenterCenterPanel.add(sliderPanel);
	}
	
	// METHOD BUILDUP DISPLAY PANEL //
	
	protected void buildupDisplay() {}

}
