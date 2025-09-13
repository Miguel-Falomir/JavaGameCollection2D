package main.utilities;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	
	// ATTRIBUTES //
	
	private static final long serialVersionUID = 1L;
	private double ratio;
	private Dimension tam;
	private Image img;
	private boolean wide;
	
	// CONSTRUCTOR //
 
	public ImagePanel(String _imageRoute, boolean _wide) {
        try {
        	// initialize attributes
            img = ImageIO.read(new File(_imageRoute));
            tam = new Dimension(img.getWidth(null), img.getHeight(null));
            ratio = (double) tam.getWidth() / tam.getHeight();
            wide = _wide;
            // set image
    		setPreferredSize(tam);
    		setMinimumSize(tam);
    		setMaximumSize(tam);
    		setSize(tam);
    		setLayout(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	// METHOD SHOW IMAGE ONSCREEN //

	@Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // get main window size
        int windowWidth = this.getParent().getWidth();
        int windowHeight = this.getParent().getHeight();
        
        // calculate resizing size
        int scaledHeight;
        int scaledWidth;
        if (wide) {
        	// In this case, image new height attaches to current window height,
        	// while image width multiplies current window height with proportions ratio.
        	// This way, if bigger than current screen, image only overflows left and right,
        	// while occupying all space from roof to floor.
        	scaledHeight = windowHeight;
        	scaledWidth = (int) (windowHeight * ratio);
        } else {
        	// In this other case, the opposite happens. Image width attaches to window width,
        	// while image height divides by proportions ratio, to only overflow up and down.
        	scaledWidth = windowWidth;
        	scaledHeight = (int) (windowWidth / ratio);
        }

        // scale image to fit JPanel size
        Image scaledImage = img.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

        // calcular el eje X para centrar la imagen
        // (primero se calcula la diferencia absoluta entre el ancho de la pantalla y el ancho
        // de la imagen. Si la pantalla es mas ancha que la imagen, la diferencia se hace
        // positiva para desplazar la imagen a la derecha. Por el contrario, la diferencia
        // se hace negativa para desplazar la imagen a la izquierda)
        int centerX = Math.abs((int) ((windowWidth / 2) - (scaledWidth / 2)));
        centerX *= (windowWidth >= scaledWidth) ? 1 : -1;
        
        // Dibujar la imagen escalada en el panel
        g.drawImage(scaledImage, centerX, 0, this);
        
        //System.out.println(screenWidth + " - " + screenHeight);
        //System.out.println(scaledWidth + " + " + screenHeight);
        //System.out.println(centerX + " * " + 0);
	}
	
	// METHOD CHANGE IMAGE //
	
    public void setImage(String imagePath) {
        try {
            img = ImageIO.read(new File(imagePath));
            repaint(); // Repaint panel with new image
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
}