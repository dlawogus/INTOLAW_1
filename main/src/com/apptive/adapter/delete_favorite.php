<?php
	require 'dbconfig.php';
	session_start();
	$S_UID = $_SESSION['user_id'];
	$BANG_ID = $_GET['bang_id'];
	$connect = mysqli_connect($DB_HOST,$DB_ID,$DB_PW,$DB_NAME);

	$que = "DELETE FROM favorite WHERE user_id='".$S_UID."' AND bang_id='".$BANG_ID."';";
	mysqli_query($connect, $que);
?>