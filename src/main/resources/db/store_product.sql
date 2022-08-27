-- MySQL dump 10.13  Distrib 8.0.30, for macos12 (x86_64)
--
-- Host: localhost    Database: store
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `image` varchar(256) NOT NULL,
  `quantity` int NOT NULL,
  `price` double NOT NULL,
  `discount` double NOT NULL,
  `book_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (2,'https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/mid/9780/3944/9780394498218.jpg',67,20.89,5.8,'Interview with the Vampire by Anne Rice'),(3,'https://d3i5mgdwi2ze58.cloudfront.net/kxk6iwn543doz8jqbs2sckh2fcot',67,34.2,2.5,'One Houndred Years of Solitude'),(4,'https://d3i5mgdwi2ze58.cloudfront.net/jzeie89rkkv1s2q7dwwgiysixnty',55,20.89,8.5,'Moby Dick'),(12,'https://d3i5mgdwi2ze58.cloudfront.net/swd3lfscp8lshlhuwfjng9s92o8q',89,20.89,2.5,'War and Peace'),(14,'https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/lrg/9781/4352/9781435249752.jpg',29,78.2,6.7,'Just Listen by Sarah Dessen'),(15,'https://d3i5mgdwi2ze58.cloudfront.net/x9oilp1o53aty07nyh57oyogot20',33,123.78,25.9,'The Divine Comedy'),(16,'https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/lrg/9781/8571/9781857150353.jpg',31,34.2,17,'Crime and Punishment'),(18,'https://d3i5mgdwi2ze58.cloudfront.net/bpjgg68n8b67g53xfzf9vvrtveqo',45,20.89,2.5,'Alice\'s Adventures in Wonderland'),(24,'https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/lrg/9780/4393/9780439324595.jpg',32,20.89,7.3,'Cut by Patricia McCormick'),(25,'https://d1w7fb2mkkr3kw.cloudfront.net/assets/images/book/lrg/9780/6708/9780670824397.jpg',32,67.8,34,'Matilda by Roald Dahl');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-08-27 23:22:41
