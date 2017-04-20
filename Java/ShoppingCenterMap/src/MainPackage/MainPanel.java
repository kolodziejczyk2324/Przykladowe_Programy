package MainPackage;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import FrontPanels.*;
import FrontPanels.Advertisement.AdvertisementPanel;
import FrontPanels.Map.MapPanel;
import FrontPanels.Shops.ShopButton;
import FrontPanels.Shops.ShopPanel;
import MyDatabase.DBReader;
import SmallClass.*;

public class MainPanel extends JPanel{
	
	/**Panel z reklama*/
	AdvertisementPanel advertisementPanel;
	/**Panel z mapa*/
	MapPanel mapPanel;
	/**Panel ze sklepem*/
	ShopPanel shopPanel;

	public MainPanel(){
		setBackground(FrameInfo.backgroundColor);
		ArrayList<PointOnMapData> shops = DBReader.getPointsOnMap(); //odbieranie listy sklepow z bazy danych
		setLayout(new BorderLayout()); //ustawienie layoutu glownego panelu
		installPanels(shops); //stworz trzy glowne panele: z reklama, z mapa, oraz ze sklepami
		setActionToShopButtons(); //daj przyciskom z shopPanelu akcje pokazujace sciezke
	}
	
	/**
	 * Funkcja majaca za zadanie zainicjowac wszystkie panele znajdujace sie w glownym frame'ie
	 */
	private void installPanels(ArrayList<PointOnMapData> shops){
		advertisementPanel = new AdvertisementPanel(); //tworzymy panel z reklama
		add(advertisementPanel, BorderLayout.PAGE_START); //ustawiamy go na gorze
		
		mapPanel = new MapPanel(shops); //tworzymy panel z mapa
		add(mapPanel, BorderLayout.CENTER); //ustawiamy go w centrum
		
		shopPanel = new ShopPanel(shops); //tworzymy panel ze sklepami
		add(shopPanel, BorderLayout.LINE_END); //ustawiamy go w prawym dolnym rogu
	}
	
	/**
	 * Metoda ustawiajaca akcje do przyciskow znajdujacych sie w panelu 'shopPanel'.
	 * Akcje powoduja, ze na mapie pokazywana jest sciezka do wcisnietego sklepu.
	 */
	private void setActionToShopButtons(){
		ArrayList<ShopButton> buttons = shopPanel.getShopButtonsList(); //lista przyciskow z 'shopPanelu'
		for(int i=0 ; i<buttons.size() ; i++) //petla przebiegajaca po wszystkich przyciskach
			buttons.get(i).addActionListener(new ShopButtonAction()); //ustaw akcje
	}
	
	/**
	 * Akcja do przyciskow znajdujacych sie w 'shopPanelu' sprawiajaca ze po wcisnieciu danego przycisku
	 * na mapie pokazywana jest sciezka do owego sklepu. Akcja zmienia flage w 'shopPanelu' mowiaca o 
	 * aktualnie pokazywanym sklepie, i od razu przerysowywuje sciezke.
	 * @author Kemer
	 */
	public class ShopButtonAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			int goal = ((ShopButton) e.getSource()).getId(); //dokad teraz bedziemy isc
			mapPanel.setNewGoalAndDrawPath(goal); //powiedz shopPanelowi zeby zmienil sciezke
		}
	}
	
}
