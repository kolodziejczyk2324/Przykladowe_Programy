package SmallClass;

import java.awt.Color;
/**
 * Klasa zawierajaca informacje o glownym framie. Zawiera rozmiary samego frame,
 * oraz paneli ktore w nim sie znajduja. Zawiera rowniez kolor tla.
 * @author Kemer
 */
public class FrameInfo {

	/**Szerokosc frame*/
	public static final int widthFrame = 1200;
	/**Wysokosc frame*/
	public static final int heightFrame = 800;
	/**wysokosc panelu z reklama*/
	public static final int heightAdvertisementPanel = (int)(heightFrame*0.3); //ustawiamy wysokosc panelu z reklama;
	/**wysokosc paneli z mapa oraz ze sklepami*/
	public static final int heightMapAndShopPanel = (int)(heightFrame*0.7); //ustawiamy wysokosc paneli z mapa i ze sklepami;
	/**szerokosc panelu ze sklepami*/
	public static final int widthShopPanel = (int)(widthFrame*0.3); //ustawiamy szerokosc panelu ze sklepami;
	/**szerokosc panelu z mapa*/
	public static final int widthMapPanel = (int)(widthFrame*0.7); //ustawiamy szerokosc panelu z mapa;
	/**Kolor tla*/
	public static final Color backgroundColor = new Color(153, 255, 255);;

}
