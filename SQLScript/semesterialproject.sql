-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: semesterialproject
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
  `statusInSystem` enum('CONFIRMED','PENDING_APPROVAL','FROZEN') DEFAULT NULL,
  `firstName` varchar(256) DEFAULT NULL,
  `lastName` varchar(256) DEFAULT NULL,
  `homeBranch` enum('NORTH','CENTER','SOUTH','NOT_APPLICABLE') DEFAULT 'NOT_APPLICABLE',
  `isLoggedIn` tinyint(1) DEFAULT '0',
  `email` varchar(256) DEFAULT NULL,
  `phoneNumber` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`userID`),
  CONSTRAINT `branchmanager_userID` FOREIGN KEY (`userID`) REFERENCES `login` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branchmanager`
--

LOCK TABLES `branchmanager` WRITE;
/*!40000 ALTER TABLE `branchmanager` DISABLE KEYS */;
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
  `statusInSystem` enum('CONFIRMED','PENDING_APPROVAL','FROZEN') DEFAULT NULL,
  `firstName` varchar(256) DEFAULT NULL,
  `lastName` varchar(256) DEFAULT NULL,
  `homeBranch` enum('NORTH','CENTER','SOUTH','NOT_APPLICABLE') DEFAULT 'NOT_APPLICABLE',
  `isLoggedIn` tinyint(1) DEFAULT '0',
  `businessW4cCodeNumber` int DEFAULT NULL,
  `email` varchar(256) DEFAULT NULL,
  `phoneNumber` varchar(256) DEFAULT NULL,
  `privateCreditCard` varchar(256) DEFAULT NULL,
  `balance` double DEFAULT NULL,
  `companyName` varchar(256) DEFAULT NULL,
  `budgetType` enum('DAILY','WEEKLY','MONTHLY') DEFAULT NULL,
  `customerPosition` enum('HR','REGULAR') DEFAULT NULL,
  `budgetMaxAmount` int DEFAULT NULL,
  `privateW4cCodeNumber` int DEFAULT NULL,
  PRIMARY KEY (`userID`),
  KEY `businesscustomer_privateCreditCard_idx` (`privateCreditCard`),
  KEY `businesscustomer_companyName_idx` (`companyName`),
  KEY `businesscustomer_businessW4cCodeNumber_idx` (`businessW4cCodeNumber`),
  CONSTRAINT `businesscustomer_businessW4cCodeNumber` FOREIGN KEY (`businessW4cCodeNumber`) REFERENCES `company` (`companyCode`),
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
  `statusInSystem` enum('CONFIRMED','PENDING_APPROVAL','FROZEN') DEFAULT NULL,
  `firstName` varchar(256) DEFAULT NULL,
  `lastName` varchar(256) DEFAULT NULL,
  `homeBranch` enum('NORTH','CENTER','SOUTH','NOT_APPLICABLE') DEFAULT 'NOT_APPLICABLE',
  `isLoggedIn` tinyint(1) DEFAULT '0',
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
  `companyStatusInSystem` enum('CONFIRMED','PENDING_APPROVAL','FROZEN') DEFAULT NULL,
  `address` varchar(256) DEFAULT NULL,
  `email` varchar(256) DEFAULT NULL,
  `companyCode` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`companyName`,`companyCode`),
  KEY `W4CNUMBER` (`companyCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
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
  `creditCardDateOfExpiration` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`creditCardNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `creditcard`
--

LOCK TABLES `creditcard` WRITE;
/*!40000 ALTER TABLE `creditcard` DISABLE KEYS */;
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
  `statusInSystem` enum('CONFIRMED','PENDING_APPROVAL','FROZEN') DEFAULT NULL,
  `firstName` varchar(256) DEFAULT NULL,
  `lastName` varchar(256) DEFAULT NULL,
  `homeBranch` enum('NORTH','CENTER','SOUTH','NOT_APPLICABLE') DEFAULT 'NOT_APPLICABLE',
  `isLoggedIn` tinyint(1) DEFAULT '0',
  `privateW4cCodeNumber` int DEFAULT NULL,
  `email` varchar(256) DEFAULT NULL,
  `phoneNumber` varchar(256) DEFAULT NULL,
  `privateCreditCard` varchar(256) DEFAULT NULL,
  `balance` double DEFAULT NULL,
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
  `statusInSystem` enum('CONFIRMED','PENDING_APPROVAL','FROZEN') DEFAULT NULL,
  `firstName` varchar(256) DEFAULT NULL,
  `lastName` varchar(256) DEFAULT NULL,
  `homeBranch` enum('NORTH','CENTER','SOUTH','NOT_APPLICABLE') DEFAULT 'NOT_APPLICABLE',
  `isLoggedIn` tinyint(1) DEFAULT '0',
  `businessW4cCodeNumber` int DEFAULT NULL,
  `email` varchar(256) DEFAULT NULL,
  `phoneNumber` varchar(256) DEFAULT NULL,
  `privateCreditCard` varchar(256) DEFAULT NULL,
  `balance` double DEFAULT NULL,
  `companyName` varchar(256) DEFAULT NULL,
  `budgetType` enum('DAILY','WEEKLY','MONTHLY') DEFAULT NULL,
  `customerPosition` enum('HR','REGULAR') DEFAULT NULL,
  `budgetMaxAmount` int DEFAULT NULL,
  `privateW4cCodeNumber` int DEFAULT NULL,
  PRIMARY KEY (`userID`),
  KEY `hrmanager_privateCreditCard_idx` (`privateCreditCard`),
  KEY `hrmanager_companyName_idx` (`companyName`),
  KEY `hrmanager_businessW4cCodeNumber_idx` (`businessW4cCodeNumber`),
  CONSTRAINT `hrmanager_businessW4cCodeNumber` FOREIGN KEY (`businessW4cCodeNumber`) REFERENCES `company` (`companyCode`),
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
/*!40000 ALTER TABLE `hrmanager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_in_menu`
--

DROP TABLE IF EXISTS `item_in_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_in_menu` (
  `itemName` varchar(256) NOT NULL,
  `supplierId` varchar(256) NOT NULL,
  `itemCategory` enum('SALAD','FIRST','MAIN','DESSERT','DRINK') DEFAULT NULL,
  `itemSize` enum('SMALL','REGULAR','LARGE') NOT NULL,
  `picturePath` varchar(256) DEFAULT NULL,
  `itemPrice` double DEFAULT NULL,
  PRIMARY KEY (`itemName`,`itemSize`,`supplierId`),
  KEY `item_in_menu_supplierId_idx` (`supplierId`),
  CONSTRAINT `item_in_menu_supplierId` FOREIGN KEY (`supplierId`) REFERENCES `supplier` (`supplierId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_in_menu`
--

LOCK TABLES `item_in_menu` WRITE;
/*!40000 ALTER TABLE `item_in_menu` DISABLE KEYS */;
INSERT INTO `item_in_menu` VALUES ('burger','5555','MAIN','LARGE','C://pictures',45),('burger','5556','MAIN','LARGE','C://pictures',45),('cola','2222','DRINK','SMALL','C://pictures',14),('cola','2223','DRINK','SMALL','C://pictures',14),('cola','5555','DRINK','LARGE','C://pictures',12),('cola','5556','DRINK','LARGE','C://pictures',12),('fries','2222','FIRST','LARGE','C://pictures',15),('fries','2223','FIRST','LARGE','C://pictures',15),('fries','5555','FIRST','LARGE','C://pictures',15),('fries','5556','FIRST','LARGE','C://pictures',15),('pizza','1111','MAIN','REGULAR','C://pictures',80),('pizza','1112','MAIN','REGULAR','C://pictures',80),('pizza','2222','MAIN','REGULAR','C://pictures',80),('pizza','2223','MAIN','REGULAR','C://pictures',80);
/*!40000 ALTER TABLE `item_in_menu` ENABLE KEYS */;
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
/*!40000 ALTER TABLE `login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `orderNumber` int NOT NULL,
  `supplierId` varchar(256) DEFAULT NULL,
  `customerUserId` varchar(256) DEFAULT NULL,
  `customerUserType` varchar(256) DEFAULT NULL,
  `branch` enum('NORTH','CENTER','SOUTH') DEFAULT NULL,
  `timeType` enum('REGULAR','PRE') DEFAULT NULL,
  `status` enum('PENDING_APPROVAL','APPROVED','UN_APPROVED') DEFAULT NULL,
  `issueDateTime` datetime DEFAULT NULL,
  `estimatedSupplyDateTime` datetime DEFAULT NULL,
  `actualSupplyDateTime` datetime DEFAULT NULL,
  `supplyType` enum('TAKE_AWAY','REGULAR','MULTI','ROBOTIC') DEFAULT NULL,
  `totalPrice` double DEFAULT NULL,
  `receiverFirstName` varchar(256) DEFAULT NULL,
  `receiverLastName` varchar(256) DEFAULT NULL,
  `receiverAddress` varchar(256) DEFAULT NULL,
  `receiverPhoneNumber` varchar(256) DEFAULT NULL,
  `deliveryFee` double DEFAULT NULL,
  `itemsList` varchar(6000) DEFAULT NULL,
  `comments` varchar(3000) DEFAULT NULL,
  PRIMARY KEY (`orderNumber`),
  KEY `order_supplierId_idx` (`supplierId`),
  CONSTRAINT `order_supplierId` FOREIGN KEY (`supplierId`) REFERENCES `supplier` (`supplierId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registration`
--

DROP TABLE IF EXISTS `registration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registration` (
  `userType` varchar(256) NOT NULL,
  `userID` varchar(256) NOT NULL,
  `statusInSystem` enum('CONFIRMED','PENDING_APPROVAL','FROZEN','PENDING_REGISTRATION') DEFAULT NULL,
  `firstName` varchar(256) DEFAULT NULL,
  `lastName` varchar(256) DEFAULT NULL,
  `homeBranch` enum('NORTH','CENTER','SOUTH','NOT_APPLICABLE') DEFAULT NULL,
  `isLoggedIn` tinyint DEFAULT NULL,
  `email` varchar(256) DEFAULT NULL,
  `phoneNumber` varchar(256) DEFAULT NULL,
  `creditCardNumber` varchar(256) DEFAULT NULL,
  `creditCardCvvCode` varchar(256) DEFAULT NULL,
  `creditCardDateOfExpiration` varchar(256) DEFAULT NULL,
  `username` varchar(256) NOT NULL,
  `password` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`userID`,`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registration`
--

LOCK TABLES `registration` WRITE;
/*!40000 ALTER TABLE `registration` DISABLE KEYS */;
/*!40000 ALTER TABLE `registration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reports`
--

DROP TABLE IF EXISTS `reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reports` (
  `reportId` varchar(256) NOT NULL,
  `supplier` varchar(256) DEFAULT NULL,
  `reportType` varchar(45) DEFAULT NULL,
  `issueDate` datetime DEFAULT NULL,
  `reportPdf` longblob,
  PRIMARY KEY (`reportId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reports`
--

LOCK TABLES `reports` WRITE;
/*!40000 ALTER TABLE `reports` DISABLE KEYS */;
/*!40000 ALTER TABLE `reports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier` (
  `supplierId` varchar(256) NOT NULL,
  `supplierName` varchar(256) DEFAULT NULL,
  `homeBranch` enum('NORTH','CENTER','SOUTH','NOT_APPLICABLE') DEFAULT NULL,
  `email` varchar(256) DEFAULT NULL,
  `phoneNumber` varchar(256) DEFAULT NULL,
  `revenueFee` double DEFAULT NULL,
  PRIMARY KEY (`supplierId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` VALUES ('1111','Dominos','CENTER','support@dominos.com','100001111',11.5),('1112','Dominos','NORTH','support@dominos.com','100001111',11.5),('2222','PizzaHut','SOUTH','support@pizzahut.com','100002222',10.2),('2223','PizzaHut','CENTER','support@pizzahut.com','100002222',10.2),('5555','Mcdonalds','NORTH','support@mcdonalds.com','100005555',7.2),('5556','Mcdonalds','CENTER','support@mcdonalds.com','100005555',7.2);
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supplierworker`
--

DROP TABLE IF EXISTS `supplierworker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplierworker` (
  `userID` varchar(256) NOT NULL,
  `statusInSystem` enum('CONFIRMED','PENDING_APPROVAL','FROZEN') DEFAULT NULL,
  `firstName` varchar(256) DEFAULT NULL,
  `lastName` varchar(256) DEFAULT NULL,
  `homeBranch` enum('NORTH','CENTER','SOUTH','NOT_APPLICABLE') DEFAULT 'NOT_APPLICABLE',
  `isLoggedIn` tinyint(1) DEFAULT '0',
  `email` varchar(256) DEFAULT NULL,
  `phoneNumber` varchar(256) DEFAULT NULL,
  `supplierId` varchar(256) DEFAULT NULL,
  `workerPosition` enum('CERTIFIED','REGULAR') DEFAULT NULL,
  PRIMARY KEY (`userID`),
  CONSTRAINT `supplierWorker_userID` FOREIGN KEY (`userID`) REFERENCES `login` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplierworker`
--

LOCK TABLES `supplierworker` WRITE;
/*!40000 ALTER TABLE `supplierworker` DISABLE KEYS */;
/*!40000 ALTER TABLE `supplierworker` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-20 18:12:20
