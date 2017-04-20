<?php

session_start();

require_once("PHP/CookieSetter.php");
require_once("PHP/basicmysql.php");
require_once("PHP/CommentCreator.php");

//MODEL
$STRONY = 	[
	['id'=>"0",		'name'=>"Algorytm Euklidesa",						'href'=>"algorytm_euklidesa.php"],
	['id'=>"1",		'name'=>"Rozszerzony Algorytm Euklidesa",	'href'=>"rozszerzony_algorytm_euklidesa.php"],
	['id'=>"2",		'name'=>"Test pierwszości",							'href'=>"test_pierwszosci.php"],
	['id'=>"3",		'name'=>"Sito Erastotenesa",							'href'=>"sito_erastotenesa.php"],
	['id'=>"4",		'name'=>"Sortowanie przez wstawianie",			'href'=>"sortowanie_przez_wstawianie.php"],
	['id'=>"5",		'name'=>"Sortowanie przez scalanie",				'href'=>"sortowanie_przez_scalanie.php"],
	['id'=>"6",		'name'=>"Wyszukiwanie binarne",					'href'=>"index.php"],
	['id'=>"7",		'name'=>"Problem wież z Hanoi",					'href'=>"problem_wiez_z_hanoi.php"],
	['id'=>"8",		'name'=>"Problem Hetmanów",						'href'=>"problem_hetmanow.php"],
	['id'=>"9",		'name'=>"Problem skoczka szachowego",		'href'=>""]
						];

//VIEW
$HEADER_1 =<<<EOT
<!doctype html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>{{TITLE}}</title>
		<link rel="apple-touch-icon" sizes="57x57" href="Obrazki/apple-icon-57x57.png">
		<link rel="apple-touch-icon" sizes="60x60" href="Obrazki/apple-icon-60x60.png">
		<link rel="apple-touch-icon" sizes="72x72" href="Obrazki/apple-icon-72x72.png">
		<link rel="apple-touch-icon" sizes="76x76" href="Obrazki/apple-icon-76x76.png">
		<link rel="apple-touch-icon" sizes="114x114" href="Obrazki/apple-icon-114x114.png">
		<link rel="apple-touch-icon" sizes="120x120" href="Obrazki/apple-icon-120x120.png">
		<link rel="apple-touch-icon" sizes="144x144" href="Obrazki/apple-icon-144x144.png">
		<link rel="apple-touch-icon" sizes="152x152" href="Obrazki/apple-icon-152x152.png">
		<link rel="apple-touch-icon" sizes="180x180" href="Obrazki/apple-icon-180x180.png">
		<link rel="icon" type="image/png" sizes="192x192"  href="/android-icon-192x192.png">
		<link rel="icon" type="image/png" sizes="32x32" href="Obrazki/favicon-32x32.png">
		<link rel="icon" type="image/png" sizes="96x96" href="Obrazki/favicon-96x96.png">
		<link rel="icon" type="image/png" sizes="16x16" href="Obrazki/favicon-16x16.png">
		<link rel="manifest" href="Obrazki/manifest.json">
		<meta name="msapplication-TileColor" content="#ffffff">
		<meta name="msapplication-TileImage" content="/ms-icon-144x144.png">
		<meta name="theme-color" content="#ffffff">
		{{MATHJAX}}
EOT;

$HEADER_2 =<<<EOT
	</head>
	<body>
		<div id="container">
			<header>
				<div class="row">
					<div id="WEBTITLEPANEL" class="row">
						<div class="row">
							{{LOGOWANIE}}
						</div>
						<div class="col-1-6"><canvas id="MY_CANVAS"></canvas> </div>
						<div class="col-5-6"> <h1  id="WEBTITLE">Podstawowe algorytmy</h1> </div>
					</div>
					<div class="row">
						<div id="DESCRIPTION" class="col-6-6">
								Na tych stronach omówione są najciekawsze algorytmy omawiane na wykładzie ze Wstępu do Informatyki na pierwszym roku studiów informatycznych na Wydziale PPT PWr.
						</div>
					</div>
				</div>
			</header>
			
			<div  id="MenuRow" class="row">
				<div class="col-6-6">
					<div id="showMenu">&#9776;Menu</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col-2-6">
					<nav id="NAVLIST">
						<ul>
						{{ITEMS}}
						</ul>
					</nav>
				{{CYTAT}}
			</div>
			<div class="col-4-6">
				<article>
				<div id="GLOSOWANIE">
				    <a id = 'vote_up'   class="vote_button">&#9757;</a>  
					<a id = 'vote_down' class="vote_button">&#9759;</a>  
					<center><div id="WYNIK">{{POCZATKOWY_WYNIK}}</div></center>
				</div>
