<?php
	// PARA EL CLIENTE: usar GET ("usuario_id"), GET ("texto"), GET ("imagen")

	include 'ManejadorPHP.php';

	$id_usuario = $_GET['id_usuario'];
	$texto = $_GET['texto'];

	$obj = new ManejadorPHP();

	$resultado = $obj->addStatus($id_usuario,$texto);
	echo json_encode($resultado);

?>
