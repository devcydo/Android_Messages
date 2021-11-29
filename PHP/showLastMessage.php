<?php
	// PARA EL CLIENTE: usar GET ("usuario_id")

	include 'ManejadorPHP.php';

	$id_chat = $_GET['id_chat'];

	$obj = new ManejadorPHP();

	$resultado = $obj->showLastMessage($id_chat);
	echo json_encode($resultado);

?>
