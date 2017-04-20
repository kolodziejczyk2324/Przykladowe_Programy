<?php 
require_once("ModelPHP.php"); 
$P = new Page(3);
$P->addJS("sito_erastotenesa.js");
$P->useMathJax();
echo $P->Begin(); 
?> 

           <h2 id="ARTICLETITLE">Sito Eratostenesa</h2> 
				<aside>
                Sitem Eratostenesa jest przypisywany <b>Eratostenesowi</b> z <b>Cyreny</b> algorytm wyznaczania <b>liczb pierwszych</b> z zadanego przedziału [2,n], działający w następujący sposób:<br/>
                <ol>
                    <li>Ze zbioru liczb naturalnych z przedziału <b>[2,n]</b>, tj. <b>{2,3,4...n}</b> wybieramy najmniejszą, czyli 2, i wykreślamy wszystkie jej wielokrotności większe od niej samej, to jest <b>4, 6, 8...</b>.</li>
                    <li>Wybieramy najmniejszą niewykreśloną liczbę (3) i usuwamy wszystkie jej wielokrotności większe od niej samej: <b>6, 9, 12...</b>, przy czym nie ma znaczenia to, że niektóre z nich (np. 6 albo 12) zostaną skreślone więcej niż raz.</li>
                    <li>Procedurę powtarzamy dla liczb 5, 7, 11, 13; aż do sprawdzenia wszystkich niewykreślonych wcześniej liczb.</li>
                    <li>Wykreślanie powtarzamy do momentu, gdy liczba <b>i</b>, której wielokrotność wykreślamy, będzie większa niż &radic;<span style="text-decoration: overline"><b>n</b></span>.</li>
                    <li>Dla danej liczby <b>n</b> wszystkie niewykreślone liczby mniejsze bądź równe <b>n</b> są <b>liczbami pierwszymi</b>.</li>
                </ol>
				</aside>
                <section class="codeSec"><h3 id="h3Code">Kod algorytmu</h3>
                    <div class="col-3-6">
                        <h4>Pseudo-kod</h4>
<pre id="PSEUDOKOD_PANEL"><code>FUNCTION eratosthenes(n)
 
i := 2;
WHILE (i &#8592; √n) DO
  IF A[i] = TRUE:
   j := i+i;
   WHILE (j &#8592;n)
    A[j] := FALSE;
    j := j + i;
   OD
  i := i+1;
OD
 
i := 2;
WHILE (i &#8592; n)
  IF A[i] = TRUE:
   PRINT i;
OD</code></pre>
                    </div>
                    <div class="col-3-6" id="jasc">
                        <h4>JavaScript</h4>
<pre id="JAVASCRIPT_PANEL"><code>function eratosthenes(n) {
  var s, i, j, max;
  var A = new Array(n);
  max = Math.floor(Math.sqrt(n));
  s = "";
 
  for (i = 0; i &#8592; n; i++) {
    A[i] = 1;
  }
 
  for (i = 2; i &#8592; max; i++) {
    if (A[i] == 1) {
      for (j = i+i; j &lt;= n; j = j+i) {
        A[j] = 0;
      }
    }
  }
 
  for (i = 2; i &#8592; n; i++) {
    if (A[i] == 1) {
        s += i + "  ";
      }
    }
}</code></pre>
                    </div>
                </section>
                <section class="col-6-6" id="complexity"><h3 id="com">Złożoność algorytmu</h3>
                    <div id="compl-1">$ O((n log n)(log log n)) $</div>
                    <div id="compl-2">$ O(n) $</div>
                    <div class="desc">czasowa</div><div class="desc">pamięciowa</div>
                </section>
                <section><h3>Wyznaczanie liczb pierwszych</h3>
                    <div class="col-6-6" id="calc">
                        &nbsp;&nbsp;Wprowadź liczbę n i naciśnij przycisk "Oblicz".<br/>
                        &nbsp;&nbsp;<label for="varN">n:</label>
                        <input type="number" min="2" max="1000000" value="19" id="varN">
                        <p>
                            <span class="button" id="CALC">OBLICZ</span>
                        </p>
                        <h4>Wynik</h4>
                    </div>
                </section>
            <div class="col-4-6" id="window">
                &nbsp;
            </div>
						
<?php echo $P->End(); ?>