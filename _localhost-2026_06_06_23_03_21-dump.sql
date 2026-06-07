-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: db_elderly_care
-- ------------------------------------------------------
-- Server version	8.0.43

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `qqq`
--

DROP TABLE IF EXISTS `qqq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `qqq` (
  `C1` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qqq`
--

LOCK TABLES `qqq` WRITE;
/*!40000 ALTER TABLE `qqq` DISABLE KEYS */;
INSERT INTO `qqq` VALUES ('CREATE DATABASE `db_elderly_care` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION=\'N\' */'),(NULL);
/*!40000 ALTER TABLE `qqq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_admin`
--

DROP TABLE IF EXISTS `t_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_admin` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '管理员表主键ID',
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户名',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '密码',
  `nickname` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '姓名',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_admin`
--

LOCK TABLES `t_admin` WRITE;
/*!40000 ALTER TABLE `t_admin` DISABLE KEYS */;
INSERT INTO `t_admin` VALUES (1,'admin','123456','管理员','2025-03-20 18:29:16','2025-03-20 18:29:16');
/*!40000 ALTER TABLE `t_admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_bed`
--

DROP TABLE IF EXISTS `t_bed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_bed` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '床位ID',
  `room_id` int DEFAULT NULL COMMENT '房间ID',
  `bed_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '编号',
  `status` int DEFAULT NULL COMMENT '状态：0空闲，1占用',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '添加时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `room_id` (`room_id`) USING BTREE,
  CONSTRAINT `t_bed_ibfk_1` FOREIGN KEY (`room_id`) REFERENCES `t_bed` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='床位信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_bed`
--

LOCK TABLES `t_bed` WRITE;
/*!40000 ALTER TABLE `t_bed` DISABLE KEYS */;
INSERT INTO `t_bed` VALUES (1,1,'A1',0,'101房A1床位','2025-04-20 13:25:17','2025-04-20 13:25:17'),(2,1,'A2',1,'101房A2床位','2025-04-20 22:29:02','2025-04-20 22:29:02'),(3,2,'A1',0,'102房间A1床位','2025-04-20 22:36:58','2025-04-20 22:37:52'),(4,3,'A1',1,'103房A1床位','2025-12-16 19:26:06','2025-12-16 19:26:06'),(5,3,'A2',1,'103房间A2','2025-12-16 19:26:36','2025-12-16 19:26:36'),(6,3,'A3',0,'103房间A3','2025-12-16 19:27:00','2025-12-16 19:27:00'),(7,3,'A4',0,'103房间A4','2025-12-16 19:27:13','2025-12-16 19:27:13'),(8,4,'A1',0,'104房间A1床位','2025-12-16 19:30:40','2025-12-16 19:30:40'),(9,4,'A2',0,'104房间A2床位','2025-12-16 19:31:37','2025-12-16 19:31:37'),(10,4,'A3',0,'104房间A3床位','2025-12-16 19:31:51','2025-12-16 19:31:51'),(11,4,'A4',0,'104房间A4床位','2025-12-16 19:32:03','2025-12-16 19:32:03'),(12,2,'A2',1,'102房间A2床位','2025-12-16 19:36:53','2025-12-16 19:54:35'),(13,1,'A4',0,'101房间A4床位','2025-12-16 19:52:43','2025-12-16 19:53:56'),(14,1,'A3',0,'104房间A3床位','2025-12-16 19:53:12','2025-12-16 19:53:12'),(15,2,'A3',1,'102房间A3床位','2025-12-16 19:54:54','2025-12-16 19:54:54'),(16,2,'A4',0,'102房间A4床位','2025-12-16 19:55:07','2025-12-16 19:55:07');
/*!40000 ALTER TABLE `t_bed` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_bill`
--

DROP TABLE IF EXISTS `t_bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_bill` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '缴费ID',
  `live_id` int DEFAULT NULL COMMENT '入住ID',
  `month` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '月份',
  `bed_cost` double(11,2) DEFAULT NULL COMMENT '床位费',
  `care_cost` double(11,2) DEFAULT NULL COMMENT '照护费',
  `food_cost` double(11,2) DEFAULT NULL COMMENT '餐饮费',
  `total_cost` double(11,2) DEFAULT NULL COMMENT '总费用',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '备注',
  `status` int DEFAULT NULL COMMENT '状态：0待缴费，1已缴费',
  `create_time` datetime DEFAULT NULL COMMENT '添加时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `live_id` (`live_id`) USING BTREE,
  CONSTRAINT `t_bill_ibfk_1` FOREIGN KEY (`live_id`) REFERENCES `t_live` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='缴费账单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_bill`
--

LOCK TABLES `t_bill` WRITE;
/*!40000 ALTER TABLE `t_bill` DISABLE KEYS */;
INSERT INTO `t_bill` VALUES (1,1,'2025-03',2000.00,1000.00,1000.00,4000.00,'3月份养老账单已出。',1,'2025-04-20 17:13:07','2025-04-20 17:13:07'),(2,3,'2025-03',2000.00,1000.00,1000.00,4000.00,'3月份养老账单已出，麻烦核对缴纳一下。',1,'2025-04-20 22:44:23','2025-04-20 22:44:23'),(3,1,'2025-11',10.00,12.00,12.00,34.00,'chs',0,'2025-12-31 09:57:05','2025-12-31 09:57:05'),(4,1,'2025-12',1.00,1.00,1.00,3.00,'1',0,'2025-12-31 09:57:36','2025-12-31 09:57:36');
/*!40000 ALTER TABLE `t_bill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_comment`
--

DROP TABLE IF EXISTS `t_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_comment` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `user_id` int DEFAULT NULL COMMENT '用户ID',
  `news_id` int DEFAULT NULL COMMENT '新闻ID',
  `pid` int DEFAULT NULL COMMENT '父节点ID',
  `content` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '评论内容',
  `create_time` datetime DEFAULT NULL COMMENT '发布时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `post_id` (`news_id`) USING BTREE,
  CONSTRAINT `t_comment_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_comment_ibfk_2` FOREIGN KEY (`news_id`) REFERENCES `t_news` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='新闻评论表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_comment`
--

LOCK TABLES `t_comment` WRITE;
/*!40000 ALTER TABLE `t_comment` DISABLE KEYS */;
INSERT INTO `t_comment` VALUES (6,6,6,0,'家中 80 岁高龄的失能老人卧床多年，一直担心照护不到位，直到遇到张明华护工才彻底放心。她照顾老人细致入微，翻身拍背、口腔清洁等操作专业娴熟，还会根据老人吞咽困难的情况定制糊状营养餐，搭配穴位按摩缓解肢体僵硬，老人皮肤始终保持完好，从未出现压疮。更难得的是她的耐心与温情，每天都会陪老人读报纸、聊往事，化解老人的孤独感，现在老人精神状态越来越稳定，还会主动和家人分享聊天内容。从业十年的口碑果然名不虚传，把老人交给她，我们全家都特别安心，真心推荐给有老年照护需求的家庭！','2025-12-10 10:44:59','2025-12-10 10:44:59'),(7,1,7,0,'家人中风后偏瘫卧床，术后康复一直是难题，幸好找到了王志强护工。他专业能力极强，根据家人的恢复情况制定了个性化康复计划，从被动肢体按摩到扶走练习，每一步都科学有序。他还坚持每日记录康复进展，定期和康复医师沟通调整方案，教给我们正确的居家按摩手法和安全注意事项。在他的鼓励和专业照护下，家人只用了 4 个月就实现了独立行走和自主进食，恢复速度远超预期。王护工不仅康复技术过硬，还特别注重患者心理建设，总能用积极的态度感染家人，是康复护理领域的靠谱之选！','2025-12-10 10:46:47','2025-12-10 10:46:47'),(8,2,8,0,'家人重症术后在 ICU 需要 24 小时陪护，刘芳护工的专业与责任让我们全家深受感动。她熟练操作各类重症护理设备，心电监护、吸痰护理、导管维护等操作精准规范，严格遵循无菌原则，确保护理过程零感染。她对生命体征变化格外敏感，一次深夜家人心率骤降，她冷静处置，及时调整体位、清理呼吸道，为医护人员救治争取了关键时间。值守期间，她定时为家人擦拭身体、更换衣物，用轻声安抚缓解患者的恐惧，每天还会详细反馈病情和饮食睡眠情况，让我们清晰了解家人状态。有三甲医院医护团队认可的专业资质，又有丰富的临床陪护经验，刘护工是重症患者陪护的安心之选！','2025-12-10 10:49:25','2025-12-10 10:49:25'),(9,3,9,0,'母亲长期卧床且患有糖尿病，需要全方位居家护理，陈秀英护工的服务让我们彻底摆脱了照护困扰。她的压疮预防 “三重防护法” 特别有效，母亲卧床两年多从未出现皮肤问题。用药管理上，她分类装盒、定时提醒，还详细记录用药反应，及时反馈给医生；健康监测时定期测量血压、血糖，建立专属健康档案，数据异常会第一时间预警。她还根据母亲的病情定制低糖营养餐，兼顾营养与口感。日常相处中，她贴心陪伴母亲聊天、散步，缓解孤独感，还主动传授伤口清洁、正确翻身等护理技巧。12 年的居家护理经验让她做事细致周到，像 “家庭护理管家” 一样靠谱，全家都特别依赖她！','2025-12-10 10:51:31','2025-12-10 10:51:31'),(10,4,11,0,'父亲患有阿尔茨海默症，情绪波动大且有记忆障碍，周文涛护工的专业照护让我们看到了明显改善。他接受过系统的认知症护理培训，运用怀旧疗法、音乐疗愈等特色方式，收集父亲的老照片、经典曲目，帮助唤醒记忆、稳定情绪。面对父亲的忘事和暴躁，他从不指责，而是耐心引导、转移注意力；为了防范走失风险，还贴心建议佩戴定位手环、设置居家标识物。他还注重训练父亲的自理能力，通过穿衣、洗漱等日常活动引导，帮助维持自理意识。此外，他定期组织家属沟通会，科普照护知识，帮助我们调整心态。经他照护后，父亲情绪稳定性显著提升，还能自主完成简单生活事务，是阿尔茨海默症老人照护的专业靠谱人选！','2025-12-10 10:52:45','2025-12-10 10:52:45'),(11,1,11,0,'AAAA','2026-03-20 22:28:02','2026-03-20 22:28:02');
/*!40000 ALTER TABLE `t_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_employee`
--

DROP TABLE IF EXISTS `t_employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_employee` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '工号',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '密码',
  `nickname` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '姓名',
  `sex` int DEFAULT NULL COMMENT '性别：0男，1女',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `telephone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '电话',
  `email` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '邮箱',
  `entry_time` date DEFAULT NULL COMMENT '入职日期',
  `address` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '籍贯',
  `status` int DEFAULT NULL COMMENT '0正常，1冻结',
  `create_time` datetime DEFAULT NULL COMMENT '录入时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='员工信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_employee`
--

LOCK TABLES `t_employee` WRITE;
/*!40000 ALTER TABLE `t_employee` DISABLE KEYS */;
INSERT INTO `t_employee` VALUES (1,'E0001','123456','张明华',1,'1991-06-03','12111111111','1211@qq.com','2020-07-25','江西南昌',0,'2025-04-20 01:04:00','2025-12-03 10:48:41'),(2,'E0002','123456','刘芳',1,'1997-06-19','12111111112','1212@qq.com','2023-07-12','江西赣州',0,'2025-04-20 22:00:55','2025-12-03 10:49:17'),(3,'E0003','123456','王志强',0,'1995-06-06','12111111113','1213@qq.com','2026-12-02','广东广州',0,'2025-04-20 22:36:09','2025-12-03 10:48:30'),(4,'E0004','123456','陈秀英',1,'1996-12-04','13894848942','123343@qq.com','2020-12-17','上海',0,'2025-12-03 10:51:12','2025-12-03 10:58:39'),(5,'E0005','123456','周文涛',0,'1987-12-10','19939003033','j1883930@qq.com','2022-12-07','北京',0,'2025-12-03 10:52:22','2025-12-03 10:52:22');
/*!40000 ALTER TABLE `t_employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_feedback`
--

DROP TABLE IF EXISTS `t_feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_feedback` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '留言反馈ID',
  `user_id` int DEFAULT NULL COMMENT '学生ID',
  `content` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '留言内容',
  `status` int DEFAULT NULL COMMENT '状态：0待回复，1已回复',
  `reply` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '回复',
  `create_time` datetime DEFAULT NULL COMMENT '提交时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE,
  CONSTRAINT `t_feedback_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_feedback`
--

LOCK TABLES `t_feedback` WRITE;
/*!40000 ALTER TABLE `t_feedback` DISABLE KEYS */;
INSERT INTO `t_feedback` VALUES (6,6,'张明华护工照顾老人很到位',0,NULL,'2025-12-10 10:30:31','2025-12-10 10:30:31'),(7,6,'专业度拉满，翻身拍背、压疮预防等操作娴熟，还会定制营养餐和穴位按摩。性格温润有耐心，常陪老人聊天解闷，照护卧床老人超靠谱，家属很放心。',0,NULL,'2025-12-10 10:45:43','2025-12-10 10:45:43'),(8,1,'王志强复方案个性化，手法专业，记录与沟通及时，还教家属居家康复技巧。鼓励患者有方法，助力术后 / 中风患者快速恢复，值得信赖。',0,NULL,'2025-12-10 10:47:35','2025-12-10 10:47:35'),(9,2,'刘芳护工重症护理经验足、资质全，设备操作熟练，应急处置冷静高效。24 小时值守贴心，病情反馈详细，守护重症患者安全超给力。',0,NULL,'2025-12-10 10:50:42','2025-12-10 10:50:42'),(10,3,'陈秀英压疮预防、用药管理、健康监测都专业，营养餐定制贴合病情。服务细致贴心，还传授护理技巧，是靠谱的居家护理管家。',0,NULL,'2025-12-10 10:51:58','2025-12-10 10:51:58'),(11,4,'周文涛特色照护方法有效，能稳定老人情绪、维持自理能力。应对记忆障碍、走失风险有办法，还科普照护知识，家属特别认可。',0,NULL,'2025-12-10 10:53:21','2025-12-10 10:53:21'),(12,1,'aaaaaaa',0,NULL,'2026-03-20 22:27:39','2026-03-20 22:27:39');
/*!40000 ALTER TABLE `t_feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_leave`
--

DROP TABLE IF EXISTS `t_leave`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_leave` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '外出报备ID',
  `live_id` int DEFAULT NULL COMMENT '入住ID',
  `begin_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '外出事由',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '审核状态：0待审核，1已通过，2已驳回',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '审核意见',
  `create_time` datetime DEFAULT NULL COMMENT '添加时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `live_id` (`live_id`) USING BTREE,
  CONSTRAINT `t_leave_ibfk_1` FOREIGN KEY (`live_id`) REFERENCES `t_live` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='外出报备表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_leave`
--

LOCK TABLES `t_leave` WRITE;
/*!40000 ALTER TABLE `t_leave` DISABLE KEYS */;
INSERT INTO `t_leave` VALUES (1,1,'2025-04-21 09:00:00','2025-04-21 16:00:00','出去有点事。','1','通过','2025-04-20 16:32:46','2025-04-20 22:10:23'),(2,3,'2025-04-21 09:00:00','2025-04-21 15:00:00','需要外出一趟，会按时回来。','1','通过','2025-04-20 22:43:27','2025-04-20 22:43:43');
/*!40000 ALTER TABLE `t_leave` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_like`
--

DROP TABLE IF EXISTS `t_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_like` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '点赞ID',
  `user_id` int DEFAULT NULL COMMENT '用户ID',
  `news_id` int DEFAULT NULL COMMENT '新闻ID',
  `create_time` datetime DEFAULT NULL COMMENT '点赞时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `post_id` (`news_id`) USING BTREE,
  CONSTRAINT `t_like_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_like_ibfk_2` FOREIGN KEY (`news_id`) REFERENCES `t_news` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='新闻点赞表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_like`
--

LOCK TABLES `t_like` WRITE;
/*!40000 ALTER TABLE `t_like` DISABLE KEYS */;
INSERT INTO `t_like` VALUES (5,1,11,'2025-12-03 11:14:31','2025-12-03 11:14:31'),(6,1,8,'2025-12-03 11:32:50','2025-12-03 11:32:50'),(7,1,7,'2025-12-03 11:32:54','2025-12-03 11:32:54'),(8,1,9,'2025-12-03 11:32:57','2025-12-03 11:32:57'),(9,1,6,'2025-12-03 11:33:02','2025-12-03 11:33:02'),(10,6,6,'2025-12-10 10:44:55','2025-12-10 10:44:55'),(11,2,8,'2025-12-10 10:49:22','2025-12-10 10:49:22'),(12,3,9,'2025-12-10 10:51:15','2025-12-10 10:51:15'),(13,4,11,'2025-12-10 10:52:44','2025-12-10 10:52:44');
/*!40000 ALTER TABLE `t_like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_live`
--

DROP TABLE IF EXISTS `t_live`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_live` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '入住登记ID',
  `user_id` int DEFAULT NULL COMMENT '用户ID',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '老人姓名',
  `sex` int DEFAULT NULL COMMENT '老人性别',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '联系电话',
  `bed_id` int DEFAULT NULL COMMENT '床位ID',
  `begin_time` date DEFAULT NULL COMMENT '入住时间',
  `end_time` date DEFAULT NULL COMMENT '离开时间',
  `content` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '注意事项',
  `status` int DEFAULT NULL COMMENT '状态：0入住中，1已离开',
  `remark` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '退房备注',
  `create_time` datetime DEFAULT NULL COMMENT '添加时间',
  `update_time` datetime DEFAULT NULL COMMENT '更细时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `user_id` (`user_id`) USING BTREE,
  KEY `bed_id` (`bed_id`) USING BTREE,
  CONSTRAINT `t_live_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `t_live_ibfk_2` FOREIGN KEY (`bed_id`) REFERENCES `t_bed` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='入住登记表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_live`
--

LOCK TABLES `t_live` WRITE;
/*!40000 ALTER TABLE `t_live` DISABLE KEYS */;
INSERT INTO `t_live` VALUES (1,1,'张三丰',0,'1960-07-07','12111111111',1,'2025-04-21','2025-04-24','老人有一点高血压，需要注意一下。',1,'退房了。','2025-04-20 14:41:22','2025-04-20 14:41:22'),(2,3,'李小丽',1,'1962-03-01','12111111113',2,'2025-04-19',NULL,'老人有一点高血压，需要注意下。',0,NULL,'2025-04-20 22:30:54','2025-04-20 22:30:54'),(3,4,'宋大杰',0,'1963-04-11','12111111114',3,'2025-04-19','2025-04-21','老人有一点高血压，注意一点。',1,'退房了。','2025-04-20 22:41:06','2025-04-20 22:41:06'),(4,2,'林秀莲',1,'1957-09-05','12111111119',12,'2024-12-12',NULL,'老人肠胃较弱，饮食需清淡软烂',0,NULL,'2025-12-16 20:04:03','2025-12-16 20:04:14'),(5,3,'马德福',0,'1962-10-12','12111111120',15,'2025-09-20',NULL,'老人听力稍弱，沟通需提高音量',0,NULL,'2025-12-16 20:05:31','2025-12-16 20:05:31'),(6,4,'孙玉芬',1,'1959-03-24','12111111120',4,'2025-08-15',NULL,'老人日常需服用降压药，按时提醒',0,NULL,'2025-12-16 20:06:48','2025-12-16 20:06:48'),(7,6,'周卫国',0,'1960-05-17','12111111122',5,'2025-10-09',NULL,'老人有轻微哮喘，避免接触粉尘',0,NULL,'2025-12-16 20:07:47','2025-12-16 20:07:47');
/*!40000 ALTER TABLE `t_live` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_news`
--

DROP TABLE IF EXISTS `t_news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_news` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '文章ID',
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '主题',
  `brief` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '简介',
  `cover` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '封面',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '内容',
  `click_num` int DEFAULT NULL COMMENT '点击量',
  `like_num` int DEFAULT NULL COMMENT '点赞量',
  `comment_num` int DEFAULT NULL COMMENT '评论量',
  `create_time` datetime DEFAULT NULL COMMENT '添加时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='政策新闻表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_news`
--

LOCK TABLES `t_news` WRITE;
/*!40000 ALTER TABLE `t_news` DISABLE KEYS */;
INSERT INTO `t_news` VALUES (6,'张明华',' 护工号：E0001\n专业老年护理护工：拥有10年老年护理经验，擅长失能老人日常照护与康复陪伴。','http://localhost:8080/files/news/bd67afcbd5484bf3a8f3ecedd2b94c29.png','<p>拥有 10 年老年护理经验，深耕失能、半失能及高龄老人照护领域，擅长日常起居照料、慢性病辅助管理与康复陪伴，累计服务超 50 位老年患者，口碑良好。</p><p>内容： 张明华护工专注老年护理十年，不仅熟练掌握翻身拍背、喂药喂食、口腔清洁、压疮预防等核心技能，还针对失能老人吞咽困难、肢体僵硬等问题形成专属照护方案 —— 如将食物加工为易吞咽的糊状，搭配针灸穴位按摩缓解肌肉萎缩。她性格温润有耐心，每天会花 1-2 小时陪老人读报纸、聊往事，用细腻的心理疏导化解老人的孤独感与焦虑情绪。从业以来，她累计收到 30 余封家属感谢信，曾成功照护一位卧床 8 年的阿尔茨海默症老人，使其始终保持皮肤完好、精神状态稳定，凭借 “把老人当亲人” 的初心，成为众多家庭的安心之选，致力于让每位老人都能有尊严、有质量地安享晚年。</p>',5,2,1,'2025-12-03 10:43:32','2025-12-29 09:45:31'),(7,'王志强','护工号：E0002\n康复护理护工:专注术后及中风患者康复护理，帮助患者逐步恢复生活自理能力。','http://localhost:8080/files/news/1e7f4978a79a426a80045510252fd055.png','<p>王志强护工深耕康复护理领域，精通关节活动度训练、平衡训练、步态矫正、语言吞咽训练等专业手法，能根据患者术后恢复阶段或中风偏瘫程度，制定个性化康复计划。针对术后卧床患者，他从被动肢体按摩、翻身训练起步，逐步过渡到主动起身、扶走练习，配合康复器械辅助，加速患者肌肉力量恢复；对于中风后失语、口齿不清的患者，他通过发音练习、实物认知等方式辅助语言康复。工作中，他坚持每日记录患者康复进展，每周与康复医师沟通调整方案，曾帮助一位中风偏瘫患者在 4 个月内实现独立行走、自主进食。同时，他格外注重患者心理建设，用 “小进步积累大康复” 的理念鼓励患者，还向家属系统科普居家康复技巧，如正确的按摩手法、康复训练的安全注意事项，构建全方位的康复支持体系。</p>',4,1,1,'2025-12-03 10:44:36','2025-12-29 09:43:59'),(8,'刘芳','护工号：E0003\n重症监护陪护:具有ICU护理经验，能够提供24小时密切监护与专业护理服务。','http://localhost:8080/files/news/a575eb24e8064c36ad61230d6de099be.png','<p>刘芳护工熟练操作心电监护仪、呼吸机、吸痰器等重症护理设备，精准掌握心电监护、气道管理、吸痰护理、导管维护、静脉输液辅助等核心操作，严格遵循无菌操作规范，确保患者护理过程零感染。她对重症患者的生命体征变化极为敏感，能快速识别心率异常、血压波动、呼吸困难等危险信号，多次在深夜突发情况中，凭借冷静处置为医护人员救治争取时间 —— 曾成功协助抢救一位心率骤降的重症患者，通过及时调整体位、清理呼吸道，为后续救治赢得关键 3 分钟。24 小时值守期间，她不仅定时为患者擦拭身体、更换衣物、整理床单位，还会用轻声细语的安抚缓解患者的恐惧与不适，同时每日向家属详细反馈患者病情、饮食睡眠情况，搭建起医护与家属之间的信任桥梁，用专业与责任守护重症患者的生命安全。</p>',7,2,1,'2025-12-03 10:45:33','2025-12-29 09:45:24'),(9,'陈秀英','护工号：E0004\n居家全程护理护工:提供长期居家护理服务，涵盖生活照料、用药管理、健康监测等全方位支持。','http://localhost:8080/files/news/754972e5a8784c4e9e0976d8f6e587fd.png','<p>陈秀英护工在长期居家护理中积累了丰富经验，尤其擅长压疮预防，总结出 “床垫选择 + 定时翻身 + 皮肤养护” 的三重防护法，服务过的 20 余位长期卧床患者均未出现压疮问题。在用药管理方面，她会将药物按 “早中晚” 分类装盒，设置手机定时提醒，同时详细记录患者用药后的反应，及时反馈给主治医生；健康监测上，她定期为患者测量体温、血压、血糖，建立专属健康档案，一旦发现数据异常立即预警。生活照料中，她擅长根据患者病情（如糖尿病、高血压、肾病）定制营养餐，兼顾营养与口感，如为糖尿病患者制作低糖杂粮饭，为肾病患者搭配低盐低钾食谱。此外，她每天会花时间陪患者聊天、散步，缓解其居家孤独感，还主动向家属传授简单护理技巧，如伤口清洁、正确翻身姿势等，以 “细致、贴心、专业” 的服务理念，成为多个家庭长期依赖的 “居家护理管家”，收获无数好评。</p>',3,2,1,'2025-12-03 10:46:39','2025-12-10 10:57:29'),(11,'周文涛','护工号：E0005\n阿尔茨海默症专项护理:专攻认知症老人护理，善于通过怀旧疗法、音乐疗愈等方式缓解病情。','http://localhost:8080/files/news/991f04177298485584e77dd7b43e9493.png','<p>周文涛护工深耕阿尔茨海默症照护领域，不仅系统掌握认知症相关医学知识，还熟练运用多种特色照护方法 —— 怀旧疗法中，他会提前收集老人的老照片、工作证件、年代物品，通过一起翻看、讲述过往故事唤醒记忆；音乐疗愈时，他精选老人年轻时喜爱的经典曲目，每日定时播放，搭配肢体律动，稳定老人情绪。面对老人的记忆障碍、情绪波动、走失倾向等问题，他有一套科学应对方案：老人忘记事情时，耐心重复引导而非指责；情绪暴躁时，通过深呼吸练习、户外散步转移注意力；有走失风险时，为老人佩戴定位手环，同时在居家环境中设置熟悉的标识物。他还注重为老人保留基本生活能力，通过穿衣、洗漱、进食等日常活动的引导训练，帮助老人维持自理意识。此外，他定期组织家属沟通会，科普认知症发展规律、居家照护注意事项，帮助家属调整心态，共同构建包容、安全的照护环境，经他照护的老人，情绪稳定性显著提升，部分老人仍能自主完成简单生活事务，深受家属信赖与认可。</p>',27,2,2,'2025-12-03 10:47:22','2026-03-20 22:27:51');
/*!40000 ALTER TABLE `t_news` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_notice`
--

DROP TABLE IF EXISTS `t_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_notice` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '主题',
  `content` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '内容',
  `create_time` datetime DEFAULT NULL COMMENT '添加时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=COMPACT COMMENT='公告信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_notice`
--

LOCK TABLES `t_notice` WRITE;
/*!40000 ALTER TABLE `t_notice` DISABLE KEYS */;
INSERT INTO `t_notice` VALUES (6,'陈秀英护工休假','<p>陈秀英护工正在休假</p>','2025-12-10 11:39:39','2025-12-10 11:39:39');
/*!40000 ALTER TABLE `t_notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_resthome`
--

DROP TABLE IF EXISTS `t_resthome`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_resthome` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '场馆ID',
  `title` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '场馆名称',
  `begin_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '开园时间',
  `end_time` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '闭园时间',
  `phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '联系电话',
  `address` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '场馆地址',
  `cover` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '场馆照片',
  `brief` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '场馆简介',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '场馆详情',
  `click_num` int DEFAULT NULL COMMENT '访问量',
  `create_time` datetime DEFAULT NULL COMMENT '添加时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='入院指南表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_resthome`
--

LOCK TABLES `t_resthome` WRITE;
/*!40000 ALTER TABLE `t_resthome` DISABLE KEYS */;
INSERT INTO `t_resthome` VALUES (1,'凯健华展养老院','06:00','22:00','027-8290647','武汉市江岸区后湖街道幸福社区底商（近地铁 3 号线后湖站）','http://localhost:8080/files/resthome/cbaac1bf821647c3a685d97f2f792ce4.png','武汉江岸区后湖街道养老院（幸福社区服务点）是经江岸区民政局备案的普惠型养老机构，隶属于后湖街道民生服务体系，作为嵌入社区的“家门口养老院”，机构最大亮点在于“便利化生活圈”布局，完美适配长者日常需求与家属照护便利性：交通方面十分便捷，紧邻地铁3号线后湖站，周边有592路、623路、725路等6条公交线路停靠，社区公交“微循环”线路直达门口，方便长者独立出行及家属高频探访。','<p>社区嵌入式养老的民生范本——探访武汉江岸区后湖街道养老院（幸福社区服务点）\n在城市养老需求日益多元的当下，“养老不离社区、服务就在身边”成为众多长者与家庭的期盼。武汉江岸区后湖街道养老院（幸福社区服务点），作为经江岸区民政局严格备案的普惠型养老机构，深度融入后湖街道民生服务体系，以“家门口的养老院”为核心定位，凭借创新性的“便利化生活圈”布局与全方位的适老服务，成为社区嵌入式养老模式的优质标杆，让普惠养老既有温度，更有质感。\n\n## 交通便捷：三位一体网络，连接亲情与世界\n对于长者而言，便捷的交通不仅意味着独立出行的自由，更关乎与家人的情感联结。这家养老院在交通规划上精准对接需求，构建起地铁、公交、社区微循环“三位一体”的立体交通网络。机构紧邻地铁3号线后湖站，步行8分钟即可抵达，无缝衔接武汉轨道交通脉络，无论是长者前往市区就医、购物，还是家属跨区探访，都无需辗转奔波；周边500米范围内，592路、623路、725路等6条公交线路密集分布，站点覆盖汉口核心商圈、政务服务中心等关键区域，班次稳定、候车时间短，满足长者多样化出行需求；更贴心的是，社区公交“微循环”专线直达机构大门口，站点距离院门不足30米，线路全程无陡坡、无急弯，车辆配备无障碍上下车设施，即便行动迟缓或借助轮椅的长者，也能轻松实现独立出行，无需家属额外陪同。便捷的交通让家属探望不再受距离限制，工作日午休、周末闲暇时光，随时都能抵达陪伴，让亲情在高频互动中愈发浓厚。\n\n## 生活配套：5分钟生活圈，解锁便捷日常\n以养老院为中心，一个覆盖衣食住行的“5分钟步行生活圈”已然成型，最大化保障了长者的生活自主性与便利性。步行3分钟，便可达幸福社区标准化菜场，摊位整齐有序，新鲜蔬菜、肉类、禽蛋、豆制品等食材一应俱全，菜场专门设置了无障碍通道、低位收银台和便民称重设备，方便长者自主挑选；隔壁的社区平价药店，常用药品、保健品、血糖仪、轮椅等医疗辅助器具琳琅满目，专业药师坐诊提供用药咨询、剂量指导等服务，遇到感冒、咳嗽等小病痛，无需远走就能及时购药。步行5分钟范围内，中百仓储超市（后湖店）满足一站式购物需求，生活用品、食品饮料、家居百货等品类丰富，超市内开辟长者专属购物区，货架高度适中、标价清晰醒目，部分商品还支持送货上门服务，切实解决行动不便长者的购物难题。对于不便外出的长者，机构护理员会提前统计购物需求，定期代为采购，确保生活物资供应不中断。此外，距离机构1.2公里的后湖城市广场商圈，涵盖大型购物中心、连锁餐饮、银行网点、邮政快递等多元业态，驾车3分钟或乘坐公交1站路即可到达，为长者提供购物休闲、社交娱乐、金融服务等进阶需求，让养老生活不再局限于小院之内，而是与城市繁华无缝衔接。\n\n## 医疗保障：近距优质资源，筑牢健康防线\n健康安全是养老服务的核心底色，这家养老院在医疗资源对接上实现了“近距便捷”与“优质可靠”的双重保障。与机构直线距离仅600米的后湖街惠民社区卫生服务站，步行10分钟即可到达，服务站配备专业医护团队，提供常见病诊疗、慢性病管理、疫苗接种、健康体检等基础医疗服务。更重要的是，机构与服务站建立了绿色转诊通道，长者日常就医、慢病随访无需长时间排队等候，医护人员还可根据需求上门开展巡诊、换药、血压血糖监测等服务，让基础医疗服务触手可及。对于突发紧急健康问题，机构同样有完善的应对方案——距离武汉市中心医院后湖院区（三甲医院）仅2公里，驾车5分钟或乘坐公交2站路便能抵达。这家医院设有老年病科、急诊科、康复医学科等重点科室，配备先进的医疗设备和专业的救治团队，为心脑血管疾病、外伤等突发情况提供快速救治保障。机构与医院建立应急联动机制，一旦发生紧急情况，可第一时间对接120急救体系，确保“黄金救治时间”不延误。此外，机构还定期邀请社区卫生服务站的医护人员开展健康讲座，内容涵盖高血压、糖尿病防治、合理膳食、冬季保暖、跌倒预防等实用主题，通过案例讲解、现场示范等形式，提升长者的自我健康管理能力。\n\n## 居住环境：适老设计赋能，舒适与安全兼具\n走进养老院，处处能感受到“实用舒适、安全优先”的设计理念。机构根据不同健康状况长者的需求，规划了单人间、双人间、多人间等多种房型，房间面积适中，通风采光条件优越，家具摆放预留出充足的活动空间，有效避免磕碰隐患。所有房间均进行了深度适老化改造：地面采用防滑耐磨的PVC材质，即便沾水也不易滑倒；走廊及房间内安装了连续式扶手，高度贴合人体工学，方便长者起身、行走时借力；床头配备紧急呼叫按钮，一旦发生意外，可即时联系护理人员；卫生间内设置防滑垫、坐式淋浴器、马桶扶手等辅助设施，卧室与卫生间之间无高低门槛，确保轮椅能够顺畅通行。房间内还配备了恒温空调、独立储物柜、紧急照明设备等基础硬件，床头插座高度经过优化，方便长者充电使用，门窗采用隔音材质，保障休息环境安静舒适。\n\n公共区域的设计同样贴心周到：一楼大厅设有接待区、休息区，摆放着柔软的沙发、饮水机、放大镜阅读灯等便民设施，成为长者日常交流的小天地；楼层走廊宽敞明亮，两侧扶手连续贯通，墙面设置清晰的导向标识和温馨提示；户外开辟了小型活动庭院，种植着四季常青的绿植和各色花卉，配备休闲座椅、慢步跑道，供长者日常散步、晒太阳、下棋聊天，在安全保障的基础上，让长者充分享受惬意的户外时光。\n\n从交通便利到生活配套，从医疗保障到居住环境，武汉江岸区后湖街道养老院（幸福社区服务点）以“嵌入式”布局打破了传统养老院的封闭格局，将养老服务与社区资源深度融合。它不仅为长者提供了专业、舒适的养老场所，更守护了长者的生活自主性与尊严，为家属减轻了照护压力，真正实现了“让长者舒心、让家属放心”的养老服务目标。作为武汉江岸区普惠型养老机构的典范，它用实际行动诠释了“社区养老”的核心价值，也为城市养老服务体系的完善提供了可借鉴的实践样本。</p>',0,'2025-04-20 00:40:43','2025-12-26 18:55:51');
/*!40000 ALTER TABLE `t_resthome` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_room`
--

DROP TABLE IF EXISTS `t_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_room` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '房间ID',
  `room_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '房间名称',
  `bed_num` int DEFAULT NULL COMMENT '床位总数',
  `remark` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '房间备注',
  `create_time` datetime DEFAULT NULL COMMENT '添加时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='房间信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_room`
--

LOCK TABLES `t_room` WRITE;
/*!40000 ALTER TABLE `t_room` DISABLE KEYS */;
INSERT INTO `t_room` VALUES (1,'101房间',4,'101房间，4个床位','2025-04-20 07:57:31','2025-04-20 13:22:28'),(2,'102房间',4,'102房间，4个床位','2025-04-20 13:22:11','2025-04-20 13:22:23'),(3,'103房间',4,'103房间，4个床位','2025-04-20 22:36:33','2025-04-20 22:36:36'),(4,'104房间',4,'104房间，4个床位','2025-12-16 19:24:57','2025-12-16 19:24:57');
/*!40000 ALTER TABLE `t_room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '准考证号',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '密码',
  `nickname` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '姓名',
  `telephone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '手机号',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '邮箱',
  `sex` int DEFAULT NULL COMMENT '性别：0男，1女',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `address` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '地址',
  `avatar` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '头像',
  `amount` double DEFAULT NULL COMMENT '余额',
  `status` int DEFAULT NULL COMMENT '录取状态：0未录取，1已录取，2已调剂',
  `create_time` datetime DEFAULT NULL COMMENT '录入时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=COMPACT COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user`
--

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` VALUES (1,'2025001','123456','李志强','13544556677','lizq@example.com',0,'1960-07-06','成都市武侯区','http://localhost:8080/files/avatar/cbcad8bc9b5147538d7ee8f5dfe31062.jpg',6000,0,'2025-04-20 00:50:24','2025-12-03 11:58:59'),(2,'2025002','123456','王晓琳','13711223344','3200wangxl@example.com',0,'1993-04-16','杭州市西湖区','http://localhost:8080/files/avatar/24fba3ab7e944fc2a5246622af105cf7.jpg',0,0,'2025-04-20 21:59:48','2025-12-03 11:58:05'),(3,'2025003','123456','陈建国','13698765432','chengjg@example.com',0,'1994-05-03','广州市天河区','http://localhost:8080/files/avatar/8a88ee760d0c47a5a14bbbad7986457c.jpg',10000,0,'2025-04-20 22:24:09','2025-12-03 11:57:19'),(4,'2025004','123456','刘思雨','13912345678','liusy@example.com',1,'1994-05-02','广东深圳','http://localhost:8080/files/avatar/3e253958bd514d11a0d254c872abc2b4.jpg',6000,0,'2025-04-20 22:38:22','2025-12-03 11:56:28'),(6,'2025005','123456','赵明华','13800138001','zhaomh@example.com',0,'1994-12-07','北京市朝阳区','http://localhost:8080/files/avatar/06a979a1fadf471c87102dd1a60109ca.jpg',0,0,'2025-12-03 11:55:34','2025-12-26 18:54:39');
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_visit`
--

DROP TABLE IF EXISTS `t_visit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_visit` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '访客登记ID',
  `live_id` int DEFAULT NULL COMMENT '走访对象ID',
  `visitor_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '来访人姓名',
  `visitor_phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '来访人电话',
  `visitor_card` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '来访人证件',
  `visit_time` datetime DEFAULT NULL COMMENT '来访时间',
  `leave_time` datetime DEFAULT NULL COMMENT '离开时间',
  `content` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '来访事由',
  `status` int DEFAULT NULL COMMENT '状态：0来访中，1已离开',
  `create_time` datetime DEFAULT NULL COMMENT '添加时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `live_id` (`live_id`) USING BTREE,
  CONSTRAINT `t_visit_ibfk_1` FOREIGN KEY (`live_id`) REFERENCES `t_live` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='来访登记表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_visit`
--

LOCK TABLES `t_visit` WRITE;
/*!40000 ALTER TABLE `t_visit` DISABLE KEYS */;
INSERT INTO `t_visit` VALUES (1,1,'张三','13111111111','111111111111111111','2025-04-20 16:04:14','2025-04-20 20:34:42','看望一下老朋友了。',1,'2025-04-20 16:04:14','2025-04-20 20:33:50'),(2,3,'李小茜','13111111112','111111111111111112','2025-04-20 22:42:19','2025-04-20 22:42:41','有点事情需要拜访一下。',1,'2025-04-20 22:42:19','2025-04-20 22:42:19');
/*!40000 ALTER TABLE `t_visit` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-06 23:03:21
