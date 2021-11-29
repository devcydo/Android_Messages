<?php
	// PARA EL CLIENTE: usar GET ("usuario_id")

	include 'ManejadorPHP.php';

	$usuario_id = $_GET['usuario_id'];

	$obj = new ManejadorPHP();

	$resultado = $obj->showChats($usuario_id);
	echo json_encode($resultado);

?>
