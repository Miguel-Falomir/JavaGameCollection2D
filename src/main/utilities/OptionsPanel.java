package main.utilities;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import main.Gui;

public abstract class OptionsPanel extends JPanel {
	
	// SERIAL VERSION IDENTIFIER //

	private static final long serialVersionUID = -195480968851890393L;
	
	// VARIABLES //
	
	protected GridBagConstraints gbcCentered = null;
	
	protected List<Dimension> buttonSize = null;
	protected List<Dimension> optionsPanelSize = null;
	protected List<Dimension> optionsTitleSize = null;
	protected List<Dimension> optionTitlePanelSize = null;
	protected List<Dimension> optionControllerPanelSize = null;
	protected List<Dimension> optionGapPanelSize = null;
	
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
		optionTitlePanelSize = Arrays.asList(
			new Dimension(200, 40),
			new Dimension(200, 40),
			new Dimension(200, 40)
		);
		optionControllerPanelSize = Arrays.asList(
			new Dimension(200, 25),
			new Dimension(200, 25),
			new Dimension(200, 25)
		);
		optionGapPanelSize = Arrays.asList(
			new Dimension(200, 20),
			new Dimension(200, 20),
			new Dimension(200, 20)
		);
		
		// set panel layouts
		this.setLayout(new BorderLayout());
		centralPanel = new JPanel(new BorderLayout());
		optionsPanel = new JPanel(new BorderLayout());
		optionsTopPanel = new JPanel(new GridBagLayout());
		optionsCenterPanel = new JPanel(new GridBagLayout());
		optionsCenterCenterPanel = new JPanel();
		optionsCenterCenterPanel.setLayout(
			new BoxLayout(optionsCenterCenterPanel, BoxLayout.Y_AXIS)
		);
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
	
	// METHOD GENERATE TEXTAREA COMPONENT //
	
	protected void generateTextField(JTextField textfield, JLabel title, JButton buton, int attributeIndex) {
		// declare panels
		JPanel titlePanel = new JPanel(new BorderLayout());
		JPanel controllerPanel = new JPanel(new BorderLayout());
		JPanel controllerTextareaPanel = new JPanel(new BorderLayout());
		JPanel controllerButtonPanel = new JPanel(new BorderLayout());
		JPanel gapPanel = new JPanel();
		
		// set all components
		title.setFont(title.getFont().deriveFont(18.0f));
		textfield.setFont(textfield.getFont().deriveFont(14.0f));
		textfield.setBorder(new EmptyBorder(2,4,2,4));
		buton.setFont(buton.getFont().deriveFont(16.0f));
		buton.setText(">");
		
		// title panel
		// set properties
		gui.setComponentSize(
			titlePanel,
			optionTitlePanelSize.get(0),
			optionTitlePanelSize.get(1),
			optionTitlePanelSize.get(2)
		);
		// add components
		titlePanel.add(title, BorderLayout.WEST);
		
		// textarea panel
		// set properties
		gui.setComponentSize(
			controllerPanel,
			optionControllerPanelSize.get(0),
			optionControllerPanelSize.get(1),
			optionControllerPanelSize.get(2)
		);
		controllerButtonPanel.setBorder(new EmptyBorder(0,10,0,0));
		// add components
		controllerTextareaPanel.add(textfield, BorderLayout.CENTER);
		controllerPanel.add(controllerTextareaPanel, BorderLayout.CENTER);
		controllerButtonPanel.add(buton, BorderLayout.CENTER);
		controllerPanel.add(controllerButtonPanel, BorderLayout.EAST);
		
		// gap panel
		// set properties
		gui.setComponentSize(
			gapPanel,
			optionGapPanelSize.get(0),
			optionGapPanelSize.get(1),
			optionGapPanelSize.get(2)
		);
		// add components
		/* just kidding, this panel is solely an empty spacer */
		
		// add panels to main panel
		optionsCenterCenterPanel.add(titlePanel);
		optionsCenterCenterPanel.add(controllerPanel);
		optionsCenterCenterPanel.add(gapPanel);
	}
	
	// METHOD GENERATE SLIDER COMPONENT //
	
