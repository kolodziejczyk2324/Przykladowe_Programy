<?php

	/*Funkcja ustawiajaca cookie dotyczace uzytkownika. Cookie posiada identyfikator za pomoca ktorego
	 *dostajemy sie do bazy danych*/
	function ustawCookie($strona){
		if(!isset($_COOKIE["USER"])){ //jesli cookie nie jest jeszcze ustawione
			while(jestUzytkownik($id = uniqid())); //wygeneruj id uzytkownikowi takie aby nie bylo go juz w bazie danych
			setcookie("USER", $id, time() + 2678400); //ustaw cookie z wygenerowanym przed chwila id oraz czasem aktywnosci = 1 miesiac
		}
		else{ //jesli cookie jest juz ustawione
			$id = $_COOKIE["USER"]; //ustaw stare id ktore zwroci funkcja
			setcookie("USER", $id, time() + 2678400); //zaktualizuj czas dzialania cookie na kolejny miesiac
		}
		return $id;
	}
	
?>