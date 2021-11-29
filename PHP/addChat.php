<?php

	include 'ManejadorPHP.php';

	$obj = new ManejadorPHP();

	$resultado = $obj->addChat();
	echo json_encode($resultado);

?>