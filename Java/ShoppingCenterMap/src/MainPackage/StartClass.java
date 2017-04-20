package MainPackage;
import java.awt.Dimension;
import javax.swing.*;

import SmallClass.FrameInfo;

/**
 * Klasa startowa zawierajaca informacje o rozmiarze frame, dodajaca do niego glowny panel
 * oraz pokazujaca go na ekranie.
 * @author Kemer
 */
public class StartClass {
	
	public static void main(String[] args) {
		JFrame mainFrame = new JFrame("Shopping Center Map"); //stworz frame
		MainPanel mainPanel = new MainPanel(); //stworz glowny panel
		mainPanel.setPreferredSize(new Dimension(FrameInfo.widthFrame, FrameInfo.heightFrame));//ustawiam rozmiary frame
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //co sie ma stac jak wylacze frame
		mainFrame.setResizable(false); //nie mozna zmieniac rozmiaru frame
		mainFrame.getContentPane().add(mainPanel); //dodanie glownego panelu do frame
		mainFrame.pack(); //tworze rozmiar frame
		mainFrame.setVisible(true); //pokaz frame
	}

}
