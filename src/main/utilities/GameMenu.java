package main.utilities;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

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

public abstract class GameMenu extends JPanel {
	
	// SERIAL VERSION IDENTIFIER //

	private static final long serialVersionUID = -195480968851890393L;
	
	// VARIABLES //
	
	protected GridBagConstraints gbcCentered = null;
	
	protected Dimension[] buttonSize = null;
	protected Dimension[] optionsPanelSize = null;
	protected Dimension[]optionsTitleSize = null;
	protected Dimension[] optionTitlePanelSize= {
		new Dimension(200, 40),
		new Dimension(200, 40),
		new Dimension(200, 40)
	};
	protected Dimension[] optionControllerPanelSize = {
		new Dimension(200, 25),
		new Dimension(200, 25),
		new Dimension(200, 25)
	};
	protected Dimension[] optionGapPanelSize = {
		new Dimension(200, 20),
		new Dimension(200, 20),
		new Dimension(200, 20)
	};
	
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
	
	protected JButton jbutton_back = null;
	
	// GAME COMPONENTS //
	
	protected GameDisplay display = null;
	
	protected GameThread gameThread = null;
	
	///protected Thread thread = null;
	
	// CONSTRUCTOR //
	
	public GameMenu(Gui gui) {
		// set ui components
		this.gui = gui;
		
		// set variables
		gbcCentered = gui.getGridBagConstraintsCentered();
		buttonSize = gui.getStandardSize(4);
		optionsPanelSize = gui.getStandardSize(1);
		optionsTitleSize = gui.getStandardSize(5);
		
		// set panel layouts
		this.setLayout(new BorderLayout());
		centralPanel = new JPanel(new GridBagLayout());
		optionsPanel = new JPanel(new BorderLayout());
		optionsTopPanel = new JPanel(new GridBagLayout());
		optionsCenterPanel = new JPanel(new BorderLayout());
		optionsCenterCenterPanel = new JPanel();
		optionsCenterCenterPanel.setLayout(
			new BoxLayout(optionsCenterCenterPanel, BoxLayout.Y_AXIS)
		);
		optionsCenterBottomPanel = new JPanel(new GridBagLayout());
		optionsBottomPanel = new JPanel(new GridBagLayout());
	}
	
	// METHOD BUILDUP OPTIONS PANEL //
	
	protected void buildupMenu() {
		// main panel
		// set properties
		gui.setComponentSize(
			optionsPanel,
			optionsPanelSize[0],
			optionsPanelSize[1],
			optionsPanelSize[2]
		);
		
		// top panel
		// set properties
		gui.setComponentSize(
			optionsTopPanel,
			optionsTitleSize[0],
			optionsTitleSize[1],
			optionsTitleSize[2]
		);
		// set components
		jlabel_title = new JLabel(gui.getMessages().getString("gameoflife_Title"));
		jlabel_title.setFont(jlabel_title.getFont().deriveFont(24.0f));
		// add components
		optionsTopPanel.add(jlabel_title, gbcCentered);
		
		// center panel
		// set properties
		gui.setComponentSize(
			optionsCenterPanel,
			new Dimension(300, 900),
			new Dimension(300, 900),
			new Dimension(300, 900)
		);
		// set components
		gui.setComponentSize(
			optionsCenterBottomPanel,
			new Dimension(300, 70),
			new Dimension(300, 70),
			new Dimension(300, 70)
		);
		// add panels
		optionsCenterPanel.add(optionsCenterCenterPanel, BorderLayout.CENTER);
		optionsCenterPanel.add(optionsCenterBottomPanel, BorderLayout.SOUTH);
		
		// bottom panel
		// set properties
		gui.setComponentSize(
			optionsBottomPanel,
			optionsTitleSize[0],
			optionsTitleSize[1],
			optionsTitleSize[2]
		);
		// set components
		jbutton_back = new JButton(gui.getMessages().getString("back_Buton"));
		gui.setComponentSize(
			jbutton_back,
			buttonSize[0],
			buttonSize[1],
			buttonSize[2]
		);
		// add action listener, to come back to main menu
		jbutton_back.addActionListener( event -> {gui.mockup(new MainMenu(gui));});
		// add components
		optionsBottomPanel.add(jbutton_back);
		
		// add panels
		optionsPanel.add(optionsTopPanel, BorderLayout.NORTH);
		optionsPanel.add(optionsCenterPanel, BorderLayout.CENTER);
		optionsPanel.add(optionsBottomPanel, BorderLayout.SOUTH);
		this.add(optionsPanel, BorderLayout.WEST);
	}
	
	// METHOD BUILDUP DISPLAY PANEL //
	
	protected void buildupDisplay() {}
	
	// METHOD EXECUTE GAME THREAD //
	
	protected void executeThread() {}
	
