CREATE DATABASE  IF NOT EXISTS `externaldb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `externaldb`;
-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: externaldb
-- ------------------------------------------------------
-- Server version	8.0.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `usersmanagement`
--

DROP TABLE IF EXISTS `usersmanagement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usersmanagement` (
  `userType` varchar(256) DEFAULT 'user',
  `userID` varchar(256) NOT NULL DEFAULT '-1',
  `statusInSystem` enum('CONFIRMED','PENDING_APPROVAL','FROZEN','PENDING_REGISTRATION') DEFAULT 'PENDING_REGISTRATION',
  `firstName` varchar(256) DEFAULT 'null',
  `lastName` varchar(256) DEFAULT 'null',
  `homeBranch` enum('NORTH','CENTER','SOUTH','NOT_APPLICABLE') DEFAULT 'NOT_APPLICABLE',
  `isLoggedIn` tinyint DEFAULT '0',
  `email` varchar(256) DEFAULT 'null',
  `phoneNumber` varchar(256) DEFAULT 'null',
  `creditCardNumber` varchar(256) DEFAULT 'null',
  `creditCardCvvCode` varchar(256) DEFAULT 'null',
  `creditCardDateOfExpiration` varchar(256) DEFAULT 'null',
  `username` varchar(256) NOT NULL DEFAULT 'null',
  `password` varchar(256) DEFAULT 'null',
  `companyName` varchar(256) DEFAULT NULL,
  `companyCode` int DEFAULT NULL,
  `revenueFee` double DEFAULT NULL,
  PRIMARY KEY (`userID`,`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usersmanagement`
--

LOCK TABLES `usersmanagement` WRITE;
/*!40000 ALTER TABLE `usersmanagement` DISABLE KEYS */;
INSERT INTO `usersmanagement` VALUES ('user','10000','PENDING_REGISTRATION','Mousa','Srour','NORTH',0,'mousa.srour@gmail.com','0544441212','1000','111','01/35','mousa','mousa',NULL,NULL,NULL),('user','10001','PENDING_REGISTRATION','Ori','Malka','NORTH',0,'ori.malka@gmail.com','0544441211','1001','111','01/35','ori','ori',NULL,NULL,NULL),('user','10002','PENDING_REGISTRATION','Alexander','Martinov','CENTER',0,'alexander.martinov@gmail.com','0544441200','1002','111','01/35','alexander','alexander',NULL,NULL,NULL),('user','10003','PENDING_REGISTRATION','Shimon','Rubin','CENTER',0,'shimon.rubin@gmail.com','0544441233','1003','111','01/35','shimon','shimon',NULL,NULL,NULL),('user','10004','PENDING_REGISTRATION','Lior','Guzovsky','SOUTH',0,'lior.guzovsky@gmail.com','0544441244','1004','111','01/35','lior','lior',NULL,NULL,NULL),('user','10005','PENDING_REGISTRATION','Dan','Micheal','SOUTH',0,'dan.micheal@gmail.com','0541112341','1005','111','01/35','dan','dan',NULL,NULL,NULL),('ceobiteme','1001','CONFIRMED','Leon','Mark','NOT_APPLICABLE',0,'leon.mark@BM.com','04-8981121','null','null','null','ceo','ceo',NULL,NULL,NULL),('branchmanager','1041','CONFIRMED','Manuel','Morelo','NORTH',0,'manuel.morelo@BM.com','04-8981122','null','null','null','bmn','bmn',NULL,NULL,NULL),('branchmanager','1042','CONFIRMED','Santos','Kuba','SOUTH',0,'santos.kuba@BM.com','04-8981123','null','null','null','bms','bms',NULL,NULL,NULL),('branchmanager','1043','CONFIRMED','David','Kell','CENTER',0,'david.kell@BM.com','04-8981124','null','null','null','bmc','bmc',NULL,NULL,NULL),('supplier','20000','PENDING_REGISTRATION','BBB','null','CENTER',0,'support@bbb.com','0524149911','null','null','null','null','null',NULL,NULL,9),('supplier','20001','PENDING_REGISTRATION','BBB','null','NORTH',0,'support@bbb.com','0524149912','null','null','null','null','null',NULL,NULL,11),('supplier','20002','PENDING_REGISTRATION','BBB','null','SOUTH',0,'support@bbb.com','0524149913','null','null','null','null','null',NULL,NULL,10),('supplier','20003','PENDING_REGISTRATION','Agadir','null','CENTER',0,'support@agadir.com','0521121111','null','null','null','null','null',NULL,NULL,10.2),('supplier','20004','PENDING_REGISTRATION','Agadir','null','NORTH',0,'support@agadir.com','0521121112','null','null','null','null','null',NULL,NULL,7.4),('supplier','20005','PENDING_REGISTRATION','Agadir','null','SOUTH',0,'support@agadir.com','0521121113','null','null','null','null','null',NULL,NULL,9.4),('hrmanager','30000','CONFIRMED','Luis','Kun','NOT_APPLICABLE',0,'luiskun@microsoft.com','0589991211','null','null','null','microsofthr','microsofthr','Microsoft',50001,NULL),('hrmanager','30001','CONFIRMED','Kun','Verat','NOT_APPLICABLE',0,'kunveat@nvidia.com','0511228167','null','null','null','nvidiahr','nvidiahr','Nvidia',50002,NULL),('hrmanager','30002','CONFIRMED','Kurti','Ilerta','NORTH',0,'kurtiilerta@dan.com','0578881578','null','null','null','danhr','danhr','Dan-Panorama',50003,NULL),('supplierworker','40000','CONFIRMED','Charlos','Nuev','CENTER',0,'charlos@bbb.com','0551123312','20000','CERTIFIED','null','bbbcc','bbbcc',NULL,NULL,NULL),('supplierworker','40001','CONFIRMED','Daniel','Ovez','CENTER',0,'daniel@bbb.com','0551231221','20000','REGULAR','null','bbbcr','bbbcr',NULL,NULL,NULL),('supplierworker','40002','CONFIRMED','Alex','Zur','NORTH',0,'alex@bbb.com','0551123318','20001','CERTIFIED','null','bbbnc','bbbnc',NULL,NULL,NULL),('supplierworker','40003','CONFIRMED','Dor','Svet','NORTH',0,'dor@bbb.com','0551241122','20001','REGULAR','null','bbbnr','bbbnr',NULL,NULL,NULL),('supplierworker','40004','CONFIRMED','Zven','Kars','SOUTH',0,'zven@bbb.com','0512221412','20002','CERTIFIED','null','bbbsc','bbbsc',NULL,NULL,NULL),('supplierworker','40005','CONFIRMED','Lian','Orel','SOUTH',0,'lian@bbb.com','0512228926','20002','REGULAR','null','bbbsn','bbbsn',NULL,NULL,NULL),('supplierworker','40006','CONFIRMED','Luma','Ali','CENTER',0,'luma@agadir.com','0512228512','20003','CERTIFIED','null','agadircc','agadircc',NULL,NULL,NULL),('supplierworker','40007','CONFIRMED','Lux','Sarl','CENTER',0,'lux@agadir.com','0512220001','20003','REGULAR','null','agadircr','agadircr',NULL,NULL,NULL),('supplierworker','40008','CONFIRMED','Kobi','Tarel','NORTH',0,'kobi@agadir.com','05122201122','20004','CERTIFIED','null','agadirnc','agadircc',NULL,NULL,NULL),('supplierworker','40009','CONFIRMED','Mund','Kuit','NORTH',0,'mund@agadir.com','05182571122','20004','REGULAR','null','agadirnr','agadirnr',NULL,NULL,NULL),('supplierworker','40010','CONFIRMED','Masgawa','Sorely','SOUTH',0,'masgawa@agadir.com','05182571222','20005','CERTIFIED','null','agadirsc','agadirsc',NULL,NULL,NULL),('supplierworker','40011','CONFIRMED','Kuraty','Fatsl','SOUTH',0,'kuraty@agadir.com','0518000162','20005','REGULAR','null','agadirsr','agadirsr',NULL,NULL,NULL),('supplierworker','40012','CONFIRMED','Dave','Clew','CENTER',0,'charlos@bbb.com','0551123312','20000','MANAGER','null','bbbcm','bbbcm',NULL,NULL,NULL),('supplierworker','40013','CONFIRMED','Natalie','Cast','NORTH',0,'natalie@bbb.com','0551123368','20001','MANAGER','null','bbbnm','bbbnm',NULL,NULL,NULL),('supplierworker','40014','CONFIRMED','Sari','Karte','SOUTH',0,'sari@bbb.com','0569162941','20002','MANAGER','null','bbbsm','bbbsm',NULL,NULL,NULL),('supplierworker','40015','CONFIRMED','Lili','Roben','CENTER',0,'lili@agadir.com','0567861231','20003','MANAGER','null','agadircm','agadircm',NULL,NULL,NULL),('supplierworker','40016','CONFIRMED','Tal','Tors','NORTH',0,'tal@agadir.com','0589991231','20004','MANAGER','null','agadirnm','agadircm',NULL,NULL,NULL),('supplierworker','40017','CONFIRMED','Eyal','Berk','SOUTH',0,'eyal@agadir.com','0589791231','20005','MANAGER','null','agadirsm','agadirsm',NULL,NULL,NULL),('company','50001','PENDING_REGISTRATION','Microsoft','Natanya, Histadrot 2','NOT_APPLICABLE',0,'microsoft@microsoft.com','null','null','null','null','null','null',NULL,NULL,NULL),('company','50002','PENDING_REGISTRATION','Nvidia','Haifa, Tsyonot 19','NOT_APPLICABLE',0,'nvidia@nvidia.com','null','null','null','null','null','null',NULL,NULL,NULL),('company','50003','PENDING_REGISTRATION','Dan-Panorama','Haifa, Hanasi 17','NOT_APPLICABLE',0,'danpanorama@dan.com','null','null','null','null','null','null',NULL,NULL,NULL);
/*!40000 ALTER TABLE `usersmanagement` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-01-01 23:53:53