EOT;

$FOOTER =<<<EOT
					</article>
				</div>
			</div>
			<div id="KOMENTARZE">
				{{INPUT_DO_KOMENTARZA}}
				{{KOMENTARZE}}
			</div>
			<div class="row">
				<footer>
						Copyright @ 2016 WPPT PWr
				</footer>
			</div>
		</div>
		
		{{LIKE_SCRIPT}}
		
	</body>
</html>
EOT;

//CONTROLER
class Page{
	private $id   = -1;
	private $CYTAT = "";
	private $MATHJAX = "";
	private $css = [];
	private $js = [];
	private $LIKE = "";
	private $user_cookie = "";
	
	function __construct($id){ 
		global $STRONY;
		$this->id    = $id;
		$this->user_cookie = ustawCookie($STRONY[$this->id]["href"]);		
		$this->addCSS("style.css");
		$this->addCSS("grid.css");
		$this->addJS("calc.js");
	} 
	
	private function ustawLogowanie(){
		if(!isset($_SESSION['NICK'])){
			$LOGOWANIE = "<a id='logowanie' href='logowanie.php'>Zaloguj sie</a>";
		}
		else{
			$LOGOWANIE = "<h3 id='IMIE_NAZWISKO'>Witaj ".$_SESSION['Imie']." ".$_SESSION['Nazwisko']."!</h3>
										<div id='logowanie'><a id='logowanie' href='wyloguj.php'>Wyloguj sie</a></div>";
		}
		return $LOGOWANIE;
	}
	
	private function ustawInputKomentarza(){
		if(!isset($_SESSION['NICK'])){
			$INPUT_DO_KOMENTARZA = "";
		}
		else{
			$INPUT_DO_KOMENTARZA =  "<div class='row'>
				<textarea  id='INPUT_KOMENTARZ' rows='3' maxlength='150' placeholder='Wpisz komentarz...'></textarea>
				<input type='submit' id='WSTAW_KOMENTARZ_BUTTON' class='button' value='Dodaj komentarz'>
			</div>
			<script>
			function wstawKomentarz(){
				var ta = document.getElementById('INPUT_KOMENTARZ');
				if(ta.value === ''){return;}
				var params = 'Text='+ta.value+'&Title='+document.title;
				var xhttp = new XMLHttpRequest();
				xhttp.onreadystatechange = function() {
					if (xhttp.readyState == 4 && xhttp.status == 200) {
						document.getElementById('KOMENTARZE').innerHTML = xhttp.responseText;
					}
				};
				xhttp.open('POST', 'PHP/CheckComment.php', true);
				xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
				xhttp.send(params);
			}
			window.addEventListener('load', function(){
				document.getElementById('WSTAW_KOMENTARZ_BUTTON').onclick = wstawKomentarz;
			});
			</script>";
		}
		return $INPUT_DO_KOMENTARZA;
	}
	
	
	private function Menu(){ 
	global $STRONY; 
	$ITEM   = "<li><a href='{{HREF}}'>{{NAME}}</a></li>"; 
	$ACTIVE = "<li><a href='javascript:void(0);' class= 'ACT'>{{NAME}}</a></li>"; 
	$S = ""; 
	for ($i=0; $i<count($STRONY); $i++){ 
		if ( (int) $STRONY[$i]["id"] == $this->id){ 
			$T= $ACTIVE;
		} else {
			$T= (string) str_replace("{{HREF}}", (string) $STRONY[$i]["href"], $ITEM); 
		};
		$T = (string) str_replace("{{NAME}}" , (string) $STRONY[$i]["name"],  $T); 
		$S.= $T . "\n"; 
	}	 
	return $S; 
	} 
	
	
	public function WstawCytat($cytat, $kogo, $ksiazka, $info_o_ksiazce){
		$this->CYTAT = '<aside class="panel cytat">
					<blockquote>
						<p>'.$cytat.'</p>
					</blockquote>
					<p class="right">'
						.$kogo.',<br> <cite>'.$ksiazka.'</cite>,<br>'.$info_o_ksiazce.'
					</p>
				</aside>';
	}
	
