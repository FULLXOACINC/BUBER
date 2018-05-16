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
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_login` varchar(45) NOT NULL,
  `user_name` varchar(45) NOT NULL,
  `user_second_name` varchar(45) NOT NULL,
  `user_password` char(40) NOT NULL,
  `user_type` enum('user','driver','admin','root_admin') NOT NULL DEFAULT 'user',
  `user_balance` decimal(7,2) NOT NULL,
  `user_phone_number` char(13) NOT NULL,
  `user_is_ban` tinyint(1) unsigned NOT NULL,
  `user_birth_dey` date NOT NULL,
  `user_discount` float(3,2) unsigned NOT NULL,
  PRIMARY KEY (`user_login`),
  UNIQUE KEY `user_phone_number_UNIQUE` (`user_phone_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('bobkov@gmail.com','Filip','Funov','8c2442bc0d2443e69616fe0918ea5490c17d4407','admin',12.00,'+375291713223',1,'2018-01-01',0.10),('fun@gmail.com','Dima','Bobr','011c945f30ce2cbafc452f39840f025693339c42','driver',12.00,'+375291713226',0,'2018-01-01',0.30),('golvol@gmail.com','Bob','Sergeev','dbdc014496f6ebc404cdd1fe1da93061a1f8ff97','user',12.00,'+375292223312',0,'2018-01-01',0.10),('popov@gmail.com','Alex','Popov','fea7f657f56a2a448da7d4b535ee5e279caf3d9a','driver',15.00,'+375783212323',0,'2018-01-01',0.30),('root@gmail.com','Alex','Zhuk','b1d8eca521bb2d7cb93812f0d4247caea1a15a9c','root_admin',99999.00,'+375291713227',0,'2018-01-01',0.20),('san91130324@gmail.com','Alex','Zhuk','b1d8eca521bb2d7cb93812f0d4247caea1a15a9c','driver',100.00,'+375291713299',0,'1998-04-13',0.99),('vlad@gmail.com','Vladislav','Voidov','011c945f30ce2cbafc452f39840f025693339c42','driver',12.00,'+375291842322',0,'2018-01-01',0.20),('void@yandex.ru','Keril','Dubov','cb990257247b592eaaed54b84b32d96b7904fd95','driver',12.00,'+375291713225',0,'2018-01-01',0.20);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-16  3:11:14
