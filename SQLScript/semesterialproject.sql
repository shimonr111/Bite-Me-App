-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: semesterialproject
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
-- Table structure for table `branchmanager`
--

DROP TABLE IF EXISTS `branchmanager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `branchmanager` (
  `userID` varchar(256) NOT NULL,
  `isConfirmedInSystem` tinyint(1) DEFAULT '0',
  `firstName` varchar(256) DEFAULT NULL,
  `lastName` varchar(256) DEFAULT NULL,
  `homeBranch` enum('NORTH','CENTER','SOUTH','NOT_APPLICABLE') DEFAULT 'NOT_APPLICABLE',
  `isLoggedIn` tinyint(1) DEFAULT '0',
  `privateW4cCodeNumber` int DEFAULT NULL,
  `email` varchar(256) DEFAULT NULL,
  `phoneNumber` varchar(256) DEFAULT NULL,
  `branchName` enum('NORTH','CENTER','SOUTH','NOT_APPLICABLE') DEFAULT NULL,
  PRIMARY KEY (`userID`),
  CONSTRAINT `branchmanager_userID` FOREIGN KEY (`userID`) REFERENCES `login` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branchmanager`
--

LOCK TABLES `branchmanager` WRITE;
/*!40000 ALTER TABLE `branchmanager` DISABLE KEYS */;
INSERT INTO `branchmanager` VALUES ('1041',1,'branchmanagerNFirstname','branchmanagerNLastname','NORTH',0,10414,'branchManagerNEmail@BM.com','104104','NORTH'),('1042',1,'branchmanagerSFirstname','branchmanagerSLastname','SOUTH',0,10424,'branchManagerSEmail@BM.com','104204','SOUTH'),('1043',1,'branchmanagerCFirstname','branchmanagerCLastname','CENTER',0,10434,'branchManagerCEmail@BM.com','104304','CENTER');
/*!40000 ALTER TABLE `branchmanager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `businesscustomer`
--

DROP TABLE IF EXISTS `businesscustomer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `businesscustomer` (
  `userID` varchar(256) NOT NULL,
  `isConfirmedInSystem` tinyint(1) DEFAULT '0',
  `firstName` varchar(256) DEFAULT NULL,
  `lastName` varchar(256) DEFAULT NULL,
  `homeBranch` enum('NORTH','CENTER','SOUTH','NOT_APPLICABLE') DEFAULT 'NOT_APPLICABLE',
  `isLoggedIn` tinyint(1) DEFAULT '0',
  `privateW4cCodeNumber` int DEFAULT NULL,
  `email` varchar(256) DEFAULT NULL,
  `phoneNumber` varchar(256) DEFAULT NULL,
  `privateCreditCard` varchar(256) DEFAULT NULL,
  `companyName` varchar(256) DEFAULT NULL,
  `budgetType` enum('DAILY','WEEKLY','MONTHLY') DEFAULT NULL,
  `customerPosition` varchar(256) DEFAULT NULL,
  `budgetMaxAmount` int DEFAULT NULL,
  `businessW4cCodeNumber` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`userID`),
  KEY `businesscustomer_privateCreditCard_idx` (`privateCreditCard`),
  KEY `businesscustomer_companyName_idx` (`companyName`),
  CONSTRAINT `businesscustomer_companyName` FOREIGN KEY (`companyName`) REFERENCES `company` (`companyName`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `businesscustomer_privateCreditCard` FOREIGN KEY (`privateCreditCard`) REFERENCES `creditcard` (`creditCardNumber`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `businesscustomer_userID` FOREIGN KEY (`userID`) REFERENCES `login` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `businesscustomer`
--

LOCK TABLES `businesscustomer` WRITE;
/*!40000 ALTER TABLE `businesscustomer` DISABLE KEYS */;
INSERT INTO `businesscustomer` VALUES ('1003',1,'businesscustomerFirstname','businesscustomerLastname','NORTH',0,10033,'businesscustomerEmail@Intel.com','100303','3003','Intel','DAILY','businesscustomerPosition',1003003,'2003');
/*!40000 ALTER TABLE `businesscustomer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ceobiteme`
--

DROP TABLE IF EXISTS `ceobiteme`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ceobiteme` (
  `userID` varchar(256) NOT NULL,
  `isConfirmedInSystem` tinyint(1) DEFAULT '0',
  `firstName` varchar(256) DEFAULT NULL,
  `lastName` varchar(256) DEFAULT NULL,
  `homeBranch` enum('NORTH','CENTER','SOUTH','NOT_APPLICABLE') DEFAULT 'NOT_APPLICABLE',
  `isLoggedIn` tinyint(1) DEFAULT '0',
  `privateW4cCodeNumber` int DEFAULT NULL,
  `email` varchar(256) DEFAULT NULL,
  `phoneNumber` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`userID`),
  CONSTRAINT `ceobiteme_userID` FOREIGN KEY (`userID`) REFERENCES `login` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ceobiteme`
--

LOCK TABLES `ceobiteme` WRITE;
/*!40000 ALTER TABLE `ceobiteme` DISABLE KEYS */;
INSERT INTO `ceobiteme` VALUES ('1001',1,'ceoFirstname','ceoLastname','NOT_APPLICABLE',0,10011,'ceoEmail@BM.com','100101');
/*!40000 ALTER TABLE `ceobiteme` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `company` (
  `companyName` varchar(256) NOT NULL,
  `isConfirmedCompanyInSystem` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`companyName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES ('Intel',1);
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `creditcard`
--

DROP TABLE IF EXISTS `creditcard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `creditcard` (
  `creditCardNumber` varchar(256) NOT NULL,
  `creditCardCvvCode` varchar(256) DEFAULT NULL,
  `creditCardDateOfExpiration` datetime DEFAULT NULL,
  PRIMARY KEY (`creditCardNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `creditcard`
--

LOCK TABLES `creditcard` WRITE;
/*!40000 ALTER TABLE `creditcard` DISABLE KEYS */;
INSERT INTO `creditcard` VALUES ('3000','111','2100-12-31 00:00:00'),('3001','111','2100-12-31 00:00:00'),('3002','111','2100-12-31 00:00:00'),('3003','111','2100-12-31 00:00:00');
/*!40000 ALTER TABLE `creditcard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `userID` varchar(256) NOT NULL,
  `isConfirmedInSystem` tinyint(1) DEFAULT '0',
  `firstName` varchar(256) DEFAULT NULL,
  `lastName` varchar(256) DEFAULT NULL,
  `homeBranch` enum('NORTH','CENTER','SOUTH','NOT_APPLICABLE') DEFAULT 'NOT_APPLICABLE',
  `isLoggedIn` tinyint(1) DEFAULT '0',
  `privateW4cCodeNumber` int DEFAULT NULL,
  `email` varchar(256) DEFAULT NULL,
  `phoneNumber` varchar(256) DEFAULT NULL,
  `privateCreditCard` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`userID`),
  KEY `privateCreditCard_idx` (`privateCreditCard`),
  CONSTRAINT `customer_privateCreditCard` FOREIGN KEY (`privateCreditCard`) REFERENCES `creditcard` (`creditCardNumber`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `customer_userID` FOREIGN KEY (`userID`) REFERENCES `login` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES ('1000',1,'customerFirstname','customerLastname','NORTH',0,10000,'customerEmail@gmeel.com','100000','3000');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hrmanager`
--

DROP TABLE IF EXISTS `hrmanager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hrmanager` (
  `userID` varchar(256) NOT NULL,
  `isConfirmedInSystem` tinyint(1) DEFAULT '0',
  `firstName` varchar(256) DEFAULT NULL,
  `lastName` varchar(256) DEFAULT NULL,
  `homeBranch` enum('NORTH','CENTER','SOUTH','NOT_APPLICABLE') DEFAULT 'NOT_APPLICABLE',
  `isLoggedIn` tinyint(1) DEFAULT '0',
  `privateW4cCodeNumber` int DEFAULT NULL,
  `email` varchar(256) DEFAULT NULL,
  `phoneNumber` varchar(256) DEFAULT NULL,
  `privateCreditCard` varchar(256) DEFAULT NULL,
  `companyName` varchar(256) DEFAULT NULL,
  `budgetType` enum('DAILY','WEEKLY','MONTHLY') DEFAULT NULL,
  `customerPosition` varchar(256) DEFAULT NULL,
  `budgetMaxAmount` int DEFAULT NULL,
  `businessW4cCodeNumber` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`userID`),
  KEY `hrmanager_privateCreditCard_idx` (`privateCreditCard`),
  KEY `hrmanager_companyName_idx` (`companyName`),
  CONSTRAINT `hrmanager_companyName` FOREIGN KEY (`companyName`) REFERENCES `company` (`companyName`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `hrmanager_privateCreditCard` FOREIGN KEY (`privateCreditCard`) REFERENCES `creditcard` (`creditCardNumber`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `hrmanager_userID` FOREIGN KEY (`userID`) REFERENCES `login` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hrmanager`
--

LOCK TABLES `hrmanager` WRITE;
/*!40000 ALTER TABLE `hrmanager` DISABLE KEYS */;
INSERT INTO `hrmanager` VALUES ('1002',1,'hrmanagerFirstname','hrmanagerLastname','NORTH',0,10022,'hrmanagerEmail@Intel.com','10022','3002','Intel','DAILY','hrmanagerPosition',1002002,'2002');
/*!40000 ALTER TABLE `hrmanager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `login` (
  `username` varchar(256) NOT NULL,
  `password` varchar(256) DEFAULT NULL,
  `userID` varchar(256) NOT NULL,
  `userType` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`userID`,`username`),
  UNIQUE KEY `userID_UNIQUE` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login`
--

LOCK TABLES `login` WRITE;
/*!40000 ALTER TABLE `login` DISABLE KEYS */;
INSERT INTO `login` VALUES ('customer','customer','1000','customer'),('ceobiteme','ceobiteme','1001','ceo'),('hrmanager','hrmanager','1002','hrmanager'),('businesscustomer','businesscustomer','1003','businesscustomer'),('supplier','supplier','1005','supplier'),('branchmanagerN','branchmanagerN','1041','branchmanager'),('branchmanagerS','branchmanagerS','1042','branchmanager'),('branchmanagerC','branchmanagerC','1043','branchmanager');
/*!40000 ALTER TABLE `login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier` (
  `userID` varchar(256) NOT NULL,
  `isConfirmedInSystem` tinyint(1) DEFAULT '0',
  `firstName` varchar(256) DEFAULT NULL,
  `lastName` varchar(256) DEFAULT NULL,
  `homeBranch` enum('NORTH','CENTER','SOUTH','NOT_APPLICABLE') DEFAULT 'NOT_APPLICABLE',
  `isLoggedIn` tinyint(1) DEFAULT '0',
  `privateW4cCodeNumber` int DEFAULT NULL,
  `email` varchar(256) DEFAULT NULL,
  `phoneNumber` varchar(256) DEFAULT NULL,
  `supplierBusinessName` varchar(256) DEFAULT NULL,
  `revenueFee` double DEFAULT NULL,
  PRIMARY KEY (`userID`),
  CONSTRAINT `supplier_userID` FOREIGN KEY (`userID`) REFERENCES `login` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` VALUES ('1005',1,'supplierFirstname','supplierLastname','NORTH',0,10055,'supplierEmail@supplies.com','100505','supplierBusinnessname',0.5);
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-01 21:57:38
