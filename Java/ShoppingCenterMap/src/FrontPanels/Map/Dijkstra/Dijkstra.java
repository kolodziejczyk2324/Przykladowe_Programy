package FrontPanels.Map.Dijkstra;

import FrontPanels.Map.Dijkstra.Graph;
import FrontPanels.Map.Dijkstra.Graph.Edge;

/**
 * Opracowany przez holenderskiego informatyka Edsgera Dijkstre, s³u¿y do znajdowania najkrótszej œcie¿ki z pojedynczego 
 * Ÿród³a w grafie o nieujemnych wagach krawêdzi.
 * @author Kemer
 */
public class Dijkstra {

	/**Zmienna symulujaca nieskonczonosc*/
	public static final int infinity = 1000;
	
	/**
	 * Klasa reprezentujaca wezel. Zawiera informacje o id, aktualnym dystansie, krawedzi ktora 'zostala odkryta', oraz aktualnej
	 * pozycji w kopcu, ktora aktualizowana jest w kazdym wywolaniu funkcji 'swap' w klasie 'NodeDistHeap'.
	 * @author Kemer
	 */
	public static class Node{
		public int position; //aktualna pozycja w kopcu
		public int id; //id wezla
		public double dist = infinity; //aktualny dystans
		public Edge prev = null; //poprzednia krawedz
		
		public Node(int position){ //konstruktor ustawiajacy 
			this.position = position; //pozycje w kopcu
			this.id = position; //oraz id
		}
	}
	
	/**
	 * Metoda wykonujaca algorytm Dijkstry, w grafie 'graph' poczynajac od wierzcholka o indeksie 's'. Na koncu wypisywane
	 * sa wszystkie dane dotyczace przebiegu algorytmu.
	 */
	public static Node[] start(Graph graph, int s) {
		Node node[] = new Node[graph.array.length]; //Tablica reprezentujaca wszystkie wierzcholki w grafie
		for(int i=0 ; i<graph.array.length ; i++) //petla tworzaca nowe wierzcholki
			node[i] = new Node(i); 
		int d = graph.getNumberOfEdge()/graph.array.length; //wylicz optymalna ilosc synow dla kazdego wezla w kopcu 
		NodeDistHeap heap = new NodeDistHeap(Node.class, d, graph.getNumberOfEdge()); //tworzenie kopca ktory zawierac bedzie wezly
		node[s].dist = 0; //ustaw dystans startowego elementu na 0
		heap.build_heap(node); //zbuduj kopiec
		Node u = heap.extract_min(); //zdejmij wierzcholek z najmniejsza odlegloscia ze szczytu kopca
		while( u!=null ){ //wykonuj petle do momentu, az kopiec bedzie pusty
			Edge e = graph.array[u.id]; //wez pierwsza krawedz wychodzaca z wierzcholka 'u'
			while( e!=null ){ //wykonuj petle po wszystkich krawedziach
				if(node[e.end].dist > node[u.id].dist + e.weight){ //jesli aktualna odleglosc konca krawedzi jest wieksza niz ta, ktora chcemy podstawic
					node[e.end].dist = node[u.id].dist + e.weight; //podstaw ja
					node[e.end].prev = e; //ustaw temu wierzcholkowi krawedz do ktorej do niej przyszedlem
					heap.decrease_key(node[e.end], node[e.end].dist); //zmniejsz wartosc temu wierzcholkowi w kopcu
				}
				e = e.next; //bierz kolejna krawedz
			}
			u = heap.extract_min(); //zdejmij kolejny wierzcholek o najmniejszej odleglosci
		}
		//printInfo(graph, node, s); //wypisz info o przebiegu algorytmu
		return node;
	}
	
	/**
	 * Metoda wypisujaca informacje o przebiegu algorymu Dijkstry. Wypisuje zrodlo, element docelowy, calkowity koszt przebytej
	 * sciezki, oraz sciezke z wagami po ktorej przeszedl.
	 * @param graph Graf na ktorym algorytm byl wykonywany
	 * @param node Tablica node'ow z zapamietanymi dystansami oraz krawedziami je poprzedzajacymi
	 * @param s Indeks startowego wierzcholka
	 */
	private static void printInfo(Graph graph, Node node[], int s){
		System.out.println("ALGORYTM DIJKSTRA dla wierzcholka "+s+"\n");
		for(int i=0 ; i<graph.array.length ; i++){ //petla przebiegajaca po wszystkich wierzcholkach do ktorych wierzcholek s mial dojsc
			if(i==s) continue; //nie pokazuj sciezki do mnie samego
			System.out.println("Zrodlo: "+s+"\nKoniec: "+i+"\nKoszty uzyte do zbudowania sciezki: "+node[i].dist+"\nSciezka:");
			Edge e = node[i].prev; //referencje do krawedzi ktorymi szedlem
			while( e!=null ){
				System.out.println(e.start + " " + e.end + " " + e.weight);
				e = node[e.start].prev; //bierz kolejna krawedz
			}
			System.out.println();
		}
	}
}
