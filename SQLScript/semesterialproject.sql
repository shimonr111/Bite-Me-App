CREATE DATABASE  IF NOT EXISTS `semesterialproject` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `semesterialproject`;
-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: semesterialproject
-- ------------------------------------------------------
-- Server version	8.0.26

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
-- Table structure for table `balance`
--

DROP TABLE IF EXISTS `balance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `balance` (
  `supplierId` varchar(256) NOT NULL,
  `customerUserId` varchar(256) NOT NULL,
  `balance` double DEFAULT '0',
  PRIMARY KEY (`supplierId`,`customerUserId`),
  KEY `supplierId_idx` (`supplierId`),
  KEY `customerUserId_idx` (`customerUserId`),
  CONSTRAINT `customerUserId` FOREIGN KEY (`customerUserId`) REFERENCES `login` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `supplierId` FOREIGN KEY (`supplierId`) REFERENCES `supplier` (`supplierId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `balance`
--

LOCK TABLES `balance` WRITE;
/*!40000 ALTER TABLE `balance` DISABLE KEYS */;
/*!40000 ALTER TABLE `balance` ENABLE KEYS */;
UNLOCK TABLES;

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
  `companyCode` int DEFAULT NULL,
  `email` varchar(256) DEFAULT NULL,
  `phoneNumber` varchar(256) DEFAULT NULL,
  `privateCreditCard` varchar(256) DEFAULT NULL,
  `companyName` varchar(256) DEFAULT NULL,
  `budgetType` enum('DAILY','WEEKLY','MONTHLY') DEFAULT NULL,
  `budgetMaxAmount` int DEFAULT NULL,
  `privateW4cCodeNumber` int DEFAULT NULL,
  `budgetUsed` double DEFAULT '0',
  PRIMARY KEY (`userID`),
  KEY `businesscustomer_privateCreditCard_idx` (`privateCreditCard`),
  KEY `businesscustomer_companyName_idx` (`companyName`),
  KEY `businesscustomer_businessW4cCodeNumber_idx` (`companyCode`),
  CONSTRAINT `businesscustomer_businessW4cCodeNumber` FOREIGN KEY (`companyCode`) REFERENCES `company` (`companyCode`),
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
  `companyStatusInSystem` enum('CONFIRMED','PENDING_APPROVAL','FROZEN','PENDING_REGISTRATION') DEFAULT NULL,
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
INSERT INTO `company` VALUES ('Apple','CONFIRMED','AppleAddress','apple@apple.com',5002),('Intel','CONFIRMED','IntelAddress','intel@intel.com',5001);
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
INSERT INTO `creditcard` VALUES ('4000','111','11/20');
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
INSERT INTO `customer` VALUES ('1000','CONFIRMED','Ricard','Luis','NORTH',0,31000,'ricard.customer@gmail.com','100000','4000');
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
  `email` varchar(256) DEFAULT NULL,
  `phoneNumber` varchar(256) DEFAULT NULL,
  `companyName` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`userID`),
  KEY `hrmanager_companyName_idx` (`companyName`),
  CONSTRAINT `hrmanager_companyName` FOREIGN KEY (`companyName`) REFERENCES `company` (`companyName`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `hrmanager_userID` FOREIGN KEY (`userID`) REFERENCES `login` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hrmanager`
--

LOCK TABLES `hrmanager` WRITE;
/*!40000 ALTER TABLE `hrmanager` DISABLE KEYS */;
INSERT INTO `hrmanager` VALUES ('1222','CONFIRMED','intelHRfirstName','intelHRlastName','NOT_APPLICABLE',0,'intelhr@intel.com','0101010','Intel'),('1333','CONFIRMED','appleHrfirstName','AppleHrLastName','NOT_APPLICABLE',0,'applhr@apple.com','200001','Apple');
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
INSERT INTO `item_in_menu` VALUES ('Big Mac','5555','MAIN','REGULAR','File:///C:/G3BiteMe/Design/ItemsImageBank/BigMac.jpg',28),('Big Mac','5556','MAIN','REGULAR','File:///C:/G3BiteMe/Design/ItemsImageBank/BigMac.jpg',28),('Ceaser Salad','2222','SALAD','REGULAR','File:///C:/G3BiteMe/Design/ItemsImageBank/pizza-hut-ceaser.jpg',33),('Ceaser Salad','2223','SALAD','REGULAR','File:///C:/G3BiteMe/Design/ItemsImageBank/pizza-hut-ceaser.jpg',25),('Chicken Salad','1111','SALAD','REGULAR','File:///C:/G3BiteMe/Design/ItemsImageBank/dominos-salad.jpg',33),('Chicken Salad','1112','SALAD','REGULAR','File:///C:/G3BiteMe/Design/ItemsImageBank/dominos-salad.jpg',33),('Chicken Wings','1111','FIRST','REGULAR','File:///C:/G3BiteMe/Design/ItemsImageBank/dominos-wings.jpg',25),('Chicken Wings','1112','FIRST','REGULAR','File:///C:/G3BiteMe/Design/ItemsImageBank/dominos-wings.jpg',25),('Chicken Wings','2222','FIRST','REGULAR','File:///C:/G3BiteMe/Design/ItemsImageBank/dominos-wings.jpg',25),('Chicken Wings','2223','FIRST','REGULAR','File:///C:/G3BiteMe/Design/ItemsImageBank/dominos-wings.jpg',25),('Coke','1111','DRINK','LARGE','File:///C:/G3BiteMe/Design/ItemsImageBank/Coca-Cola.jpg',12),('Coke','1112','DRINK','LARGE','File:///C:/G3BiteMe/Design/ItemsImageBank/Coca-Cola.jpg',12),('Coke','2222','DRINK','LARGE','File:///C:/G3BiteMe/Design/ItemsImageBank/Coca-Cola.jpg',12),('Coke','2223','DRINK','LARGE','File:///C:/G3BiteMe/Design/ItemsImageBank/Coca-Cola.jpg',12),('Coke','5555','DRINK','LARGE','File:///C:/G3BiteMe/Design/ItemsImageBank/Coca-Cola.jpg',12),('Coke','5556','DRINK','LARGE','File:///C:/G3BiteMe/Design/ItemsImageBank/Coca-Cola.jpg',12),('Lava Cake','1111','DESSERT','SMALL','File:///C:/G3BiteMe/Design/ItemsImageBank/dominos-cake.jpg',17),('Lava Cake','1112','DESSERT','SMALL','File:///C:/G3BiteMe/Design/ItemsImageBank/dominos-cake.jpg',17),('Lava Cake','2222','DESSERT','SMALL','File:///C:/G3BiteMe/Design/ItemsImageBank/dominos-cake.jpg',17),('Lava Cake','2223','DESSERT','SMALL','File:///C:/G3BiteMe/Design/ItemsImageBank/dominos-cake.jpg',17),('Mac Fries','5555','FIRST','REGULAR','File:///C:/G3BiteMe/Design/ItemsImageBank/Fries.jpg',18),('Mac Fries','5556','FIRST','REGULAR','File:///C:/G3BiteMe/Design/ItemsImageBank/Fries.jpg',18),('Mac Salad','5555','SALAD','REGULAR','File:///C:/G3BiteMe/Design/ItemsImageBank/MacSalad.jpg',25),('Mac Salad','5556','SALAD','REGULAR','File:///C:/G3BiteMe/Design/ItemsImageBank/MacSalad.jpg',25),('Olives','2222','MAIN','LARGE','File:///C:/G3BiteMe/Design/ItemsImageBank/pizza-hut-olives.jpg',38),('Olives','2223','MAIN','LARGE','File:///C:/G3BiteMe/Design/ItemsImageBank/pizza-hut-olives.jpg',55),('Pepperoni','1111','MAIN','LARGE','File:///C:/G3BiteMe/Design/ItemsImageBank/dominos-pepperoni.jpg',55),('Pepperoni','1112','MAIN','LARGE','File:///C:/G3BiteMe/Design/ItemsImageBank/dominos-pepperoni.jpg',55),('Quarter Pounder','5555','MAIN','LARGE','File:///C:/G3BiteMe/Design/ItemsImageBank/QuarterPounder.jpg',45),('Quarter Pounder','5556','MAIN','LARGE','File:///C:/G3BiteMe/Design/ItemsImageBank/QuarterPounder.jpg',45),('Sunday','5555','DESSERT','SMALL','File:///C:/G3BiteMe/Design/ItemsImageBank/Sunday.jpg',10),('Sunday','5556','DESSERT','SMALL','File:///C:/G3BiteMe/Design/ItemsImageBank/Sunday.jpg',10);
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
INSERT INTO `login` VALUES ('cu','cu','1000','customer'),('intelhr','intelhr','1222','hrmanager'),('applehr','applehr','1333','hrmanager'),('phsc','phsc','2000','supplierworker'),('phsr','phsr','2001','supplierworker'),('mnc','mnc','2002','supplierworker'),('phsm','phsm','2003','supplierworker'),('phcc','phcc','2004','supplierworker'),('phcr','phcr','2005','supplierworker'),('phcm','phcm','2006','supplierworker'),('dcc','dcc','2007','supplierworker'),('dcr','dcr','2008','supplierworker'),('dcm','dcm','2009','supplierworker'),('dnc','dnc','2010','supplierworker'),('dnr','dnr','2011','supplierworker'),('dnm','dnm','2012','supplierworker'),('mnr','mnr','2013','supplierworker'),('mnm','mnm','2014','supplierworker'),('mcc','mcc','2015','supplierworker'),('mcr','mcr','2016','supplierworker'),('mcm','mcm','2017','supplierworker');
/*!40000 ALTER TABLE `login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `orderNumber` int NOT NULL AUTO_INCREMENT,
  `supplierId` varchar(256) DEFAULT NULL,
  `customerUserId` varchar(256) DEFAULT NULL,
  `customerUserType` varchar(256) DEFAULT NULL,
  `branch` enum('NORTH','CENTER','SOUTH') DEFAULT NULL,
  `timeType` enum('REGULAR','PRE') DEFAULT NULL,
  `status` enum('PENDING_APPROVAL','APPROVED','UN_APPROVED','COMPLETED') DEFAULT NULL,
  `issueDateTime` datetime DEFAULT NULL,
  `estimatedSupplyDateTime` datetime DEFAULT NULL,
  `actualSupplyDateTime` datetime DEFAULT NULL,
  `supplyType` enum('TAKE_AWAY','DELIVERY') DEFAULT NULL,
  `totalPrice` double DEFAULT NULL,
  `receiverFirstName` varchar(256) DEFAULT NULL,
  `receiverLastName` varchar(256) DEFAULT NULL,
  `receiverAddress` varchar(256) DEFAULT NULL,
  `receiverPhoneNumber` varchar(256) DEFAULT NULL,
  `deliveryFee` double DEFAULT NULL,
  `itemsList` varchar(6000) DEFAULT NULL,
  `comments` varchar(3000) DEFAULT NULL,
  `deliveryType` enum('NA','REGULAR','MULTI','JOIN_MULTI','ROBOTIC') DEFAULT NULL,
  `balanceUsed` double DEFAULT '0',
  `budgetBalanceUsed` double DEFAULT '0',
  `participantsAmount` int DEFAULT '1',
  PRIMARY KEY (`orderNumber`),
  KEY `order_supplierId_idx` (`supplierId`),
  CONSTRAINT `order_supplierId` FOREIGN KEY (`supplierId`) REFERENCES `supplier` (`supplierId`)
) ENGINE=InnoDB AUTO_INCREMENT=180 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (33,'1111','1000','customer','CENTER','REGULAR','COMPLETED','2021-12-02 18:22:00','2021-12-02 19:00:00','2021-12-02 18:45:00','DELIVERY',87,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Salad,Coke,Lava Cake,','null,null,null,','REGULAR',0,0,1),(34,'1111','1000','customer','CENTER','REGULAR','COMPLETED','2021-12-03 18:22:00','2021-12-03 19:00:00','2021-12-03 18:32:00','DELIVERY',105,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Wings,Pepperoni,','null,null,null,','REGULAR',0,0,1),(35,'1111','1000','customer','CENTER','REGULAR','COMPLETED','2021-12-12 18:22:00','2021-12-12 19:00:00','2021-12-12 19:28:00','DELIVERY',87,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Salad,Coke,Lava Cake,','null,null,null,','REGULAR',0,0,1),(36,'1111','1000','customer','CENTER','REGULAR','COMPLETED','2021-12-14 18:22:00','2021-12-14 19:00:00','2021-12-14 18:52:00','DELIVERY',105,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Wings,Pepperoni,','null,null,null,','REGULAR',0,0,1),(37,'1111','1000','customer','CENTER','REGULAR','COMPLETED','2021-12-15 18:22:00','2021-12-15 19:00:00','2021-12-15 18:45:00','DELIVERY',70,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Salad,Coke,','null,null,null,','REGULAR',0,0,1),(38,'1111','1000','customer','CENTER','REGULAR','COMPLETED','2021-12-22 18:22:00','2021-12-22 19:00:00','2021-12-22 19:02:00','DELIVERY',37,'A','M','Haifa Rambam 12','0543333333',25,'Coke,','null,null,null,','REGULAR',0,0,1),(39,'1111','1000','customer','CENTER','REGULAR','COMPLETED','2021-12-02 18:22:00','2021-12-02 19:00:00','2021-12-02 18:45:00','DELIVERY',87,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Salad,Coke,Lava Cake,','null,null,null,','REGULAR',0,0,1),(40,'1112','1000','customer','NORTH','REGULAR','COMPLETED','2021-12-03 18:22:00','2021-12-03 19:00:00','2021-12-03 18:32:00','DELIVERY',105,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Wings,Pepperoni,','null,null,null,','REGULAR',0,0,1),(44,'1112','1000','customer','NORTH','REGULAR','COMPLETED','2021-12-22 18:22:00','2021-12-22 19:00:00','2021-12-22 19:02:00','DELIVERY',37,'A','M','Haifa Rambam 12','0543333333',25,'Coke,','null,null,null,','REGULAR',0,0,1),(45,'2223','1000','customer','CENTER','REGULAR','COMPLETED','2021-12-02 18:22:00','2021-12-02 19:00:00','2021-12-02 18:45:00','DELIVERY',83,'A','M','Haifa Rambam 12','0543333333',25,'Caesar Salad,Chicken Wings,','null,null,null,','REGULAR',0,0,1),(46,'2222','1000','customer','SOUTH','REGULAR','COMPLETED','2021-12-03 18:22:00','2021-12-03 19:00:00','2021-12-03 18:32:00','DELIVERY',92,'A','M','Haifa Rambam 12','0543333333',25,'Coke,Lava Cake,Olives,','null,null,null,','REGULAR',0,0,1),(47,'2222','1000','customer','SOUTH','REGULAR','COMPLETED','2021-12-12 18:22:00','2021-12-12 19:00:00','2021-12-12 19:28:00','DELIVERY',92,'A','M','Haifa Rambam 12','0543333333',25,'Coke,Lava Cake,Olives,','null,null,null,','REGULAR',0,0,1),(50,'2223','1000','customer','CENTER','REGULAR','COMPLETED','2021-12-22 18:22:00','2021-12-22 19:00:00','2021-12-22 19:02:00','DELIVERY',37,'A','M','Haifa Rambam 12','0543333333',25,'Coke,','null,null,null,','REGULAR',0,0,1),(51,'2222','1000','customer','SOUTH','REGULAR','COMPLETED','2021-12-02 18:22:00','2021-12-02 19:00:00','2021-12-02 18:45:00','DELIVERY',87,'A','M','Haifa Rambam 12','0543333333',25,'Caesar Salad,Coke,Lava Cake,','null,null,null,','REGULAR',0,0,1),(52,'5556','1000','customer','CENTER','REGULAR','COMPLETED','2021-12-02 18:22:00','2021-12-02 19:00:00','2021-12-02 18:45:00','DELIVERY',65,'A','M','Haifa Rambam 12','0543333333',25,'Big Mac,Coke,','null,null,null,','REGULAR',0,0,1),(53,'5555','1000','customer','NORTH','REGULAR','COMPLETED','2021-12-03 18:22:00','2021-12-03 19:00:00','2021-12-03 18:32:00','DELIVERY',93,'A','M','Haifa Rambam 12','0543333333',25,'Mac Fries,Mac Salad,Mac Salad,','null,null,null,','REGULAR',0,0,1),(54,'5555','1000','customer','NORTH','REGULAR','COMPLETED','2021-12-12 18:22:00','2021-12-12 19:00:00','2021-12-12 19:28:00','DELIVERY',93,'A','M','Haifa Rambam 12','0543333333',25,'Mac Fries,Mac Salad,Mac Salad,','null,null,null,','REGULAR',0,0,1),(55,'5556','1000','customer','CENTER','REGULAR','COMPLETED','2021-12-14 18:22:00','2021-12-14 19:00:00','2021-12-14 18:52:00','DELIVERY',93,'A','M','Haifa Rambam 12','0543333333',25,'Mac Fries,Mac Salad,Mac Salad,','null,null,null,','REGULAR',0,0,1),(56,'5556','1000','customer','CENTER','REGULAR','COMPLETED','2021-12-15 18:22:00','2021-12-15 19:00:00','2021-12-15 18:45:00','DELIVERY',65,'A','M','Haifa Rambam 12','0543333333',25,'Big Mac,Coke,','null,null,null,','REGULAR',0,0,1),(58,'5555','1000','customer','NORTH','REGULAR','COMPLETED','2021-12-02 18:22:00','2021-12-02 19:00:00','2021-12-02 18:45:00','DELIVERY',53,'A','M','Haifa Rambam 12','0543333333',25,'Big Mac,','null,null,null,','REGULAR',0,0,1),(59,'1111','1000','customer','CENTER','REGULAR','COMPLETED','2021-10-02 18:22:00','2021-10-02 19:00:00','2021-10-02 18:45:00','DELIVERY',87,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Salad,Coke,Lava Cake,','null,null,null,','REGULAR',0,0,1),(60,'1111','1000','customer','CENTER','REGULAR','COMPLETED','2021-10-03 18:22:00','2021-10-03 19:00:00','2021-10-03 18:32:00','DELIVERY',105,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Wings,Pepperoni,','null,null,null,','REGULAR',0,0,1),(61,'1111','1000','customer','CENTER','REGULAR','COMPLETED','2021-10-12 18:22:00','2021-10-12 19:00:00','2021-10-12 19:28:00','DELIVERY',87,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Salad,Coke,Lava Cake,','null,null,null,','REGULAR',0,0,1),(63,'1111','1000','customer','CENTER','REGULAR','COMPLETED','2021-10-15 18:22:00','2021-10-15 19:00:00','2021-10-15 18:45:00','DELIVERY',70,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Salad,Coke,','null,null,null,','REGULAR',0,0,1),(65,'1111','1000','customer','CENTER','REGULAR','COMPLETED','2021-10-02 18:22:00','2021-10-02 19:00:00','2021-10-02 18:45:00','DELIVERY',87,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Salad,Coke,Lava Cake,','null,null,null,','REGULAR',0,0,1),(66,'1112','1000','customer','NORTH','REGULAR','COMPLETED','2021-10-03 18:22:00','2021-10-03 19:00:00','2021-10-03 18:32:00','DELIVERY',105,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Wings,Pepperoni,','null,null,null,','REGULAR',0,0,1),(67,'1112','1000','customer','NORTH','REGULAR','COMPLETED','2021-10-12 18:22:00','2021-10-12 19:00:00','2021-10-12 19:28:00','DELIVERY',87,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Salad,Coke,Lava Cake,','null,null,null,','REGULAR',0,0,1),(68,'1112','1000','customer','NORTH','REGULAR','COMPLETED','2021-10-22 18:22:00','2021-10-22 19:00:00','2021-10-22 19:02:00','DELIVERY',37,'A','M','Haifa Rambam 12','0543333333',25,'Coke,','null,null,null,','REGULAR',0,0,1),(69,'2223','1000','customer','CENTER','REGULAR','COMPLETED','2021-10-02 18:22:00','2021-10-02 19:00:00','2021-10-02 18:45:00','DELIVERY',83,'A','M','Haifa Rambam 12','0543333333',25,'Caesar Salad,Chicken Wings,','null,null,null,','REGULAR',0,0,1),(70,'2222','1000','customer','SOUTH','REGULAR','COMPLETED','2021-10-03 18:22:00','2021-10-03 19:00:00','2021-10-03 18:32:00','DELIVERY',92,'A','M','Haifa Rambam 12','0543333333',25,'Coke,Lava Cake,Olives,','null,null,null,','REGULAR',0,0,1),(71,'2222','1000','customer','SOUTH','REGULAR','COMPLETED','2021-10-12 18:22:00','2021-10-12 19:00:00','2021-10-12 19:28:00','DELIVERY',92,'A','M','Haifa Rambam 12','0543333333',25,'Coke,Lava Cake,Olives,','null,null,null,','REGULAR',0,0,1),(72,'2223','1000','customer','CENTER','REGULAR','COMPLETED','2021-10-14 18:22:00','2021-10-14 19:00:00','2021-10-14 18:52:00','DELIVERY',37,'A','M','Haifa Rambam 12','0543333333',25,'Coke,','null,null,null,','REGULAR',0,0,1),(73,'2223','1000','customer','CENTER','REGULAR','COMPLETED','2021-10-15 18:22:00','2021-10-15 19:00:00','2021-10-15 18:45:00','DELIVERY',83,'A','M','Haifa Rambam 12','0543333333',25,'Caesar Salad,Chicken Wings,','null,null,null,','REGULAR',0,0,1),(74,'2223','1000','customer','CENTER','REGULAR','COMPLETED','2021-10-22 18:22:00','2021-10-22 19:00:00','2021-10-22 19:02:00','DELIVERY',37,'A','M','Haifa Rambam 12','0543333333',25,'Coke,','null,null,null,','REGULAR',0,0,1),(76,'5556','1000','customer','CENTER','REGULAR','COMPLETED','2021-10-02 18:22:00','2021-10-02 19:00:00','2021-10-02 18:45:00','DELIVERY',65,'A','M','Haifa Rambam 12','0543333333',25,'Big Mac,Coke,','null,null,null,','REGULAR',0,0,1),(78,'5555','1000','customer','NORTH','REGULAR','COMPLETED','2021-10-12 18:22:00','2021-10-12 19:00:00','2021-10-12 19:28:00','DELIVERY',93,'A','M','Haifa Rambam 12','0543333333',25,'Mac Fries,Mac Salad,Mac Salad,','null,null,null,','REGULAR',0,0,1),(79,'5556','1000','customer','CENTER','REGULAR','COMPLETED','2021-10-14 18:22:00','2021-10-14 19:00:00','2021-10-14 18:52:00','DELIVERY',93,'A','M','Haifa Rambam 12','0543333333',25,'Mac Fries,Mac Salad,Mac Salad,','null,null,null,','REGULAR',0,0,1),(80,'5556','1000','customer','CENTER','REGULAR','COMPLETED','2021-10-15 18:22:00','2021-10-15 19:00:00','2021-10-15 18:45:00','DELIVERY',65,'A','M','Haifa Rambam 12','0543333333',25,'Big Mac,Coke,','null,null,null,','REGULAR',0,0,1),(81,'5556','1000','customer','CENTER','REGULAR','COMPLETED','2021-10-22 18:22:00','2021-10-22 19:00:00','2021-10-22 19:02:00','DELIVERY',65,'A','M','Haifa Rambam 12','0543333333',25,'Big Mac,Coke,','null,null,null,','REGULAR',0,0,1),(82,'5555','1000','customer','NORTH','REGULAR','COMPLETED','2021-10-02 18:22:00','2021-10-02 19:00:00','2021-10-02 18:45:00','DELIVERY',53,'A','M','Haifa Rambam 12','0543333333',25,'Big Mac,','null,null,null,','REGULAR',0,0,1),(83,'1111','1000','customer','CENTER','REGULAR','COMPLETED','2021-11-02 18:22:00','2021-11-02 19:00:00','2021-11-02 18:45:00','DELIVERY',87,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Salad,Coke,Lava Cake,','null,null,null,','REGULAR',0,0,1),(86,'1111','1000','customer','CENTER','REGULAR','COMPLETED','2021-11-14 18:22:00','2021-11-14 19:00:00','2021-11-14 18:52:00','DELIVERY',105,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Wings,Pepperoni,','null,null,null,','REGULAR',0,0,1),(87,'1111','1000','customer','CENTER','REGULAR','COMPLETED','2021-11-15 18:22:00','2021-11-15 19:00:00','2021-11-15 18:45:00','DELIVERY',70,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Salad,Coke,','null,null,null,','REGULAR',0,0,1),(89,'1111','1000','customer','CENTER','REGULAR','COMPLETED','2021-11-02 18:22:00','2021-11-02 19:00:00','2021-11-02 18:45:00','DELIVERY',87,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Salad,Coke,Lava Cake,','null,null,null,','REGULAR',0,0,1),(90,'1112','1000','customer','NORTH','REGULAR','COMPLETED','2021-11-03 18:22:00','2021-11-03 19:00:00','2021-11-03 18:32:00','DELIVERY',105,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Wings,Pepperoni,','null,null,null,','REGULAR',0,0,1),(91,'1112','1000','customer','NORTH','REGULAR','COMPLETED','2021-11-12 18:22:00','2021-11-12 19:00:00','2021-11-12 19:28:00','DELIVERY',87,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Salad,Coke,Lava Cake,','null,null,null,','REGULAR',0,0,1),(93,'2223','1000','customer','CENTER','REGULAR','COMPLETED','2021-11-02 18:22:00','2021-11-02 19:00:00','2021-11-02 18:45:00','DELIVERY',83,'A','M','Haifa Rambam 12','0543333333',25,'Caesar Salad,Chicken Wings,','null,null,null,','REGULAR',0,0,1),(94,'2222','1000','customer','SOUTH','REGULAR','COMPLETED','2021-11-03 18:22:00','2021-11-03 19:00:00','2021-11-03 18:32:00','DELIVERY',92,'A','M','Haifa Rambam 12','0543333333',25,'Coke,Lava Cake,Olives,','null,null,null,','REGULAR',0,0,1),(95,'2222','1000','customer','SOUTH','REGULAR','COMPLETED','2021-11-12 18:22:00','2021-11-12 19:00:00','2021-11-12 19:28:00','DELIVERY',92,'A','M','Haifa Rambam 12','0543333333',25,'Coke,Lava Cake,Olives,','null,null,null,','REGULAR',0,0,1),(96,'2223','1000','customer','CENTER','REGULAR','COMPLETED','2021-11-14 18:22:00','2021-11-14 19:00:00','2021-11-14 18:52:00','DELIVERY',37,'A','M','Haifa Rambam 12','0543333333',25,'Coke,','null,null,null,','REGULAR',0,0,1),(97,'2223','1000','customer','CENTER','REGULAR','COMPLETED','2021-11-15 18:22:00','2021-11-15 19:00:00','2021-11-15 18:45:00','DELIVERY',83,'A','M','Haifa Rambam 12','0543333333',25,'Caesar Salad,Chicken Wings,','null,null,null,','REGULAR',0,0,1),(98,'2223','1000','customer','CENTER','REGULAR','COMPLETED','2021-11-22 18:22:00','2021-11-22 19:00:00','2021-11-22 19:02:00','DELIVERY',37,'A','M','Haifa Rambam 12','0543333333',25,'Coke,','null,null,null,','REGULAR',0,0,1),(99,'2222','1000','customer','SOUTH','REGULAR','COMPLETED','2021-11-02 18:22:00','2021-11-02 19:00:00','2021-11-02 18:45:00','DELIVERY',87,'A','M','Haifa Rambam 12','0543333333',25,'Caesar Salad,Coke,Lava Cake,','null,null,null,','REGULAR',0,0,1),(100,'5556','1000','customer','CENTER','REGULAR','COMPLETED','2021-11-02 18:22:00','2021-11-02 19:00:00','2021-11-02 18:45:00','DELIVERY',65,'A','M','Haifa Rambam 12','0543333333',25,'Big Mac,Coke,','null,null,null,','REGULAR',0,0,1),(102,'5555','1000','customer','NORTH','REGULAR','COMPLETED','2021-11-12 18:22:00','2021-11-12 19:00:00','2021-11-12 19:28:00','DELIVERY',93,'A','M','Haifa Rambam 12','0543333333',25,'Mac Fries,Mac Salad,Mac Salad,','null,null,null,','REGULAR',0,0,1),(104,'5556','1000','customer','CENTER','REGULAR','COMPLETED','2021-11-15 18:22:00','2021-11-15 19:00:00','2021-11-15 18:45:00','DELIVERY',65,'A','M','Haifa Rambam 12','0543333333',25,'Big Mac,Coke,','null,null,null,','REGULAR',0,0,1),(105,'5556','1000','customer','CENTER','REGULAR','COMPLETED','2021-11-22 18:22:00','2021-11-22 19:00:00','2021-11-22 19:02:00','DELIVERY',65,'A','M','Haifa Rambam 12','0543333333',25,'Big Mac,Coke,','null,null,null,','REGULAR',0,0,1),(106,'5555','1000','customer','NORTH','REGULAR','COMPLETED','2021-11-02 18:22:00','2021-11-02 19:00:00','2021-11-02 18:45:00','DELIVERY',53,'A','M','Haifa Rambam 12','0543333333',25,'Big Mac,','null,null,null,','REGULAR',0,0,1),(108,'1111','1000','customer','CENTER','REGULAR','COMPLETED','2021-09-03 18:22:00','2021-09-03 19:00:00','2021-09-03 18:32:00','DELIVERY',105,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Wings,Pepperoni,','null,null,null,','REGULAR',0,0,1),(109,'1111','1000','customer','CENTER','REGULAR','COMPLETED','2021-09-12 18:22:00','2021-09-12 19:00:00','2021-09-12 19:28:00','DELIVERY',87,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Salad,Coke,Lava Cake,','null,null,null,','REGULAR',0,0,1),(110,'1111','1000','customer','CENTER','REGULAR','COMPLETED','2021-09-14 18:22:00','2021-09-14 19:00:00','2021-09-14 18:52:00','DELIVERY',105,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Wings,Pepperoni,','null,null,null,','REGULAR',0,0,1),(111,'1111','1000','customer','CENTER','REGULAR','COMPLETED','2021-09-15 18:22:00','2021-09-15 19:00:00','2021-09-15 18:45:00','DELIVERY',70,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Salad,Coke,','null,null,null,','REGULAR',0,0,1),(112,'1111','1000','customer','CENTER','REGULAR','COMPLETED','2021-09-22 18:22:00','2021-09-22 19:00:00','2021-09-22 19:02:00','DELIVERY',37,'A','M','Haifa Rambam 12','0543333333',25,'Coke,','null,null,null,','REGULAR',0,0,1),(113,'1111','1000','customer','CENTER','REGULAR','COMPLETED','2021-09-02 18:22:00','2021-09-02 19:00:00','2021-09-02 18:45:00','DELIVERY',87,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Salad,Coke,Lava Cake,','null,null,null,','REGULAR',0,0,1),(114,'1112','1000','customer','NORTH','REGULAR','COMPLETED','2021-09-03 18:22:00','2021-09-03 19:00:00','2021-09-03 18:32:00','DELIVERY',105,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Wings,Pepperoni,','null,null,null,','REGULAR',0,0,1),(115,'1112','1000','customer','NORTH','REGULAR','COMPLETED','2021-09-12 18:22:00','2021-09-12 19:00:00','2021-09-12 19:28:00','DELIVERY',87,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Salad,Coke,Lava Cake,','null,null,null,','REGULAR',0,0,1),(116,'1112','1000','customer','NORTH','REGULAR','COMPLETED','2021-09-22 18:22:00','2021-09-22 19:00:00','2021-09-22 19:02:00','DELIVERY',37,'A','M','Haifa Rambam 12','0543333333',25,'Coke,','null,null,null,','REGULAR',0,0,1),(118,'2222','1000','customer','SOUTH','REGULAR','COMPLETED','2021-09-03 18:22:00','2021-09-03 19:00:00','2021-09-03 18:32:00','DELIVERY',92,'A','M','Haifa Rambam 12','0543333333',25,'Coke,Lava Cake,Olives,','null,null,null,','REGULAR',0,0,1),(119,'2222','1000','customer','SOUTH','REGULAR','COMPLETED','2021-09-12 18:22:00','2021-09-12 19:00:00','2021-09-12 19:28:00','DELIVERY',92,'A','M','Haifa Rambam 12','0543333333',25,'Coke,Lava Cake,Olives,','null,null,null,','REGULAR',0,0,1),(121,'2223','1000','customer','CENTER','REGULAR','COMPLETED','2021-09-15 18:22:00','2021-09-15 19:00:00','2021-09-15 18:45:00','DELIVERY',83,'A','M','Haifa Rambam 12','0543333333',25,'Caesar Salad,Chicken Wings,','null,null,null,','REGULAR',0,0,1),(122,'2223','1000','customer','CENTER','REGULAR','COMPLETED','2021-09-22 18:22:00','2021-09-22 19:00:00','2021-09-22 19:02:00','DELIVERY',37,'A','M','Haifa Rambam 12','0543333333',25,'Coke,','null,null,null,','REGULAR',0,0,1),(123,'2222','1000','customer','SOUTH','REGULAR','COMPLETED','2021-09-02 18:22:00','2021-09-02 19:00:00','2021-09-02 18:45:00','DELIVERY',87,'A','M','Haifa Rambam 12','0543333333',25,'Caesar Salad,Coke,Lava Cake,','null,null,null,','REGULAR',0,0,1),(124,'5556','1000','customer','CENTER','REGULAR','COMPLETED','2021-09-02 18:22:00','2021-09-02 19:00:00','2021-09-02 18:45:00','DELIVERY',65,'A','M','Haifa Rambam 12','0543333333',25,'Big Mac,Coke,','null,null,null,','REGULAR',0,0,1),(125,'5555','1000','customer','NORTH','REGULAR','COMPLETED','2021-09-03 18:22:00','2021-09-03 19:00:00','2021-09-03 18:32:00','DELIVERY',93,'A','M','Haifa Rambam 12','0543333333',25,'Mac Fries,Mac Salad,Mac Salad,','null,null,null,','REGULAR',0,0,1),(126,'5555','1000','customer','NORTH','REGULAR','COMPLETED','2021-09-12 18:22:00','2021-09-12 19:00:00','2021-09-12 19:28:00','DELIVERY',93,'A','M','Haifa Rambam 12','0543333333',25,'Mac Fries,Mac Salad,Mac Salad,','null,null,null,','REGULAR',0,0,1),(127,'5556','1000','customer','CENTER','REGULAR','COMPLETED','2021-09-14 18:22:00','2021-09-14 19:00:00','2021-09-14 18:52:00','DELIVERY',93,'A','M','Haifa Rambam 12','0543333333',25,'Mac Fries,Mac Salad,Mac Salad,','null,null,null,','REGULAR',0,0,1),(128,'5556','1000','customer','CENTER','REGULAR','COMPLETED','2021-09-15 18:22:00','2021-09-15 19:00:00','2021-09-15 18:45:00','DELIVERY',65,'A','M','Haifa Rambam 12','0543333333',25,'Big Mac,Coke,','null,null,null,','REGULAR',0,0,1),(129,'5556','1000','customer','CENTER','REGULAR','COMPLETED','2021-09-22 18:22:00','2021-09-22 19:00:00','2021-09-22 19:02:00','DELIVERY',65,'A','M','Haifa Rambam 12','0543333333',25,'Big Mac,Coke,','null,null,null,','REGULAR',0,0,1),(130,'5555','1000','customer','NORTH','REGULAR','COMPLETED','2021-09-02 18:22:00','2021-09-02 19:00:00','2021-09-02 18:45:00','DELIVERY',53,'A','M','Haifa Rambam 12','0543333333',25,'Big Mac,','null,null,null,','REGULAR',0,0,1),(131,'1111','1000','customer','CENTER','REGULAR','COMPLETED','2021-07-02 18:22:00','2021-07-02 19:00:00','2021-07-02 18:45:00','DELIVERY',87,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Salad,Coke,Lava Cake,','null,null,null,','REGULAR',0,0,1),(132,'1111','1000','customer','CENTER','REGULAR','COMPLETED','2021-07-03 18:22:00','2021-07-03 19:00:00','2021-07-03 18:32:00','DELIVERY',105,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Wings,Pepperoni,','null,null,null,','REGULAR',0,0,1),(133,'1111','1000','customer','CENTER','REGULAR','COMPLETED','2021-07-12 18:22:00','2021-07-12 19:00:00','2021-07-12 19:28:00','DELIVERY',87,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Salad,Coke,Lava Cake,','null,null,null,','REGULAR',0,0,1),(134,'1111','1000','customer','CENTER','REGULAR','COMPLETED','2021-07-14 18:22:00','2021-07-14 19:00:00','2021-07-14 18:52:00','DELIVERY',105,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Wings,Pepperoni,','null,null,null,','REGULAR',0,0,1),(135,'1111','1000','customer','CENTER','REGULAR','COMPLETED','2021-07-15 18:22:00','2021-07-15 19:00:00','2021-07-15 18:45:00','DELIVERY',70,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Salad,Coke,','null,null,null,','REGULAR',0,0,1),(139,'1112','1000','customer','NORTH','REGULAR','COMPLETED','2021-07-12 18:22:00','2021-07-12 19:00:00','2021-07-12 19:28:00','DELIVERY',87,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Salad,Coke,Lava Cake,','null,null,null,','REGULAR',0,0,1),(140,'1112','1000','customer','NORTH','REGULAR','COMPLETED','2021-07-22 18:22:00','2021-07-22 19:00:00','2021-07-22 19:02:00','DELIVERY',37,'A','M','Haifa Rambam 12','0543333333',25,'Coke,','null,null,null,','REGULAR',0,0,1),(141,'2223','1000','customer','CENTER','REGULAR','COMPLETED','2021-07-02 18:22:00','2021-07-02 19:00:00','2021-07-02 18:45:00','DELIVERY',83,'A','M','Haifa Rambam 12','0543333333',25,'Caesar Salad,Chicken Wings,','null,null,null,','REGULAR',0,0,1),(142,'2222','1000','customer','SOUTH','REGULAR','COMPLETED','2021-07-03 18:22:00','2021-07-03 19:00:00','2021-07-03 18:32:00','DELIVERY',92,'A','M','Haifa Rambam 12','0543333333',25,'Coke,Lava Cake,Olives,','null,null,null,','REGULAR',0,0,1),(143,'2222','1000','customer','SOUTH','REGULAR','COMPLETED','2021-07-12 18:22:00','2021-07-12 19:00:00','2021-07-12 19:28:00','DELIVERY',92,'A','M','Haifa Rambam 12','0543333333',25,'Coke,Lava Cake,Olives,','null,null,null,','REGULAR',0,0,1),(144,'2223','1000','customer','CENTER','REGULAR','COMPLETED','2021-07-14 18:22:00','2021-07-14 19:00:00','2021-07-14 18:52:00','DELIVERY',37,'A','M','Haifa Rambam 12','0543333333',25,'Coke,','null,null,null,','REGULAR',0,0,1),(145,'2223','1000','customer','CENTER','REGULAR','COMPLETED','2021-07-15 18:22:00','2021-07-15 19:00:00','2021-07-15 18:45:00','DELIVERY',83,'A','M','Haifa Rambam 12','0543333333',25,'Caesar Salad,Chicken Wings,','null,null,null,','REGULAR',0,0,1),(147,'2222','1000','customer','SOUTH','REGULAR','COMPLETED','2021-07-02 18:22:00','2021-07-02 19:00:00','2021-07-02 18:45:00','DELIVERY',87,'A','M','Haifa Rambam 12','0543333333',25,'Caesar Salad,Coke,Lava Cake,','null,null,null,','REGULAR',0,0,1),(148,'5556','1000','customer','CENTER','REGULAR','COMPLETED','2021-07-02 18:22:00','2021-07-02 19:00:00','2021-07-02 18:45:00','DELIVERY',65,'A','M','Haifa Rambam 12','0543333333',25,'Big Mac,Coke,','null,null,null,','REGULAR',0,0,1),(150,'5555','1000','customer','NORTH','REGULAR','COMPLETED','2021-07-12 18:22:00','2021-07-12 19:00:00','2021-07-12 19:28:00','DELIVERY',93,'A','M','Haifa Rambam 12','0543333333',25,'Mac Fries,Mac Salad,Mac Salad,','null,null,null,','REGULAR',0,0,1),(151,'5556','1000','customer','CENTER','REGULAR','COMPLETED','2021-07-14 18:22:00','2021-07-14 19:00:00','2021-07-14 18:52:00','DELIVERY',93,'A','M','Haifa Rambam 12','0543333333',25,'Mac Fries,Mac Salad,Mac Salad,','null,null,null,','REGULAR',0,0,1),(152,'5556','1000','customer','CENTER','REGULAR','COMPLETED','2021-07-15 18:22:00','2021-07-15 19:00:00','2021-07-15 18:45:00','DELIVERY',65,'A','M','Haifa Rambam 12','0543333333',25,'Big Mac,Coke,','null,null,null,','REGULAR',0,0,1),(154,'5555','1000','customer','NORTH','REGULAR','COMPLETED','2021-07-02 18:22:00','2021-07-02 19:00:00','2021-07-02 18:45:00','DELIVERY',53,'A','M','Haifa Rambam 12','0543333333',25,'Big Mac,','null,null,null,','REGULAR',0,0,1),(155,'1111','1000','customer','CENTER','REGULAR','COMPLETED','2021-08-02 18:22:00','2021-08-02 19:00:00','2021-08-02 18:45:00','DELIVERY',87,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Salad,Coke,Lava Cake,','null,null,null,','REGULAR',0,0,1),(156,'1111','1000','customer','CENTER','REGULAR','COMPLETED','2021-08-03 18:22:00','2021-08-03 19:00:00','2021-08-03 18:32:00','DELIVERY',105,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Wings,Pepperoni,','null,null,null,','REGULAR',0,0,1),(158,'1111','1000','customer','CENTER','REGULAR','COMPLETED','2021-08-14 18:22:00','2021-08-14 19:00:00','2021-08-14 18:52:00','DELIVERY',105,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Wings,Pepperoni,','null,null,null,','REGULAR',0,0,1),(159,'1111','1000','customer','CENTER','REGULAR','COMPLETED','2021-08-15 18:22:00','2021-08-15 19:00:00','2021-08-15 18:45:00','DELIVERY',70,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Salad,Coke,','null,null,null,','REGULAR',0,0,1),(160,'1111','1000','customer','CENTER','REGULAR','COMPLETED','2021-08-22 18:22:00','2021-08-22 19:00:00','2021-08-22 19:02:00','DELIVERY',37,'A','M','Haifa Rambam 12','0543333333',25,'Coke,','null,null,null,','REGULAR',0,0,1),(161,'1111','1000','customer','CENTER','REGULAR','COMPLETED','2021-08-02 18:22:00','2021-08-02 19:00:00','2021-08-02 18:45:00','DELIVERY',87,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Salad,Coke,Lava Cake,','null,null,null,','REGULAR',0,0,1),(162,'1112','1000','customer','NORTH','REGULAR','COMPLETED','2021-08-03 18:22:00','2021-08-03 19:00:00','2021-08-03 18:32:00','DELIVERY',105,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Wings,Pepperoni,','null,null,null,','REGULAR',0,0,1),(163,'1112','1000','customer','NORTH','REGULAR','COMPLETED','2021-08-12 18:22:00','2021-08-12 19:00:00','2021-08-12 19:28:00','DELIVERY',87,'A','M','Haifa Rambam 12','0543333333',25,'Chicken Salad,Coke,Lava Cake,','null,null,null,','REGULAR',0,0,1),(166,'2222','1000','customer','SOUTH','REGULAR','COMPLETED','2021-08-03 18:22:00','2021-08-03 19:00:00','2021-08-03 18:32:00','DELIVERY',92,'A','M','Haifa Rambam 12','0543333333',25,'Coke,Lava Cake,Olives,','null,null,null,','REGULAR',0,0,1),(168,'2222','1000','customer','SOUTH','REGULAR','COMPLETED','2021-08-12 18:22:00','2021-08-12 19:00:00','2021-08-12 19:28:00','DELIVERY',92,'A','M','Haifa Rambam 12','0543333333',25,'Coke,Lava Cake,Olives,','null,null,null,','REGULAR',0,0,1),(169,'2223','1000','customer','CENTER','REGULAR','COMPLETED','2021-08-14 18:22:00','2021-08-14 19:00:00','2021-08-14 18:52:00','DELIVERY',37,'A','M','Haifa Rambam 12','0543333333',25,'Coke,','null,null,null,','REGULAR',0,0,1),(171,'2223','1000','customer','CENTER','REGULAR','COMPLETED','2021-08-22 18:22:00','2021-08-22 19:00:00','2021-08-22 19:02:00','DELIVERY',37,'A','M','Haifa Rambam 12','0543333333',25,'Coke,','null,null,null,','REGULAR',0,0,1),(172,'2222','1000','customer','SOUTH','REGULAR','COMPLETED','2021-08-02 18:22:00','2021-08-02 19:00:00','2021-08-02 18:45:00','DELIVERY',87,'A','M','Haifa Rambam 12','0543333333',25,'Caesar Salad,Coke,Lava Cake,','null,null,null,','REGULAR',0,0,1),(173,'5556','1000','customer','CENTER','REGULAR','COMPLETED','2021-08-02 18:22:00','2021-08-02 19:00:00','2021-08-02 18:45:00','DELIVERY',65,'A','M','Haifa Rambam 12','0543333333',25,'Big Mac,Coke,','null,null,null,','REGULAR',0,0,1),(175,'5555','1000','customer','NORTH','REGULAR','COMPLETED','2021-08-12 18:22:00','2021-08-12 19:00:00','2021-08-12 19:28:00','DELIVERY',93,'A','M','Haifa Rambam 12','0543333333',25,'Mac Fries,Mac Salad,Mac Salad,','null,null,null,','REGULAR',0,0,1),(177,'5556','1000','customer','CENTER','REGULAR','COMPLETED','2021-08-15 18:22:00','2021-08-15 19:00:00','2021-08-15 18:45:00','DELIVERY',65,'A','M','Haifa Rambam 12','0543333333',25,'Big Mac,Coke,','null,null,null,','REGULAR',0,0,1),(178,'5556','1000','customer','CENTER','REGULAR','COMPLETED','2021-08-22 18:22:00','2021-08-22 19:00:00','2021-08-22 19:02:00','DELIVERY',65,'A','M','Haifa Rambam 12','0543333333',25,'Big Mac,Coke,','null,null,null,','REGULAR',0,0,1),(179,'5555','1000','customer','NORTH','REGULAR','COMPLETED','2021-08-02 18:22:00','2021-08-02 19:00:00','2021-08-02 18:45:00','DELIVERY',53,'A','M','Haifa Rambam 12','0543333333',25,'Big Mac,','null,null,null,','REGULAR',0,0,1);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quarterlypdf`
--

DROP TABLE IF EXISTS `quarterlypdf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quarterlypdf` (
  `pdfId` int NOT NULL AUTO_INCREMENT,
  `filename` varchar(256) DEFAULT NULL,
  `dateuploaded` datetime DEFAULT CURRENT_TIMESTAMP,
  `homeBranch` enum('NORTH','CENTER','SOUTH','NOT_APPLICABLE') DEFAULT NULL,
  `pdffile` longblob,
  PRIMARY KEY (`pdfId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quarterlypdf`
--

LOCK TABLES `quarterlypdf` WRITE;
/*!40000 ALTER TABLE `quarterlypdf` DISABLE KEYS */;
/*!40000 ALTER TABLE `quarterlypdf` ENABLE KEYS */;
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
  `companyName` varchar(256) DEFAULT NULL,
  `companyCode` int DEFAULT NULL,
  `revenueFee` double DEFAULT NULL,
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
  `reportId` int NOT NULL AUTO_INCREMENT,
  `supplier` varchar(256) DEFAULT NULL,
  `reportType` varchar(45) DEFAULT NULL,
  `homeBranch` enum('NORTH','CENTER','SOUTH','NOT_APPLICABLE') DEFAULT NULL,
  `issueDate` datetime DEFAULT NULL,
  `report` longblob,
  PRIMARY KEY (`reportId`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reports`
--

LOCK TABLES `reports` WRITE;
/*!40000 ALTER TABLE `reports` DISABLE KEYS */;
INSERT INTO `reports` VALUES (31,'1111','monthly','CENTER','2021-07-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0t\000:30:24sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	Lava Cakexsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0\rChicken Wingsxt\0454.0t\0\n2021-07-01sq\0~\0\0\0\0w\0\0\0t\0	Pepperonixt\0monthlysq\0~\0\0\0\0w\0\0\0t\0\rChicken Saladxt\0CENTERsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@333333t\01111t\0Domino\'s Pizzaur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(32,'1112','monthly','NORTH','2021-07-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0t\000:53:00sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	Lava Cakexsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0\rChicken Wingsxt\0124.0t\0\n2021-07-01sq\0~\0\0\0\0w\0\0\0t\0	Pepperonixt\0monthlysq\0~\0\0\0\0w\0\0\0t\0\rChicken Saladxt\0NORTHsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@#ôôôôôöt\01112t\0Domino\'s Pizzaur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(33,'2222','monthly','SOUTH','2021-07-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0t\000:33:00sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	Lava Cakexsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0\rChicken Wingsxt\0271.0t\0\n2021-07-01sq\0~\0\0\0\0w\0\0\0t\0Olivesxt\0monthlysq\0~\0\0\0\0w\0\0\0t\0Ceaser Saladxt\0SOUTHsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@$\0\0\0\0\0\0t\02222t\0	Pizza Hutur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(34,'2223','monthly','CENTER','2021-07-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0\0t\000:25:20sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	Lava Cakexsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0\rChicken Wingsxt\0203.0t\0\n2021-07-01sq\0~\0\0\0\0w\0\0\0t\0Olivesxt\0monthlysq\0~\0\0\0\0w\0\0\0t\0Ceaser Saladxt\0CENTERsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@(\0\0\0\0\0\0t\02223t\0	Pizza Hutur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(35,'5555','monthly','NORTH','2021-07-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0t\000:44:30sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0Sundayxsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0	Mac Friesxt\0146.0t\0\n2021-07-01sq\0~\0\0\0\0w\0\0\0t\0Big Mact\0Quarter Pounderxt\0monthlysq\0~\0\0\0\0w\0\0\0t\0	Mac Saladxt\0NORTHsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@&ôôôôôöt\05555t\0\nMcDonald\'sur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(36,'5556','monthly','CENTER','2021-07-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0\0t\000:25:20sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0Sundayxsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0	Mac Friesxt\0223.0t\0\n2021-07-01sq\0~\0\0\0\0w\0\0\0t\0Big Mact\0Quarter Pounderxt\0monthlysq\0~\0\0\0\0w\0\0\0t\0	Mac Saladxt\0CENTERsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@\Ã\Ã\Ã\Ã\Ã\Õt\05556t\0\nMcDonald\'sur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(37,'1111','monthly','CENTER','2021-08-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0\0t\000:24:50sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	Lava Cakexsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0\rChicken Wingsxt\0491.0t\0\n2021-08-01sq\0~\0\0\0\0w\0\0\0t\0	Pepperonixt\0monthlysq\0~\0\0\0\0w\0\0\0t\0\rChicken Saladxt\0CENTERsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@333333t\01111t\0Domino\'s Pizzaur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(38,'1112','monthly','NORTH','2021-08-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0t\000:38:00sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	Lava Cakexsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0\rChicken Wingsxt\0192.0t\0\n2021-08-01sq\0~\0\0\0\0w\0\0\0t\0	Pepperonixt\0monthlysq\0~\0\0\0\0w\0\0\0t\0\rChicken Saladxt\0NORTHsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@#ôôôôôöt\01112t\0Domino\'s Pizzaur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(39,'2222','monthly','SOUTH','2021-08-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0t\000:33:00sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	Lava Cakexsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0\rChicken Wingsxt\0271.0t\0\n2021-08-01sq\0~\0\0\0\0w\0\0\0t\0Olivesxt\0monthlysq\0~\0\0\0\0w\0\0\0t\0Ceaser Saladxt\0SOUTHsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@$\0\0\0\0\0\0t\02222t\0	Pizza Hutur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(40,'2223','monthly','CENTER','2021-08-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0\0t\000:35:00sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	Lava Cakexsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0\rChicken Wingsxt\074.0t\0\n2021-08-01sq\0~\0\0\0\0w\0\0\0t\0Olivesxt\0monthlysq\0~\0\0\0\0w\0\0\0t\0Ceaser Saladxt\0CENTERsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@(\0\0\0\0\0\0t\02223t\0	Pizza Hutur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(41,'5555','monthly','NORTH','2021-08-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0t\000:44:30sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0Sundayxsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0	Mac Friesxt\0146.0t\0\n2021-08-01sq\0~\0\0\0\0w\0\0\0t\0Big Mact\0Quarter Pounderxt\0monthlysq\0~\0\0\0\0w\0\0\0t\0	Mac Saladxt\0NORTHsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@&ôôôôôöt\05555t\0\nMcDonald\'sur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(42,'5556','monthly','CENTER','2021-08-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0\0t\000:28:40sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0Sundayxsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0	Mac Friesxt\0195.0t\0\n2021-08-01sq\0~\0\0\0\0w\0\0\0t\0Big Mact\0Quarter Pounderxt\0monthlysq\0~\0\0\0\0w\0\0\0t\0	Mac Saladxt\0CENTERsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@\Ã\Ã\Ã\Ã\Ã\Õt\05556t\0\nMcDonald\'sur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(43,'1111','monthly','CENTER','2021-09-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0t\000:32:00sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	Lava Cakexsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0\rChicken Wingsxt\0491.0t\0\n2021-09-01sq\0~\0\0\0\0w\0\0\0t\0	Pepperonixt\0monthlysq\0~\0\0\0\0w\0\0\0t\0\rChicken Saladxt\0CENTERsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@333333t\01111t\0Domino\'s Pizzaur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(44,'1112','monthly','NORTH','2021-09-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0t\000:38:40sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	Lava Cakexsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0\rChicken Wingsxt\0229.0t\0\n2021-09-01sq\0~\0\0\0\0w\0\0\0t\0	Pepperonixt\0monthlysq\0~\0\0\0\0w\0\0\0t\0\rChicken Saladxt\0NORTHsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@#ôôôôôöt\01112t\0Domino\'s Pizzaur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(45,'2222','monthly','SOUTH','2021-09-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0t\000:33:00sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	Lava Cakexsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0\rChicken Wingsxt\0271.0t\0\n2021-09-01sq\0~\0\0\0\0w\0\0\0t\0Olivesxt\0monthlysq\0~\0\0\0\0w\0\0\0t\0Ceaser Saladxt\0SOUTHsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@$\0\0\0\0\0\0t\02222t\0	Pizza Hutur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(46,'2223','monthly','CENTER','2021-09-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0\0t\000:31:30sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	Lava Cakexsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0\rChicken Wingsxt\0120.0t\0\n2021-09-01sq\0~\0\0\0\0w\0\0\0t\0Olivesxt\0monthlysq\0~\0\0\0\0w\0\0\0t\0Ceaser Saladxt\0CENTERsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@(\0\0\0\0\0\0t\02223t\0	Pizza Hutur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(47,'5555','monthly','NORTH','2021-09-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0t\000:33:00sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0Sundayxsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0	Mac Friesxt\0239.0t\0\n2021-09-01sq\0~\0\0\0\0w\0\0\0t\0Big Mact\0Quarter Pounderxt\0monthlysq\0~\0\0\0\0w\0\0\0t\0	Mac Saladxt\0NORTHsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@&ôôôôôöt\05555t\0\nMcDonald\'sur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(48,'5556','monthly','CENTER','2021-09-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0\0t\000:29:00sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0Sundayxsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0	Mac Friesxt\0288.0t\0\n2021-09-01sq\0~\0\0\0\0w\0\0\0t\0Big Mact\0Quarter Pounderxt\0monthlysq\0~\0\0\0\0w\0\0\0t\0	Mac Saladxt\0CENTERsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@\Ã\Ã\Ã\Ã\Ã\Õt\05556t\0\nMcDonald\'sur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(49,'1111','monthly','CENTER','2021-10-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0t\000:29:00sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	Lava Cakexsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0\rChicken Wingsxt\0436.0t\0\n2021-10-01sq\0~\0\0\0\0w\0\0\0t\0	Pepperonixt\0monthlysq\0~\0\0\0\0w\0\0\0t\0\rChicken Saladxt\0CENTERsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@333333t\01111t\0Domino\'s Pizzaur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(50,'1112','monthly','NORTH','2021-10-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0t\000:38:40sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	Lava Cakexsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0\rChicken Wingsxt\0229.0t\0\n2021-10-01sq\0~\0\0\0\0w\0\0\0t\0	Pepperonixt\0monthlysq\0~\0\0\0\0w\0\0\0t\0\rChicken Saladxt\0NORTHsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@#ôôôôôöt\01112t\0Domino\'s Pizzaur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(51,'2222','monthly','SOUTH','2021-10-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0t\000:38:00sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	Lava Cakexsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0\rChicken Wingsxt\0184.0t\0\n2021-10-01sq\0~\0\0\0\0w\0\0\0t\0Olivesxt\0monthlysq\0~\0\0\0\0w\0\0\0t\0Ceaser Saladxt\0SOUTHsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@$\0\0\0\0\0\0t\02222t\0	Pizza Hutur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(52,'2223','monthly','CENTER','2021-10-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0\0t\000:29:00sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	Lava Cakexsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0\rChicken Wingsxt\0240.0t\0\n2021-10-01sq\0~\0\0\0\0w\0\0\0t\0Olivesxt\0monthlysq\0~\0\0\0\0w\0\0\0t\0Ceaser Saladxt\0CENTERsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@(\0\0\0\0\0\0t\02223t\0	Pizza Hutur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(53,'5555','monthly','NORTH','2021-10-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0t\000:44:30sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0Sundayxsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0	Mac Friesxt\0146.0t\0\n2021-10-01sq\0~\0\0\0\0w\0\0\0t\0Big Mact\0Quarter Pounderxt\0monthlysq\0~\0\0\0\0w\0\0\0t\0	Mac Saladxt\0NORTHsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@&ôôôôôöt\05555t\0\nMcDonald\'sur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(54,'5556','monthly','CENTER','2021-10-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0\0t\000:29:00sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0Sundayxsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0	Mac Friesxt\0288.0t\0\n2021-10-01sq\0~\0\0\0\0w\0\0\0t\0Big Mact\0Quarter Pounderxt\0monthlysq\0~\0\0\0\0w\0\0\0t\0	Mac Saladxt\0CENTERsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@\Ã\Ã\Ã\Ã\Ã\Õt\05556t\0\nMcDonald\'sur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(55,'1111','monthly','CENTER','2021-11-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0\0t\000:24:45sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	Lava Cakexsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0\rChicken Wingsxt\0349.0t\0\n2021-11-01sq\0~\0\0\0\0w\0\0\0t\0	Pepperonixt\0monthlysq\0~\0\0\0\0w\0\0\0t\0\rChicken Saladxt\0CENTERsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@333333t\01111t\0Domino\'s Pizzaur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(56,'1112','monthly','NORTH','2021-11-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0t\000:38:00sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	Lava Cakexsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0\rChicken Wingsxt\0192.0t\0\n2021-11-01sq\0~\0\0\0\0w\0\0\0t\0	Pepperonixt\0monthlysq\0~\0\0\0\0w\0\0\0t\0\rChicken Saladxt\0NORTHsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@#ôôôôôöt\01112t\0Domino\'s Pizzaur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(57,'2222','monthly','SOUTH','2021-11-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0t\000:33:00sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	Lava Cakexsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0\rChicken Wingsxt\0271.0t\0\n2021-11-01sq\0~\0\0\0\0w\0\0\0t\0Olivesxt\0monthlysq\0~\0\0\0\0w\0\0\0t\0Ceaser Saladxt\0SOUTHsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@$\0\0\0\0\0\0t\02222t\0	Pizza Hutur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(58,'2223','monthly','CENTER','2021-11-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0\0t\000:29:00sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	Lava Cakexsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0\rChicken Wingsxt\0240.0t\0\n2021-11-01sq\0~\0\0\0\0w\0\0\0t\0Olivesxt\0monthlysq\0~\0\0\0\0w\0\0\0t\0Ceaser Saladxt\0CENTERsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@(\0\0\0\0\0\0t\02223t\0	Pizza Hutur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(59,'5555','monthly','NORTH','2021-11-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0t\000:44:30sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0Sundayxsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0	Mac Friesxt\0146.0t\0\n2021-11-01sq\0~\0\0\0\0w\0\0\0t\0Big Mact\0Quarter Pounderxt\0monthlysq\0~\0\0\0\0w\0\0\0t\0	Mac Saladxt\0NORTHsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@&ôôôôôöt\05555t\0\nMcDonald\'sur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(60,'5556','monthly','CENTER','2021-11-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0\0t\000:28:40sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0Sundayxsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0	Mac Friesxt\0195.0t\0\n2021-11-01sq\0~\0\0\0\0w\0\0\0t\0Big Mact\0Quarter Pounderxt\0monthlysq\0~\0\0\0\0w\0\0\0t\0	Mac Saladxt\0CENTERsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@\Ã\Ã\Ã\Ã\Ã\Õt\05556t\0\nMcDonald\'sur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(61,'1111','monthly','CENTER','2021-12-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0t\000:30:42sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	Lava Cakexsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0\rChicken Wingsxt\0578.0t\0\n2021-12-01sq\0~\0\0\0\0w\0\0\0t\0	Pepperonixt\0monthlysq\0~\0\0\0\0w\0\0\0t\0\rChicken Saladxt\0CENTERsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@333333t\01111t\0Domino\'s Pizzaur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(62,'1112','monthly','NORTH','2021-12-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0\0t\000:25:00sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	Lava Cakexsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0\rChicken Wingsxt\0142.0t\0\n2021-12-01sq\0~\0\0\0\0w\0\0\0t\0	Pepperonixt\0monthlysq\0~\0\0\0\0w\0\0\0t\0\rChicken Saladxt\0NORTHsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@#ôôôôôöt\01112t\0Domino\'s Pizzaur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(63,'2222','monthly','SOUTH','2021-12-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0t\000:33:00sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	Lava Cakexsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0\rChicken Wingsxt\0271.0t\0\n2021-12-01sq\0~\0\0\0\0w\0\0\0t\0Olivesxt\0monthlysq\0~\0\0\0\0w\0\0\0t\0Ceaser Saladxt\0SOUTHsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@$\0\0\0\0\0\0t\02222t\0	Pizza Hutur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(64,'2223','monthly','CENTER','2021-12-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0\0t\000:31:30sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	Lava Cakexsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0\rChicken Wingsxt\0120.0t\0\n2021-12-01sq\0~\0\0\0\0w\0\0\0t\0Olivesxt\0monthlysq\0~\0\0\0\0w\0\0\0t\0Ceaser Saladxt\0CENTERsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@(\0\0\0\0\0\0t\02223t\0	Pizza Hutur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(65,'5555','monthly','NORTH','2021-12-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0t\000:33:00sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0Sundayxsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0	Mac Friesxt\0239.0t\0\n2021-12-01sq\0~\0\0\0\0w\0\0\0t\0Big Mact\0Quarter Pounderxt\0monthlysq\0~\0\0\0\0w\0\0\0t\0	Mac Saladxt\0NORTHsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@&ôôôôôöt\05555t\0\nMcDonald\'sur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(66,'5556','monthly','CENTER','2021-12-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0\0t\000:25:20sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0Sundayxsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0	Mac Friesxt\0223.0t\0\n2021-12-01sq\0~\0\0\0\0w\0\0\0t\0Big Mact\0Quarter Pounderxt\0monthlysq\0~\0\0\0\0w\0\0\0t\0	Mac Saladxt\0CENTERsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@\Ã\Ã\Ã\Ã\Ã\Õt\05556t\0\nMcDonald\'sur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(67,'1111','quarterly','CENTER','2021-10-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0t\000:28:41sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	Lava Cakexsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0\rChicken Wingsxt\01363.0t\0\n2021-10-01sq\0~\0\0\0\0w\0\0\0t\0	Pepperonixt\0	quarterlysq\0~\0\0\0\0w\0\0\0t\0\rChicken Saladxt\0CENTERsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@333333t\01111t\0Domino\'s Pizzaur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(68,'1112','quarterly','NORTH','2021-10-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0t\000:34:34sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	Lava Cakexsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0\rChicken Wingsxt\0563.0t\0\n2021-10-01sq\0~\0\0\0\0w\0\0\0t\0	Pepperonixt\0	quarterlysq\0~\0\0\0\0w\0\0\0t\0\rChicken Saladxt\0NORTHsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@#ôôôôôöt\01112t\0Domino\'s Pizzaur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(69,'2222','quarterly','SOUTH','2021-10-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0t\000:34:15sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	Lava Cakexsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0\rChicken Wingsxt\0726.0t\0\n2021-10-01sq\0~\0\0\0\0w\0\0\0t\0Olivesxt\0	quarterlysq\0~\0\0\0\0w\0\0\0t\0Ceaser Saladxt\0SOUTHsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@$\0\0\0\0\0\0t\02222t\0	Pizza Hutur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(70,'2223','quarterly','CENTER','2021-10-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0\0\nt\000:29:30sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	Lava Cakexsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0\rChicken Wingsxt\0600.0t\0\n2021-10-01sq\0~\0\0\0\0w\0\0\0t\0Olivesxt\0	quarterlysq\0~\0\0\0\0w\0\0\0t\0Ceaser Saladxt\0CENTERsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@(\0\0\0\0\0\0t\02223t\0	Pizza Hutur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(71,'5555','quarterly','NORTH','2021-10-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0t\000:39:34sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0Sundayxsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0	Mac Friesxt\0531.0t\0\n2021-10-01sq\0~\0\0\0\0w\0\0\0t\0Big Mact\0Quarter Pounderxt\0	quarterlysq\0~\0\0\0\0w\0\0\0t\0	Mac Saladxt\0NORTHsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@&ôôôôôöt\05555t\0\nMcDonald\'sur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(72,'5556','quarterly','CENTER','2021-10-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0\0\nt\000:27:48sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0Sundayxsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0	Mac Friesxt\0706.0t\0\n2021-10-01sq\0~\0\0\0\0w\0\0\0t\0Big Mact\0Quarter Pounderxt\0	quarterlysq\0~\0\0\0\0w\0\0\0t\0	Mac Saladxt\0CENTERsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@\Ã\Ã\Ã\Ã\Ã\Õt\05556t\0\nMcDonald\'sur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(73,'1111','quarterly','CENTER','2021-07-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0t\000:29:00sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	Lava Cakexsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0\rChicken Wingsxt\01436.0t\0\n2021-07-01sq\0~\0\0\0\0w\0\0\0t\0	Pepperonixt\0	quarterlysq\0~\0\0\0\0w\0\0\0t\0\rChicken Saladxt\0CENTERsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@333333t\01111t\0Domino\'s Pizzaur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0	\0\0\0\0\0\0\0\0\0\0\0\0'),(74,'1112','quarterly','NORTH','2021-07-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0t\000:42:34sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	Lava Cakexsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0\rChicken Wingsxt\0545.0t\0\n2021-07-01sq\0~\0\0\0\0w\0\0\0t\0	Pepperonixt\0	quarterlysq\0~\0\0\0\0w\0\0\0t\0\rChicken Saladxt\0NORTHsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@#ôôôôôöt\01112t\0Domino\'s Pizzaur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(75,'2222','quarterly','SOUTH','2021-07-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0	t\000:33:00sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	Lava Cakexsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0\rChicken Wingsxt\0813.0t\0\n2021-07-01sq\0~\0\0\0\0w\0\0\0t\0Olivesxt\0	quarterlysq\0~\0\0\0\0w\0\0\0t\0Ceaser Saladxt\0SOUTHsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@$\0\0\0\0\0\0t\02222t\0	Pizza Hutur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0	\0\0\0	'),(76,'2223','quarterly','CENTER','2021-07-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0\0t\000:29:51sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	Lava Cakexsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0\rChicken Wingsxt\0397.0t\0\n2021-07-01sq\0~\0\0\0\0w\0\0\0t\0Olivesxt\0	quarterlysq\0~\0\0\0\0w\0\0\0t\0Ceaser Saladxt\0CENTERsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@(\0\0\0\0\0\0t\02223t\0	Pizza Hutur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(77,'5555','quarterly','NORTH','2021-07-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0t\000:39:34sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0Sundayxsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0	Mac Friesxt\0531.0t\0\n2021-07-01sq\0~\0\0\0\0w\0\0\0t\0Big Mact\0Quarter Pounderxt\0	quarterlysq\0~\0\0\0\0w\0\0\0t\0	Mac Saladxt\0NORTHsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@&ôôôôôöt\05555t\0\nMcDonald\'sur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(78,'5556','quarterly','CENTER','2021-07-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportÇ\»\ÕDªs\œ\‰\0I\0\nlateOrdersI\0totalOrdersL\0averageSupplyTimet\0Ljava/lang/String;L\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomeq\0~\0L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0supplierFeet\0Ljava/lang/Double;L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0\0\nt\000:27:48sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0Sundayxsq\0~\0\0\0\0w\0\0\0t\0Cokexsq\0~\0\0\0\0w\0\0\0t\0	Mac Friesxt\0706.0t\0\n2021-07-01sq\0~\0\0\0\0w\0\0\0t\0Big Mact\0Quarter Pounderxt\0	quarterlysq\0~\0\0\0\0w\0\0\0t\0	Mac Saladxt\0CENTERsr\0java.lang.DoubleÄ≥\¬J)k˚\0D\0valuexr\0java.lang.NumberÜ¨ïî\‡ã\0\0xp@\Ã\Ã\Ã\Ã\Ã\Õt\05556t\0\nMcDonald\'sur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0');
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
  `statusInSystem` enum('CONFIRMED','PENDING_APPROVAL','FROZEN','PENDING_REGISTRATION') DEFAULT 'PENDING_REGISTRATION',
  PRIMARY KEY (`supplierId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` VALUES ('1111','Domino\'s Pizza','CENTER','support@dominos.com','100001111',7.8,'CONFIRMED'),('1112','Domino\'s Pizza','NORTH','support@dominos.com','100001111',9.8,'CONFIRMED'),('2222','Pizza Hut','SOUTH','support@pizzahut.com','100002222',10,'CONFIRMED'),('2223','Pizza Hut','CENTER','support@pizzahut.com','100002222',12,'CONFIRMED'),('5555','McDonald\'s','NORTH','support@mcdonalds.com','100005555',11.3,'CONFIRMED'),('5556','McDonald\'s','CENTER','support@mcdonalds.com','100005555',7.2,'CONFIRMED');
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
  `workerPosition` enum('CERTIFIED','REGULAR','MANAGER') DEFAULT NULL,
  PRIMARY KEY (`userID`),
  CONSTRAINT `supplierWorker_userID` FOREIGN KEY (`userID`) REFERENCES `login` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplierworker`
--

LOCK TABLES `supplierworker` WRITE;
/*!40000 ALTER TABLE `supplierworker` DISABLE KEYS */;
INSERT INTO `supplierworker` VALUES ('2000','CONFIRMED','phSouthCert','phSouthCert','SOUTH',0,'phSouthCert@pizzahut.com','0525779007','2222','CERTIFIED'),('2001','CONFIRMED','phSouthReg','phSouthReg','SOUTH',0,'phSouthReg@pizzahut.com','0525779007','2222','REGULAR'),('2002','CONFIRMED','mNorthCert','mNorthCert','NORTH',0,'mNorthCert@mac.com','0525779007','5555','CERTIFIED'),('2003','CONFIRMED','phSouthMan','phSouthMan','SOUTH',0,'phSouthMan@pizzahut.com','0525779007','2222','MANAGER'),('2004','CONFIRMED','phCenterCert','phCenterCert','CENTER',0,'phCenterCert@pizzahut.com','0525779007','2223','CERTIFIED'),('2005','CONFIRMED','phCenterReg','phCenterReg','CENTER',0,'phCenterReg@pizzahut.com','0525779007','2223','REGULAR'),('2006','CONFIRMED','phCenterMan','phCenterMan','CENTER',0,'phCenterMan@piizahut.com','0525779007','2223','MANAGER'),('2007','CONFIRMED','dCenterCert','dCenterCert','CENTER',0,'dCenterCert@dominos.com','0525779007','1111','CERTIFIED'),('2008','CONFIRMED','dCenterReg','dCenterReg','CENTER',0,'dCenterReg@dominos.com','0525779007','1111','REGULAR'),('2009','CONFIRMED','dCenterMan','dCenterMan','CENTER',0,'dCenterMan@dominos.com','0525779007','1111','MANAGER'),('2010','CONFIRMED','dNorthCert','dNorthCert','NORTH',0,'dNorthCert@dominos.com','0525779007','1112','CERTIFIED'),('2011','CONFIRMED','dNorthReg','dNorthReg','NORTH',0,'dNorthReg@dominos.com','0525779007','1112','REGULAR'),('2012','CONFIRMED','dNorthMan','dNorthMan','NORTH',0,'dNorthMan@dominos.com','0525779007','1112','MANAGER'),('2013','CONFIRMED','mNorthReg','mNorthReg','NORTH',0,'mNorthReg@mac.com','0525779007','5555','REGULAR'),('2014','CONFIRMED','mNorthMan','mNorthMan','NORTH',0,'mNorthMan@mac.com','0525779007','5555','MANAGER'),('2015','CONFIRMED','mCenterCert','mCenterCert','CENTER',0,'mCenterCert@mac.com','0525779007','5556','CERTIFIED'),('2016','CONFIRMED','mCenterReg','mCenterReg','CENTER',0,'mCenterReg@mac.com','0525779007','5556','REGULAR'),('2017','CONFIRMED','mCenterMan','mCenterMan','CENTER',0,'mCenterMan@mac.com','0525779007','5556','MANAGER');
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

-- Dump completed on 2022-01-04 19:25:35
