package FrontPanels.Advertisement;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import SmallClass.FrameInfo;

/**
 * Panel zawierajacy reklame.
 * @author Kemer
 */
public class AdvertisementPanel extends JPanel {
	/**Obrazek z rekalama*/
	private BufferedImage image;
	/**stale odnosnie reklam*/
	private final int imageWidth, imageHeight, odstep_y;
	/**Labele z informacja o produkcie*/
	private JLabel nameLabel, valueLabel, shopNameLabel;
	
	public AdvertisementPanel(){
		setLayout(null); //sami ustawiamy gdzie co ma byc
		setOpaque(false); //przezroczyste tlo
		imageWidth = (int)(FrameInfo.widthFrame*0.2); //ustaw szerokosc obrazka
		imageHeight = (int) (0.9*FrameInfo.heightAdvertisementPanel); //wysokosc obrazka
		odstep_y = (FrameInfo.heightAdvertisementPanel-imageHeight)/2; //odstep reklamy na gorze i na dole
		setPreferredSize(new Dimension(FrameInfo.widthFrame, FrameInfo.heightAdvertisementPanel));//ustawiam rozmiary panelu
		installInfoLabels(); //wstaw labele na odpowiednie miejsce
		
		/* PRZYKLADOWA REKLAMA - USUNAC POTEM */
		setAdvertisement("koszulaHM.jpg", "Koszula w krate", 69.99, "H&M");
	}
	
	/**
	 * Metoda ustawiajaca nowa reklame w panelu.
	 * @param imageName Nazwa pliku ze zdjeciem
	 * @param name Nazwa produktu
	 * @param value Cena produktu
	 * @param shopName Sklep z produktem
	 */
	public void setAdvertisement(String imageName, String name, double value, String shopName){
		setImage("ShopItems/"+imageName); //ustaw obrazek
		nameLabel.setText(name); //aktualizacja nazwy produktu
		valueLabel.setText("Cena: "+value); //aktualizacja ceny produktu
		shopNameLabel.setText(shopName); //aktualizacja nazwy sklepu
		repaint(); //przerysuj obrazek
	}
	
	/**
	 * Metoda wstawiajaca labele na swoje miejsce.
	 */
	private void installInfoLabels(){
		nameLabel = new JLabel(); //stworz label z nazwa produktu
		valueLabel = new JLabel(); //stworz label z cena
		shopNameLabel = new JLabel(); //stworz label z nazwa sklepu
		int labelWidth = (int) (0.3*FrameInfo.widthFrame); //szerokosc labela
		int labelHeight = (int) (0.2*imageHeight); //wysokosc labela
		Font font = new Font("Helvetica", Font.BOLD | Font.ITALIC, 28); //czcionka w labelach
		nameLabel.setFont(font); valueLabel.setFont(font); shopNameLabel.setFont(font); //ustaw kazdemu ta sama czcionke
		JPanel labelPanel = new JPanel(); //Panel w ktorym beda labele, bedzie mial grid layout
		labelPanel.setBounds(	FrameInfo.widthFrame/2+imageWidth/2+50, //ustawiamy gdzie ten panel ma byc
								odstep_y, labelWidth, imageHeight);
		labelPanel.setLayout(new GridLayout(3, 1, (int)(labelHeight-3*labelHeight/2), 0)); //ustaw mu layout tak zeby labele byle pod soba
		labelPanel.add(nameLabel); labelPanel.add(valueLabel); labelPanel.add(shopNameLabel); //wstaw do panelu labele
		labelPanel.setOpaque(false); //panel ma byc przezroczysty
		add(labelPanel); //dodaj panel z labelami do glownego panelu
	}
	
	/**
	 * Metoda aktualizujaca aktualny obrazek. NIE rysuje obrazka, podstawia tylko do zmiennej z aktualnym obrazkiem nowy obrazek
	 * po czym w metodzie 'setAdvertisement' wywolywana jest metoda repaint, ktora go rysuje.
	 * @param imagePath Nazwa obrazka
	 */
	private void setImage(String imagePath){
		try {
			image = ImageIO.read(new File(imagePath));
		} catch (IOException ex) {
			System.out.println("Blad czytania obrazka reklamy!");
		}
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, FrameInfo.widthFrame/2-imageWidth/2, //na srodku rysuj obrazek 
        			odstep_y,
        			imageWidth,
        			imageHeight, this); //ustawia mape
    }
}
