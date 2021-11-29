<?php
	// PARA EL CLIENTE: usar GET ("usuario_id")

	include 'ManejadorPHP.php';

	$numero = $_GET['numero'];

	$obj = new ManejadorPHP();

	$resultado = $obj->lookByNumber($numero);
	echo json_encode($resultado);

?>
