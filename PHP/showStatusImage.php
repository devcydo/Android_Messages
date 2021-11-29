<?php
	// PARA EL CLIENTE: usar GET ("usuario_id")

	include 'ManejadorPHP.php';

	$estado_id = $_GET['estado_id'];

	$obj = new ManejadorPHP();

	$resultado = $obj->showStatusImage($estado_id,);
	echo json_encode($resultado);

?>