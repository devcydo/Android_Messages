<?php
	// PARA EL CLIENTE: usar GET ("chat_id")

	include 'ManejadorPHP.php';

	$id_usuario = $_GET['id_usuario'];

	$obj = new ManejadorPHP();

	$resultado = $obj->showStatus($id_usuario);
	echo json_encode($resultado);

?>
