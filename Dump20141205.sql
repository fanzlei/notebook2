CREATE DATABASE  IF NOT EXISTS `notebook` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `notebook`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: notebook
-- ------------------------------------------------------
-- Server version	5.7.3-m13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `pass` varchar(45) NOT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='用户注册信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'fanz','1112','14718090417','fanz_lei@126.com'),(3,'abc','abc',NULL,NULL),(4,'qq','qq','qq','qq'),(5,'fanzz','1111',NULL,'fasd2@126.com'),(6,'ss','ss',NULL,'fanfs@121.com'),(7,'abctt','abctt',NULL,'fanz@123.com'),(8,'qqqq','1111','14718090417','dtf5@126.com'),(9,'qqqqq','1111','14718090417','fgft@126.com'),(12,'qqqqqq','1111','14718090417','dgdty@126.com'),(13,'pioi','1111','14718090417','fht@126.com'),(15,'pioii','1111','14718090417','fht@126.com');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_notebook`
--

DROP TABLE IF EXISTS `user_notebook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_notebook` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) NOT NULL,
  `title` varchar(45) DEFAULT NULL,
  `content` varchar(45) DEFAULT NULL,
  `date` varchar(45) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_notebook`
--

LOCK TABLES `user_notebook` WRITE;
/*!40000 ALTER TABLE `user_notebook` DISABLE KEYS */;
INSERT INTO `user_notebook` VALUES (1,'fanz','我的第一个笔记','今天很开心，今天去游泳池游泳了！','2014.12.03',NULL),(3,'fanz','鏃犳爣棰�','rrtt','2014-12-03','1'),(4,'fanz','cggh','gghh咯哦哦哦','2014-12-03','1'),(5,'fanz','好了','gghh咯哦哦哦','2014-12-03','1'),(6,'fanz','好了','gghh咯哦哦哦','2014-12-03','2'),(7,'fanz','好了','gghh咯哦哦哦','2014-12-03','3'),(8,'fanz','好了','gghh咯哦哦哦','2014-12-03','1'),(9,'fanz','无标题','','2014-12-03','1'),(10,'fanz','无标题','林肯','2014-12-03','1'),(11,'fanz','无标题','看你','2014-12-03','1'),(12,'fanz','无标题','gjhhhn','2014-12-04','2'),(13,'fanz','无标题','瞳孔哦哦','2014-12-04','1'),(14,'fanz','接龙弄','接龙弄','2014-12-04','2'),(15,'fanz','咯么么','咯陌陌陌','2014-12-04','1'),(16,'fanz','12.4的奇迹','今天很开心','2014-12-04','2'),(17,'fanz','2','监控','2014-12-04','1'),(18,'fanz','萝莉控','萝莉控','2014-12-04','2'),(19,'fanz','流量监控','流量监控','2014-12-04','3'),(20,'fanz','无标题','啊啊啊','2014-12-04','3'),(21,'fanz','无标题','','2014-12-04','1'),(22,'fanz','无标题','','2014-12-04','1'),(23,'fanz','无标题','爸爸','2014-12-04','1'),(24,'fanz','无标题','萝莉控','2014-12-04','1'),(25,'fanz','无标题','爸爸','2014-12-04','1'),(26,'fanz','无标题','来咯','2014-12-04','1'),(27,'fanz','无标题','阿拉','2014-12-04','1'),(28,'fanz','来咯','','2014-12-04','1'),(29,'fanz','无标题','爸爸','2014-12-04','1'),(30,'fanz','无标题','vgg','2014-12-04','1'),(31,'fanz','图图图','图图','2014-12-04','1'),(32,'fanz','摸摸你','噢噢噢','2014-12-04','1'),(33,'fanz','得到的爸爸','流量监控','2014-12-04','1'),(34,'fanz','无标题','默默','2014-12-04','1'),(35,'fanz','萝莉控','爸爸','2014-12-04','1'),(36,'fanz','无标题','萝莉控','2014-12-04','1'),(37,'fanz','无标题','来咯','2014-12-04','1'),(38,'fanz','无标题','流量监控','2014-12-04','1'),(39,'fanz','霸道','','2014-12-04','1'),(40,'fanz','无标题','萝莉控','2014-12-04','1'),(41,'fanz','无标题','来咯','2014-12-04','1'),(42,'fanz','无标题','咯麽','2014-12-04','1'),(43,'fanz','无标题','爸爸们','2014-12-04','1'),(44,'fanz','无标题','来咯','2014-12-04','1'),(45,'fanz','扣扣分','','2014-12-04','1'),(46,'fanz','无标题','来咯来咯','2014-12-04','1'),(47,'fanz','来咯来咯','卡拉借记卡','2014-12-04','1'),(48,'fanz','无标题','我的错','2014-12-04','3'),(49,'fanz','无标题','    yuuu','2014-12-04','1'),(50,'fanz','无标题','    yyy','2014-12-04','1'),(51,'fanz','无标题','    空空空哦','2014-12-04','1'),(52,'fanz','1','    哦哦','2014-12-04','1'),(53,'fanz','1','    ','2014-12-05','1'),(54,'fanz','2','    喇叭','2014-12-05','1'),(55,'fanz','3','    ','2014-12-05','1'),(56,'fanz','4','    ','2014-12-05','1'),(57,'fanz','1','    1','2014-12-05','1'),(58,'fanz','1','    萝莉控','2014-12-05','1'),(59,'fanz','1','    ','2014-12-05','1'),(60,'fanz','3','    3','2014-12-05','1'),(61,'fanz','4','    ','2014-12-05','1'),(62,'fanz','5','    ','2014-12-05','1'),(63,'fanz','6','    ','2014-12-05','1'),(64,'fanz','无标题','    7','2014-12-05','2'),(65,'fanz','8','    ','2014-12-05','2'),(66,'fanz','无标题','    吧','2014-12-05','3'),(67,'fanz','无标题','    吧','2014-12-05','3'),(68,'fanz','无标题','    我的','2014-12-05','1'),(69,'fanz','无标题','    吧','2014-12-05','3'),(70,'fanz','无标题','    得到','2014-12-05','1'),(71,'fanz','无标题','    我的','2014-12-05','3'),(72,'fanz','无标题','    得到的','2014-12-05','3'),(73,'fanz','最新','    1','2014-12-05','1'),(74,'fanz','无标题','    的','2014-12-05','3'),(75,'fanz','无标题','    得的','2014-12-05','1'),(76,'fanz','无标题','    的','2014-12-05','1'),(77,'fanz','最新','    最新','2014-12-05','1'),(78,'fanz','无标题','    ','2014-12-05','1'),(79,'fanz','无标题','    ','2014-12-05','1'),(80,'fanz','无标题','','2014-12-05','1'),(81,'fanz','无标题','    ','2014-12-05','1');
/*!40000 ALTER TABLE `user_notebook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'notebook'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-12-05 23:53:12
