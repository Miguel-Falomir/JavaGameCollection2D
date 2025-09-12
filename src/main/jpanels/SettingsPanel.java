package main.jpanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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
		public void setName(String name) {this.name = name;}
		public Locale getLocale() {return locale;}
		
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
	
	private LocationItem[] locationList = {
		new LocationItem("en_US", new Locale("en", "US")),
		new LocationItem("es_ES", new Locale("es", "ES")),
		new LocationItem("fr_FR", new Locale("fr", "FR"))
	};
	private LookAndFeelItem[] lookAndFeelList = {
		new LookAndFeelItem("Metal", "javax.swing.plaf.metal.MetalLookAndFeel"),
		new LookAndFeelItem("Nimbus", "javax.swing.plaf.nimbus.NimbusLookAndFeel"),
		new LookAndFeelItem("Motif", "com.sun.java.swing.plaf.motif.MotifLookAndFeel"),
		new LookAndFeelItem("GTK", "com.sun.java.swing.plaf.gtk.GTKLookAndFeel")
	};	
	
	// UI COMPONENTS //
	
	Gui gui = null;
	
	JPanel jpanel_top = new JPanel();
	JPanel jpanel_center = new JPanel();
	JPanel jpanel_setting_location = new JPanel();
	JPanel jpanel_setting_lookandfeel = new JPanel();
	JPanel jpanel_bottom = new JPanel();
	JPanel[] jpanel_gridbag = {
		/* need one JPanel for each use. If you used the same JPanel more times,
		 * last iteration would override the previous ones.
		 */
		new JPanel(),
		new JPanel()
	};
	
	JLabel jlabel_page_title = new JLabel();
	JLabel jlabel_setting_location = new JLabel();
	JLabel jlabel_setting_lookandfeel = new JLabel();
	
	JButton jbuton_back = new JButton();
	JButton jbuton_save = new JButton();
	
	JComboBox<LocationItem> jcombo_setting_location = new JComboBox<LocationItem>();
	JComboBox<LookAndFeelItem> jcombo_setting_lookandfeel = new JComboBox<LookAndFeelItem>();
	
	// CONSTRUCTOR //
	
	public SettingsPanel (Gui gui) {
		// set ui components
		this.gui = gui;
		
		// set variables
		for (LocationItem locitem : locationList) {
			String name = locitem.getId();
			locitem.setName(gui.getMessages().getString(name));
		}
		for (LookAndFeelItem lafitem : lookAndFeelList) {
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
		// set main panel layout
		this.setLayout(new BorderLayout());
		
		// jpanel_top
		// set panel properties
		jpanel_top.setLayout(new FlowLayout(FlowLayout.CENTER));
		// set component properties
		jlabel_page_title.setText(gui.getMessages().getString("settings_Title"));
		jlabel_page_title.setFont(jlabel_page_title.getFont().deriveFont(24.0f));
		jlabel_page_title.setBorder(new EmptyBorder(20, 20, 20, 20));
		//add components
		jpanel_top.add(jlabel_page_title);
		
		// jpanel_center
		// set panel properties
		jpanel_center.setLayout(new GridLayout(1, 2));
		jpanel_setting_location.setLayout(new BoxLayout(jpanel_setting_location, BoxLayout.Y_AXIS));
		jpanel_setting_lookandfeel.setLayout(new BoxLayout(jpanel_setting_lookandfeel, BoxLayout.Y_AXIS));
		// set component properties
		jlabel_setting_location.setText(gui.getMessages().getString("language_Label"));
		gui.setJLabelSize(jlabel_setting_location, gui.getComboBoxSize());
		jlabel_setting_lookandfeel.setText(gui.getMessages().getString("lookandfeel_Label"));
		gui.setJLabelSize(jlabel_setting_lookandfeel, gui.getComboBoxSize());
		for (LocationItem locitem : locationList) {
			jcombo_setting_location.addItem(locitem);
		}
		for (LookAndFeelItem lafitem : lookAndFeelList) {
			jcombo_setting_lookandfeel.addItem(lafitem);
		}
		// add components
		buildJComboPanel(jpanel_setting_location, jlabel_setting_location, jcombo_setting_location);
		buildJComboPanel(jpanel_setting_lookandfeel, jlabel_setting_lookandfeel, jcombo_setting_lookandfeel);
		// manually set each initially selected item
		jcombo_setting_location.setSelectedItem(initialLanguage());
		jcombo_setting_lookandfeel.setSelectedItem(initialLookAndFeel());

		// bottom
		// set panel properties
		jpanel_bottom.setLayout(new GridLayout(1, 1));
		jpanel_bottom.setPreferredSize(new Dimension(100, 100));
		jpanel_gridbag[0].setLayout(new GridBagLayout());
		jpanel_gridbag[1].setLayout(new GridBagLayout());
		// set component properties
		jbuton_back.setText(gui.getMessages().getString("back_Buton"));
		gui.setJButtonSize(jbuton_back, gui.getNormalButtonSize());
		jbuton_save.setText(gui.getMessages().getString("save_Buton"));
		gui.setJButtonSize(jbuton_save, gui.getNormalButtonSize());
		// add events
		jbuton_back.addActionListener(event -> {
			gui.readSettings();
			gui.mockup(new MenuPanel(gui));
		});
		jbuton_save.addActionListener(event -> {
			gui.writeSettings();
			gui.mockup(new MenuPanel(gui));
		});
		// add components
		jpanel_gridbag[0].add(jbuton_back, gui.getGridBagConstraintsCentered());
		jpanel_bottom.add(jpanel_gridbag[0]);
		jpanel_gridbag[1].add(jbuton_save, gui.getGridBagConstraintsCentered());
		jpanel_bottom.add(jpanel_gridbag[1]);
		
		// add action listeners at the very end to ensure every UI component is recognized
		jcombo_setting_location.addActionListener(
			event -> {
				// set Locale
				LocationItem loci = (LocationItem) jcombo_setting_location.getSelectedItem();
				gui.setLocale(loci.getLocale());
				// manually update all texts
				/* This might look unskilled, but listen for a moment:
				 * When I swap to another JPanel, those texts update fine. But in this
				 * current JPanel, they don't. So the simplest solution I came with was
				 * to manually update all texts as this JPanel happens to have few.
				 * Also, I don't master the 'ResourceBundle' class :(
				 */
				jlabel_page_title.setText(gui.getMessages().getString("settings_Title"));
				for (LocationItem locitem : locationList) {
					String name = locitem.getId();
					locitem.setName(gui.getMessages().getString(name));
				}
				for (LookAndFeelItem lafitem : lookAndFeelList) {
					String name = lafitem.getId();
					lafitem.setName(name);
				}
				jlabel_setting_location.setText(gui.getMessages().getString("language_Label"));
				jlabel_setting_lookandfeel.setText(gui.getMessages().getString("lookandfeel_Label"));
				jbuton_back.setText(gui.getMessages().getString("back_Buton"));
				jbuton_save.setText(gui.getMessages().getString("save_Buton"));
			}
		);
		jcombo_setting_lookandfeel.addActionListener(
			event -> {
				// set LookAndFeel
				LookAndFeelItem lafi = (LookAndFeelItem) jcombo_setting_lookandfeel.getSelectedItem();
				gui.setLookAndFeel(lafi.getLookAndFeel());
			}
		);
		
		// add panels to container
		this.add(jpanel_top, BorderLayout.NORTH);
		this.add(jpanel_center, BorderLayout.CENTER);
		this.add(jpanel_bottom, BorderLayout.SOUTH);
	}
	
	// METHOD BUILD SETTING PANEL SEPARATELY //
	
	private void buildJComboPanel (JPanel panel, JLabel label, JComboBox<? extends Item> combo) {
		// initialize custom JPanels
		JPanel spacer = new JPanel();
		JPanel centered = new JPanel();
		
		// set custom JPanels properties
		Dimension[] spacerSize = {
			new Dimension(100, 10),
			new Dimension(100, 10),
			new Dimension(100, 10)
		};
		gui.setJPanelSize(
			spacer,
			spacerSize
		);
		centered.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		// set component properties
		label.setFont(label.getFont().deriveFont(18.0f));
		gui.setJComboBoxSize(combo, gui.getComboBoxSize());
		
		// add components to 'panel'
		panel.add(label);
		panel.add(spacer);
		panel.add(combo);
		
		// add 'panel' to 'centered'
		centered.add(panel);
		
		// add 'centered' to 'jpanel_center'
		jpanel_center.add(centered);
	}
	
	// METHOD GET INITIAL LANGUAGE FROM SAVED DATA //
	
	private LocationItem initialLanguage() {
		String name = gui.getLocaleName();
		switch (name) {
			case ("es_ES"):
				return locationList[1];
			default:
				return locationList[0];
		}
	}
	
	// METHOD GET INITIAL LOOKANDFEEL FROM SAVED DATA //
	
	private LookAndFeelItem initialLookAndFeel() {
		String name = gui.getLookAndFeelName();
		switch (name) {
			case ("Nimbus"):
				return lookAndFeelList[1];
			case ("Motif"):
				return lookAndFeelList[2];
			case ("GTK"):
				return lookAndFeelList[3];
			default:
				return lookAndFeelList[0];
		}
	}

}
