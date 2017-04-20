package MyDatabase;

import java.awt.Point;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import FrontPanels.PointOnMapData;

/**
 * Klasa czytajaca baze danych. Zawiera metody zwracajace punkty na mapie (sklepy i punkty stale), oraz
 * metode zwracajaca krawedzie w postaci listy elementow klasy Point.
 * @author Kemer
 */
public class DBReader {

	/**
	 * Funkcja zwracajaca liste punktow na mapie. Pobierajac punkty na mapie pobiera ona rowniez (co za tym idzie)
	 * informacje o sklepach. Czy jest to sklep, czy jest to punkt staly mowi nam flaga znajdujaca sie w 4 kolumnie
	 * tabeli (1 - to jest sklep, 0 - punkt staly). Reszta kolumn zawiera odpowiednio: 1 - nazwe sklepu, 2 - punkt x
	 * na mapie, 3 - punkt y na mapie.
	 * @return
	 */
	public static ArrayList<PointOnMapData> getPointsOnMap(){
		ArrayList<PointOnMapData> points = new ArrayList<>(); //Lista punktow na mapie
		/* MOCK */
		ResultSet rs = new MyResultSet();
		try {
			while (rs.next()){
				if(rs.getInt(4)==1){ //to znaczy ze jest sklep
					// ID, X, Y, NAME
					points.add(new PointOnMapData(rs.getInt(0), rs.getInt(2), rs.getInt(3) ,rs.getString(1), 1)); //dodaj sklep do listy
				}
				else if(rs.getInt(4)==0){ //to znaczy ze jest staly punkt
					points.add(new PointOnMapData(rs.getInt(0), rs.getInt(2), rs.getInt(3), "CONST", 0)); //dodaj punkt staly do listy
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return points; //zwroc liste punktow na mapie
	}
	
	/**
	 * Zwraca pobrana z bazy danych liste punktow (x,y) w ktorych x i y to id wierzcholkow
	 * krawedzi.
	 * @return Lista krawedzi
	 */
	public static ArrayList<Point> getEdges(){
		ArrayList<Point> edges = new ArrayList<>(); //tutaj beda przechowywane krawedzie (x - id pierwszego wierzcholka, y - drugiego)
		ResultSet rs = new MyResultSetEdges(); //MOCK bazy danych
		try {
			while (rs.next())
				edges.add(new Point(rs.getInt(0), rs.getInt(1))); // w zerowej kolumnie jest id pierwszego wierzcholka, w 1 drugiego
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return edges; //zwroc pobrana z bazy danych liste krawedzi
	}
	
}
