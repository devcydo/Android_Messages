<?php
	// PARA EL CLIENTE: usar GET ("usuario_id")

	include 'ManejadorPHP.php';

	$id_usuario = $_GET['id_usuario'];

	$obj = new ManejadorPHP();

	$resultado = $obj->showContacts($id_usuario);
	echo json_encode($resultado);

?>
