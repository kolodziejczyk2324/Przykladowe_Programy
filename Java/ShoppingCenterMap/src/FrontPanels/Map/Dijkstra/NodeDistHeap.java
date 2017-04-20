package FrontPanels.Map.Dijkstra;

import FrontPanels.Map.Dijkstra.Dijkstra.Node;

/**
 * Kopiec d-arny zawierajacy wezly grafu. Porownuje node'y po ich wartosci dist (dystans). Jest wykorzystywany w algorytmie Dijkstra.
 * W klasie nastepuje przesloniecie metody 'swap' z klasy bazowej Heap, poniewaz przy okazji zamiany elementow w kopcu musza byc
 * podmieniane zmienne mowiace o aktualnym indeksie w kopcu danych wezlow. Sa one wykorzystywane przy okazji wykonywania metody
 * decrease_key, gdyz w parametrze podawana jest tylko referencja do node'a. Dzieki pamietaniu indeksu w wezle mamy staly dostep do
 * wezla w kopcu.
 * @author Kemer
 */
public class NodeDistHeap extends Heap<Node> {
	
	public NodeDistHeap(Class<Node> c, int d, int s) {
		super(c, d, s);
	}
	
	/**
	 * Zdefiniowana metoda abstrakcyjna z klasy Heap. Porownywanie wezlow jest po ich wartosci dist (dystans).
	 */
	public int equals(Node x, Node y) {
		if(x.dist > y.dist) return 1;
		else if(x.dist < y.dist) return -1;
		else if(x.dist == y.dist) return 0;
		return 0;
	}
	
	/**
	 * Metoda zmniejszajaca wartosc node'owi na new_value. Odczytuje ona od node'a jego aktualna pozycje w kopcu, nadaje mu 
	 * pomniejszona wartosc, i odpowiednio naprawia kopiec idac w gore.
	 * @param node Node ktoremu zmniejszamy wartosc
	 * @param new_value Nowa wartosc node'a
	 */
	public void decrease_key(Node node, double new_value){
		if(node.dist < new_value){ //jesli stara wartosc jest mniejsza niz nowa wartosc
			System.out.println("Nowa wartosc wieksza od starej."); //wypisz komunikat o bledzie
			return; //zakoncz procedure
		}
		node.dist = new_value; //podstaw node'owi nowa, mniejsza wartosc
		int i = node.position; //odczytaj indeks node'a
		int parent = (i-1)/d; //ojciec elementu na ktory wskazuje 'i'
		//jesli nie doszedles jeszcze do roota i twoj ojciec jest wiekszy od nowej wartosci to wejdz w petle
		while(i>0 && array[parent].dist>array[i].dist){
			swap(i, parent);  //zamien element 'i' z parentem
			i = parent; //idziemy do gory
			parent = (i-1)/d; //dopasowujemy parenta naszemu elementowi
		}
	}

	/**
	 * Metoda wypisujaca kopiec, zaczynajac od poczatku tablic, konczac na jej koncu.
	 */
	public void writeHeap(){
		for(int i=0 ; i<size ; i++) System.out.print(array[i].dist+" ");
		System.out.println();
	}
	
	/**
	 * Metoda zamieniajaca miejscami dwa elementy w tablicy 'array[]'
	 * @param i indeks elementu w tablicy ktory chcemy zamienic z elementem j-tym
	 * @param j indeks elementu w tablicy ktory chcemy zamienic z elementem i-tym
	 */
	protected void swap(int i, int j){
		Node temp = array[i]; //tymczasowa wartosc
		int tempPosition = array[i].position; //pozycja tymczasowego elementu
		array[i].position = array[j].position; //podmiana indeksow
		array[j].position = tempPosition; //podmien pozycje
		array[i] = array[j]; //zamiana i z j
		array[j] = temp; //podstawienie tymczasowej wartosci pod j
	}
}
