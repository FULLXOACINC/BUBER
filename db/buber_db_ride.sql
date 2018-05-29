-- MySQL dump 10.13  Distrib 5.7.22, for Linux (i686)
--
-- Host: localhost    Database: buber_db
-- ------------------------------------------------------
-- Server version	5.7.22-0ubuntu0.16.04.1

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
-- Table structure for table `ride`
--

DROP TABLE IF EXISTS `ride`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ride` (
  `ride_index` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ride_driver_login` varchar(45) NOT NULL,
  `ride_passenger_login` varchar(45) NOT NULL,
  `ride_start_lat_coordinate` float(10,6) NOT NULL,
  `ride_start_lng_coordinate` float(10,6) NOT NULL,
  `ride_end_lat_coordinate` float(10,6) NOT NULL,
  `ride_end_lng_coordinate` float(10,6) NOT NULL,
  `ride_price` decimal(7,2) unsigned NOT NULL,
  `ride_data` date DEFAULT NULL,
  `ride_is_passenger_start_accept` tinyint(1) NOT NULL,
  `ride_is_driver_start_accept` tinyint(1) NOT NULL,
  `ride_is_passenger_end_accept` tinyint(1) NOT NULL,
  `ride_is_driver_end_accept` tinyint(1) NOT NULL,
  PRIMARY KEY (`ride_index`),
  KEY `ride_passenger_login_idx` (`ride_passenger_login`,`ride_driver_login`),
  KEY `ride_driver_login_idx` (`ride_driver_login`),
  CONSTRAINT `ride_driver_login` FOREIGN KEY (`ride_driver_login`) REFERENCES `driver` (`driver_login`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ride_passenger_login` FOREIGN KEY (`ride_passenger_login`) REFERENCES `user` (`user_login`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ride`
--

LOCK TABLES `ride` WRITE;
/*!40000 ALTER TABLE `ride` DISABLE KEYS */;
INSERT INTO `ride` VALUES (2,'bobkov@gmail.com','fun@gmail.com',54.238991,35.238110,54.238125,35.238312,24.00,'2018-04-09',1,1,1,1),(3,'bobkov@gmail.com','void@yandex.ru',54.238323,35.234211,54.238422,35.238232,20.00,'2018-04-09',1,1,1,1),(5,'vlad@gmail.com','golvol@gmail.com',54.238232,35.238865,54.233112,35.238319,13.00,'2018-04-09',1,1,1,1),(6,'void@yandex.ru','bobkov@gmail.com',54.238422,35.238743,54.238232,35.238319,18.00,'2018-04-09',1,1,1,1),(13,'san91130324@gmail.com','buberteam@gmail.com',53.941120,27.655682,53.900581,27.624460,4.84,'2018-05-29',0,0,0,0);
/*!40000 ALTER TABLE `ride` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-29  3:29:18
