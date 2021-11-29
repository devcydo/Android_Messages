<?php
	// PARA EL CLIENTE: usar GET ("usuario_id"), GET ("chat_id"), GET ("mensaje")

	include 'ManejadorPHP.php';

	$usuario_id = $_GET['usuario_id'];
	$chat_id = $_GET['chat_id'];
	$mensaje = $_GET['mensaje'];

	$obj = new ManejadorPHP();

	$resultado = $obj->addMessage($usuario_id,$chat_id,$mensaje);
	echo json_encode($resultado);

?>
