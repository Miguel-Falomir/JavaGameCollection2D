package main;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import main.utilities.Item;
import main.utilities.MainMenu;

public class Gui extends JFrame {
	
	// SERIAL VERSION IDENTIFIER (whatever this is, eclipse cries when not implemented) //

	private static final long serialVersionUID = 6896552375386521626L;
	
	// VARIABLES //
	
	ResourceBundle messages = null;
	
	String language = null;
	String country = null;
	String lookandfeel = null;
	
	Dimension[] optionsPanelSize = {
		new Dimension(300, 300),
		new Dimension(300, 300),
		new Dimension(300, 300)
	};
	Dimension[] squaredPanelSize = {
		new Dimension(480, 480),
		new Dimension(480, 480),
		new Dimension(480, 480)
	};
	Dimension[] panoramicPanelSize = {
		new Dimension(560, 420),
		new Dimension(560, 420),
		new Dimension(560, 420)
	};
	Dimension[] normalButtonSize = {
		new Dimension(100, 50),	// minimum size
		new Dimension(100, 50),	// preferred size
		new Dimension(200, 100)	// maximum size
	};
	Dimension[] squaredButtonSize = {
		new Dimension(100, 100),
		new Dimension(100, 100),
		new Dimension(200, 200)
	};
	Dimension[] miniButtonSize = {
		new Dimension(50, 20),
		new Dimension(50, 20),
		new Dimension(50, 20)
	};
	Dimension[] comboboxSize = {
		new Dimension(150, 30),
		new Dimension(150, 30),
		new Dimension(300, 60)
	};
	
	GridBagConstraints gridBagConstraintsCentered = new GridBagConstraints(
		0,							// gridX (fila posicion)
		0,							// gridY (columna posicion)
		1,							// gridwidth (cantidad filas ocupadas)
		1,							// gridheight (cantidad columnas ocupadas)
		0.0,						// weightX (peso del componente respecto al ancho libre)
		0.0,						// weightY (peso del componente respecto a la altura disponible)
		GridBagConstraints.CENTER,	// anchor (lugar de la celda donde colocar el componente, si este es mas pequenyo)
		GridBagConstraints.NONE,	// fill (metodo para reescalar el componente, si este es mas pequenyo que la celda)
		new Insets(0, 0, 0, 0),		// insets (margenes externos para dejar espacio libre fuera de la celda)
		0,							// ipadX (margen interno respecto al eje X, espacio libre dentro de la celda)
		0							// ipadY (margen interno respecto al eje Y, espacio libre dentro de la celda)
	);
	
	// UI COMPONENTS //
	
	Container cont = null;
	
	// GETTERS AND SETTERS //
	
	public LookAndFeel getLookAndFeel() {
		return UIManager.getLookAndFeel();
	}
	
	public String getLookAndFeelName() {
		Pattern regex = Pattern.compile("(?<=\\.)[A-Z]+[a-z]*(?=L)");
		Matcher search = regex.matcher(lookandfeel);
		String result = search.find() ? search.group() : "";
		return result;
	}
	
