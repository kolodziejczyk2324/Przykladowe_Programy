package FrontPanels.Map;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import FrontPanels.PointOnMapData;
import FrontPanels.Map.Dijkstra.Dijkstra;
import FrontPanels.Map.Dijkstra.Dijkstra.Node;
import FrontPanels.Map.Dijkstra.Graph.Edge;
import FrontPanels.Map.Dijkstra.Graph;
import MyDatabase.DBReader;
import SmallClass.FrameInfo;

/**
 * Panel z mapa.
 * @author Kemer
 */
public class MapPanel extends JPanel {

	/**Obrazek mapy*/
	private BufferedImage image;
	/**Punkty na mapie*/
	ArrayList<PointOnMapData> points;
	/**Dystanse oraz sciezki je poprzedzajace*/
	Node node[];
	/**Wierzcholek gdzie aktualnie jest narysowana sciezka*/
	int actuallGoal = 0;
	
	/**
	 * Konstruktor klasy MapPanel. Inicjalizuje point, ustawia rozmiar, tworzy sciezki, i ustawia zdjecie.
	 * @param points
	 */
	public MapPanel(ArrayList<PointOnMapData> points){
		this.points = points; //inicjalizacja punktow
		setPreferredSize(new Dimension(FrameInfo.widthMapPanel, FrameInfo.heightMapAndShopPanel));//ustawiam rozmiary panelu
		installGraph(); //tworzymy graf ze sciezkami
		setImage("mapa.jpg"); //ustawiam obrazek mapy
	}
	
	/**
	 * Metoda tworzaca graf ze sciezkami. Pod tablice Node sa podstawione te odleglosci.
	 */
	private void installGraph(){
		Graph graph = new Graph(points.size()); //stworz graf z punktami na mapie
		ArrayList<Point> edges = DBReader.getEdges(); //odbierz z bazy danych krawedzie
		for(int i=0 ; i<edges.size(); i++) //petla przechodzaca po wszystkich krawedziach, dodaje je do grafu
			graph.addUndirectedEdge(edges.get(i).x, edges.get(i).y, distance(	points.get(edges.get(i).x).getLocation(),
																				points.get(edges.get(i).y).getLocation())	);
		//mam juz graf z krawedziami i wagami na nich
		node = Dijkstra.start(graph, 0); //dijkstra liczy mi najkrotsze sciezki
	}
	
	/**
	 * Metoda ustawiajaca obrazek mapy znajdujacej sie w sciezce 'imagePath'
	 */
	private void setImage(String imagePath){
		try {
			image = ImageIO.read(new File(imagePath)); //ustaw mape
		} catch (IOException ex) {
			System.out.println("Blad czytania obrazka mapy!");
			System.exit(0);
		}
	}
	
	/**
	 * Metoda rysujaca siatke. Proste sa ustawiane co 100 pikseli. Metoda pomocna w trakcie
	 * ustawiania punktow dla sklepow.
	 * @param g Do rysowania z paintComponent
	 */
	private void drawNet(Graphics g){
		for(int i=1; i<=FrameInfo.widthMapPanel/100 ; i++){ //rysuj kreski pionowe
        	g.drawLine(i*100, 0, i*100, FrameInfo.heightMapAndShopPanel);
        }
        for(int i=1; i<=FrameInfo.heightMapAndShopPanel/100 ; i++){ //rysuj kreski poziome
        	g.drawLine(0, i*100, FrameInfo.widthMapPanel, i*100);
        }
	}
	
	/**
	 * Metoda rysujaca sciezke od punktu startowego, do punktu o id 'actuallGoal'.
	 * @param g
	 */
	private void drawPath(Graphics g){
		g.setColor(Color.RED); //sciezka ma miec kolor czerwony
		if(actuallGoal != 0){ //jesli nie ma ustawionej zmiennej to nie rysuj sciezki
        	Edge e = node[actuallGoal].prev; //referencje do krawedzi ktorymi szedlem
			while( e!=null ){
				Graphics2D g2 = (Graphics2D) g; //zmienna potrzebna do ustawienia grubosci prostej
                g2.setStroke(new BasicStroke(5)); //grubosc prostej
                g2.draw(new Line2D.Float(	points.get(e.start).getLocation().x, points.get(e.start).getLocation().y, 
											points.get(e.end).getLocation().x, points.get(e.end).getLocation().y)	);
				e = node[e.start].prev; //bierz kolejna krawedz
			}
        }
	}
	
	/**
	 * Metoda z ktorej korzysta akcja przyciskow z shopPanel. Metoda ta ustawia zmienna actuallGoal,
	 * oraz rysuje sciezke. Sciezka ta bedzie przebiegala od punktu startowego do wierzcholka o id
	 * actuallGoal.
	 * @param new_goal Id wierzcholka do ktorego teraz idziemy.
	 */
	public void setNewGoalAndDrawPath(int new_goal){
		actuallGoal = new_goal; //ustaw zmienna mowiaca o wierzcholku do ktorego chcemy isc na 'new_goal', teraz paintComponent wie gdzie isc
		repaint(); //rysuj sciezke
	}
	
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, FrameInfo.widthMapPanel, FrameInfo.heightMapAndShopPanel, this); //ustawia mape
        drawNet(g);
        drawPath(g); //rysuj sciezke
    }
    
    /**
     * Metoda liczaca odleglosc pomiedzy punktami.
     * @param p1 Punkt 1
     * @param p2 Punkt 2
     * @return Odleglosc pomiedzy tymi punktami.
     */
    private int distance(Point p1, Point p2){
    	return (int) ( Math.sqrt(Math.abs(Math.pow(p1.y-p2.y, 2)) + Math.abs(Math.pow(p1.x-p2.x, 2))) ); //zwykly pitagoras
    }
}
