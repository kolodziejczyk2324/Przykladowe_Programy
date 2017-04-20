<?php

/*
  Sprawdzanie użytkownika.
  Bardziej zaawansowane rozwiązanie: użytkowniików i ich hasła trzymamy w bazie danych
*/

session_start();

$users = [
	["NICK"=>"Kemer",	"password"=>"1234",		"Imie"=>"Przemek",	"Nazwisko"=>"Kołodziejczyk"],
	["NICK"=>"Kamaz",	"password"=>"1234",		"Imie"=>"Kamil",		"Nazwisko"=>"Sikorski"],
	["NICK"=>"Igi",		"password"=>"1234",		"Imie"=>"Igor",		"Nazwisko"=>"Bodnar"],
	["NICK"=>"Mati",		"password"=>"1234",		"Imie"=>"Mateusz",	"Nazwisko"=>"Szewczuk"]
];

if (!(isset($_POST['NICK']) and isset($_POST['pass']))){
	header("location: http://" . $_SERVER['HTTP_HOST'] . "/Komentarze/logowanie.php");
}


$NICK = (string)$_POST['NICK'];
$PASS = (string)$_POST['pass'];


for ($i=0;$i<count($users);$i++){
	if (($users[$i]["NICK"]===$NICK) and ($users[$i]["password"]===$PASS)){
		$_SESSION['NICK'] = $NICK;
		$_SESSION['Imie'] = $users[$i]["Imie"];
		$_SESSION['Nazwisko'] = $users[$i]["Nazwisko"];
		header("location: http://" . $_SERVER['HTTP_HOST'] . "/Komentarze/index.php");
		exit;
	}
}

header("location: http://" . $_SERVER['HTTP_HOST'] ."/Komentarze/logowanie.php");
	
?>