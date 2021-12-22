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
INSERT INTO `usersmanagement` VALUES ('user','1000','PENDING_REGISTRATION','customerFirstname','customerLastname','NORTH',0,'customerEmail@gmeel.com','100000','3000','111','01/35','cu','cu',NULL,NULL,NULL),('ceobiteme','1001','CONFIRMED','ceoFirstname','ceoLastname','NOT_APPLICABLE',0,'ceoEmail@BM.com','100101','null','null','null','ceo','ceo',NULL,NULL,NULL),('user','1002','PENDING_REGISTRATION','hrmanagerFirstname','hrmanagerLastname','NORTH',0,'hrmanagerEmail@Intel.com','10022','3002','111','01/35','hr','hr',NULL,NULL,NULL),('user','1003','PENDING_REGISTRATION','businesscustomerFirstName','businesscustomerLastName','NORTH',0,'businessCustomerEmail@business.com','100303','3003','111','01/35','bcu','bcu',NULL,NULL,NULL),('user','1005','PENDING_REGISTRATION','supplierWorkerFirstname','supplierWorkerLastname','NORTH',0,'supplierWorkerEmail@supplies.com','100505','3004','111','01/35','sw','sw',NULL,NULL,NULL),('branchmanager','1041','CONFIRMED','branchmanagerNfirstName','branchmanagerNlastName','NORTH',0,'branchManagerNEmail@BM.com','104104','null','null','null','bmn','bmn',NULL,NULL,NULL),('branchmanager','1042','CONFIRMED','branchmanagerSfirstName','branchmanagerSlastName','SOUTH',0,'branchManagerSEmail@BM.com','104204','null','null','null','bms','bms',NULL,NULL,NULL),('branchmanager','1043','CONFIRMED','branchmanagerCfirstName','branchmanagerClastName','CENTER',0,'branchManagerCEmail@BM.com','104304','null','null','null','bmc','bmc',NULL,NULL,NULL),('supplier','1111','PENDING_REGISTRATION','Dominos','null','CENTER',0,'support@dominos.com','100001111','null','null','null','null','null',NULL,NULL,7.8),('supplier','1112','PENDING_REGISTRATION','Dominos','null','NORTH',0,'support@dominos.com','100001111','null','null','null','null','null',NULL,NULL,9.8),('hrmanager','1222','CONFIRMED','intelHRfirstName','intelHRlastName','NOT_APPLICABLE',0,'intelhr@intel.com','0101010','3005','111','01/35','intelhr','intelhr','Intel',5001,NULL),('hrmanager','1333','CONFIRMED','appleHrfirstName','AppleHrLastName','NOT_APPLICABLE',0,'applhr@apple.com','200001','3006','111','01/35','applehr','applehr','Apple',5002,NULL),('supplierworker','2000','CONFIRMED','PHWfirstName','PHWlastname','SOUTH',0,'phw@pizzahut.com','1000211','2222','CERTIFIED','null','phsw','phsw',NULL,NULL,NULL),('supplierworker','2001','CONFIRMED','PHWfirstName','PHWlastname','SOUTH',0,'phw@pizzahut.com','1000222','2222','REGULAR','null','phsrw','phsrw',NULL,NULL,NULL),('supplier','2222','PENDING_REGISTRATION','PizzaHut','null','SOUTH',0,'support@pizzahut.com','100002222','null','null','null','null','null',NULL,NULL,10),('supplier','2223','PENDING_REGISTRATION','PizzaHut','null','CENTER',0,'support@pizzahut.com','100002222','null','null','null','null','null',NULL,NULL,12),('company','5001','PENDING_REGISTRATION','Intel','IntelAddress','NOT_APPLICABLE',0,'intel@intel.com','null','null','null','null','null','null',NULL,NULL,NULL),('company','5002','PENDING_REGISTRATION','Apple','AppleAddress','NOT_APPLICABLE',0,'apple@apple.com','null','null','null','null','null','null',NULL,NULL,NULL),('supplier','5555','PENDING_REGISTRATION','Mcdonalds','null','NORTH',0,'support@mcdonalds.com','100005555','null','null','null','null','null',NULL,NULL,11.3),('supplier','5556','PENDING_REGISTRATION','Mcdonalds','null','CENTER',0,'support@mcdonalds.com','100005555','null','null','null','null','null',NULL,NULL,7.2);
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

-- Dump completed on 2021-12-22 11:22:36
