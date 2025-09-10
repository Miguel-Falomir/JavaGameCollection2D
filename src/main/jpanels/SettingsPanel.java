package main.jpanels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.tools.DocumentationTool.Location;

import main.Gui;

public class SettingsPanel extends JPanel {
	
	// SERIAL VERSION IDENTIFIER //

	private static final long serialVersionUID = 732858372691824155L;
	
	// CUSTOM CLASSES FOR COMBOBOX ITEMS //
	
	private class Item {}
	
	private class LocationItem extends Item {
		// attributes
		private String id;
		private String name;
		private Locale locale;
		
		// getters and setters
		public String getId() {return id;}
		//public void setId(String id) {this.id = id;}
		//public String getName() {return name;}
		public void setName(String name) {this.name = name;}
		public Locale getLocale() {return locale;}
		//public void setLocale(Locale locale) {this.locale = locale;}
		
		// constructor
		public LocationItem (String id, Locale locale) {
			this.id = id;
			this.locale = locale;
		}
		
		// override method 'toString'
		@Override
		public String toString() {
			return name;
		}
	}
	
	private class LookAndFeelItem extends Item {
		// attributes
		private String id;
		private String name;
		private String lookAndFeel;
		
		// getters and setters
		public String getId() {return id;}
		//public void setId(String id) {this.id = id;}
		//public String getName() {return name;}
		public void setName(String name) {this.name = name;}
		public String getLookAndFeel() {return lookAndFeel;}
		//public void setLookAndFeel(String lookAndFeel) {this.lookAndFeel = lookAndFeel;}
		
		// constructor
		public LookAndFeelItem (String id, String lookAndFeel) {
			this.id = id;
			this.lookAndFeel = lookAndFeel;
		}
		
		// override method 'toString'
		@Override
		public String toString() {
			return name;
		}
	}
	
	// VARIABLES //
	
	private LocationItem[] locList = {
		new LocationItem("en_US", new Locale("en", "US")),
		new LocationItem("es_ES", new Locale("es", "ES")),
		new LocationItem("fr_FR", new Locale("fr", "FR"))
	};
	
	private LookAndFeelItem[] lafList = {
		new LookAndFeelItem("Metal", "javax.swing.plaf.metal.MetalLookAndFeel"),
		new LookAndFeelItem("Nimbus", "javax.swing.plaf.nimbus.NimbusLookAndFeel"),
		new LookAndFeelItem("Motif", "com.sun.java.swing.plaf.motif.MotifLookAndFeel"),
		new LookAndFeelItem("GTK", "com.sun.java.swing.plaf.gtk.GTKLookAndFeel")
	};
	
	// UI COMPONENTS //
	
	Gui gui = null;
	
	// CONSTRUCTOR //
	
	public SettingsPanel (Gui gui) {
		// set ui components
		this.gui = gui;
		
		// set variables
		for (LocationItem locitem : locList) {
			String name = locitem.getId();
			locitem.setName(gui.getMessages().getString(name));
		}
		for (LookAndFeelItem lafitem : lafList) {
			String name = lafitem.getId();
			lafitem.setName(name);
		}
		
		// build-up JPanel components
		this.buildup();
		
		// mock-up JPanel in gui
		this.gui.mockup(this);
	}
	
	// METHOD BUILD SETTINGS MENU //
	
	private void buildup() {
		// set JPanel properties
		this.setLayout(new BorderLayout());
		
		// declare variables
		Dimension[] settingComboSize = {
			new Dimension(150, 30),	// minimum size
			new Dimension(150, 30),	// preferred size
			new Dimension(300, 60)	// maximum size
		};
		Dimension[] optionBtnSize = {
			new Dimension(100, 50),
			new Dimension(100, 50),
			new Dimension(100, 50)
		};
		GridBagConstraints gbcCenter = new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0);
		
		// declare panels
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel centerPanel = new JPanel(new GridLayout(1, 2));
		JPanel bottomPanel = new JPanel(new GridLayout(1, 1));
		
		// top
		// set properties
		/* or not */
		// set components
		JLabel title = new JLabel(gui.getMessages().getString("settingsTitle"));
		title.setFont(title.getFont().deriveFont(24.0f));
		title.setBorder(new EmptyBorder(20, 20, 20, 20));
		//add components
		topPanel.add(title);
		
