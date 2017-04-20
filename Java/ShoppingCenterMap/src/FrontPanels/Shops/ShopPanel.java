package FrontPanels.Shops;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import FrontPanels.PointOnMapData;
import SmallClass.FrameInfo;

/**
 * Panel z przyciskami. Zawiera przyciski ze sklepami po ktorych nacisnieciu pokazywana jest sciezka
 * do owego sklepu.
 * @author Kemer
 */
public class ShopPanel extends JPanel {

	/**Lista zawierajaca przyciski*/
	private ArrayList<ShopButton> buttonList;
	/**rozmiar przycisku potrzebny do ustawienia odpowiedniego rozmiaru zdjecia na przycisku*/
	private int button_size = 0;
	
	/**
	 * Konstruktor klasy ShopPanel. Inicjuje zmienne, ustawia rozmiar panelu, oraz instaluje przyciski.
	 * Akcje do przyciskow sa ustawiane w klasie MainPanel.
	 * @param frameInfo Informacje o rozmiarach glownych paneli (w tym tego)
	 * @param points Punkty na mapie, zawiera tez informacje o sklepach co bedzie tu potrzebne.
	 */
	public ShopPanel(ArrayList<PointOnMapData> points){
		setPreferredSize(new Dimension(FrameInfo.widthShopPanel, FrameInfo.heightMapAndShopPanel));//ustawiam rozmiary paneli
		installButtons(points); //wstaw przyciski (bez akcji, akcja ustawiana w MainPanel)
		setOpaque(false); //tlo przezroczyste
	}
	
	/**
	 * Metoda wstawiajaca przyciski. Dodaje je do listy i dodaje je do panelu. Metoda NIE dodaje
	 * akcji. Akcje dodawane sa w MainPanel.
	 * @param points Punkty na mapie, w tym informacje o sklepach, co bedzie tu przydatne.
	 */
	private void installButtons(ArrayList<PointOnMapData> points){
		buttonList = new ArrayList<>(); //stworz liste z przyciskami
		setLayout(getGridLayout(points.size())); //ustaw layout panelu
		for(int i=0 ; i<points.size(); i++){ //chodzimy po wszystkich punktach i zbieramy same sklepy
			if(points.get(i).isShop()){ //jesli widzisz sklep
				//stworz nowy przycisk, nadaj mu nazwe i ID (id bedzie potrzebne do przejscia do odpowiedniego miejsca na mapie
				ShopButton new_button = new ShopButton(points.get(i).getName(), points.get(i).getId(), button_size);
				buttonList.add(new_button); //dodaj przycisk do listy
				add(new_button); //dodaj przycisk do panelu
			}
		}
	}
	
	/**
	 * Metoda ustawiajaca odpowiedni gridLayout, dzieki czemu przyciski dostosowuja swoj rozmiar
	 * do ilosci sklepow.
	 * @param ile Ile jest sklepow
	 * @return gridLayout uzywany w tym panelu
	 */
	private GridLayout getGridLayout(int ile){
		int i = 1, k;
		double proc_odstep_x = 0.2;
		int width = FrameInfo.widthShopPanel;
		int height = FrameInfo.heightMapAndShopPanel;
		do{
			i++;
			button_size = (int)((1-proc_odstep_x)*width)/i;
			k = height/button_size;
		}while(i*k < ile);
		int odstep_x = (int)(width*proc_odstep_x)/(i+1);
		int odstep_y = (int)(height-k*button_size)/(k+1);
		return new GridLayout(k, i, odstep_y, odstep_x);
	}
	
	/**
	 * Getter zwracajacy liste z przyciskami, znajdujacymi sie w tym panelu. Uzywany do nadania im akcji.
	 * @return Lista przyciskow.
	 */
	public ArrayList<ShopButton> getShopButtonsList(){
		return buttonList;
	}
}