	public function useMathJax(){
		$this->MATHJAX = '<script type="text/x-mathjax-config">
									MathJax.Hub.Config({
										extensions: ["tex2jax.js"],
											jax: ["input/TeX", "output/HTML-CSS"],
											tex2jax: {
											inlineMath:  [[\'$\',\'$\']],
											displayMath: [[\'$$\',\'$$\']],
											processEscapes: true
											},
											"HTML-CSS": { availableFonts: ["TeX"]}
										  });
										</script>
										<script type="text/javascript" 
										src="http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML">
									</script>';
	}
	
	public function ustawLikeScript(){
		global $STRONY;
		if(!jestIDnaStronie($this->user_cookie, $STRONY[$this->id]["href"])){
			$this->LIKE = '		<script>
				function send_Vote(ud)
				{
					var xhttp;
					if (window.XMLHttpRequest) {
						xhttp = new XMLHttpRequest();
					} else {
						xhttp = new ActiveXObject("Microsoft.XMLHTTP");
					}
					
					xhttp.onreadystatechange = function() {
							if (xhttp.readyState == 4 && xhttp.status == 200) {
								document.getElementById("WYNIK").innerHTML = xhttp.responseText;
								
								var element = document.getElementById("vote_up");
								var old_element = element;
								var new_element = old_element.cloneNode(true);
								old_element.parentNode.replaceChild(new_element, old_element);
							
								var element2 = document.getElementById("vote_down");
								var old_element = element2;
								var new_element = old_element.cloneNode(true);
								old_element.parentNode.replaceChild(new_element, old_element);
							}
					 };
					
					xhttp.open("GET", "PHP/vote.php?i='.$this->user_cookie.'&ud="+ud+"&s='.$STRONY[$this->id]["href"].'", true);
					xhttp.send();
					
				}
			
				window.addEventListener("load", function(event){
					var up =document.getElementById("vote_up");
					up.addEventListener("click", function(){send_Vote(\'up\')});
					var down =document.getElementById("vote_down");
					down.addEventListener("click", function(){send_Vote(\'down\')});
				});
			</script>';
		}
	}
		
	public function addCSS($name){
		$this->css[] =  (string) str_replace("{{C}}", $name, "<link href='css/{{C}}' rel='stylesheet'>");
	}
	
	public function addJS($name){
		$this->js[] =  (string) str_replace("{{J}}", $name, "<script src='JavaScript/{{J}}'></script>");
	}
	
	public function Begin(){ 
		global $HEADER_1, $HEADER_2; 
		global $STRONY;
		$H1 = (string) str_replace("{{TITLE}}", (string) $STRONY[$this->id]["name"],  $HEADER_1);
		$H1 = (string) str_replace("{{MATHJAX}}", $this->MATHJAX, $H1);
		$H2 = (string) str_replace("{{ITEMS}}", $this->Menu(), $HEADER_2);
		$H2 = (string) str_replace("{{POCZATKOWY_WYNIK}}", pobierzLike($STRONY[$this->id]["href"]), $H2);
		$H2 = (string) str_replace("{{CYTAT}}", $this->CYTAT, $H2);
		$H2 = (string) str_replace("{{LOGOWANIE}}", $this->ustawLogowanie(), $H2);
		
		//dodawanie css
		$X = "";
		for($i = 0 ; $i < count($this->css) ; $i++){
			$X .= "\t\t" . $this->css[$i]  . "\n";
		}
		if($X !== "") {
			$H1 .=  "\n" . $X;
		}
		
		//dodawanie js
		$X = "";
		for($i = 0 ; $i < count($this->js) ; $i++){
			$X .= "\t\t" .  $this->js[$i]  . "\n";
		}
		if($X !== ""){ 
			$H1 .=  $X;
		}
		
    return $H1 . $H2; 
	} 
	
	public function End(){
		$this->ustawLikeScript();
		global $FOOTER;
		global $STRONY;
		$F = (string) str_replace("{{LIKE_SCRIPT}}", $this->LIKE,  $FOOTER);
		$F = (string) str_replace("{{INPUT_DO_KOMENTARZA}}", $this->ustawInputKomentarza(),  $F);
		$F = (string) str_replace("{{KOMENTARZE}}", stworzKomentarze($STRONY[$this->id]['name']),  $F);
		return $F; 
	}
}



?>