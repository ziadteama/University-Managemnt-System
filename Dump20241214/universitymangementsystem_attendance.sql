-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: universitymangementsystem
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `attendance`
--

DROP TABLE IF EXISTS `attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attendance` (
  `schedule_id` varchar(10) NOT NULL,
  `user_id` int NOT NULL,
  `status` tinyint(1) NOT NULL,
  `week` enum('1st week','2nd week','3rd week','4th week','5th week','6th week','7th week','8th week','9th week','10th week','11th week','12th week','13th week','14th week','15th week','16th week') NOT NULL,
  PRIMARY KEY (`week`,`user_id`,`schedule_id`),
  KEY `schedule_id` (`schedule_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `attendance_ibfk_1` FOREIGN KEY (`schedule_id`) REFERENCES `schedules` (`schedule_id`),
  CONSTRAINT `attendance_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendance`
--

LOCK TABLES `attendance` WRITE;
/*!40000 ALTER TABLE `attendance` DISABLE KEYS */;
INSERT INTO `attendance` VALUES ('23FCS101AL',231010016,1,'1st week'),('23FCS101AS',231010016,1,'1st week'),('23FCS101AL',231010017,0,'1st week'),('23FCS101AS',231010017,1,'1st week'),('23FCS101AL',231010018,0,'1st week'),('23FCS101AS',231010018,1,'1st week'),('23FCS101AL',231010019,0,'1st week'),('23FCS101AS',231010019,1,'1st week'),('23FCS101AL',231010020,0,'1st week'),('23FCS101AS',231010020,1,'1st week'),('23FCS101AL',231010021,0,'1st week'),('23FCS101AS',231010021,0,'1st week'),('23FCS101AL',231010022,0,'1st week'),('23FCS101AS',231010022,0,'1st week'),('23FCS101AL',231010023,0,'1st week'),('23FCS101AS',231010023,0,'1st week'),('23FCS101AL',231010024,0,'1st week'),('23FCS101AS',231010024,0,'1st week'),('23FCS101AL',231010025,0,'1st week'),('23FCS101AS',231010025,0,'1st week'),('23FCS101AS',231010016,1,'5th week'),('23FCS101AS',231010017,1,'5th week'),('23FCS101AS',231010018,1,'5th week'),('23FCS101AS',231010019,1,'5th week'),('23FCS101AS',231010020,0,'5th week'),('23FCS101AS',231010021,0,'5th week'),('23FCS101AS',231010022,0,'5th week'),('23FCS101AS',231010023,0,'5th week'),('23FCS101AS',231010024,0,'5th week'),('23FCS101AS',231010025,0,'5th week');
/*!40000 ALTER TABLE `attendance` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-14 23:56:43
