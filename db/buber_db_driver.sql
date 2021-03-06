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
-- Table structure for table `driver`
--

DROP TABLE IF EXISTS `driver`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `driver` (
  `driver_login` varchar(45) NOT NULL,
  `driver_car_number` char(7) NOT NULL,
  `driver_document_id` char(9) NOT NULL,
  `driver_car_mark` tinyint(4) NOT NULL,
  `driver_current_lat_coordinate` float(10,6) NOT NULL,
  `driver_current_lng_coordinate` float(10,6) NOT NULL,
  `driver_is_working` tinyint(1) NOT NULL DEFAULT '1',
  `driver_positive_mark` mediumint(9) NOT NULL,
  `driver_negative_mark` mediumint(9) NOT NULL,
  `driver_tariff` decimal(3,2) NOT NULL,
  PRIMARY KEY (`driver_login`),
  UNIQUE KEY `driver_car_number_UNIQUE` (`driver_car_number`),
  UNIQUE KEY `driver_document_id_UNIQUE` (`driver_document_id`),
  KEY `driver_car_mark_idx` (`driver_car_mark`),
  CONSTRAINT `driver_login` FOREIGN KEY (`driver_login`) REFERENCES `user` (`user_login`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `driver`
--

LOCK TABLES `driver` WRITE;
/*!40000 ALTER TABLE `driver` DISABLE KEYS */;
INSERT INTO `driver` VALUES ('bobkov@gmail.com','7511AT7','7AB203315',13,53.904537,27.561535,1,0,0,3.98),('fun@gmail.com','7522AT1','7AD123132',25,53.904537,27.561535,1,0,0,0.65),('golvol@gmail.com','7534AT7','7AB204115',13,53.904537,27.561535,1,0,0,1.23),('popov@gmail.com','4312FF5','7AB204225',12,53.905537,27.571535,1,2,0,0.94),('san91130324@gmail.com','7522AT7','7AD123188',17,53.949036,27.714005,0,3,0,0.98),('vlad@gmail.com','4323FF5','7AB204955',14,53.914536,27.561535,1,3,0,0.99),('void@yandex.ru','4312DD5','5AB205225',15,53.904537,27.561535,1,0,0,0.97);
/*!40000 ALTER TABLE `driver` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-29  3:29:19
