<?php
	// PARA EL ACTUALIZAR IMG DEL USUARIO: usar GET ("foto"), GET ("usuario_id")

	include 'ManejadorPHP.php';

	$foto = $_GET['foto'];
	$usuario_id = $_GET['usuario_id'];

	$obj = new ManejadorPHP();

	$resultado = $obj->updateImgUser($foto,$usuario_id);
	echo json_encode($resultado);

?>