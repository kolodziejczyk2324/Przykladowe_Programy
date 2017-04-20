<?php

require_once("MyDatabase.php");

function pobierzLike($Strona){
	$up = myDbSelect(myDB(),"SELECT COUNT(ID) AS Ilosc FROM `like` WHERE UpDown='up' AND Strona='". $Strona."'"); 
	$down = myDbSelect(myDB(),"SELECT COUNT(ID) AS Ilosc FROM `like` WHERE UpDown='down' AND Strona='". $Strona."'"); 
	return "Tak: ".$up[0]['Ilosc']." Nie: ".$down[0]['Ilosc'];
}

function wstawLike($ID, $UpDown, $Strona){
	myDB() -> query("INSERT INTO `like`(`ID`, `UpDown`, `Strona`) VALUES ('".$ID."','".$UpDown."','".$Strona."')");
}

function jestUzytkownik($id){
	return myDbSelect(myDB(),"SELECT `ID` FROM `like` WHERE `ID`='$id'") != null;
}

function jestIDnaStronie($id, $strona){
	return myDbSelect(myDB(),"SELECT `ID` FROM `like` WHERE `ID`='".$id."' AND `Strona`='".$strona."'") != null;
}

//komentarze

function dodajKomentarz($NICK, $Tresc, $JakaStrona){
	$Tresc = strip_tags($Tresc);
	myDB() -> query("INSERT INTO `KomentarzeDB`(`Nick`, `Tresc`, `JakaStrona`, `Data`) VALUES ('".$NICK."','".$Tresc."','".$JakaStrona."', NOW())");
}

function pobierzKomentarze($STRONA){
	return myDbSelect(myDB(),"SELECT `Nick`, `Tresc`, `Data` FROM `KomentarzeDB` WHERE JakaStrona='".$STRONA."' ORDER BY Data DESC");
}
?>