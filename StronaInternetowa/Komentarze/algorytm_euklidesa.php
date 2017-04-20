<?php 
require_once("ModelPHP.php"); 
$P = new Page(0); 
$P->WstawCytat(	"[Algorytm Euklidesa] jest pradziadkiem wszystkich algorytmów, ponieważ jest najstarszym nietrywialnym algorytmem, który przetrwał do naszych czasów.",
							"Donald Knuth", "Sztuka programowania", "tom. 3, wydanie 2 (1981), s. 274.");
$P->addJS("algorytm_euklidesa.js");
echo $P->Begin(); 
?> 

 <h1 id="ARTICLETITLE">Największy Wspólny Dzielnik</h1>
      <p>
      Największym wspólnym dzielnikiem dwóch <strong>liczb naturalnych</strong> a, b nazywamy najwiekszą liczbę naturalną k taką, że (k|a) oraz (k|b). Oznaczamy ją NWD(a,b). Oto najważnieje własności tej funkcji:
      </p>
	  <ul id="first">
			<li>NWD(a,a) = a;</li>
			<li>NWD(a,b) = NWD(b,a);</li>
			<li>Jeśli a > b to NWD(a,b) = NWD(a-b,b);</li>
			<li>Jesli a = k|b + t to NWD(a,b) = NWD(b,r)</li>
		</ul>

      <aside>
      <h2>Historia algorytmu Euklidesa</h2>
      <p>
      Algorytm wyznaczania największego wspólnego dzielnika dwóch liczb naturalnych oparty na przedstawionych tutaj własnościach został opisany przez Euklidesa w dziele <cite>Elementy</cite>, w księgach siódmej oraz dziesiątej, które powstało około 300 lat p.n.e.
      </p>
      </aside>

      <section id="PANEL_Z_KODEM">
        <h2>Kod funkcji NWD</h2>
        <div class="row">
          <div class="col-3-6">
            <h3>Pseudo-kod</h3>
            <pre id="PSEUDOKOD_PANEL"><code>FUNCTION NWD(a,b)
BEGIN
  WHILE (b &lt;&gt; 0) DO
    [a,b] := [b, a mod b]
  OD
  RETURN a;
END</code></pre>
		</div>
          <diV class="col-3-6">
            <h3>JavaScript</h3>
            <pre  id="JAVASCRIPT_PANEL"><code>function NWD(a,b){
  var q;
  while (b != 0){
    q = a;
    a = b;
    b = q mod b;
 }
 return a;
}</code></pre>
          </div>
        </div>
      </section>

      <section>
        <h4>Obliczanie NWD</h2>
        <div class="panel">
          Wprowadź liczby X i Y i następnie naciśnij przycisk "Oblicz".<br><br>
           <label for="varX">X:</label> 
           <input type="number" min="1" max="1000000" value="700" id="varX"/>&nbsp;
           <label for="varY">Y:</label> 
           <input type="number" min="1" max="1000000" value="245" id="varY"/>
        </div>
        <p>
          <span class = "button" id="CALC">OBLICZ</span>
        </p>

        <h3>Obliczenia</h3>
        <div id="trace">
          &nbsp;
        </div>
      </section>
	  
<?php echo $P->End(); ?>

