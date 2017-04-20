package FrontPanels.Map.Dijkstra;

/**
 * Klasa reprezentujaca kopiec d-arny bedacy kolejka priorytetowa, z minimalnym elementem majacym najwyzszy priorytet.
 * @author Kemer
 * @param <T> Typ wartosci bedacych w kopcu
 */
public abstract class Heap <T> {

	/**
	 * Metoda equals ktora musi zaimplementowac kazda klasa, chcaca korzystac z kopca. Porownuje ona dwa elementy.
	 * @param x Pierwszy porownywany element
	 * @param y Drugi porownywany element
	 * @return 1 gdy element x wiekszy od y, 0 gdy elementy sa rowne, -1 gdy element x mniejszy od y
	 */
	public abstract int equals(T x, T y);
	
	/**aktualny rozmiar tablicy*/
	protected int size=0;
	/**Tablica zawierajaca elementy kopca*/
	protected T array[];
	/**Ilosc synow kazdego wezla (arnosc kopca)*/
	protected int d;
	
	/**
	 * Konstruktor klasy Heap. Wykonuje on odpowiednie rzutowanie typu generycznego na tablice elemtow typu 'c'
	 * @param c typ tablicy
	 * @param d ilosc synow kazdego wezla (arnosc kopca)
	 * @param s maksymalny rozmiar tablicy
	 */
	@SuppressWarnings("unchecked")
	public Heap(Class<T> c, int d, int s){
		this.d = d; //ustawienie arnosci dla calego kopca
		array = (T[])java.lang.reflect.Array.newInstance(c, s);
	}
	
	/**
	 * Metoda dodajaca element z kluczem 'key' do kopca. Dodawanie polega na wstawieniu na ostatnie miejsce w kopcu elementu
	 * i odpowiednim 'naprawieniu' kopca, poprzez porownanywanie key'a z wielkoscia jego parent'a. W momencie gdy parent bedzie mniejszy
	 * od wstawianego elementu, lub dojdziemy do root'a konczymy procedure.
	 * @param key Wstawiany element
	 */
	public void insert(T key){
		int i = size; //miejsce w kopcu na ktore wstawiamy poczatkowo nowy element
		array[i] = key; //ustawiamy na ostatnia pozycje w kopcu nowo wstawiany element
		size++; //zwiekszenie rozmiaru kopca
		int parent = (i-1)/d; //odczytanie ojca wstawianego elementu
		while(i>0 && equals(array[parent], key)>0 ){ //petla konczaca dzialanie w przypadku dojscia do root'a lub znalezienia mniejszego ojca
			swap(i, parent); //jesli warunki spelnione, zamieniamy miejscami ojca i key'a
			i = parent; //idziemy 'w gore' kopca, na aktualne miejsce key'a
			parent = (i-1)/d; //odczytujemy nowego parent'a naszego wstawianego elementu
		}
	}
	
	/**
	 * Metoda zwracajaca i jednoczesnie usuwajaca element na szczycie kopca, bedacy najmniejszym elementem kopca.
	 * Podmienia on ostatni element kopca z owym szczytem i zmniejsza rozmiar kopca. Nastepnie naprawia kopiec
	 * metoda heapify zaczynajac od szczytu, co spowoduje ponowne umieszczenie najmniejszego elementu na szczycie
	 * kopca.
	 * @return Najmniejszy element kopca, znajdujacy sie na jego szczycie
	 */
	public T extract_min(){
		if(size<1) return null; //jesli za maly kopiec, zwroc null
		T min = array[0]; //zapamietaj najmniejsza wartosc, aby pozniej ja zwrocic
		swap(0, size-1); //podmien ostatni element z pierszym (szczytem)
		size--; //zmniejsz rozmiar kopca
		heapify(0); //naprawa kopca, ktora jednoczesnie umiesci najmniejszy element na szczycie
		return min; //zwroc wczesniej zapamietany najmniejszy element kopca
	}
	
