<?php 
require_once("ModelPHP.php"); 
$P = new Page(1); 
$P->WstawCytat(	"Gdyby budowlańcy tak budowali budynki, jak informatycy piszą swoje programy, to jeden dzięcioł załatwiłby całą cywilizację.",
							"Jedno z", "Ogólnych praw Murphyego", "dotyczące komputerów.");
$P->addJS("rozszerzony_algorytm_euklidesa.js");
echo $P->Begin(); 
?> 


						<h1 id="ARTICLETITLE">Rozszerzony Algorytm Euklidesa</h1>
						<p>
							Jeśli przechowywana będzie informacja o kolejnych ilorazach, to będzie też można wyznaczyć liczby całkowite w równaniu  <i>a•p+b•q=</i>NWD<i>(a,b)</i>. Ta metoda nazywana jest rozszerzonym algorytmem Euklidesa.
						</p>
						<p>
							Dla liczb <i>a</i> = 1547 i <i>b</i> = 560 algorytm Euklidesa zwróci NWD(<i>a</i>, <i>b</i>) = 7
						</p>
						<ol>
							<li>7 = <b>28</b> -1 • <b>21</b> = 28 - 1 • (133 - 4 • 28)</li>
							<li>28 - 1 • (133 - 4 • 28) = -1 • <b>133</b> + 5 • <b>28</b> = -1• 133 + 5 • (427 -3 • 133)</li>
							<li>-1• 133 + 5 • (427 -3 • 133) = 5 • <b>427</b> - 16 • <b>133</b> = 5 • 427 - 16 • (560 - 1 • 427)</li>
							<li>5 • 427 - 16 • (560 - 1 • 427) = -16 • <b>560</b> + 21 • <b>427</b> = -16 • 560 - 21 • (1547- 2 • 560)</li>
							<li>-16 • 560 - 21 • (1547- 2 • 560) = 21 • <b>1547</b> - 58 • <b>560</b></li>
						</ol>
						<aside>
							<p>
								Ponadto rozszerzony algorytm Euklidesa jest intensywnie stosowany do rozwiązywania równań, w przekształceniach kryptograficznych.
							</p>
						</aside>
						<section id="PANEL_Z_KODEM">
							</br>Pseudo-kod algorytmu
							<div class="row">
								<diV class="col-3-6">
									<h3>Pseudo-kod</h3>
									<pre id="PSEUDOKOD_PANEL" style="font-size: 13px;">
FUNCTION NWD(a,b)
BEGIN
   a0 := a;
   b0 := b;
   p := 1; 
   q := 0;
   r := 0; 
   s := 1;
   WHILE (b != 0) DO
      c := a mod b;
	  quot := FLOOR( a/b );
	  a := b;
	  b := c;
	  r_tmp := r; 
	  s_tmp := s;
	  r := p - quot * r;
	  s := q - quot * s;
	  p := r_tmp; 
	  q := s_tmp;
    OD
END</pre>
								</div>
								<div class="col-3-6">
									<h3>JavaScript</h3>
									<pre id="JAVASCRIPT_PANEL" style="font-size: 13px;">
function nwd(a,b){
  var x = 0, y = 1, 
  lastx = 1, lasty = 0, 
  q, temp1, temp2, temp3;
  while (b != 0){
    q = Math.floor(a/b);
    temp1 = a % b;
    a = b;
    b = temp1;
		
    temp2 = x;
    x = lastx - q * x;
    lastx = temp2;
		
    temp3 = y;
    y = lasty - q * y;
    lasty = temp3;
  }
  return 0;
}
</pre>
								</div>
							</div>
						</section>
						<section>
							<h2>Obliczanie współczynników</h2>
							<div class="panel">
								Wprowadź liczby X i Y i następnie naciśnij przycisk "Oblicz".
								<br>
								<br>
								<label for="varX">X:</label> 
								<input type="number" min="1" max="1000000" value="1547" id="varX">&nbsp;
								<label for="varY">Y:</label> 
								<input type="number" min="1" max="1000000" value="560" id="varY">
							</div>
							<p>
								<span class = "button" id="CALC">OBLICZ</span>
							</p>
							<h3>Obliczenia</h3>
							<div id="trace" style="font: Consolas;">
								&nbsp;
							</div>
						</section>
						
						
						
<?php echo $P->End(); ?>