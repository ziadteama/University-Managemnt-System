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
-- Table structure for table `sections`
--

DROP TABLE IF EXISTS `sections`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sections` (
  `section_id` varchar(9) NOT NULL,
  `course_id` varchar(5) DEFAULT NULL,
  `year` int DEFAULT NULL,
  `dr_id` int DEFAULT NULL,
  `ta_id` int DEFAULT NULL,
  `period` enum('fall','spring','summer') NOT NULL,
  PRIMARY KEY (`section_id`),
  KEY `course_id` (`course_id`),
  KEY `ta_id` (`dr_id`),
  KEY `fk_ta_id` (`ta_id`),
  CONSTRAINT `dr_id` FOREIGN KEY (`dr_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_ta_id` FOREIGN KEY (`ta_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sections_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`),
  CONSTRAINT `ta_id` FOREIGN KEY (`dr_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sections`
--

LOCK TABLES `sections` WRITE;
/*!40000 ALTER TABLE `sections` DISABLE KEYS */;
INSERT INTO `sections` VALUES ('22FCE101A','CE101',2022,231020001,231020011,'fall'),('22FCE101B','CE101',2022,231020001,231020012,'fall'),('22FCE102A','CE102',2022,231020002,231020013,'fall'),('22FCE102B','CE102',2022,231020002,231020014,'fall'),('22FCE103A','CE103',2022,231020003,231020015,'fall'),('22FCE103B','CE103',2022,231020003,231020011,'fall'),('22FCE104A','CE104',2022,231020004,231020012,'fall'),('22FCE104B','CE104',2022,231020004,231020013,'fall'),('22FCE105A','CE105',2022,231020005,231020014,'fall'),('22FCE105B','CE105',2022,231020005,231020015,'fall'),('22FCE106A','CE106',2022,231020006,231020011,'fall'),('22FCE106B','CE106',2022,231020006,231020012,'fall'),('22FCS101A','CS101',2022,231010001,231010011,'fall'),('22FCS101B','CS101',2022,231010001,231010012,'fall'),('22FCS102A','CS102',2022,231010002,231010013,'fall'),('22FCS102B','CS102',2022,231010002,231010014,'fall'),('22FCS103A','CS103',2022,231010003,231010015,'fall'),('22FCS103B','CS103',2022,231010003,231010011,'fall'),('22FCS104A','CS104',2022,231010004,231010012,'fall'),('22FCS104B','CS104',2022,231010004,231010013,'fall'),('22FCS105A','CS105',2022,231010005,231010014,'fall'),('22FCS105B','CS105',2022,231010005,231010015,'fall'),('22FCS106A','CS106',2022,231010006,231010011,'fall'),('22FCS106B','CS106',2022,231010006,231010012,'fall'),('23FCE101A','CE101',2023,231020001,231020011,'fall'),('23FCE101B','CE101',2023,231020001,231020012,'fall'),('23FCE102A','CE102',2023,231020002,231020013,'fall'),('23FCE102B','CE102',2023,231020002,231020014,'fall'),('23FCE103A','CE103',2023,231020003,231020015,'fall'),('23FCE103B','CE103',2023,231020003,231020011,'fall'),('23FCE104A','CE104',2023,231020004,231020012,'fall'),('23FCE104B','CE104',2023,231020004,231020013,'fall'),('23FCE105A','CE105',2023,231020005,231020014,'fall'),('23FCE105B','CE105',2023,231020005,231020015,'fall'),('23FCE106A','CE106',2023,231020006,231020011,'fall'),('23FCE106B','CE106',2023,231020006,231020012,'fall'),('23FCE301A','CE301',2023,231020001,231020011,'fall'),('23FCE301B','CE301',2023,231020001,231020012,'fall'),('23FCE302A','CE302',2023,231020002,231020013,'fall'),('23FCE302B','CE302',2023,231020002,231020014,'fall'),('23FCE303A','CE303',2023,231020003,231020015,'fall'),('23FCE303B','CE303',2023,231020003,231020011,'fall'),('23FCE304A','CE304',2023,231020004,231020012,'fall'),('23FCE304B','CE304',2023,231020004,231020013,'fall'),('23FCE305A','CE305',2023,231020005,231020014,'fall'),('23FCE305B','CE305',2023,231020005,231020015,'fall'),('23FCE306A','CE306',2023,231020006,231020011,'fall'),('23FCE306B','CE306',2023,231020006,231020012,'fall'),('23FCS101A','CS101',2023,231010001,231010011,'fall'),('23FCS101B','CS101',2023,231010001,231010012,'fall'),('23FCS102A','CS102',2023,231010002,231010013,'fall'),('23FCS102B','CS102',2023,231010002,231010014,'fall'),('23FCS103A','CS103',2023,231010003,231010015,'fall'),('23FCS103B','CS103',2023,231010003,231010011,'fall'),('23FCS104A','CS104',2023,231010004,231010012,'fall'),('23FCS104B','CS104',2023,231010004,231010013,'fall'),('23FCS105A','CS105',2023,231010005,231010014,'fall'),('23FCS105B','CS105',2023,231010005,231010015,'fall'),('23FCS106A','CS106',2023,231010006,231010011,'fall'),('23FCS106B','CS106',2023,231010006,231010012,'fall'),('23FCS301A','CS301',2023,231010001,231010011,'fall'),('23FCS301B','CS301',2023,231010001,231010012,'fall'),('23FCS302A','CS302',2023,231010002,231010013,'fall'),('23FCS302B','CS302',2023,231010002,231010014,'fall'),('23FCS303A','CS303',2023,231010003,231010015,'fall'),('23FCS303B','CS303',2023,231010003,231010011,'fall'),('23FCS304A','CS304',2023,231010004,231010012,'fall'),('23FCS304B','CS304',2023,231010004,231010013,'fall'),('23FCS305A','CS305',2023,231010005,231010014,'fall'),('23FCS305B','CS305',2023,231010005,231010015,'fall'),('23FCS306A','CS306',2023,231010006,231010011,'fall'),('23FCS306B','CS306',2023,231010006,231010012,'fall'),('23PCE201A','CE201',2023,231020007,231020013,'spring'),('23PCE201B','CE201',2023,231020007,231020014,'spring'),('23PCE202A','CE202',2023,231020008,231020015,'spring'),('23PCE202B','CE202',2023,231020008,231020011,'spring'),('23PCE203A','CE203',2023,231020009,231020012,'spring'),('23PCE203B','CE203',2023,231020009,231020013,'spring'),('23PCE204A','CE204',2023,231020010,231020014,'spring'),('23PCE204B','CE204',2023,231020010,231020015,'spring'),('23PCE205A','CE205',2023,231020006,231020011,'spring'),('23PCE205B','CE205',2023,231020006,231020012,'spring'),('23PCE206A','CE206',2023,231020005,231020013,'spring'),('23PCE206B','CE206',2023,231020005,231020014,'spring'),('23PCS201A','CS201',2023,231010001,231010011,'spring'),('23PCS201B','CS201',2023,231010001,231010012,'spring'),('23PCS202A','CS202',2023,231010002,231010013,'spring'),('23PCS202B','CS202',2023,231010002,231010014,'spring'),('23PCS203A','CS203',2023,231010003,231010015,'spring'),('23PCS203B','CS203',2023,231010003,231010011,'spring'),('23PCS204A','CS204',2023,231010004,231010012,'spring'),('23PCS204B','CS204',2023,231010004,231010013,'spring'),('23PCS205A','CS205',2023,231010005,231010014,'spring'),('23PCS205B','CS205',2023,231010005,231010015,'spring'),('23PCS206A','CS206',2023,231010006,231010011,'spring'),('23PCS206B','CS206',2023,231010006,231010012,'spring'),('23SCE101S','CE101',2023,231020001,231020011,'summer'),('23SCE102S','CE102',2023,231020002,231020012,'summer'),('23SCE103S','CE103',2023,231020003,231020012,'summer'),('23SCE204S','CE204',2023,231020004,231020014,'summer'),('23SCE205S','CE205',2023,231020005,231020015,'summer'),('23SCE206S','CE206',2023,231020006,231020011,'summer'),('23SCS101S','CS101',2023,231010001,231010011,'summer'),('23SCS102S','CS102',2023,231010002,231010012,'summer'),('23SCS103S','CS103',2023,231010003,231010013,'summer'),('23SCS201S','CS201',2023,231010004,231010014,'summer'),('23SCS202S','CS202',2023,231010005,231010015,'summer'),('23SCS203S','CS203',2023,231010006,231010011,'summer');
/*!40000 ALTER TABLE `sections` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-14 23:56:42
