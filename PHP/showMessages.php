<?php
	// PARA EL CLIENTE: usar GET ("chat_id")

	include 'ManejadorPHP.php';

	$chat_id = $_GET['chat_id'];

	$obj = new ManejadorPHP();

	$resultado = $obj->showMessages($chat_id);
	echo json_encode($resultado);

?>
