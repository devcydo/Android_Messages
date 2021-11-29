<?php
	// PARA EL CLIENTE: usar GET ("nombre"), GET ("numero"), GET ("frase"), GET ("foto")

	include 'ManejadorPHP.php';

	$nombre = $_GET['nombre'];
	$numero = $_GET['numero'];
	$frase = $_GET['frase'];
	$foto = $_GET['foto'];

	$obj = new ManejadorPHP();

	$resultado = $obj->addUser($nombre,$numero,$frase,$foto);
	echo json_encode($resultado);

?>
