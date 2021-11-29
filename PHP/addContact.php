<?php
	// PARA EL CLIENTE: usar GET ("usuario_id"), GET ("amigo_id"),

	include 'ManejadorPHP.php';

	$usuario_id = $_GET['usuario_id'];
	$amigo_id = $_GET['amigo_id'];

	$obj = new ManejadorPHP();

	$resultado = $obj->addContact($usuario_id,$amigo_id);
	echo json_encode($resultado);

?>