	/**
	 * Metoda zmniejszajaca wartosc elementu o indeksie 'i' na wartosc 'new_value'.
	 * @param i Indeks zmniejszanego elementu
	 * @param new_value Nowa wartosc elementu, ktora musi byc mniejsza niz stara wartosc
	 */
	public void decrease_key(int i, T new_value){
		if(equals(array[i],new_value)<0){ //jesli stara wartosc jest mniejsza niz nowa wartosc
			System.out.println("Nowa wartosc wieksza od starej."); //wypisz komunikat o bledzie
			return; //zakoncz procedure
		}
		int parent = (i-1)/d; //ojciec elementu na ktory wskazuje 'i'
		//jesli nie doszedles jeszcze do roota i twoj ojciec jest wiekszy od nowej wartosci to wejdz w petle
		while(i>0 && equals(array[parent], new_value)>0){
			swap(i, parent);  //zamien ojca z synem
			i = parent; //idziemy do gory, czyli bedziemy wskazywac na parent'a
			parent = (i-1)/d; //dopasowujemy parenta naszemu elementowi
		}
	}
	
	
	/**
	 * Metoda naprawiajaca wlasnosci kopca. Wykonuje sie ja na podanym wierzcholku o indeksie 'i'. Podpisuje ona
	 * pod zmienna 'smallest' indeks elementu miejacego najmniejsza wartosc sposrod node'a o indeksie 'i', oraz jego
	 * synow. Jesli tym elementem jest 'i' to znaczy ze jest on juz na odpowiednim miejscu i konczymy procedure. 
	 * Jesli nie to swapujemy go z najmniejszym elementem. Po swapie wykonywana jest ta sama procedura, zaczynajaca 
	 * sie w miejscu, na ktore podmienilismy indeks 'i'.
	 * @param i Indeks elementu od ktorego chcemy zaczac naprawe
	 */
	private void heapify(int i){
		int l = (i*d)+1; //indeks mozliwego lewego syna
		int smallest=i; //zmienna ktora bedzie zawierala indeks najmniejszego elementu sposrod 'i', oraz jego synow
		for(int j=l ; j<l+d && j<size ; j++) //petla lecaca po wszystkich synach majaca za zadanie ustawic indeks najmniejszego elementu 
			if(equals(array[j], array[smallest]) < 0) //jesli jestes mniejszy od najmniejszego dotad elementu
				smallest = j; //ustaw nowy najmniejszy element
		
		if(smallest != i){  //jesli 'i' nie jest najmniejszym elementem, czyli nie jest on jeszcze na dobrej pozycji
			swap(i, smallest); //zamien 'i' z najmniejszym 
			heapify(smallest); //powtorz procedure, z elementem wejsciowym bedacym juz "nizej" w kopcu
		}
	}
	
	/**
	 * Metoda budujaca kopiec z tablicy tab
	 * @param tab Tablica ktora daje nam elementy, z ktorych utworzymy kopiec
	 */
	public void build_heap(T tab[]){
		size = tab.length; //rozmiar kopca to wielkosc podanej dablicy
		for(int i=0 ; i<tab.length ; i++) array[i] = tab[i]; //zapisz elementy tablicy
		for(int i=array.length/d ; i>=0 ; i--) //wykonywanie heapify ktore stworzy kopiec
			heapify(i);
	}
	
	/**
	 * Metoda wypisujaca kopiec, zaczynajac od poczatku tablic, konczac na jej koncu.
	 */
	public void writeHeap(){
		for(int i=0 ; i<size ; i++) System.out.print(array[i]+" ");
		System.out.println();
	}
	
	/**
	 * Metoda zamieniajaca miejscami dwa elementy w tablicy 'array[]'
	 * @param i indeks elementu w tablicy ktory chcemy zamienic z elementem j-tym
	 * @param j indeks elementu w tablicy ktory chcemy zamienic z elementem i-tym
	 */
	protected void swap(int i, int j){
		T temp = array[i]; //tymczasowa wartosc
		array[i] = array[j]; //zamiana i z j
		array[j] = temp; //podstawienie tymczasowej wartosci pod j
	}
}