		// center
		// set properties
		/* or not */
		// set components
		JPanel[] settingPanelList = {
			new JPanel(),
			new JPanel()
		};
		JLabel[] settingLabelList = {
			new JLabel(gui.getMessages().getString("languageLabel")),
			new JLabel(gui.getMessages().getString("lookandfeelLabel"))
		};
		List<JComboBox<? extends Item>> settingComboList = Arrays.asList(
			new JComboBox<LocationItem>(locList),
			new JComboBox<LookAndFeelItem>(lafList)
		);
		// add components
		for (int i = 0 ; i < 2; i++) {
			// set vertical layout
			settingPanelList[i].setLayout(
				new BoxLayout(settingPanelList[i], BoxLayout.Y_AXIS)
			);
			// add JLabel
			settingLabelList[i].setFont(settingLabelList[i].getFont().deriveFont(18.0f));
			settingLabelList[i].setMinimumSize(settingComboSize[0]);
			settingLabelList[i].setPreferredSize(settingComboSize[1]);
			settingLabelList[i].setMaximumSize(settingComboSize[2]);
			settingPanelList[i].add(settingLabelList[i]);
			// add some spacer
			JPanel spacer = new JPanel();
			spacer.setPreferredSize(new Dimension(100, 10));
			settingPanelList[i].add(spacer);
			// add JComboBox
			settingComboList.get(i).setMinimumSize(settingComboSize[0]);
			settingComboList.get(i).setPreferredSize(settingComboSize[1]);
			settingComboList.get(i).setMaximumSize(settingComboSize[2]);
			settingPanelList[i].add(settingComboList.get(i));
			// pack within centered JPanel
			JPanel pan = new JPanel(new FlowLayout(FlowLayout.CENTER));
			pan.add(settingPanelList[i]);
			centerPanel.add(pan);
		}
		// manually set each initially selected item
		settingComboList.get(0).setSelectedItem(initialLocale());
		settingComboList.get(1).setSelectedItem(initialLookAndFeel());

		// bottom
		// set properties
		bottomPanel.setPreferredSize(new Dimension(100, 100));
		// set components
		JButton[] optionButtonList = {
			new JButton(gui.getMessages().getString("back")),
			new JButton(gui.getMessages().getString("save"))
		};
		for (JButton btn : optionButtonList) {
			btn.setMinimumSize(optionBtnSize[0]);
			btn.setPreferredSize(optionBtnSize[1]);
			btn.setMaximumSize(optionBtnSize[2]);
		}
		// add events
		optionButtonList[0].addActionListener(event -> {
			backToMenuPopUp();
		});
		optionButtonList[1].addActionListener(event -> {
			saveDataPopUp();
		});
		// add components
		for (JButton btn : optionButtonList) {
			JPanel pan = new JPanel(new GridBagLayout());
			pan.add(btn, gbcCenter);
			bottomPanel.add(pan);
		}
		
		// add action listeners at the very end to ensure every UI component is recognized
		settingComboList.get(0).addActionListener(
			event -> {
				// set Locale
				LocationItem loci = (LocationItem) settingComboList.get(0).getSelectedItem();
				gui.setLocale(loci.getLocale());
				// manually update all texts
				/* This might look unskilled, but listen for a moment:
				 * When I swap to another JPanel, those texts update fine. But in this
				 * current JPanel, they don't. So the simplest solution I came with was
				 * to manually update all texts as this JPanel happens to have few.
				 * Also, I don't master the 'ResourceBundle' class :(
				 */
				title.setText(gui.getMessages().getString("settingsTitle"));
				for (LocationItem locitem : locList) {
					String name = locitem.getId();
					locitem.setName(gui.getMessages().getString(name));
				}
				for (LookAndFeelItem lafitem : lafList) {
					String name = lafitem.getId();
					lafitem.setName(name);
				}
				settingLabelList[0].setText(gui.getMessages().getString("languageLabel"));
				settingLabelList[1].setText(gui.getMessages().getString("lookandfeelLabel"));
				optionButtonList[0].setText(gui.getMessages().getString("back"));
				optionButtonList[1].setText(gui.getMessages().getString("save"));
			}
		);
		settingComboList.get(1).addActionListener(
			event -> {
				// set LookAndFeel
				LookAndFeelItem lafi = (LookAndFeelItem) settingComboList.get(1).getSelectedItem();
				gui.setLookAndFeel(lafi.getLookAndFeel());
			}
		);
		
		// add panels to container
		this.add(topPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);
	}
	
	// METHOD GET INITIAL LOCALE FROM LOCLIST //
	
	private LocationItem initialLocale() {
		String name = gui.getLocaleName();
		switch (name) {
			case ("es_ES"):
				return locList[1];
			default:
				return locList[0];
		}
	}
	
	// METHOD GET INITIAL LOOKANDFEEL FROM LAFLIST //
	
	private LookAndFeelItem initialLookAndFeel() {
		String name = gui.getLookAndFeelName();
		switch (name) {
			case ("Nimbus"):
				return lafList[1];
			case ("Motif"):
				return lafList[2];
			case ("GTK"):
				return lafList[3];
			default:
				return lafList[0];
		}
	}
	
	// METHOD SAVE DATA //
	
	private void saveDataPopUp() {
		gui.writeSettings();
		gui.mockup(new MenuPanel(gui));
	}
	
	// METHOD GO BACK //
	
	private void backToMenuPopUp() {
		gui.readSettings();
		gui.mockup(new MenuPanel(gui));
	}

}
