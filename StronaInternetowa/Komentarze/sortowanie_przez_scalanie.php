<?php 
require_once("ModelPHP.php"); 
$P = new Page(5);
$P->addJS("sortowanie_przez_scalanie.js");
$P->useMathJax();
echo $P->Begin(); 
?> 

<h2 id="ARTICLETITLE">Sortowanie przez scalanie</h2>
<p>
	Rekurencyjny algorytm sortowania danych, stosujący metodę dziel i zwyciężaj. Odkrycie algorytmu przypisuje się Johnowi von Neumannowi.
</p>
<p>
	Wyróżnić można trzy podstawowe kroki.
</p>
	<ol>
		<li>Podziel zestaw danych na dwie równe części,</li>
		<li>Zastosuj sortowanie przez scalanie dla każdej z nich oddzielnie, chyba że pozostał już tylko jeden element,</li>
		<li>Połącz posortowane podciągi w jeden.</li>
	</ol>
<p>
	Szczególnie jest przydatny zwłaszcza przy danych dostępnych sekwencyjnie (po kolei, jeden element naraz), na przykład w postaci listy jednokierunkowej (tj. łączonej jednostronnie) albo pliku sekwencyjnego.
</p>

<h2>Złożoność obliczeniowa</h2>
<div class="row">
	<ol>
		<li>Pierwszy krok (podział) trwa zawsze stały czas, niezależnie od rozmiaru tablicy - $ \theta ( 1 ) $</li>
		<li>Drugi krok (scalanie podtablic) trwa logarytmicznie długo w stosunku do rozmiaru tablicy - $ \theta( \log( n ) ) $</li>
		<li>Ostatni krok trwa liniowo długo w stosunku do rozmiaru tablicy - $ \theta( n ) $</li>
	</ol>
	<p>
		<center>Całkowita złożoność obliczeniowa sortowania przez scalanie:</center>
		<div class="blackboard">
			$$ T(n) = \theta( n\log(n) ) $$
		</div>
	</p>
</div>
	
<h2>Kod funkcji Merge-Sort</h2>
<div class="row">
	<div class="col-3-6">
		<h3>Pseudo-kod</h3>
			<pre id="PSEUDOKOD_PANEL">SORT-SCAL(T, p, r):
  JEŚLI p &lt; r:
	q → (p+r)/2
	SORT-SCAL(T, p, q)
	SORT-SCAL(T, q+1, r)
	SCALANIE(T, p, q, r)</pre>
	</div>
	<div class="col-3-6">
		<h3>JavaScript</h3>
			<pre id="JAVASCRIPT_PANEL">function mergeSort( arr )
{    
  if( arr.length &lt; 2 )
  {
	return arr;
  }
  
  var mid = Math.floor( arr.length / 2 );
  var subLeft = mergeSort( arr.slice( 0, mid ) );
  var subRight = mergeSort( arr.slice( mid ) );
  
  return merge( subLeft, subRight );
}

function merge( a, b )
{
  var result = [];
  
  while( a.length &gt; 0 && b.length &gt; 0 )
  {
	result.push( a[0] &lt; b[0] ? a.shift() : b.shift() );
  }

  return result.concat( a.length ? a : b );
}</pre>
	</div>
</div>

<h2>Sortowanie przez scalanie</h2>
<div class="panel">
	Wprowadź liczby całkowite oddzielone spacją i naciśnij przycisk "Posortuj".
	<br><br>
	<label for="elements">Liczby (elementy): </label>
	<br>
	<input type="text" id="elements" size="50" />
	<br><br>
	<span class="button" id="CALC">Posortuj</span>
</div>
<h3>Wynik</h3>
<div id="sortResult" class="sortResult"></div>
<h3>Krok po kroku</h3>
<div id="stepByStep" class="stepByStep"></div>

<?php echo $P->End(); ?>