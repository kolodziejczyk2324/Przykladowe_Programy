package FrontPanels;

import java.awt.Point;

public class PointOnMapData {
	/**Id punktu*/
	private int id;
	/**Lokalizacja punktu na mapie*/
	private Point point;
	/**Nazwa sklepu, lub w przypadku punktu stalego zostanie przypisany string "CONST"*/
	private String name;
	/**Flaga mowiaca o tym czy punkt jest sklepem czy nie*/
	private int flagIsShop;
	
	/**Konstruktor inicjujacy powyzsze zmienne*/
	public PointOnMapData(int id, int x, int y, String name, int isShop){
		this.id = id;
		point = new Point(x,y);
		this.name = name;
		flagIsShop = isShop;
	}
	
	/**Getter zwracajacy nazwe sklepu*/
	public String getName(){
		return name;
	}
	
	/**Getter zwracajacy miejsce punktu na mapie*/
	public Point getLocation(){
		return point;
	}
	
	/**Getter zwracajacy id punktu*/
	public int getId(){
		return id;
	}
	
	/**
	 * Funkcja mowiaca czy ten punkt jest sklepem czy nie
	 * @return true - jest sklepem, false - nie jest sklepem
	 */
	public boolean isShop(){
		return flagIsShop == 1;
	}
}
