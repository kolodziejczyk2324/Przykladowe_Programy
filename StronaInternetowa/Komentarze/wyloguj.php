<?php 
	session_start();
	
	//w przypadku gdy ktos chce tutaj wejsc nie bedac zalogowanym to przekieruj go do strony logowania
	if(!isset($_SESSION['NICK'])){
		header("location: http://" . $_SERVER['HTTP_HOST'] ."/Komentarze/logowanie.php");
		exit;
	}
	//wylacz wszystkie zmienne sesyjne
	unset($_SESSION['NICK']);
	unset($_SESSION['Imie']);
	unset($_SESSION['Nazwisko']);
?>

<!doctype html>
<html>
	<head>
		<link href='css/logowanie.css' rel='stylesheet'>
		<link href='css/grid.css' rel='stylesheet'>
	</head>
	<body>
		<div id="container">
			<h1>Zostales pomyslnie wylogowany ze strony</h1>
			<a id="POWROT" href="index.php">Wroc do strony glownej</a></br>
		</div>
	</body>
</html>