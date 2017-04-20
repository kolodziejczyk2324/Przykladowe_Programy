<?php
session_start();

//gdy ktos bedzie chcial tu wejsc nielegalnie to go przenies do indexu
if(!(isset($_POST['Text']) and isset($_POST['Title']))){
	header("location: http://" . $_SERVER['HTTP_HOST'] ."/Komentarze/index.php");
	exit;
}

require_once("basicmysql.php");
require_once("CommentCreator.php");

$TEXT = $_POST['Text'];
$TITLE = $_POST['Title'];

dodajKomentarz($_SESSION['NICK'], $TEXT, $TITLE);

echo stworzKomentarze($TITLE);
	
?>