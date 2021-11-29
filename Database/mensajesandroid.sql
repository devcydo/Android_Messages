-- MySQL dump 10.16  Distrib 10.1.37-MariaDB, for Win32 (AMD64)
--
-- Host: localhost    Database: messageapp
-- ------------------------------------------------------
-- Server version	10.1.37-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `chat`
--

DROP TABLE IF EXISTS `chat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL DEFAULT 'direct_chat',
  `imagen` int(1) NOT NULL DEFAULT '0',
  `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat`
--

LOCK TABLES `chat` WRITE;
/*!40000 ALTER TABLE `chat` DISABLE KEYS */;
INSERT INTO `chat` VALUES (1,'direct_chat',0,'2019-05-28 03:37:09'),(2,'direct_chat',0,'2019-05-29 00:18:00'),(3,'direct_chat',0,'2019-05-28 03:42:20'),(4,'Chicas Dramaticas ISC',9,'2019-05-29 02:30:33'),(5,'direct_chat',0,'2019-05-29 02:20:21'),(7,'direct_chat',0,'2019-05-29 02:23:26'),(8,'direct_chat',0,'2019-05-29 15:19:37'),(9,'direct_chat',0,'2019-05-29 16:44:35');
/*!40000 ALTER TABLE `chat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contacto`
--

DROP TABLE IF EXISTS `contacto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contacto` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_usuario` int(11) NOT NULL,
  `id_amigo` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_usuario` (`id_usuario`),
  KEY `id_amigo` (`id_amigo`),
  CONSTRAINT `contacto_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`),
  CONSTRAINT `contacto_ibfk_2` FOREIGN KEY (`id_amigo`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contacto`
--

LOCK TABLES `contacto` WRITE;
/*!40000 ALTER TABLE `contacto` DISABLE KEYS */;
INSERT INTO `contacto` VALUES (1,16,15),(2,18,16),(3,18,19),(4,20,16),(5,17,20),(6,15,19),(7,19,18),(8,19,16),(10,18,15),(11,17,19),(12,21,18),(13,18,21),(14,22,16),(15,22,15),(16,16,22),(17,22,21),(18,17,22);
/*!40000 ALTER TABLE `contacto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estado`
--

DROP TABLE IF EXISTS `estado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `estado` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_usuario` int(11) NOT NULL,
  `texto` text,
  `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `estado_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estado`
--

LOCK TABLES `estado` WRITE;
/*!40000 ALTER TABLE `estado` DISABLE KEYS */;
INSERT INTO `estado` VALUES (1,15,'Si no vas con todo, a que vas?','2019-05-28 06:30:29'),(2,15,'Vamonos!','2019-05-29 01:03:56'),(3,19,'Gran partido','2019-05-29 01:05:18'),(4,20,'Como estan todos?','2019-05-29 01:08:29'),(5,17,'Wuuuu','2019-05-29 02:31:45'),(6,21,'Vacaciones','2019-05-29 02:35:14'),(7,21,'Ya casi es mi cumpleaÃ±os','2019-05-29 02:35:40'),(8,15,'Ya casi salimos','2019-05-29 02:49:48'),(9,22,'Hola','2019-05-29 16:45:22');
/*!40000 ALTER TABLE `estado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mensaje`
--

DROP TABLE IF EXISTS `mensaje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mensaje` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_usuario` int(11) NOT NULL,
  `id_chat` int(11) NOT NULL,
  `mensaje` text NOT NULL,
  `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `leido` smallint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `id_usuario` (`id_usuario`),
  KEY `id_chat` (`id_chat`),
  CONSTRAINT `mensaje_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`),
  CONSTRAINT `mensaje_ibfk_2` FOREIGN KEY (`id_chat`) REFERENCES `chat` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mensaje`
--

LOCK TABLES `mensaje` WRITE;
/*!40000 ALTER TABLE `mensaje` DISABLE KEYS */;
INSERT INTO `mensaje` VALUES (1,15,1,'Hola lalita','2019-05-28 05:30:46',1),(2,16,1,'Hola carolita','2019-05-28 05:30:59',0),(3,19,2,'Hola','2019-05-28 20:51:48',0),(4,16,3,'Hola Gretel!','2019-05-28 20:53:47',0),(5,17,3,'Hola Lalo!','2019-05-28 20:55:37',0),(6,18,2,'Hola Jean!','2019-05-29 00:18:00',0),(8,15,4,'Hola chicas dramaticas','2019-05-29 01:28:23',1),(9,16,4,'Como les va? :)','2019-05-29 01:29:04',1),(10,18,4,'Bien gracias','2019-05-29 01:29:41',1),(12,19,5,'Lalo!','2019-05-29 02:20:15',0),(13,19,5,'Hola','2019-05-29 02:20:21',0),(14,18,7,'Hey','2019-05-29 02:23:15',1),(15,18,7,'como estas karol','2019-05-29 02:23:26',1),(16,18,4,'y a ustedes?','2019-05-29 02:30:33',1),(17,21,8,'Hola David','2019-05-29 02:36:09',1),(18,22,9,'Hola','2019-05-29 02:48:16',1),(19,22,9,'Lalo','2019-05-29 02:48:24',1),(20,18,8,'hola','2019-05-29 15:19:37',0),(21,16,9,'Hola','2019-05-29 16:44:35',1);
/*!40000 ALTER TABLE `mensaje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` text NOT NULL,
  `numero` text NOT NULL,
  `frase` varchar(50) NOT NULL DEFAULT 'Hey there!',
  `foto` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (15,'Karol Pinon','111','Si no vas con todo, a quÃ© vas?',2),(16,'Luis Martinez','222','No fear. Just fight.',3),(17,'Gretel Paz','333','Love.',4),(18,'David Zavala','444','Reloj.',1),(19,'Jean Barubi','555','Hola.',1),(20,'Jane Mendoza','666','Girasol',2),(21,'Paola Zuniga','777','Corazon',4),(22,'Carlos','888','hey there',3);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_chat`
--

DROP TABLE IF EXISTS `usuario_chat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario_chat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_chat` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_chat` (`id_chat`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `usuario_chat_ibfk_1` FOREIGN KEY (`id_chat`) REFERENCES `chat` (`id`),
  CONSTRAINT `usuario_chat_ibfk_2` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_chat`
--

LOCK TABLES `usuario_chat` WRITE;
/*!40000 ALTER TABLE `usuario_chat` DISABLE KEYS */;
INSERT INTO `usuario_chat` VALUES (1,1,15),(2,1,16),(3,2,18),(4,2,19),(5,3,16),(6,3,17),(7,4,15),(8,4,16),(9,4,18),(10,5,19),(11,5,16),(14,7,18),(15,7,15),(16,8,21),(17,8,18),(18,9,22),(19,9,16);
/*!40000 ALTER TABLE `usuario_chat` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-05-29 12:09:50
