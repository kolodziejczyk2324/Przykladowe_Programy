package FrontPanels.Map.Dijkstra;

/**
 * Klasa reprezentujaca graf. Zawiera w sobie podklase reprezentujaca krawedz.
 * @author Kemer
 */
public class Graph {

	/**Zmienna zawierajaca ilosc krawedzi. Inkrementowana jest przy kazdym pomyslnym wywolaniu metody 'addEdge'*/
	private int numberOfEdge=0;
	
	/**
	 * Klasa reprezentujaca krawedz. Zawiera jej poczatek i koniec reprezentowany przez liczbe calkowita, wage oraz referencje na
	 * kolejny w kolejnosci obiekt typu 'Edge', sluzacy do reprezentacji listy. Owa kolejna krawedz jest krawedzia wychodzaca z
	 * tego samego wierzcholka.
	 * @author Kemer
	 */
	public class Edge{
		public int start, end; //publiczne zmienne represuentujace poczatek i koniec krawedzi
		public double weight; //publiczna zmienna represuentujaca wage
		public Edge next; //wskaznik na kolejna krawedz w liscie, ktora to krawedz wychodzi z tego samego wierzcholka co ta
		
		//Konstruktor sluzacy do przypisania powyzszych zmiennych
		public Edge(int s, int e, double w){
			start = s; //poczatek krawedzi
			end = e; //koniec krawedzi
			weight = w; //waga krawedzi
		}
	}
	
	/**
	 * Tablica ktorej indeks elementu reprezentuje id wierzcholka. Kazdy element zawiera liste krawedzi ktore
	 * z niego wychodza
	 */
	public Edge array[];
	
	/**
	 * Konstruktor klasy Graph. Tworzy tablice elementow reprezuentujacych kazdy wierzcholek. Kazdy ten element zawiera liste krawedzi
	 * ktore wychodza z owego wierzcholka.
	 * @param numberOfNode Liczba wierzcholkow
	 */
	public Graph(int numberOfNode){
		array = new Edge[numberOfNode]; //tworzenie tablicy wierzcholkow, w ktorych znajdowac bedzie sie lista krawedzi ktore z niego wychodza
		for(int i=0 ; i<numberOfNode ; i++) array[i] = null; 	//ustawienie kazdego elementu na null, ostatni element listy bedzie wskazywal
																//wlasnie na null
	}
	
	/**
	 * Metoda dodajaca krawedz skierowana do grafu. Przy poprawnym dodaniu inkrementowana jast zmienna mowiaca o ilosci krawedzi.
	 * @param start Id wierzcholka startowego
	 * @param end Id wierzcholka koncowego
	 * @param weight Waga krawedzi
	 */
	public void addDirectedEdge(int start, int end, double weight){
		if(	correctIdNode(start, end) ){ //sprawdzenie czy podana zostala poprawna krawedz	
			System.out.println("Bledne id wierzcholka!"); //jesli nie wypisz komunikat o bledzie
			return; //nie dodawanaj krawedzi
		}
		Edge e = new Edge(start, end, weight); //stworz nowa krwedz
		e.next = array[start]; //wstaw ja na poczatek listy
		array[start] = e; //ustaw poczatek listy na ta krawedz
		numberOfEdge++; //inkrementacja zmiennej mowiacej o 
	}
	
	/**
	 * Metoda dodajaca krawedz nieskierowana do grafu. Dzieje sie to przez dodanie dwoch krawedzi skierowanych 
	 * od 'start' do 'end' i od 'end' do 'start'
	 * @param start Indeks pierwszego wierzcholka krawedzi
	 * @param end Indeks drugiego wierzcholka krawedzi
	 * @param weight Waga nowej krawedzi
	 */
	public void addUndirectedEdge(int start, int end, double weight){
		addDirectedEdge(start, end, weight); //dodaj krawedz z 'start' do 'end'
		addDirectedEdge(end, start, weight); //dodaj krawedz z 'end' do 'start'
	}
	
	/**
	 * Metoda sprawdzajaca poprawnosc parametrow poczatku i konca krawedzi
	 * @param start Poczatek krawedzi
	 * @param end Koniec krawedzi
	 * @return true-paramtetry poprawne, false-parametry niepoprawne
	 */
	private boolean correctIdNode(int start, int end){
		return (	start<0 && 	start>=array.length &&
					end<0 	&& 	end>=array.length		);
	}
	
	/**
	 * Metoda wypisujaca caly graf. Wypisuje cala tablice 'array' poczynajaca od 0 wierzcholka i wypisujac jego sasiednie krwedzie.
	 */
	public void printEdges(){
		double suma = 0; //zmienna ktora zawierac bedzie suma wag krawedzi
		for(int i=0 ; i<array.length ; i++){ //petla lecaca po calej tablicy array
			Edge e = array[i]; //referencja na odpowiednia sasiednia krawedz wlasnie sprawdzanego wierzcholka
			while(e!=null){ //jesli listat sie jeszcze nie skonczyla to wejdz w petle
				System.out.println(e.start+" "+e.end+" "+e.weight); //wypisz informacje o krawedzi
				suma += e.weight; //zwiekszenie sumy wag wszystkich krawedzi
				e = e.next; //przejdz do nastepnego sasiada w liscie
			}
		}
		System.out.println("Suma wag krawedzi: "+suma); //wypisz info o sumie wag krawedzi
	}
	
	/**
	 * Getter zwracajacy ilosc krawedzi w grafie
	 * @return Ilosc krawedzi w grafie
	 */
	public int getNumberOfEdge(){
		return numberOfEdge;
	}
}