	public void setLookAndFeel(String laf) {
		try {
			UIManager.setLookAndFeel(laf);
			lookandfeel = laf;
			refresh();
		} catch (
			ClassNotFoundException |
			InstantiationException |
			IllegalAccessException |
			UnsupportedLookAndFeelException e
		) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Locale getLocale() {
		return super.getLocale();
	}
	
	public String getLocaleName() {
		return this.getLocale().toString();
	}
	
	@Override
	public void setLocale(Locale loc) {
		super.setLocale(loc);
		messages = ResourceBundle.getBundle("resources/i18n/messages", loc);
		language = this.getLocale().getLanguage();
		country = this.getLocale().getCountry();
		refresh();
	}
	
	public ResourceBundle getMessages() {
		return messages;
	}
	
	public Dimension[] getStandardSize(int index) {
		switch (index) {
			case 1: 
				return optionsPanelSize;
			case 2:
				return squaredPanelSize;
			case 3:
				return panoramicPanelSize;
			case 4:
				return normalButtonSize;
			case 5:
				return squaredButtonSize;
			case 6:
				return miniButtonSize;
			case 7:
				return comboboxSize;
			default:
				throw new IllegalArgumentException("Unexpected value: " + index);
		}
	}
	
	public GridBagConstraints getGridBagConstraintsCentered() {
		return gridBagConstraintsCentered;
	}
	
	// CONSTRUCTOR //
	
	public Gui (String title, String iconRoute, int width, int height, boolean resizable) throws HeadlessException {
		// read settings
		readSettings();
		
		// on pressing 'X', stop main thread
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// title
		this.setTitle((title == null) ? "title" : title );
		
		// icon
		this.setIconImage((iconRoute == null) ? null : (new ImageIcon(getClass().getResource(iconRoute))).getImage());
		
		// dimensions
		this.setSize(width, height);
		this.setResizable(resizable);
		
		// layout
		this.setLayout(new BorderLayout());
		
		// location
		messages = ResourceBundle.getBundle("resources/i18n/messages", getLocale());
		
		// theme
		try {
			UIManager.setLookAndFeel(lookandfeel);
		} catch (
			ClassNotFoundException |
			InstantiationException |
			IllegalAccessException |
			UnsupportedLookAndFeelException e
		) {
			e.printStackTrace();
		}
		
		// build UI components
		cont = this.getContentPane();
		mockup( new MainMenu(this));
		
		// adjust screen size to components
		// or not, because if there is no component at all the window resizes to 0x0
		//this.pack();
		
		// show window
		this.setVisible(true);
	}
	
	// METHOD BUILD NEW PANEL //
	
	public void mockup (JPanel panel) {
		// clear container
		cont.removeAll();
		
		// add external JPanel
		cont.add(panel, BorderLayout.CENTER);
		
		// refresh container
		cont.revalidate();
		cont.repaint();
	}
	
	// METHOD(S) SET COMPONENT SIZE //
	
	public void setComponentSize(Object obj, Dimension minimum, Dimension preferred, Dimension maximum) {
		if (obj instanceof JPanel) {			// JPanel
			JPanel panel = (JPanel) obj;
			setJPanelSize(panel, minimum, preferred, maximum);
		} else if (obj instanceof JLabel) {		// JLabel
			JLabel label = (JLabel) obj;
			setJLabelSize(label, minimum, preferred, maximum);
		} else if (obj instanceof JButton) {	// JButton
			JButton button = (JButton) obj;
			setJButtonSize(button, minimum, preferred, maximum);			
		} else if (obj instanceof JComboBox) {	// JComboBox
			JComboBox<Item> combo = (JComboBox<Item>) obj;
			setJComboBoxSize(combo, minimum, preferred, maximum);
		} else {
			System.out.println("Class not valid");
		}
	}
	
	private void setJPanelSize(JPanel panel, Dimension minimum, Dimension preferred, Dimension maximum) {
		if (minimum != null) {panel.setMinimumSize(minimum);}
		if (preferred != null) {panel.setPreferredSize(preferred);}
		if (maximum != null) {panel.setMaximumSize(maximum);}
	}
	
	private void setJLabelSize(JLabel label, Dimension minimum, Dimension preferred, Dimension maximum) {
		if (minimum != null) {label.setMinimumSize(minimum);}
		if (preferred != null) {label.setPreferredSize(preferred);}
		if (maximum != null) {label.setMaximumSize(maximum);}
	}
	
	private void setJButtonSize(JButton button, Dimension minimum, Dimension preferred, Dimension maximum) {
		if (minimum != null) {button.setMinimumSize(minimum);}
		if (preferred != null) {button.setPreferredSize(preferred);}
		if (maximum != null) {button.setMaximumSize(maximum);}
	}
	
	private void setJComboBoxSize (JComboBox<Item> combo, Dimension minimum, Dimension preferred, Dimension maximum) {
		if (minimum != null) {combo.setMinimumSize(minimum);}
		if (preferred != null) {combo.setPreferredSize(preferred);}
		if (maximum != null) {combo.setMaximumSize(maximum);}
	}
	
	// METHOD UPDATE UI //
	
	private void refresh () {
		// update Swing UI
		SwingUtilities.updateComponentTreeUI(this);
		
		// refresh container
		if (cont != null) {
			cont.revalidate();
			cont.repaint();
		}
	}
	
	// METHOD READ USER SETTINGS //
	
	public void readSettings() {
		try {
			// read raw code
			File settings = new File("src/resources/data/settings.txt");
			Scanner scanner = new Scanner(settings);
			
			// get language
			String data = scanner.nextLine();
			String language = data.substring(data.indexOf("=") + 1, data.indexOf(";"));
			
			// get country
			data = scanner.nextLine();
			String country = data.substring(data.indexOf("=") + 1, data.indexOf(";"));
			
			// get lookandfeel
			data = scanner.nextLine();
			lookandfeel = data.substring(data.indexOf("=") + 1, data.indexOf(";"));
			
			// update both locale and lookandfeel
			this.setLocale( new Locale(language, country));
			this.setLookAndFeel(lookandfeel);
			
			// close scanner
			scanner.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	// METHOD WRITE USER SETTINGS //
	
	public void writeSettings() {
		try {
			// generate old and new files
			File oldFile = new File("src/resources/data/settings.txt");
			File newFile = new File("src/resources/data/settings.txt");
			
			// delete old file
			oldFile.delete();
			
			// create new empty file
			newFile.createNewFile();
			
			// generate file writer for new file
			FileWriter writer = new FileWriter(newFile);
			
			// write new file
			writer.write(
				"language=" + language + ";\n"
				+ "country=" + country +";\n"
				+ "lookandfeel=" + lookandfeel + ";"
			);
			
			// close writer
			writer.close();
			
			// read file once more (it works as a refreshment)
			readSettings();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
