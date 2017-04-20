<?php 
require_once("ModelPHP.php"); 
$P = new Page(6); 
$P->WstawCytat(	"Pomimo, że podstawowa idea wyszukiwania binarnego jest stosunkowo prosta, dane mogą być zaskakująco trudne...",
							"Donald Knuth", "Sztuka programowania", "tom. 3, wydanie 2 (1981), s. 274.");
$P->useMathJax();
$P->addJS("wyszukiwanie_binarne.js");
echo $P->Begin(); 
?> 

						<h2 id="ARTICLETITLE">Wyszukiwanie binarne</h2>
						<aside>				
								Wyszukiwanie binarne to jeden z najpopularniejszych algorytmów do wyszukiwania danych w tablicy posortowanych liczb. Algorytm ten został oparty o metodę „dziel i zwyciężaj” dzięki czemu potrafi w czasie logarytmicznym znaleźć szukaną liczbę oraz zwrócić jej indeks jeśli oczywiście została odnaleziona.
						</aside>
						<h3>Złożoność obliczeniowa</h3>
						<aside>
								<center>$ T(n) = O(log(n)) $</center>
						</aside>
						<section id="PANEL_Z_KODEM">
							<h3>Kod wyszukiwania binarnego</h3>
								<div class="row">
									<div class="col-3-6">
										<h4>Pseudo-kod</h4>
<pre id="PSEUDOKOD_PANEL">
A:=[...]
lewo:=1
prawo:=n

y:=poszukiwana wartość
indeks:=pusty

while lewo &lt; prawo do
begin
  środek:=(lewo + prawo)/2;

  if A[środek] &lt; y then
    lewo:=środek + 1 
  else 
    prawo:=środek; 
end;

if A[lewo]=y then
	indeks:=lewo
indeks:=brak; 
</pre>
								</div>
								<div class="col-3-6">
										<h4>JavaScript</h4>
<pre id="JAVASCRIPT_PANEL">
function BS(array, key) 
 var l = 0,
 h = array.length - 1,
 mid,
 element;
 while (l <= h) {
  mid=Math.floor((l+h)/2,10);
  element = array[mid];
  if (element < key) {
   l = mid + 1;
  }
  else if (element > key) {
   h = mid - 1;
   } else {
    return mid;
   }
  }
 return -1;
}
</pre>
								</div>
							</div>
						</section>
						
						<section>
							<h2>Binarne wyszukiwanie liczby</h2>
							<div class="panel">
								Wprowadź szukaną liczbę ze zbioru [1,2,3,4,5,6,7,8,9,10]<br><br>
								<label for="varX">X:</label>
								<input type="number" min="1" max="10" value="8" id="varX">
							</div>
							<p>
								<span class = "button" id="CALC">SZUKAJ</span>
							</p>
							<h3>Obliczenia</h3>
							<div id="trace" class="panel"></div>
						</section>

<?php echo $P->End(); ?>