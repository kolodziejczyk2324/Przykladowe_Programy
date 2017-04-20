<?php

/**
* @return mixed
*/

function myDb() {
	$mysqli = new mysqli('localhost', 'root', '', 'likeDB');
	if ($mysqli->connect_errno) {
		echo "Bkad: nie uda³o siê nawi¹zanie po³¹czenie z MySQL: \n";  //Nie w kodzie produkcyjnym
		echo "Numer bledu : " . $mysqli->connect_errno . "\n";
		echo "Blad: " . $mysqli->connect_error . "\n";
		return false;
	} else {
		$mysqli->set_charset("utf8");
		return $mysqli;
	}
}

/**
* @param mysqli $sqli
* @param string $q query typu select
* @return string[int]
*/

function myDbSelect($mysqli, $q){
    /*. string[int] .*/ $rows   = array();
	$result = $mysqli -> query($q);
	if (!$result) {
		echo "Blad pytania: " . $q . "<br>\n";  //Nie w kodzie produkcyjnym
		echo "Numer bledu : " . $mysqli->errno . "<br>\n";
		echo "Blad: " . $mysqli->error . "\n";			
	} else {
		while ($row = $result->fetch_assoc()) {
				$rows[] = $row;
		}
	}
	return $rows;
}
	
?>