-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: dbproject
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `adoptionrecord`
--

DROP TABLE IF EXISTS `adoptionrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `adoptionrecord` (
  `dogId` int NOT NULL,
  `memberId` int NOT NULL,
  `adoptLocation` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `adoptDate` date DEFAULT NULL,
  `adoptStatus` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`dogId`,`memberId`),
  KEY `memberId` (`memberId`),
  CONSTRAINT `adoptionrecord_ibfk_1` FOREIGN KEY (`dogId`) REFERENCES `straydog` (`dogId`),
  CONSTRAINT `adoptionrecord_ibfk_2` FOREIGN KEY (`memberId`) REFERENCES `member` (`memberId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adoptionrecord`
--

LOCK TABLES `adoptionrecord` WRITE;
/*!40000 ALTER TABLE `adoptionrecord` DISABLE KEYS */;
/*!40000 ALTER TABLE `adoptionrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `donationrecord`
--

DROP TABLE IF EXISTS `donationrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donationrecord` (
  `donationId` int NOT NULL,
  `memberId` int DEFAULT NULL,
  `item` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `donationDate` date DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`donationId`),
  KEY `memberId` (`memberId`),
  CONSTRAINT `donationrecord_ibfk_1` FOREIGN KEY (`memberId`) REFERENCES `member` (`memberId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donationrecord`
--

LOCK TABLES `donationrecord` WRITE;
/*!40000 ALTER TABLE `donationrecord` DISABLE KEYS */;
/*!40000 ALTER TABLE `donationrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `intakerecord`
--

DROP TABLE IF EXISTS `intakerecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `intakerecord` (
  `dogId` int NOT NULL,
  `memberId` int NOT NULL,
  `dogSource` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `availableLocation` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `intakeDate` date DEFAULT NULL,
  PRIMARY KEY (`dogId`,`memberId`),
  KEY `memberId` (`memberId`),
  CONSTRAINT `intakerecord_ibfk_1` FOREIGN KEY (`dogId`) REFERENCES `straydog` (`dogId`),
  CONSTRAINT `intakerecord_ibfk_2` FOREIGN KEY (`memberId`) REFERENCES `member` (`memberId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `intakerecord`
--

LOCK TABLES `intakerecord` WRITE;
/*!40000 ALTER TABLE `intakerecord` DISABLE KEYS */;
/*!40000 ALTER TABLE `intakerecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `memberId` int AUTO_INCREMENT NOT NULL,
  `name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gender` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `address` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`memberId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `straydog`
--

DROP TABLE IF EXISTS `straydog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `straydog` (
  `dogId` int NOT NULL,
  `dogName` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `breed` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `age` int DEFAULT NULL,
  `gender` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `medicalStatus` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `isAdoptable` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`dogId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `straydog`
--

LOCK TABLES `straydog` WRITE;
/*!40000 ALTER TABLE `straydog` DISABLE KEYS */;
/*!40000 ALTER TABLE `straydog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `volunteeractivity`
--

DROP TABLE IF EXISTS `volunteeractivity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `volunteeractivity` (
  `activityId` int NOT NULL,
  `activityName` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `activityType` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `activityDate` date DEFAULT NULL,
  `activityLocation` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`activityId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `volunteeractivity`
--

LOCK TABLES `volunteeractivity` WRITE;
/*!40000 ALTER TABLE `volunteeractivity` DISABLE KEYS */;
/*!40000 ALTER TABLE `volunteeractivity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `volunteerparticipation`
--

DROP TABLE IF EXISTS `volunteerparticipation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `volunteerparticipation` (
  `activityId` int NOT NULL,
  `memberId` int NOT NULL,
  PRIMARY KEY (`activityId`,`memberId`),
  KEY `memberId` (`memberId`),
  CONSTRAINT `volunteerparticipation_ibfk_1` FOREIGN KEY (`activityId`) REFERENCES `volunteeractivity` (`activityId`),
  CONSTRAINT `volunteerparticipation_ibfk_2` FOREIGN KEY (`memberId`) REFERENCES `member` (`memberId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `volunteerparticipation`
--

LOCK TABLES `volunteerparticipation` WRITE;
/*!40000 ALTER TABLE `volunteerparticipation` DISABLE KEYS */;
/*!40000 ALTER TABLE `volunteerparticipation` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-30 18:54:36
