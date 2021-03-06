<?php 
require_once("ModelPHP.php"); 
$P = new Page(2); 
$P->addJS("test_pierwszosci.js");
echo $P->Begin(); 
?> 

<h1 id="ARTICLETITLE">Test pierwszosci</h1>
<p>Test pierwszości to algorytm określający, czy dana liczba jest pierwsza, czy złożona. Nie jest to równoważne znalezieniu jej rozkładu na czynniki pierwsze. W obecnej chwili (2011 rok) nie są znane efektywne algorytmy rozkładu na czynniki pierwsze, natomiast testy pierwszości można przeprowadzać bardzo szybko.</p>
 
<p><h3> Metoda Naiwna </h3></p>
Najprostszy test pierwszości wygląda następująco: dla danej liczby <i>n</i> należy sprawdzić, czy dzieli się ona kolejno przez 2, 3, aż do <i>n−1</i>. Jeśli przez żadną z nich się nie dzieli, oznacza to, że jest pierwsza.
 
Zamiast testować wszystkie liczby do <i>n−1</i>, wystarczy sprawdzić podzielność n przez liczby mniejsze lub równe pierwiastkowi z <i>n</i>.
 
Kolejne udoskonalenie polega na sprawdzaniu podzielności n jedynie przez liczby pierwsze mniejsze lub równe pierwiastkowi z <i>n</i>. Ich listę łatwo możemy uzyskać metodą sita Eratostenesa. Metoda ta wciąż wymaga wykonania dużej liczby dzieleń, co oznacza, że już dla 50-cyfrowych liczb pierwszych jest niewykonalna na współczesnych komputerach.
 
<p><h3>Testy probabilistyczne</h3></p>
Obecnie najbardziej efektywne i najczęściej stosowane są testy probabilistyczne. Korzysta się w nich z losowo wygenerowanych liczb z ustalonego przedziału – pewien dobór tych wartości może dać błędny wynik testu, ale przy wybraniu wystarczająco wielu z nich prawdopodobieństwo takiego zdarzenia jest znikome.
 
<p><h4>Przebieg testu probabilistycznego wygląda następująco:</h4></p>
 
  <ol>
    <li>Wybrać losowo liczbę <i>a</i>. <br></li>
    <li>Sprawdzić pewne równanie zawierające a oraz zadaną liczbę n. Jeśli okaże się fałszywe, zwrócić wynik <i>n jest złożona</i>. Wartość a jest wtedy świadkiem złożoności i test można zakończyć.</li>
    <li>Powtarzać całą procedurę, aż uzyska się wystarczającą pewność.</li>
  </ol>
 
      <section>
        <div class="row">
            <div class="col-3-6">
            <p>  <h3>Java</h3> </p>
              <pre id="PSEUDOKOD_PANEL"><code>private static boolean fermatTest(int n, int k)
{
  int a, i;
  Random rand = new Random();
 
  if (n < 4)
 {
   return true;
 }
 
 for (i=0; i < k; i++)
 {
   a = rand.nextInt(n-2) + 2;
   if (power_modulo_fast(a, n-1, n) != 1)
   {
     return false;
   }
 }
 
 return true;
}
</code></pre>
          </div>
 
          <div class="col-3-6">
            <p>  <h3>JavaScript</h3> </p>
              <pre id="JAVASCRIPT_PANEL"><code>function fermatTest(var n, var k)
{
  var a, i, result;
 
  if(n < 4)
 {
   result = " jest pierwsza!";
 }
 
 for (i = 0; i < k; i++)
 {
   a = Math.floor((Math.random() * (n-2) + 2));
   if (power_modulo_fast(a, n-1, n) != 1)
   {
     result = "Nie jest pierwsza.";
     return result;
   }
 }
 result = n + " prawdopodobnie jest pierwsza!";
 
 return result;
}
</code></pre>
          </div>
      </section>
     
      <section>
        <h2>Test Pierwszości Fermata</h2>
        <div class="panel">
          Wprowadź liczbę n oraz dokładność testu  następnie naciśnij przycisk "Testuj".<br><br>
           <label for="varN">n:</label>
           <input type="number" min="1" max="1000000" value="17" id="varN">&nbsp;
           <label for="varK">Dokładność:</label>
           <input type="number" min="1" max="1000000" value="5" id="varK">&nbsp;
        </div>
        <p>
          <button class = "button" id="CALC">Testuj</button>
        </p>
 
        <h3>Wynik testu:</h3>
        <div id="trace" class="panel result">
        </div>

<?php echo $P->End(); ?>