<?php

	include 'ManejadorPHP.php';

	$obj = new ManejadorPHP();

	$resultado = $obj->showUsers();
	echo json_encode($resultado);

?>
