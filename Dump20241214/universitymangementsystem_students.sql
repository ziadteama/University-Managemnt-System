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
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `students` (
  `student_id` int NOT NULL,
  `gpa` decimal(3,2) DEFAULT NULL,
  `advisor_id` int DEFAULT NULL,
  `status` enum('active','suspended','withdrawn') DEFAULT 'active',
  `current_semester` int NOT NULL,
  PRIMARY KEY (`student_id`),
  KEY `advisor_id` (`advisor_id`),
  CONSTRAINT `students_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `students_ibfk_2` FOREIGN KEY (`advisor_id`) REFERENCES `users` (`user_id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students`
--

LOCK TABLES `students` WRITE;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
INSERT INTO `students` VALUES (231010016,NULL,231010011,'active',1),(231010017,NULL,231010012,'active',1),(231010018,NULL,231010013,'active',1),(231010019,NULL,231010014,'active',1),(231010020,NULL,231010015,'active',1),(231010021,NULL,231010011,'active',1),(231010022,NULL,231010012,'active',1),(231010023,NULL,231010013,'active',1),(231010024,NULL,231010014,'active',1),(231010025,NULL,231010015,'active',1),(231010026,NULL,231010011,'active',1),(231010027,NULL,231010012,'withdrawn',1),(231010028,NULL,231010013,'active',1),(231010029,NULL,231010014,'active',1),(231010030,NULL,231010015,'suspended',1),(231010031,NULL,231010011,'active',1),(231010032,NULL,231010012,'active',1),(231010033,NULL,231010013,'active',1),(231010034,NULL,231010014,'active',1),(231010035,NULL,231010015,'active',1),(231020016,2.01,231010011,'active',3),(231020017,NULL,231010012,'active',3),(231020018,NULL,231010013,'active',3),(231020019,NULL,231010014,'active',3),(231020020,NULL,231010015,'active',3),(231020021,NULL,231010011,'active',3),(231020022,NULL,231010012,'active',3),(231020023,NULL,231010013,'active',3),(231020024,NULL,231010014,'active',3),(231020025,NULL,231010015,'active',3),(231020026,NULL,231010011,'active',3),(231020027,NULL,231010012,'withdrawn',3),(231020028,NULL,231010013,'active',3),(231020029,NULL,231010014,'active',3),(231020030,NULL,231010015,'suspended',3),(231020031,NULL,231010011,'active',3),(231020032,NULL,231010012,'active',3),(231020033,NULL,231010013,'active',3),(231020034,NULL,231010014,'active',3),(231020035,NULL,231010015,'active',3),(241020016,NULL,231020011,'active',1),(241020017,NULL,231020012,'active',1),(241020018,NULL,231020013,'active',1),(241020019,NULL,231020014,'active',1),(241020020,NULL,231020015,'active',1),(241020021,NULL,231020011,'active',1),(241020022,NULL,231020012,'active',1),(241020023,NULL,231020013,'active',1),(241020024,NULL,231020014,'active',1),(241020025,NULL,231020015,'active',1),(241020026,NULL,231020011,'active',1),(241020027,NULL,231020012,'withdrawn',1),(241020028,NULL,231020013,'active',1),(241020029,NULL,231020014,'active',1),(241020030,NULL,231020015,'suspended',1),(241020031,NULL,231020011,'active',1),(241020032,NULL,231020012,'active',1),(241020033,NULL,231020013,'active',1),(241020034,NULL,231020014,'active',1),(241020035,NULL,231020015,'active',1),(241020036,NULL,231020011,'active',3),(241020037,NULL,231020012,'active',3),(241020038,NULL,231020013,'active',3),(241020039,NULL,231020014,'active',3),(241020040,NULL,231020015,'active',3),(241020041,NULL,231020011,'active',3),(241020042,NULL,231020012,'active',3),(241020043,NULL,231020013,'active',3),(241020044,NULL,231020014,'active',3),(241020045,NULL,231020015,'active',3),(241020046,NULL,231020011,'active',3),(241020047,NULL,231020012,'active',3),(241020048,NULL,231020013,'withdrawn',3),(241020049,NULL,231020014,'active',3),(241020050,NULL,231020015,'suspended',3),(241020051,NULL,231020011,'active',3),(241020052,NULL,231020012,'active',3),(241020053,NULL,231020013,'active',3),(241020054,NULL,231020014,'active',3),(241020055,NULL,231020015,'active',3);
/*!40000 ALTER TABLE `students` ENABLE KEYS */;
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