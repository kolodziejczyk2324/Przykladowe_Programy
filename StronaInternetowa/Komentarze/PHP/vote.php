<?php

	require_once("basicmysql.php");
	$ID = $_GET["i"];
	$S = $_GET["s"];
	if(!jestIDnaStronie($ID, $S)){
		$UD = $_GET["ud"];
		
		wstawLike($ID, $UD, $S);
		
		$like = pobierzLike($S);

		echo $like;
	}
?>