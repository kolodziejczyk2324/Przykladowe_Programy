<?php
	session_start();
	//jesli ktos zalogowany chce tu wejsc to przekieruj go do strony glownej
	if(isset($_SESSION['NICK'])){
		header("location: http://" . $_SERVER['HTTP_HOST'] ."/Komentarze/index.php");
		exit;
	}
?>
<!doctype html>
<html>
	<head>
		<link href='css/logowanie.css' rel='stylesheet'>
		<link href='css/grid.css' rel='stylesheet'>
	</head>
	<body>
		<div id="container">
			<h1>Logowanie</h1>
			<form method="post" action="PHP/CheckUser.php">
				<div class="row">
					<span>Twój NICK: </span> <input class="textField" type="text"     name="NICK" size="6"  maxlength="6"><br>
				</div>
				<div class="row">
					<span>Hasło:     </span> <input class="textField"  type="password" name="pass" size="20" maxlength="20"> <br>
				</div>
				<input type="submit"  class="button" value="Zaloguj się">
			</form>
			<a id="POWROT" href="index.php">Wroc do strony glownej</a></br>
		</div>
	</body>
</html>