	protected void generateSlider(JSlider slider, JLabel title, JLabel value, boolean useTicks, int attributeIndex) {
		// declare panels
		JPanel titlePanel = new JPanel(new BorderLayout());
		JPanel controllerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel gapPanel = new JPanel();

		// set all components
		title.setFont(title.getFont().deriveFont(18.0f));
		value.setFont(value.getFont().deriveFont(18.0f));
		
		// title panel
		// set properties
		gui.setComponentSize(
			titlePanel,
			optionTitlePanelSize.get(0),
			optionTitlePanelSize.get(1),
			optionTitlePanelSize.get(2)
		);
		// add components
		titlePanel.add(title, BorderLayout.WEST);
		titlePanel.add(new JPanel(), BorderLayout.CENTER); // this is just a spacer
		titlePanel.add(value, BorderLayout.EAST);
		
		// slider panel
		// set properties
		if (useTicks) {
			gui.setComponentSize(
				controllerPanel,
				new Dimension(200, 25),
				new Dimension(200, 25),
				new Dimension(200, 25)
			);
		} else {			
			gui.setComponentSize(
				controllerPanel,
				optionControllerPanelSize.get(0),
				optionControllerPanelSize.get(1),
				optionControllerPanelSize.get(2)
			);
		}
		// add components
		controllerPanel.add(slider);
		
		// gap panel
		// set properties
		gui.setComponentSize(
			gapPanel,
			optionGapPanelSize.get(0),
			optionGapPanelSize.get(1),
			optionGapPanelSize.get(2)
		);
		// add components
		/* just kidding, this panel is solely an empty spacer */
		
		// add panels to main panel
		optionsCenterCenterPanel.add(titlePanel);
		optionsCenterCenterPanel.add(controllerPanel);
		optionsCenterCenterPanel.add(gapPanel);
	}
	
	// METHOD GENERATE COMBO BOX //
	
	protected void generateComboBox(JComboBox<Item> combo, JLabel title, List<Item> list, int attributeIndex) {
		// declare panels
		JPanel titlePanel = new JPanel(new BorderLayout());
		JPanel controllerPanel = new JPanel(new BorderLayout());
		JPanel gapPanel = new JPanel();
		
		// set all components
		title.setFont(title.getFont().deriveFont(18.0f));
		for (Item item : list) {
			combo.addItem(item);
		}
		
		// title panel
		// set properties
		gui.setComponentSize(
			titlePanel,
			optionTitlePanelSize.get(0),
			optionTitlePanelSize.get(1),
			optionTitlePanelSize.get(2)
		);
		// add components
		titlePanel.add(title, BorderLayout.WEST);
		
		// combo box panel
		// set properties
		gui.setComponentSize(
			controllerPanel,
			optionControllerPanelSize.get(0),
			optionControllerPanelSize.get(1),
			optionControllerPanelSize.get(2)
		);
		// add components
		controllerPanel.add(combo, BorderLayout.CENTER);
		
		// gap panel
		// set properties
		gui.setComponentSize(
			gapPanel,
			optionGapPanelSize.get(0),
			optionGapPanelSize.get(1),
			optionGapPanelSize.get(2)
		);
		// add components
		/* just kidding, this panel is solely an empty spacer */
		
		// add panels to main panel
		optionsCenterCenterPanel.add(titlePanel);
		optionsCenterCenterPanel.add(controllerPanel);
		optionsCenterCenterPanel.add(gapPanel);
	}
	
	// METHOD GENERATE CHECK BOXES LIST //
	
	protected void generateCheckBoxesList(ArrayList<JCheckBox> check, JLabel title, List<Item> list, int attributeIndex) {
		// declare panels
		JPanel titlePanel = new JPanel(new BorderLayout());
		JPanel controllerPanel = new JPanel();
		controllerPanel.setLayout(
			new BoxLayout(controllerPanel, BoxLayout.Y_AXIS)
		);
		JPanel gapPanel = new JPanel();
		
		// set all components
		title.setFont(title.getFont().deriveFont(18.0f));
		for (Item item : list) {
			JCheckBox checkBox = new JCheckBox(item.toString());
			checkBox.setFont(checkBox.getFont().deriveFont(14.0f));
			checkBox.setName(item.id);
			check.add(checkBox);
		}
		
		// title panel
		// set properties
		gui.setComponentSize(
			titlePanel,
			optionTitlePanelSize.get(0),
			optionTitlePanelSize.get(1),
			optionTitlePanelSize.get(2)
		);
		// add components
		titlePanel.add(title, BorderLayout.WEST);
		
		// toggle buttons list panel
		// set properties
		int height = 30 * list.size();
		gui.setComponentSize(
			controllerPanel,
			new Dimension(200, height),
			new Dimension(200, height),
			new Dimension(200, height)
		);
		// add components
		for (JCheckBox box : check) {
			JPanel checkPanel = new JPanel(new BorderLayout());
			checkPanel.add(box, BorderLayout.WEST);
			controllerPanel.add(checkPanel);
		}
		
		// gap panel
		// set properties
		gui.setComponentSize(
			gapPanel,
			optionGapPanelSize.get(0),
			optionGapPanelSize.get(1),
			optionGapPanelSize.get(2)
		);
		// add components
		/* just kidding, this panel is solely an empty spacer */
		
		// add panels to main panel
		optionsCenterCenterPanel.add(titlePanel);
		optionsCenterCenterPanel.add(controllerPanel);
		optionsCenterCenterPanel.add(gapPanel);
	}
	
	// METHOD BUILDUP DISPLAY PANEL //
	
	protected void buildupDisplay() {}

}
