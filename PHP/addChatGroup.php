<?php
	// PARA EL CLIENTE: usar GET ("chat_nombre")

	include 'ManejadorPHP.php';

	$chat_nombre = $_GET['chat_nombre'];
	$imagen = $_GET['imagen'];

	$obj = new ManejadorPHP();

	$resultado = $obj->addChatGroup($chat_nombre,$imagen);
	echo json_encode($resultado);

?>