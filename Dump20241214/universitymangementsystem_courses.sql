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
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courses` (
  `course_id` varchar(5) NOT NULL,
  `course_name` varchar(100) NOT NULL,
  `credit_hours` int NOT NULL,
  PRIMARY KEY (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES ('CE101','Introduction to Civil Engineering',3),('CE102','Engineering Mechanics',2),('CE103','Surveying Basics',3),('CE104','Mathematics for Civil Engineering',2),('CE105','Materials Science',3),('CE106','Construction Materials',2),('CE201','Strength of Materials',3),('CE202','Fluid Mechanics',3),('CE203','Soil Mechanics',3),('CE204','Building Construction Techniques',3),('CE205','Environmental Engineering',2),('CE206','Structural Analysis I',3),('CE301','Reinforced Concrete Design',3),('CE302','Hydraulics and Hydrology',3),('CE303','Transportation Engineering',3),('CE304','Geotechnical Engineering',3),('CE305','Engineering Economics',2),('CE306','Construction Project Management',3),('CS101','Introduction to Business Information Systems',3),('CS102','Computer Programming Basics',2),('CS103','Business Foundations',3),('CS104','Database Fundamentals',3),('CS105','Mathematics for Business',2),('CS106','Business Communication',2),('CS201','Data Structures and Algorithms',3),('CS202','Business Statistics',3),('CS203','Systems Analysis and Design',3),('CS204','Managerial Accounting',2),('CS205','Principles of Marketing',2),('CS206','Web Development for Business',3),('CS301','Database Management Systems',3),('CS302','Business Information Security',3),('CS303','Enterprise Resource Planning (ERP)',2),('CS304','Business Intelligence and Data Mining',3),('CS305','E-Commerce and Digital Business',2),('CS306','Project Management for IT',3);
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
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
