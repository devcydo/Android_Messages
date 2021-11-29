<?php
	// PARA EL CLIENTE: usar GET ("chat_id")

	include 'ManejadorPHP.php';

	$chat_id = $_GET['chat_id'];
	$usuario_id = $_GET['usuario_id'];

	$obj = new ManejadorPHP();

	$resultado = $obj->setChatRead($chat_id, $usuario_id);
	echo json_encode($resultado);

?>