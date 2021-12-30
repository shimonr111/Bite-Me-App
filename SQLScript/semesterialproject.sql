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
  `businessW4cCodeNumber` int DEFAULT NULL,
  `email` varchar(256) DEFAULT NULL,
  `phoneNumber` varchar(256) DEFAULT NULL,
  `privateCreditCard` varchar(256) DEFAULT NULL,
  `balance` double DEFAULT '0',
  `companyName` varchar(256) DEFAULT NULL,
  `budgetType` enum('DAILY','WEEKLY','MONTHLY') DEFAULT NULL,
  `budgetMaxAmount` int DEFAULT NULL,
  `privateW4cCodeNumber` int DEFAULT NULL,
  `budgetUsed` double DEFAULT '0',
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
INSERT INTO `businesscustomer` VALUES ('1002','PENDING_APPROVAL','hrmanagerFirstname','hrmanagerLastname','NORTH',0,5001,'hrmanagerEmail@Intel.com','10022','3002',0,'Intel','WEEKLY',200,31062,0);
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
INSERT INTO `creditcard` VALUES ('1000','01/35','111'),('1001','01/35','111'),('3000','01/35','111'),('3002','01/35','111'),('3005','111','01/35'),('3006','111','01/35');
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
INSERT INTO `customer` VALUES ('1000','CONFIRMED','customerFirstname','customerLastname','NORTH',0,31000,'customerEmail@gmeel.com','100000','3000',0);
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
INSERT INTO `login` VALUES ('cu','cu','1000','customer'),('bc','bc','1002','businesscustomer'),('intelhr','intelhr','1222','hrmanager'),('applehr','applehr','1333','hrmanager'),('phsw','phsw','2000','supplierworker'),('phsrw','phsrw','2001','supplierworker'),('mnw','mnw','2002','supplierworker');
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
  `deliveryType` enum('NA','REGULAR','MULTI','ROBOTIC') DEFAULT NULL,
  `balanceUsed` double DEFAULT '0',
  `budgetBalanceUsed` double DEFAULT '0',
  PRIMARY KEY (`orderNumber`),
  KEY `order_supplierId_idx` (`supplierId`),
  CONSTRAINT `order_supplierId` FOREIGN KEY (`supplierId`) REFERENCES `supplier` (`supplierId`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (13,'1112','1000','customer','NORTH','PRE','PENDING_APPROVAL','2021-12-22 18:57:24','2021-12-22 22:00:00',NULL,'DELIVERY',58,'hfg','fgdh','dfgs','456',25,'caesar,fries,','null,null,','REGULAR',0,0),(14,'1112','1002','businesscustomer','NORTH','REGULAR','PENDING_APPROVAL','2021-12-22 19:31:08','2021-12-22 20:00:00',NULL,'DELIVERY',49,'dfsga','sdgf','xcvz','345',25,'ice cream,cola,','null,null,','REGULAR',0,0),(15,'5555','1000','customer','NORTH','REGULAR','PENDING_APPROVAL','2021-12-24 15:53:36','2021-12-24 17:00:00',NULL,'TAKE_AWAY',81,'Test','test2','','1122',0,'burger,cola,cola,pie,','null,null,null,null,','NA',0,0),(16,'5555','1000','customer','NORTH','REGULAR','PENDING_APPROVAL','2021-12-24 15:59:31','2021-12-24 17:00:00',NULL,'DELIVERY',51,'Mosa','Srour','Raanan','123123',25,'cola,fries,','null,null,','REGULAR',0,0),(17,'5555','1000','customer','NORTH','REGULAR','PENDING_APPROVAL','2021-12-24 16:01:45','2021-12-24 18:00:00',NULL,'DELIVERY',37,'ss','ss','as','123',25,'cola,','null,','REGULAR',0,0),(18,'5555','1002','businesscustomer','NORTH','REGULAR','PENDING_APPROVAL','2021-12-24 16:07:43','2021-12-24 18:00:00',NULL,'DELIVERY',37,'s','s','s2s2','2',25,'pie,','null,','REGULAR',0,0),(19,'5555','1002','businesscustomer','NORTH','REGULAR','PENDING_APPROVAL','2021-12-24 16:12:19','2021-12-24 18:00:00',NULL,'DELIVERY',70,'sds','sds','sasd','12321',25,'burger,','null,','REGULAR',0,0),(20,'1112','1000','customer','NORTH','REGULAR','PENDING_APPROVAL','2021-12-24 16:13:41','2021-12-24 17:00:00',NULL,'DELIVERY',70,'asd','asd','s','32432s',25,'pizza,','null,','REGULAR',0,0),(21,'1112','1000','customer','NORTH','REGULAR','PENDING_APPROVAL','2021-12-24 20:29:33','2021-12-24 21:00:00',NULL,'TAKE_AWAY',40,'mm','mm','','12321',0,'fries,fries,ice cream,','null,big,small,','NA',0,0),(22,'2222','1000','customer','SOUTH','REGULAR','PENDING_APPROVAL','2021-12-26 21:59:19','2021-12-26 23:00:00',NULL,'TAKE_AWAY',12,'ss','ss','','333',0,'cola,','null,','NA',0,0),(23,'2222','1000','customer','SOUTH','REGULAR','PENDING_APPROVAL','2021-12-26 22:00:18','2021-12-26 23:00:00',NULL,'TAKE_AWAY',37,'sds','dsds','','121',0,'caesar,cola,','null,null,','NA',0,0);
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
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reports`
--

LOCK TABLES `reports` WRITE;
/*!40000 ALTER TABLE `reports` DISABLE KEYS */;
INSERT INTO `reports` VALUES (1,'1111','monthly','CENTER','2021-01-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportAn\Á\\í/î\0I\0\nlateOrdersI\0totalOrdersL\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomet\0Ljava/lang/String;L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	ice creamxsq\0~\0\0\0\0w\0\0\0t\0colaxsq\0~\0\0\0\0w\0\0\0t\0friesxt\0176.0t\0\n2021-01-01sq\0~\0\0\0\0w\0\0\0t\0pizzaxt\0monthlysq\0~\0\0\0\0w\0\0\0t\0caesarxt\0CENTERt\01111t\0Dominosur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(2,'1112','monthly','NORTH','2021-01-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportAn\Á\\í/î\0I\0\nlateOrdersI\0totalOrdersL\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomet\0Ljava/lang/String;L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	ice creamxsq\0~\0\0\0\0w\0\0\0t\0colaxsq\0~\0\0\0\0w\0\0\0t\0friesxt\0176.0t\0\n2021-01-01sq\0~\0\0\0\0w\0\0\0t\0pizzaxt\0monthlysq\0~\0\0\0\0w\0\0\0t\0caesarxt\0NORTHt\01112t\0Dominosur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(3,'2222','monthly','SOUTH','2021-01-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportAn\Á\\í/î\0I\0\nlateOrdersI\0totalOrdersL\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomet\0Ljava/lang/String;L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	ice creamxsq\0~\0\0\0\0w\0\0\0t\0colaxsq\0~\0\0\0\0w\0\0\0t\0friesxt\0176.0t\0\n2021-01-01sq\0~\0\0\0\0w\0\0\0t\0pizzaxt\0monthlysq\0~\0\0\0\0w\0\0\0t\0caesarxt\0SOUTHt\02222t\0PizzaHutur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(4,'2223','monthly','CENTER','2021-01-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportAn\Á\\í/î\0I\0\nlateOrdersI\0totalOrdersL\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomet\0Ljava/lang/String;L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	ice creamxsq\0~\0\0\0\0w\0\0\0t\0colaxsq\0~\0\0\0\0w\0\0\0t\0friesxt\0176.0t\0\n2021-01-01sq\0~\0\0\0\0w\0\0\0t\0pizzaxt\0monthlysq\0~\0\0\0\0w\0\0\0t\0caesarxt\0CENTERt\02223t\0PizzaHutur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(5,'5555','monthly','NORTH','2021-01-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportAn\Á\\í/î\0I\0\nlateOrdersI\0totalOrdersL\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomet\0Ljava/lang/String;L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0\0\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0piexsq\0~\0\0\0\0w\0\0\0t\0colaxsq\0~\0\0\0\0w\0\0\0t\0friesxt\00t\0\n2021-01-01sq\0~\0\0\0\0w\0\0\0t\0burgerxt\0monthlysq\0~\0\0\0\0w\0\0\0t\0\ntuna saladxt\0NORTHt\05555t\0	Mcdonaldsur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(6,'5556','monthly','CENTER','2021-01-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportAn\Á\\í/î\0I\0\nlateOrdersI\0totalOrdersL\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomet\0Ljava/lang/String;L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0\0\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0piexsq\0~\0\0\0\0w\0\0\0t\0colaxsq\0~\0\0\0\0w\0\0\0t\0friesxt\00t\0\n2021-01-01sq\0~\0\0\0\0w\0\0\0t\0burgerxt\0monthlysq\0~\0\0\0\0w\0\0\0t\0\ntuna saladxt\0CENTERt\05556t\0	Mcdonaldsur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(7,'1111','quarterly','CENTER','2021-10-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportAn\Á\\í/î\0I\0\nlateOrdersI\0totalOrdersL\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomet\0Ljava/lang/String;L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	ice creamxsq\0~\0\0\0\0w\0\0\0t\0colaxsq\0~\0\0\0\0w\0\0\0t\0friesxt\0176.0t\0\n2021-01-01sq\0~\0\0\0\0w\0\0\0t\0pizzaxt\0	quarterlysq\0~\0\0\0\0w\0\0\0t\0caesarxt\0CENTERt\01111t\0Dominosur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(8,'1112','quarterly','NORTH','2021-10-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportAn\Á\\í/î\0I\0\nlateOrdersI\0totalOrdersL\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomet\0Ljava/lang/String;L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	ice creamxsq\0~\0\0\0\0w\0\0\0t\0colaxsq\0~\0\0\0\0w\0\0\0t\0friesxt\0176.0t\0\n2021-01-01sq\0~\0\0\0\0w\0\0\0t\0pizzaxt\0	quarterlysq\0~\0\0\0\0w\0\0\0t\0caesarxt\0NORTHt\01112t\0Dominosur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(9,'2222','quarterly','SOUTH','2021-10-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportAn\Á\\í/î\0I\0\nlateOrdersI\0totalOrdersL\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomet\0Ljava/lang/String;L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	ice creamxsq\0~\0\0\0\0w\0\0\0t\0colaxsq\0~\0\0\0\0w\0\0\0t\0friesxt\0176.0t\0\n2021-01-01sq\0~\0\0\0\0w\0\0\0t\0pizzaxt\0	quarterlysq\0~\0\0\0\0w\0\0\0t\0caesarxt\0SOUTHt\02222t\0PizzaHutur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(10,'2223','quarterly','CENTER','2021-10-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportAn\Á\\í/î\0I\0\nlateOrdersI\0totalOrdersL\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomet\0Ljava/lang/String;L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	ice creamxsq\0~\0\0\0\0w\0\0\0t\0colaxsq\0~\0\0\0\0w\0\0\0t\0friesxt\0176.0t\0\n2021-01-01sq\0~\0\0\0\0w\0\0\0t\0pizzaxt\0	quarterlysq\0~\0\0\0\0w\0\0\0t\0caesarxt\0CENTERt\02223t\0PizzaHutur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(11,'5555','quarterly','NORTH','2021-10-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportAn\Á\\í/î\0I\0\nlateOrdersI\0totalOrdersL\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomet\0Ljava/lang/String;L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0\0\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0piexsq\0~\0\0\0\0w\0\0\0t\0colaxsq\0~\0\0\0\0w\0\0\0t\0friesxt\00t\0\n2021-01-01sq\0~\0\0\0\0w\0\0\0t\0burgerxt\0	quarterlysq\0~\0\0\0\0w\0\0\0t\0\ntuna saladxt\0NORTHt\05555t\0	Mcdonaldsur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(12,'5556','quarterly','CENTER','2021-10-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportAn\Á\\í/î\0I\0\nlateOrdersI\0totalOrdersL\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomet\0Ljava/lang/String;L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0\0\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0piexsq\0~\0\0\0\0w\0\0\0t\0colaxsq\0~\0\0\0\0w\0\0\0t\0friesxt\00t\0\n2021-01-01sq\0~\0\0\0\0w\0\0\0t\0burgerxt\0	quarterlysq\0~\0\0\0\0w\0\0\0t\0\ntuna saladxt\0CENTERt\05556t\0	Mcdonaldsur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(25,'1111','quarterly','CENTER','2021-01-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportAn\Á\\í/î\0I\0\nlateOrdersI\0totalOrdersL\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomet\0Ljava/lang/String;L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	ice creamxsq\0~\0\0\0\0w\0\0\0t\0colaxsq\0~\0\0\0\0w\0\0\0t\0friesxt\0176.0t\0\n2021-01-01sq\0~\0\0\0\0w\0\0\0t\0pizzaxt\0	quarterlysq\0~\0\0\0\0w\0\0\0t\0caesarxt\0CENTERt\01111t\0Dominosur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(26,'1112','quarterly','NORTH','2021-01-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportAn\Á\\í/î\0I\0\nlateOrdersI\0totalOrdersL\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomet\0Ljava/lang/String;L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	ice creamxsq\0~\0\0\0\0w\0\0\0t\0colaxsq\0~\0\0\0\0w\0\0\0t\0friesxt\0176.0t\0\n2021-01-01sq\0~\0\0\0\0w\0\0\0t\0pizzaxt\0	quarterlysq\0~\0\0\0\0w\0\0\0t\0caesarxt\0NORTHt\01112t\0Dominosur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(27,'2222','quarterly','SOUTH','2021-01-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportAn\Á\\í/î\0I\0\nlateOrdersI\0totalOrdersL\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomet\0Ljava/lang/String;L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	ice creamxsq\0~\0\0\0\0w\0\0\0t\0colaxsq\0~\0\0\0\0w\0\0\0t\0friesxt\0176.0t\0\n2021-01-01sq\0~\0\0\0\0w\0\0\0t\0pizzaxt\0	quarterlysq\0~\0\0\0\0w\0\0\0t\0caesarxt\0SOUTHt\02222t\0PizzaHutur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(28,'2223','quarterly','CENTER','2021-01-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportAn\Á\\í/î\0I\0\nlateOrdersI\0totalOrdersL\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomet\0Ljava/lang/String;L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0	ice creamxsq\0~\0\0\0\0w\0\0\0t\0colaxsq\0~\0\0\0\0w\0\0\0t\0friesxt\0176.0t\0\n2021-01-01sq\0~\0\0\0\0w\0\0\0t\0pizzaxt\0	quarterlysq\0~\0\0\0\0w\0\0\0t\0caesarxt\0CENTERt\02223t\0PizzaHutur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(29,'5555','quarterly','NORTH','2021-01-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportAn\Á\\í/î\0I\0\nlateOrdersI\0totalOrdersL\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomet\0Ljava/lang/String;L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0\0\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0piexsq\0~\0\0\0\0w\0\0\0t\0colaxsq\0~\0\0\0\0w\0\0\0t\0friesxt\00t\0\n2021-01-01sq\0~\0\0\0\0w\0\0\0t\0burgerxt\0	quarterlysq\0~\0\0\0\0w\0\0\0t\0\ntuna saladxt\0NORTHt\05555t\0	Mcdonaldsur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0'),(30,'5556','quarterly','CENTER','2021-01-01 00:00:00',_binary '¨\Ì\0sr\0util.SupplierByReportAn\Á\\í/î\0I\0\nlateOrdersI\0totalOrdersL\0dessertst\0Ljava/util/ArrayList;L\0drinksq\0~\0L\0firstsq\0~\0L\0incomet\0Ljava/lang/String;L\0	issueDateq\0~\0L\0mainsq\0~\0L\0\nreportTypeq\0~\0L\0saladsq\0~\0L\0supplierBranchq\0~\0L\0\nsupplierIdq\0~\0L\0supplierNameq\0~\0[\0typeSumst\0[Ixp\0\0\0\0\0\0\0\0sr\0java.util.ArrayListxÅ\“ô\«aù\0I\0sizexp\0\0\0w\0\0\0t\0piexsq\0~\0\0\0\0w\0\0\0t\0colaxsq\0~\0\0\0\0w\0\0\0t\0friesxt\00t\0\n2021-01-01sq\0~\0\0\0\0w\0\0\0t\0burgerxt\0	quarterlysq\0~\0\0\0\0w\0\0\0t\0\ntuna saladxt\0CENTERt\05556t\0	Mcdonaldsur\0[IM∫`&vÍ≤•\0\0xp\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0');
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
INSERT INTO `supplier` VALUES ('1111','Domino\'s Pizza','CENTER','support@dominos.com','100001111',7.8,'PENDING_REGISTRATION'),('1112','Domino\'s Pizza','NORTH','support@dominos.com','100001111',9.8,'CONFIRMED'),('2222','Pizza Hut','SOUTH','support@pizzahut.com','100002222',10,'CONFIRMED'),('2223','Pizza Hut','CENTER','support@pizzahut.com','100002222',12,'PENDING_REGISTRATION'),('5555','McDonald\'s','NORTH','support@mcdonalds.com','100005555',11.3,'CONFIRMED'),('5556','McDonald\'s','CENTER','support@mcdonalds.com','100005555',7.2,'PENDING_REGISTRATION');
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
INSERT INTO `supplierworker` VALUES ('2000','CONFIRMED','PHWfirstName','PHWlastname','SOUTH',0,'phw@pizzahut.com','1000211','2222','CERTIFIED'),('2001','CONFIRMED','PHWfirstName','PHWlastname','SOUTH',0,'phw@pizzahut.com','1000222','2222','REGULAR'),('2002','CONFIRMED','MN','MN','NORTH',1,'mnw@mac.com','1002234','5555','CERTIFIED');
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

-- Dump completed on 2021-12-30 17:33:26
