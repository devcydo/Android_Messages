<?php
	// PARA EL CLIENTE: usar GET ("chat_id")

	include 'ManejadorPHP.php';

	$id_usuario = $_GET['id_usuario'];
	$id_amigo = $_GET['id_amigo'];

	$obj = new ManejadorPHP();

	$resultado = $obj->isContact($id_usuario, $id_amigo);
	echo json_encode($resultado);

?>