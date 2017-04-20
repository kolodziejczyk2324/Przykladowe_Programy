<?php

require_once("basicmysql.php");

/* 
 * Funkcja zwracajaca html komentarzy ze strony $NazwaStrony 
 */
function stworzKomentarze($NazwaStrony){
	global $STRONY; 
	$komentarze = pobierzKomentarze($NazwaStrony);
	$S = "";
	for ($i=0; $i<count($komentarze); $i++){ 
		$S.="<div class='row' id='JedenKomentarz'>
			<div class='row' id='naglowek'><p><strong id='nick'>".$komentarze[$i]['Nick']."</strong>   ".$komentarze[$i]['Data']."</p></div>
			<div class='row' id='tresc'>".$komentarze[$i]['Tresc']."</div>
		</div>\n";
	}	
	return $S;
}

?>

<style>
#naglowek{
	font-size:80%;
	border-bottom: 2px solid white;
}
#tresc{
	padding-top:1%;
}
#nick{
	color: #DE4A2A;
	font-size:180%;
}
#JedenKomentarz{
	background-color: #006183;
	color: white;
	border-radius: 15px;
	resize: none;
	padding: 1% 1% 0.5% 1%;
	margin: 1% 0 1% 0;
}

</style>