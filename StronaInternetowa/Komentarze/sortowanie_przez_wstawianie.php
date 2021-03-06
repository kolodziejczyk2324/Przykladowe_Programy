<?php 
require_once("ModelPHP.php"); 
$P = new Page(4); 
$P->addJS("sortowanie_przez_wstawianie.js");
echo $P->Begin(); 
?> 

               <h2 id="ARTICLETITLE">Sortowanie przez wstawianie</h2>
                Sortowanie przez wstawianie należy do prostych algorytmów sortowania o złożoności O(n<sup>2</sup>). Polega ono na tym, że bierzemy kolejne elementy do posortowania i wstawiamy je pomiędzy elementy będące już na odpowiednich pozycjach.
                <ol>
                    <li>Weźmy przykładową tablicę o elementach [3,9,5,2,4].</li>
                    <li>Zaczynamy od drugiego elementu tablicy i sprawdzamy, czy jest on mniejszy od elementu po lewej stronie. Jeśli tak, to zamieniamy je miejscami i sprawdzamy następny wyraz, aż napotkany element będzie miał wartość mniejszą od obecnie wstawianego.</li>
                    <li>Po pierwszym kroku 9 pozostanie na swojej pozycji, ponieważ nie jest mniejsza od swojego lewego sąsiada.</li>
                    <li>Teraz bierzemy 5 i sprawdzamy, czy jest mniejsza od poprzedniego elementu. Ponieważ jest, to zamieniamy miejscami te elementy. Następnie sprawdzamy, czy znowu 5 jest mniejsza od poprzednika – nie jest, a więc zostaje na tej pozycji (tablica wygląda tak: [3,5,9,2,4]).</li>
                    <li>Następnie bierzemy kolejny element (2) i dopóki poprzednie elementy są większe od 2, dopóty zamieniamy je miejscami. Tablica zmieni się następująco:<br/>
                    [3,5,<b>9,2</b>,4] -> [3,<b>5,2</b>,9,4] -> [<b>3,2</b>,5,9,4] -> [2,3,5,9,4]</li>
                    <li>Ostatni element podobnie zamieniamy miejscami z poprzednikami, aż ich wartość będzie nie większa od wstawianego elementu.</li>
                </ol>
                <section class="codeSec"><h3 id="h3Code">Kod sortowania przez wstawianie</h3>
                    <div class="col-3-6">
                        <h4>Pseudo-kod</h4>
<pre id="PSEUDOKOD_PANEL"><code>INSERTION-SORT (A)
for i ← 1 to length(A)
    tmp ← A[i];
    j ← i - 1
    while j >= 0 and A[j] > tmp
        swap A[j+1] and A[j]
        j ← j - 1
    end while
    A[j+1] ← tmp
end for</code></pre>
                    </div>
                    <div class="col-3-6" id="jasc">
                        <h4>JavaScript</h4>
<pre id="JAVASCRIPT_PANEL"><code>INSERTION-SORT (A)
{
  for(var i=1; i&#60;A.length; ++i)
  {
    var tmp = A[i];
    var j=i-1;
    while((j>=0) &#38;&#38; (A[j]>tmp))
    {
      A[j+1] = A[j];
      --j;
    }
    A[j+1] = tmp;
  }
}</code></pre>
                    </div>
                </section>
                <section class="col-6-6" id="complexity"><h3 id="com">Złożoność algorytmu</h3>
                    <div id="compl-1">$ O(n^2) $</div>
                    <div id="compl-2">$ O(1) $</div>
                    <div class="desc">czasowa</div><div class="desc">pamięciowa</div>
                </section>
                <section><h3 id="cprim">Sortowanie ciągu</h3>
                    <div class="col-6-6" id="calc">
                        &nbsp;&nbsp;Wprowadź liczbę n (ilość elementów) i naciśnij przycisk "Oblicz".<br/>
                        &nbsp;&nbsp;<label for="varN">n:</label>
                        <input type="number" min="2" max="1000000" value="19" id="varN">
                        <p>
                            <span class="button" id="CALC">OBLICZ</span>
                        </p>
                        <h4>Wynik</h4>
                    </div>
                </section>
            </article>
            <div class="col-4-6" id="window">
                &nbsp;
            </div>

<?php echo $P->End(); ?>