<?php
	// PARA EL ACTUALIZAR FECHA DEL CHAT: usar GET ("chat_id")

	include 'ManejadorPHP.php';

	$chat_id = $_GET['chat_id'];

	$obj = new ManejadorPHP();

	$resultado = $obj->updateChatDate($chat_id);
	echo json_encode($resultado);

?>