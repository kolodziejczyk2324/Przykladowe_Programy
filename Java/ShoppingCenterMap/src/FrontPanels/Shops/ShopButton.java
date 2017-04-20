package FrontPanels.Shops;

import java.awt.Color;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Klasa bedaca przyciskiem ze sklepem. Zawiera informacje o danym sklepie.
 * Zawiera id sklepu ktore identyfikuje go od reszty. Jest to rowniez id wierzcholka
 * do znajdujacego sie na mapie.
 * @author Kemer
 *
 */
public class ShopButton extends JButton{

	/**Id sklepu*/
	private int id;
	
	/**
	 * Konstruktor inicjujacy powyzsze zmienne oraz wstawiajacy nazwe na przycisk
	 * @param imageName Nazwa sklepu znajdujaca sie na przycisku.
	 * @param id Id sklepu.
	 * @param buttonSize rozmiar przycisku
	 */
	public ShopButton(String imageName, int id, int buttonSize){
		this.id = id; //ustaw pole id
		setBackground(Color.WHITE); //tlo biale
		setImage(imageName, buttonSize); //ustaw obrazek
	}
	/**
	 * Ustawia rysunek do przycisku
	 * @param buttonSize Rozmiar przycisku
	 */
	private void setImage(String imageName, int buttonSize){
		try {
		    Image img = ImageIO.read(new File("ShopsImage/"+imageName)).getScaledInstance(buttonSize, buttonSize,  java.awt.Image.SCALE_SMOOTH);;
		    setIcon(new ImageIcon(img));
		  } catch (Exception ex) {
		    System.out.println(ex);
		  }
	}
	
	public int getId(){
		return id;
	}
	
}
