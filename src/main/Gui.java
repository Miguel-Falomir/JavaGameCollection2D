package main;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import main.jpanels.MenuPanel;

public class Gui extends JFrame {
	
	// SERIAL VERSION IDENTIFIER (whatever this is, eclipse cries when not implemented) //

	private static final long serialVersionUID = 6896552375386521626L;
	
	// VARIABLES //
	
	ResourceBundle messages = null;
	String language = null;
	String country = null;
	String lookandfeel = null;
	
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
		mockup( new MenuPanel(this));
		
		// adjust screen size to components
		// or not, because if there is no component at all the window resizes to 0x0
		//this.pack();
		
		// show window
		this.setVisible(true);
	}
	
	// METHOD BUILD MAIN MENU //
	
	public void mockup () {
		// clear container
		cont.removeAll();
		
		// declare variables
		Dimension[] gameBtnSize = {
			new Dimension(100, 100),
			new Dimension(100, 100),
			new Dimension(200, 200)
		};
		Dimension[] optionBtnSize = {
			new Dimension(100, 50),
			new Dimension(100, 50),
			new Dimension(100, 50)
		};
		GridBagConstraints gbcCenter = new GridBagConstraints(
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
		
		// declare panels
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel centerPanel = new JPanel(new GridLayout(2, 4));
		JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
		
		// top
		// set properties
		//topPanel.setBackground(Color.RED);
		// set components
		JLabel title = new JLabel(messages.getString("mainTitle"));
		title.setFont(title.getFont().deriveFont(24.0f));
		title.setBorder( new EmptyBorder(20, 20, 20, 20));
		//add components
		topPanel.add(title);
		
		// center
		// set properties
		
		// set components
		JButton[] gameButtonList = {
			new JButton("Solitaire"),
			new JButton("Game of life"),
			new JButton("Snake"),
			new JButton("Pong"),
			new JButton("Breakout"),
			new JButton("Invaders"),
			new JButton("Tetris"),
			new JButton("Pacman")
		};
		for (JButton btn : gameButtonList) {
			btn.setMinimumSize(gameBtnSize[0]);
			btn.setPreferredSize(gameBtnSize[1]);
			btn.setMaximumSize(gameBtnSize[2]);
		}
		// add components
		for (JButton btn : gameButtonList) {
			JPanel pan = new JPanel( new GridBagLayout());
			pan.add(btn, gbcCenter);
			centerPanel.add(pan);
		}
		
		// bottom
		// set properties
		//bottomPanel.setBackground(Color.GREEN);
		bottomPanel.setPreferredSize(new Dimension(100, 100));
		// set components
		JButton[] optionButtonList = {
			new JButton(messages.getString("settings")),
			new JButton(messages.getString("quit"))
		};
		for (JButton btn : optionButtonList) {
			btn.setMinimumSize(optionBtnSize[0]);
			btn.setPreferredSize(optionBtnSize[1]);
			btn.setMaximumSize(optionBtnSize[2]);
		}
		// disable settings button until better implementation
		optionButtonList[0].setEnabled(true);
		// add events
		optionButtonList[0].addActionListener( event -> {
			//new SettingsPanel(this, messages, settings);
			System.out.println("not implemented due to bad design");
		});
		optionButtonList[1].addActionListener( event -> {
			setVisible(false);
    		dispose();
    		SwingUtilities.invokeLater(new Runnable() {
    			public void run() {
    				System.exit(0);
    			}
    		});
		});
		// add components
		for (JButton btn : optionButtonList) {
			JPanel pan = new JPanel( new GridBagLayout());
			pan.add(btn, gbcCenter);
			bottomPanel.add(pan);
		}
		
		// add panels to container
		cont.add(topPanel, BorderLayout.NORTH);
		cont.add(centerPanel, BorderLayout.CENTER);
		cont.add(bottomPanel, BorderLayout.SOUTH);
		
		// refresh container
		cont.revalidate();
		cont.repaint();
	}
	
	// METHOD CHANGE MAIN PANEL //
	
	public void mockup (JPanel panel) {
		// clear container
		cont.removeAll();
		
		// add external JPanel
		cont.add(panel, BorderLayout.CENTER);
		
		// refresh container
		cont.revalidate();
		cont.repaint();
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
	
	// METHOD READ SETTINGS FILE
	
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
	
	// METHOD WRITE SETTINGS FILE
	
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
