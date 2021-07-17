-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: localhost    Database: prices
-- ------------------------------------------------------
-- Server version	8.0.18

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
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (4,'Bakery products'),(5,'Confectionery'),(3,'Fish'),(8,'Fish2'),(2,'Meat and meat products'),(1,'Milk products'),(9,'Test'),(22,'TestDeleteCstegory'),(6,'Vegetables and fruits');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `prices`
--

LOCK TABLES `prices` WRITE;
/*!40000 ALTER TABLE `prices` DISABLE KEYS */;
INSERT INTO `prices` VALUES (1,1.20,'2021-06-20',1),(2,1.50,'2021-06-20',1),(3,2.00,'2021-06-20',1),(4,1.70,'2021-05-25',1),(5,2.50,'2021-06-15',1),(6,1.00,'2021-06-21',2),(7,1.10,'2021-05-19',2),(8,0.90,'2021-06-05',2),(9,1.50,'2021-06-18',2),(10,1.20,'2021-05-29',2),(11,5.00,'2021-06-10',3),(12,5.20,'2021-06-15',3),(13,8.00,'2021-06-02',3),(14,4.50,'2021-06-21',3),(15,4.80,'2021-05-28',3),(16,10.00,'2021-06-10',4),(17,12.00,'2021-06-15',4),(18,10.50,'2021-06-08',4),(19,15.00,'2021-06-01',4),(20,13.50,'2021-06-21',4),(21,15.00,'2021-06-21',5),(22,17.00,'2021-05-29',5),(23,20.00,'2021-06-10',5),(24,15.50,'2021-06-18',5),(25,18.30,'2021-06-20',5),(26,5.00,'2021-06-02',6),(27,7.00,'2021-06-03',6),(28,8.00,'2021-06-02',6),(29,7.80,'2021-06-03',6),(30,8.50,'2021-06-18',6),(31,14.00,'2021-06-05',7),(32,15.00,'2021-06-08',7),(33,17.00,'2021-06-18',7),(34,12.00,'2021-06-18',7),(35,13.50,'2021-06-20',7),(36,8.00,'2021-06-11',8),(37,9.00,'2021-06-11',8),(38,10.00,'2021-06-18',8),(39,5.00,'2021-06-15',8),(40,6.00,'2021-06-20',8),(41,16.00,'2021-05-19',9),(42,15.00,'2021-05-28',9),(43,12.00,'2021-06-12',9),(44,16.00,'2021-06-15',9),(45,12.50,'2021-06-15',9),(46,1.00,'2021-06-10',10),(47,1.50,'2021-06-10',10),(48,5.00,'2021-06-12',10),(49,0.70,'2021-06-11',10),(50,1.80,'2021-06-11',10),(51,3.00,'2021-06-15',11),(52,1.50,'2021-06-14',11),(53,2.50,'2021-06-15',11),(54,3.50,'2021-06-20',11),(55,5.00,'2021-06-18',11),(56,1.00,'2021-06-18',12),(57,2.00,'2021-06-20',12),(58,1.50,'2021-06-20',12),(59,1.50,'2021-06-10',12),(60,3.00,'2021-06-12',12),(61,5.00,'2021-06-02',13),(62,6.00,'2021-06-02',13),(63,6.50,'2021-06-15',13),(64,7.00,'2021-06-16',13),(65,8.00,'2021-06-18',13),(66,6.00,'2021-06-15',14),(67,7.00,'2021-06-18',14),(68,7.00,'2021-06-18',14),(69,8.50,'2021-06-20',14),(70,8.80,'2021-06-21',14),(71,3.00,'2021-06-04',15),(72,8.00,'2021-06-06',15),(73,7.00,'2021-06-09',15),(74,6.00,'2021-06-11',15),(75,6.00,'2021-06-11',15),(76,2.00,'2021-06-10',16),(77,3.00,'2021-06-10',16),(78,1.50,'2021-06-12',16),(79,3.00,'2021-06-15',16),(80,5.00,'2021-06-16',16),(81,1.00,'2021-06-06',17),(82,0.70,'2021-06-15',17),(83,1.00,'2021-06-12',17),(84,1.50,'2021-06-12',17),(85,2.00,'2021-06-18',17),(86,3.00,'2021-06-14',18),(87,5.00,'2021-06-15',18),(88,8.00,'2021-06-14',18),(89,5.50,'2021-06-18',18),(90,8.00,'2021-06-20',18),(91,2.00,'2021-06-02',19),(92,3.00,'2021-06-03',19),(93,5.00,'2021-06-15',19),(94,5.00,'2021-06-20',19),(95,3.50,'2021-06-15',19),(96,3.00,'2021-06-15',20),(97,3.50,'2021-06-14',20),(98,3.20,'2021-06-18',20),(99,2.50,'2021-06-19',20),(100,2.80,'2021-06-19',20),(101,1.50,'2021-06-22',1),(130,111.00,'2021-01-01',40),(131,222.00,'2021-02-02',40);
/*!40000 ALTER TABLE `prices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Milk','Liter',1),(2,'Yogurt','Unit',1),(3,'Cheese','Kg',1),(4,'Tuna','Kg',3),(5,'Trout','Kg',3),(6,'Pork','Kg',2),(7,'Beef','Kg',2),(8,'Sausages','Kg',2),(9,'Ham','Kg',2),(10,'Bread','Kg',4),(11,'Toast','Kg',4),(12,'Buns','Unit',4),(13,'Cake','Unit',5),(14,'Pies','Unit',5),(15,'Cookie','Kg',5),(16,'Chocolates','Unit',5),(17,'Potatoes','Kg',6),(18,'Tomatoes','Kg',6),(19,'Apples','Kg',6),(20,'Bananas','Kg',6),(24,'test25','test2',8),(25,'Test','Test',9),(40,'TestDeleteName','Unit',22);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `products_has_shops`
--

LOCK TABLES `products_has_shops` WRITE;
/*!40000 ALTER TABLE `products_has_shops` DISABLE KEYS */;
INSERT INTO `products_has_shops` VALUES (1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1),(11,1),(12,1),(13,1),(14,1),(15,1),(16,1),(17,1),(18,1),(19,1),(20,1),(1,2),(2,2),(3,2),(4,2),(5,2),(6,2),(7,2),(8,2),(9,2),(10,2),(11,2),(12,2),(13,2),(14,2),(15,2),(16,2),(17,2),(18,2),(19,2),(20,2),(1,3),(2,3),(3,3),(4,3),(5,3),(6,3),(7,3),(8,3),(9,3),(10,3),(11,3),(12,3),(13,3),(14,3),(15,3),(16,3),(17,3),(18,3),(19,3),(20,3),(1,4),(2,4),(3,4),(4,4),(5,4),(6,4),(7,4),(8,4),(9,4),(10,4),(11,4),(12,4),(13,4),(14,4),(15,4),(16,4),(17,4),(18,4),(19,4),(20,4),(1,5),(2,5),(3,5),(4,5),(5,5),(6,5),(7,5),(8,5),(9,5),(10,5),(11,5),(12,5),(13,5),(14,5),(15,5),(16,5),(17,5),(18,5),(19,5),(20,5),(40,21);
/*!40000 ALTER TABLE `products_has_shops` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `shops`
--

LOCK TABLES `shops` WRITE;
/*!40000 ALTER TABLE `shops` DISABLE KEYS */;
INSERT INTO `shops` VALUES (1,'Evroopt','Hataevicha, 1'),(2,'Dionis','Checherskaya, 38'),(3,'Green','Mazuruva, 5'),(4,'Almi','Mazurova, 57'),(5,'Gippo','Sovetskaya, 15'),(6,'test shop','test address'),(21,'TestDeleteShop','Test Address');
/*!40000 ALTER TABLE `shops` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `shops_has_prices`
--

LOCK TABLES `shops_has_prices` WRITE;
/*!40000 ALTER TABLE `shops_has_prices` DISABLE KEYS */;
INSERT INTO `shops_has_prices` VALUES (1,1),(2,2),(3,3),(4,4),(5,5),(1,6),(2,7),(3,8),(4,9),(5,10),(1,11),(2,12),(3,13),(4,14),(5,15),(1,16),(2,17),(3,18),(4,19),(5,20),(1,21),(2,22),(3,23),(4,24),(5,25),(1,26),(2,27),(3,28),(4,29),(5,30),(1,31),(2,32),(3,33),(4,34),(5,35),(1,36),(2,37),(3,38),(4,39),(5,40),(1,41),(2,42),(3,43),(4,44),(5,45),(1,46),(2,47),(3,48),(4,49),(5,50),(1,51),(2,52),(3,53),(4,54),(5,55),(1,56),(2,57),(3,58),(4,59),(5,60),(1,61),(2,62),(3,63),(4,64),(5,65),(1,66),(2,67),(3,68),(4,69),(5,70),(1,71),(2,72),(3,73),(4,74),(5,75),(1,76),(2,77),(3,78),(4,79),(5,80),(1,81),(2,82),(3,83),(4,84),(5,85),(1,86),(2,87),(3,88),(4,89),(5,90),(1,91),(2,92),(3,93),(4,94),(5,95),(1,96),(2,97),(3,98),(4,99),(5,100),(1,101),(21,130),(21,131);
/*!40000 ALTER TABLE `shops_has_prices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','$2y$12$Tv8/N0pNER.fQfaFGWEnf.he/O6OOPFIGeJ7i8IhUTEF1moZM3Kue'),(2,'user','$2y$12$R.OQnGj4v95pCOZ6nbdTQ.rgTCNUSW5QDBzOfnm3PUmYc7hbrbojW'),(3,'user11','$2a$10$O8zcSsOT2p.BIFHmCOxjAOiU4ZwtzgbzTDHmcFGCyo/aiTk5PyMge'),(11,'user7','$2a$10$hr/aq.B/DwXWJFmHALl/9ueBJ.MyfqXoh7BcAnys5bMyt9M7pb862'),(12,'user2','$2a$10$ePnUwTqWLByX6rfceSgvver4vMEhc3UnmTZg5aE5oeOsPt2y5xyGu'),(13,'user3','$2a$10$aCv1z5XOc5pxWymt2sty9OEmH2RqDF58IqjQBkXvJWaF2kf.6nqSm');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `users_has_roles`
--

LOCK TABLES `users_has_roles` WRITE;
/*!40000 ALTER TABLE `users_has_roles` DISABLE KEYS */;
INSERT INTO `users_has_roles` VALUES (1,1),(12,1),(2,2),(3,2),(11,2),(13,2);
/*!40000 ALTER TABLE `users_has_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-14 22:15:48