	// METHOD GENERATE TEXTAREA COMPONENT //
	
	protected void generateTextField(JTextField textfield, JLabel title, JButton button, int attributeIndex) {
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
		button.setFont(button.getFont().deriveFont(16.0f));
		button.setText(">");
		
		// title panel
		// set properties
		gui.setComponentSize(
			titlePanel,
			optionTitlePanelSize[0],
			optionTitlePanelSize[1],
			optionTitlePanelSize[2]
		);
		// add components
		titlePanel.add(title, BorderLayout.WEST);
		
		// textarea panel
		// set properties
		gui.setComponentSize(
			controllerPanel,
			optionControllerPanelSize[0],
			optionControllerPanelSize[1],
			optionControllerPanelSize[2]
		);
		controllerButtonPanel.setBorder(new EmptyBorder(0,10,0,0));
		// add components
		controllerTextareaPanel.add(textfield, BorderLayout.CENTER);
		controllerPanel.add(controllerTextareaPanel, BorderLayout.CENTER);
		controllerButtonPanel.add(button, BorderLayout.CENTER);
		controllerPanel.add(controllerButtonPanel, BorderLayout.EAST);
		
		// gap panel
		// set properties
		gui.setComponentSize(
			gapPanel,
			optionGapPanelSize[0],
			optionGapPanelSize[1],
			optionGapPanelSize[2]
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
			optionTitlePanelSize[0],
			optionTitlePanelSize[1],
			optionTitlePanelSize[2]
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
				new Dimension(200, 45),
				new Dimension(200, 45),
				new Dimension(200, 45)
			);
		} else {			
			gui.setComponentSize(
				controllerPanel,
				optionControllerPanelSize[0],
				optionControllerPanelSize[1],
				optionControllerPanelSize[2]
			);
		}
		// add components
		controllerPanel.add(slider);
		
		// gap panel
		// set properties
		gui.setComponentSize(
			gapPanel,
			optionGapPanelSize[0],
			optionGapPanelSize[1],
			optionGapPanelSize[2]
		);
		// add components
		/* just kidding, this panel is solely an empty spacer */
		
		// add panels to main panel
		optionsCenterCenterPanel.add(titlePanel);
		optionsCenterCenterPanel.add(controllerPanel);
		optionsCenterCenterPanel.add(gapPanel);
	}
	
	// METHOD GENERATE COMBO BOX //
	
	protected void generateComboBox(JComboBox<Item> combo, JLabel title, Item[] list, int attributeIndex) {
		// declare panels
		JPanel titlePanel = new JPanel(new BorderLayout());
		JPanel controllerPanel = new JPanel(new BorderLayout());
		JPanel gapPanel = new JPanel();
		
		// title panel
		// set properties
		gui.setComponentSize(
			titlePanel,
			optionTitlePanelSize[0],
			optionTitlePanelSize[1],
			optionTitlePanelSize[2]
		);
		// set components
		title.setFont(title.getFont().deriveFont(18.0f));
		// add components
		titlePanel.add(title, BorderLayout.WEST);
		
		// combo box panel
		// set properties
		gui.setComponentSize(
			controllerPanel,
			optionControllerPanelSize[0],
			optionControllerPanelSize[1],
			optionControllerPanelSize[2]
		);
		// set components
		for (Item item : list) {
			combo.addItem(item);
		}
		// add components
		controllerPanel.add(combo, BorderLayout.CENTER);
		
		// gap panel
		// set properties
		/* or not... */
		// set components
		gui.setComponentSize(
			gapPanel,
			optionGapPanelSize[0],
			optionGapPanelSize[1],
			optionGapPanelSize[2]
		);
		// add components
		/* just kidding, this panel is solely an empty spacer */
		
		// add panels to main panel
		optionsCenterCenterPanel.add(titlePanel);
		optionsCenterCenterPanel.add(controllerPanel);
		optionsCenterCenterPanel.add(gapPanel);
	}
	
	// METHOD GENERATE CHECK BOXES LIST //
	
	protected void generateCheckBoxesList(ArrayList<JCheckBox> check, JLabel title, Item[] list, int attributeIndex) {
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
			optionTitlePanelSize[0],
			optionTitlePanelSize[1],
			optionTitlePanelSize[2]
		);
		// add components
		titlePanel.add(title, BorderLayout.WEST);
		
		// toggle buttons list panel
		// set properties
		int height = 30 * list.length;
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
			optionGapPanelSize[0],
			optionGapPanelSize[1],
			optionGapPanelSize[2]
		);
		// add components
		/* just kidding, this panel is solely an empty spacer */
		
		// add panels to main panel
		optionsCenterCenterPanel.add(titlePanel);
		optionsCenterCenterPanel.add(controllerPanel);
		optionsCenterCenterPanel.add(gapPanel);
	}

}
