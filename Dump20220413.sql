-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: warehouse
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
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `client` (
  `id` int NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `age` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (1,'Ion Pop','ionpop@gmail.com','Str. Plopilor',25),(2,'Mara Stan','stan_mara@mymail.ro','Aleea Brazilor nr. 7',30),(3,'Ana','ana_123@gmail.com','str. plopilor nr.16',19),(4,'Ana','ana_123@gmail.com','str. plopilor nr.16',19),(5,'Elena','ena@yahoo.ro','str teilor',70),(6,'Alin','alin@fdfsf.ro','str teilor 334',45);
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comanda`
--

DROP TABLE IF EXISTS `comanda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comanda` (
  `id` int NOT NULL,
  `idClient` int DEFAULT NULL,
  `idProduct` int DEFAULT NULL,
  `priceProduct` double DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idClient_fk_idx` (`idClient`),
  KEY `idProduct_fk_idx` (`idProduct`) /*!80000 INVISIBLE */,
  KEY `priceProduct_fk_idx` (`priceProduct`),
  CONSTRAINT `idClient_fk` FOREIGN KEY (`idClient`) REFERENCES `client` (`id`),
  CONSTRAINT `idProduct_fk` FOREIGN KEY (`idProduct`) REFERENCES `product` (`id`),
  CONSTRAINT `priceProduct_fk` FOREIGN KEY (`priceProduct`) REFERENCES `product` (`price`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comanda`
--

LOCK TABLES `comanda` WRITE;
/*!40000 ALTER TABLE `comanda` DISABLE KEYS */;
INSERT INTO `comanda` VALUES (1,1,2,12,5),(2,3,2,12,5),(4,1,2,12,5),(5,3,1,15,12),(6,2,3,14,7),(7,2,3,14,4),(8,3,1,15,10),(9,4,1,15,8),(10,1,2,12,2),(11,2,2,12,2),(12,1,4,10,3),(13,1,1,15,5),(14,2,4,10,2),(15,4,4,10,2),(16,4,4,10,2),(17,3,4,10,6);
/*!40000 ALTER TABLE `comanda` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` int NOT NULL,
  `name` varchar(45) NOT NULL,
  `price` double NOT NULL,
  `stock` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `price_index` (`price`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Cheesecake',15,15),(2,'Lava Cake',12,16),(3,'Chocolate Mousse',14,4),(4,'coffee',10,30);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'warehouse'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-04-13 22:24:16
