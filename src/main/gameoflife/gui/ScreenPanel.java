package main.gameoflife.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import main.Gui;
import main.jpanels.MenuPanel;

public class ScreenPanel extends JPanel {
	
	// SERIAL VERSION IDENTIFIER //

	private static final long serialVersionUID = 2869916836838460159L;
	
	// VARIABLES //
	
	Dimension[] buttonSize = {
		new Dimension(100, 50),
		new Dimension(100, 50),
		new Dimension(100, 50)
	};
	Dimension[] miniButtonSize = {
		new Dimension(50, 20),
		new Dimension(50, 20),
		new Dimension(50, 20)
	};
	Dimension[] optionCenterSize = {
		new Dimension(300, 300),
		new Dimension(300, 300),
		new Dimension(300, 300)
	};
	Dimension[] optionSize = {
		new Dimension(250, 200)
	};
	Dimension[] optionBottomSize = {
		new Dimension(100, 100),
		new Dimension(100, 100),
		new Dimension(100, 100)
	};
	GridBagConstraints gbcCenter = new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
	
	// UI COMPONENTS //
	
	Gui gui = null;
	
	// CONSTRUCTOR //
	
	public ScreenPanel (Gui gui) {
		// set ui components
		this.gui = gui;
		
		// build-up JPanel components
		this.buildup();
		
		// mock-up JPanel in gui
		this.gui.mockup(this);
	}
	
	// METHOD BUILD SCREEN //
	
	private void buildup() {
		// set JPanel properties
		this.setLayout(new BorderLayout());
		
		// declare panels
		JPanel[] panelList = {
			new JPanel(new BorderLayout()),		// [MOP] main options panel, goes to left of the screen
			new JPanel(),						// [COP] central options panel, goes inside MOP
			new JPanel(),						// [BLO] box layout options, goes inside COP
			new JPanel(new GridBagLayout()),	// [BOP] bottom options panel, goes inside MOP
			new JPanel(new GridBagLayout()),	// [CEN] center panel, goes to center of the screen
			new DisplayPanel(this)				// [DIS] display panel, goes inside CEN. Displays current game
		};
		
		// declare labels
		JLabel[] labelList = {
			new JLabel(gui.getMessages().getString("gameoflife_Title"))
		};
		
		// declare JButtons
		JButton[] buttonList = {
			new JButton(gui.getMessages().getString("back_Buton"))	// back to main menu
		};
		
		// declare ActionListeners
		ActionListener[] actionList = {
			new ActionListener() { // back to main menu
				@Override
				public void actionPerformed(ActionEvent e) {
					gui.mockup(new MenuPanel(gui));
				}
			}
		};
		
		// set properties of each JButton
		for (int i = 0; i < buttonList.length; i++) {
			setComponentSize(buttonList[i], buttonSize);
			buttonList[i].addActionListener(actionList[i]);
		}
		
		// options (left)
		// set properties
		setComponentSize(panelList[0], optionCenterSize);						// [MOP]
		panelList[0].setBackground(Color.GREEN);
		setComponentSize(panelList[1], optionCenterSize);						// [COP]
		panelList[1].setOpaque(false);
		setComponentSize(panelList[2], optionSize);								// [BLO]
		panelList[2].setLayout(new BoxLayout(panelList[2], BoxLayout.Y_AXIS));
		panelList[2].setOpaque(false);
		setComponentSize(panelList[3], optionBottomSize);						// [BOP]
		panelList[3].setOpaque(false);
		// add components to [BLO]
		labelList[0].setFont(labelList[0].getFont().deriveFont(24.0f));
		labelList[0].setBorder(new EmptyBorder(20, 20, 20, 20));
		panelList[2].add(labelList[0]);
		panelList[2].add(gridRangePanel( (DisplayPanel) panelList[5]));
		// add [BLO] inside [COP]
		panelList[1].add(panelList[2]);
		// add components to [BOP]
		panelList[3].add(buttonList[0], gbcCenter);
		// add JPanels to [MOP]
		panelList[0].add(panelList[1], BorderLayout.CENTER);
		panelList[0].add(panelList[3], BorderLayout.SOUTH);
		
		// game (center)
		// initialize [DIS] inside [CEN]
		panelList[4].add(panelList[5], gbcCenter);
		
		// add JPanels to [CEN]
		this.add(panelList[0], BorderLayout.LINE_START);
		this.add(panelList[4], BorderLayout.CENTER);
	}
	
	// METHOD BUILD UP GRID RANGE SETTING PANEL //
	
	private JPanel gridRangePanel(DisplayPanel display) {
		// declare JPanels
		JPanel[] panList = {
			new JPanel(),	// main panel
			new JPanel(),	// panel for label
			new JPanel()	// panel for controller
		};
		
		// set properties of each JPanel
		panList[0].setLayout(new BoxLayout(panList[0], BoxLayout.Y_AXIS));
		panList[1].setLayout(new BorderLayout());
		panList[2].setLayout(new FlowLayout());
		panList[2].setBorder(new EmptyBorder(10, 5, 10, 5));
		
		// add components to panList[1] (label)
		// title
		JLabel title = new JLabel("Grid Range:");
		title.setFont(title.getFont().deriveFont(18.0f));
		panList[1].add(title, BorderLayout.CENTER);
		// grid range value
		JLabel value = new JLabel(String.valueOf(display.getGridRange()));
		value.setFont(title.getFont().deriveFont(18.0f));
		panList[1].add(value, BorderLayout.EAST);
		
		// add components to panList[2] (controller)
		// slider
		JSlider slider = new JSlider(
			display.getGridRange(),	// min
			128,					// max
			display.getGridRange()	// preset value
		);
		slider.addChangeListener( event -> {
			int newValue = slider.getValue();
			value.setText(String.valueOf(newValue));
		});
		panList[2].add(slider);
		
		// add JPanels to main panel
		panList[1].setBackground(Color.cyan);
		panList[2].setBackground(Color.red);
		panList[0].add(panList[1]);
		panList[0].add(panList[2]);
		
		// return main JPanel
		return panList[0];
	}
	
	// METHOD SET SIZE OF UI COMPONENT //
	
	private void setComponentSize(Object obj, Dimension[] dim) {
		if (obj instanceof JPanel) {
			JPanel pan = (JPanel) obj;
			switch (dim.length) {
			case 1:
				pan.setPreferredSize(dim[0]);
				break;
			case 2:
				pan.setMinimumSize(dim[0]);
				pan.setPreferredSize(dim[1]);
				break;
			case 3:
				pan.setMinimumSize(dim[0]);
				pan.setPreferredSize(dim[1]);
				pan.setMaximumSize(dim[2]);
				break;
			default:
				System.out.println("No size can be applied.\nMake sure that the Dimension array has [1 - 3] objects.");
				break;
			}
		} else if (obj instanceof JButton) {
			JButton btn = (JButton) obj;
			switch (dim.length) {
			case 1:
				btn.setPreferredSize(dim[0]);
				break;
			case 2:
				btn.setMinimumSize(dim[0]);
				btn.setPreferredSize(dim[1]);
				break;
			case 3:
				btn.setMinimumSize(dim[0]);
				btn.setPreferredSize(dim[1]);
				btn.setMaximumSize(dim[2]);
				break;
			default:
				System.out.println("No size can be applied.\nMake sure that the Dimension array has [1 - 3] objects.");
				break;
			}
		} else {
			System.out.println("This object is untreatable. Please enter a JPanel or JButton component.");
		}
	}
}
