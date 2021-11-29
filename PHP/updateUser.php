<?php
	// PARA EL ACTUALIZAR DATOS DEL USUARIO: usar GET ("nombre"), GET ("numero"), GET ("frase"), GET ("usuario_id")

	include 'ManejadorPHP.php';

	$nombre = $_GET['nombre'];
	$numero = $_GET['numero'];
	$frase = $_GET['frase'];
	$usuario_id = $_GET['usuario_id'];

	$obj = new ManejadorPHP();

	$resultado = $obj->updateUser($nombre,$numero,$frase,$usuario_id);
	echo json_encode($resultado);

?>