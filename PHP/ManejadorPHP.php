<?php
		/*	NOTAS DEL PARCHE (xD) 0.1.3:
				- Los metodos addChat() y addChatGroup() ahora regresan el ID del chat que se agregó
					- addChatGroup() 	
					- addChat()			
			NOTAS DEL PARCHE 0.1.4:
				- Se supone que los chats ahora pueden almacenar imagenes
					- addChatGroup()
					- addChat()
				- AÚN NO SE PUEDE OBTENER EL BYTE[] DESDE ESTE WEB SERVICE

			NOTAS DEL PARCHE 0.1.5:
				- Ahora el metodo setChatRead() recive 2 parametros chat_id y usuario_id
				- Ahora el metodo setChatRead() estblece como LEIDO = 1 solo a los elementos de la base
				  de datos que no sean del usuario_id
				  	- setChatRead()
				- YA SE PUEDE OBTENER EL BYTE[] DESDE ESTE WEB SERVICE
					- CHAT(IMAGEN)
			  	- Se agregó el método showGroupImage()
			  		- showGroupImage()

	  		NOTAS DEL PARCHE 0.1.6:
	  			- Ahora showGroupUsers() ya no regresa FOTO
	  				- showGroupUsers
  				- Ahora showMessages() ya no regresa FOTO
	  				- showMessages
				- Ahora showUsers() ya no regresa FOTO
	  				- showUsers
	  			- Ahora showChats() ya no regresa FOTO	
	  				- showChats
  				- Ahora showContacts() ya no regresa FOTO
	  				- showContacts

  				- Se actualizaron las descripciones de los metodos afectados

			NOTAS DEL PARCHE 0.1.7:
				- Ahora setChatRead.php envia los argumentos que son necesarios
				  estaba mandando solo CHAT_ID y no USUARIO_ID
				- Ahora addUser() ya no recive FOTO
					-addUser.php
				- Ahora addChatGroup() ya no recive IMAGEN
					-addChatGroup.php
				- Ahora addStatus() ya no recive IMAGEN
					-addStatus.php
				- Se añadió el metodo lookByNumber()
		*/
	class ManejadorPHP {

		private $db;
		private $host;
		private $user;
		private $pass;
		private $port;
		private $result;

		function __construct() {
			$this->db = 'messageapp';
			$this->host = 'localhost';
			$this->user = 'root';
			$this->pass = NULL;
			$this->port = 3307;

			$this->result = new \stdClass();
			$this->result->code = 200;
			$this->result->msg = 'Success';
			$this->result->output = array();
		}

		private function open() {
			$link = mysqli_connect($this->host, $this->user, $this->pass, $this->db, $this->port) or die('Error connecting to DB');
			return $link;
		}

		private function close($link) {
			return mysqli_close($link);
		}

		//	Muestra los mensajes del chat ordenados por fecha
		//	CONSUMES:
		//		ID chat
		//	RETURN:
		//		ID usuario, NOMBRE usuario, MENSAJE mensaje, MULTIMEDIA mensaje, LEIDO mensaje, FECHA mensaje
		public function showMessages($chat_id){
			try {
				$link = $this->open();

					$qry = "SELECT m.id,m.id_usuario,u.nombre,m.mensaje, m.leido, m.fecha
							FROM mensaje m , usuario_chat uc, usuario u
							WHERE uc.id_chat = '$chat_id' 
								AND m.id_usuario = uc.id_usuario 
								AND u.id = m.id_usuario
								AND m.id_chat = '$chat_id'
							ORDER BY fecha";

					$r = mysqli_query($link, $qry);

					while( $result[] = mysqli_fetch_array( $r, MYSQLI_ASSOC ) );

					foreach ($result as $value) {
						if($value) {
							array_push($this->result->output, $value);
						}
					}

				$this->close($link);
			} catch (Exception $e) {
				$this->result->code = 500;
				$this->result->msg = 'Failed: '.$e;
			}

			return $this->result;
		}

		//	Para chats grupales
		//	CONSUMES:
		//		NOMBRE chat
		// 	RETURNS:
		//		chat_id
		public function addChatGroup($chat_nombre, $imagen){
			try {
				$link = $this->open();

					$qry = "INSERT INTO chat VALUES(NULL,'$chat_nombre','$imagen',DEFAULT)";

					$r = mysqli_query($link, $qry);

							$qry = "SELECT LAST_INSERT_ID() as chat_id";

							$r = mysqli_query($link, $qry);

							while( $result[] = mysqli_fetch_array( $r, MYSQLI_ASSOC ) );

							foreach ($result as $value) {
								if($value) {
									array_push($this->result->output, $value);
									$e = $value;
								}
							}

				$this->close($link);

			} catch (Exception $e) {
				$this->result->code = 500;
				$this->result->msg = 'Failed: '.$e;
			}

			return $e;
		}

		//	Para chats de mensajes directos
		// 	RETURNS:
		//		chat_id
		public function addChat(){
			try {

				$link = $this->open();

					$qry = "INSERT INTO chat VALUES(NULL, DEFAULT, DEFAULT, DEFAULT)";

					$r = mysqli_query($link, $qry);

							$qry = "SELECT LAST_INSERT_ID() as chat_id";

							$r = mysqli_query($link, $qry);

							while( $result[] = mysqli_fetch_array( $r, MYSQLI_ASSOC ) );

							foreach ($result as $value) {
								if($value) {
									array_push($this->result->output, $value);
									$e = $value;
								}
							}

				$this->close($link);

			} catch (Exception $e) {
				$this->result->code = 500;
				$this->result->msg = 'Failed: '.$e;
			}

			return $e;
		}

		//	CONSUMES:
		//		ID chat, ID usuario
		public function addUserToChat($chat_id,$usuario_id){
			try {

				$link = $this->open();

					$qry = "INSERT INTO usuario_chat VALUES(NULL, $chat_id, $usuario_id)";

					$r = mysqli_query($link, $qry);

				$this->close($link);

			} catch (Exception $e) {
				$this->result->code = 500;
				$this->result->msg = 'Failed: '.$e;
			}

			return $this->result;
		}	


		//	Agrega mensaje LEIDO=DEFAULT(0) 
		//	CONSUMES:
		//		ID usuario, ID chat, MENSAJE mensaje
		public function addMessage($usuario_id, $chat_id, $mensaje){
			try {
				$link = $this->open();

					$qry = "INSERT INTO mensaje VALUES(NULL,'$usuario_id','$chat_id','$mensaje',DEFAULT,DEFAULT)";

					$r = mysqli_query($link, $qry);

				$this->close($link);

			} catch (Exception $e) {
				$this->result->code = 500;
				$this->result->msg = 'Failed: '.$e;
			}

			return $this->result;
		}

		//	Para agregar usuarios
		//	CONSUMES:
		//		NOMBRE usuario, NUMERO usuario, FRASE usuario
		public function addUser($nombre, $numero, $frase, $foto){
			try {

				$link = $this->open();

					$qry = "INSERT INTO usuario VALUES(NULL, '$nombre','$numero','$frase','$foto')";

					$r = mysqli_query($link, $qry);
				$this->close($link);

			} catch (Exception $e) {
				$this->result->code = 500;
				$this->result->msg = 'Failed: '.$e;
			}

			return $this->result;
		}

		public function updateUser($nombre, $numero, $frase, $foto, $usuario_id){
			try {

				$link = $this->open();

					$qry = "UPDATE usuario SET nombre = '$nombre', numero = '$numero', frase = '$frase', foto = '$foto'
							WHERE id = '$usuario_id'";

					$r = mysqli_query($link, $qry);
				$this->close($link);

			} catch (Exception $e) {
				$this->result->code = 500;
				$this->result->msg = 'Failed: '.$e;
			}

			return $this->result;
		}

		public function updateChatDate($chat_id){
			try {

				$link = $this->open();

					$qry = "UPDATE chat SET fecha = NOW() WHERE id = '$chat_id'";

					$r = mysqli_query($link, $qry);
				$this->close($link);

			} catch (Exception $e) {
				$this->result->code = 500;
				$this->result->msg = 'Failed: '.$e;
			}

			return $this->result;
		}
		
		//	Para agregar contactos
		//	CONSUMES:
		//		ID usuario, ID_AMIGO usuario
		public function addContact($usuario_id, $amigo_id){
			try {

				$link = $this->open();

					$qry = "INSERT INTO contacto VALUES(NULL, '$usuario_id','$amigo_id')";

					$r = mysqli_query($link, $qry);

				$this->close($link);

			} catch (Exception $e) {
				$this->result->code = 500;
				$this->result->msg = 'Failed: '.$e;
			}

			return $this->result;
		}

		//	Para agregar estados
		//	CONSUMES:
		//		ID usuario, TEXTO estado
		public function addStatus($id_usuario, $texto){
			try {

				$link = $this->open();

					$qry = "INSERT INTO estado VALUES(NULL,'$id_usuario','$texto',DEFAULT)";

					$r = mysqli_query($link, $qry);

				$this->close($link);

			} catch (Exception $e) {
				$this->result->code = 500;
				$this->result->msg = 'Failed: '.$e;
			}

			return $this->result;
		}

		//	Muestra a todos los Usuarios registrados
		//	RETURN:
		//		ID usuario, NOMBRE usuario, NUMERO usuario, FRASE usuario
		public function showUsers(){
			try {
				$link = $this->open();

					$qry = "SELECT id, nombre, numero, frase, foto
							FROM usuario
							ORDER BY nombre ASC";

					$r = mysqli_query($link, $qry);

					while( $result[] = mysqli_fetch_array( $r, MYSQLI_ASSOC ) );

					foreach ($result as $value) {
						if($value) {
							array_push($this->result->output, $value);
						}
					}

				$this->close($link);
			} catch (Exception $e) {
				$this->result->code = 500;
				$this->result->msg = 'Failed: '.$e;
			}

			return $this->result;
		}

		//	Muestra a todos los Contactos de usuario
		//	CONSUMES:
		//		ID usuario
		//	RETURN:
		//		ID amigo, NOMBRE amigo, NUMERO amigo, FRASE amigo
		public function showContacts($id_usuario){
			try {
				$link = $this->open();

					$qry = "SELECT u.id, u.nombre, u.numero, u.frase, u.foto
							FROM contacto c, usuario u
							WHERE c.id_usuario = '$id_usuario' AND u.id = c.id_amigo
							ORDER BY u.nombre";

					$r = mysqli_query($link, $qry);

					while( $result[] = mysqli_fetch_array( $r, MYSQLI_ASSOC ) );

					foreach ($result as $value) {
						if($value) {
							array_push($this->result->output, $value);
						}
					}

				$this->close($link);
			} catch (Exception $e) {
				$this->result->code = 500;
				$this->result->msg = 'Failed: '.$e;
			}

			return $this->result;
		}

		public function showOwnStatus($id_usuario){
			try {
				$link = $this->open();

					$qry = "SELECT * FROM estado WHERE id_usuario = '$id_usuario'";

					$r = mysqli_query($link, $qry);

					while( $result[] = mysqli_fetch_array( $r, MYSQLI_ASSOC ) );

					foreach ($result as $value) {
						if($value) {
							array_push($this->result->output, $value);
						}
					}

				$this->close($link);
			} catch (Exception $e) {
				$this->result->code = 500;
				$this->result->msg = 'Failed: '.$e;
			}

			return $this->result;
		}

		public function isContact($id_usuario, $id_amigo){
			try {
				$link = $this->open();

					$qry = "SELECT u.id, u.nombre
							FROM usuario u
							INNER JOIN contacto c ON u.id = c.id_amigo
							WHERE c.id_usuario = '$id_usuario'
							AND c.id_amigo = '$id_amigo'";

					$r = mysqli_query($link, $qry);

					while( $result[] = mysqli_fetch_array( $r, MYSQLI_ASSOC ) );

					foreach ($result as $value) {
						if($value) {
							array_push($this->result->output, $value);
						}
					}

				$this->close($link);
			} catch (Exception $e) {
				$this->result->code = 500;
				$this->result->msg = 'Failed: '.$e;
			}

			return $this->result;
		}

		//	Muestra los chats en los que ha participado el usuario
		//	CONSUMES:
		//		ID usuario
		//	RETURN:
		//		ID chat, NOMBRE chat
		public function showChats($usuario_id){
			try {
				$link = $this->open();

					$qry = "SELECT c.id, c.nombre, c.imagen
							FROM chat c, usuario_chat uc
							WHERE c.id=uc.id_chat AND uc.id_usuario = '$usuario_id'
							ORDER BY fecha DESC";

					$r = mysqli_query($link, $qry);

					while( $result[] = mysqli_fetch_array( $r, MYSQLI_ASSOC ) );

					foreach ($result as $value) {
						if($value) {
							array_push($this->result->output, $value);
						}
					}

				$this->close($link);
			} catch (Exception $e) {
				$this->result->code = 500;
				$this->result->msg = 'Failed: '.$e;
			}

			return $this->result;
		}

		public function showStatus($id_usuario){
			try {
				$link = $this->open();

					$qry = "SELECT  N.id AS estado_id, usuario.id AS usuario_id, usuario.nombre, usuario.foto, N.texto, N.fecha
							FROM usuario
							INNER JOIN (SELECT * 
								FROM(SELECT estados.id, contacto.id_amigo, estados.texto, estados.limite, estados.fecha
									FROM contacto
									INNER JOIN  (SELECT *
										FROM (SELECT id, id_usuario, texto, DATE_ADD(e.fecha,INTERVAL 1 DAY) AS limite, e.fecha
										FROM estado e) T) estados
									ON contacto.id_amigo = estados.id_usuario
									WHERE contacto.id_usuario='$id_usuario') B) N
							ON usuario.id = N.id_amigo
							WHERE CURRENT_DATE <= N.limite
							ORDER BY N.limite DESC";

					$r = mysqli_query($link, $qry);

					while( $result[] = mysqli_fetch_array( $r, MYSQLI_ASSOC ) );

					foreach ($result as $value) {
						if($value) {
							array_push($this->result->output, $value);
						}
					}

				$this->close($link);
			} catch (Exception $e) {
				$this->result->code = 500;
				$this->result->msg = 'Failed: '.$e;
			}

			return $this->result;
		}


		//	Cambia todos los mensajes de un chat a LEIDO = 1
		//	CONSUMES:
		//		ID chat, ID usuario
		public function setChatRead($chat_id, $usuario_id){
			try {

				$link = $this->open();

					$qry = "UPDATE mensaje SET leido = '1' WHERE  id_chat = '$chat_id' AND id_usuario !='$usuario_id'";

					$r = mysqli_query($link, $qry);

				$this->close($link);

			} catch (Exception $e) {
				$this->result->code = 500;
				$this->result->msg = 'Failed: '.$e;
			}

			return $this->result;
		}

		//	Muestra los usuarios participantes en un grupo
		//	CONSUMES:
		//		ID chat
		//	RETURN:
		//		ID usuario, NOMBRE usuario, NUMERO usuario, FRASE usuario
		public function showGroupUsers($chat_id, $usuario_id){
			try {
				$link = $this->open();

					$qry = "SELECT u.id, u.nombre, u.foto, u.numero, u.frase
							FROM usuario_chat cu, usuario u
							WHERE cu.id_usuario = u.id
							AND cu.id_chat ='$chat_id'
							AND cu.id_usuario != '$usuario_id'";

					$r = mysqli_query($link, $qry);

					while( $result[] = mysqli_fetch_array( $r, MYSQLI_ASSOC ) );

					foreach ($result as $value) {
						if($value) {
							array_push($this->result->output, $value);
						}
					}

				$this->close($link);
			} catch (Exception $e) {
				$this->result->code = 500;
				$this->result->msg = 'Failed: '.$e;
			}

			return $this->result;
		}


		//	Regresa el ID del usuario que tenga el NUMERO ingresado
		//	CONSUMES:
		//		NUMERO usuario
		public function lookByNumber($numero){
			try {
				$link = $this->open();

					$qry = "SELECT id, nombre, numero, frase, foto FROM usuario WHERE numero ='$numero'";

					$r = mysqli_query($link, $qry);

					while( $result[] = mysqli_fetch_array( $r, MYSQLI_ASSOC ) );

					foreach ($result as $value) {
						if($value) {
							array_push($this->result->output, $value);
						}
					}

				$this->close($link);
			} catch (Exception $e) {
				$this->result->code = 500;
				$this->result->msg = 'Failed: '.$e;
			}
			return $this->result;
		}
	}

?>