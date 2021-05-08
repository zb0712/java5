/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.5.54 : Database - java5
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`java5` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `java5`;

/*Table structure for table `admin` */

DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
  `admin_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `admin_name` varchar(255) DEFAULT NULL,
  `admin_password` varchar(255) DEFAULT NULL,
  `department_id` bigint(20) DEFAULT NULL,
  `role` varchar(255) NOT NULL DEFAULT 'manager',
  PRIMARY KEY (`admin_id`),
  KEY `department_id` (`department_id`),
  CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`department_id`) REFERENCES `department` (`department_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `admin` */

insert  into `admin`(`admin_id`,`admin_name`,`admin_password`,`department_id`,`role`) values (1,'admin','$2a$10$csVdc2Iyfn.NFEha0/6Mqe52GGh4v9LlzgXe2zu1Y9c2UsWyrZ/V6',NULL,'super'),(3,'IOSmanager','$2a$10$2ot44gorJeS069GP9TgvZ.487yE2Fpo84/va7hjDOw0n2I4YQt6lG',5,'manager');

/*Table structure for table `answer` */

DROP TABLE IF EXISTS `answer`;

CREATE TABLE `answer` (
  `answer_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `question_id` bigint(20) DEFAULT NULL,
  `stu_id` bigint(20) DEFAULT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `questionnaire_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`answer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

/*Data for the table `answer` */

insert  into `answer`(`answer_id`,`question_id`,`stu_id`,`answer`,`questionnaire_id`) values (34,9,7,'ans1',4),(35,10,7,'ans2',4);

/*Table structure for table `department` */

DROP TABLE IF EXISTS `department`;

CREATE TABLE `department` (
  `department_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `department_name` varchar(255) NOT NULL,
  `department_url` varchar(255) NOT NULL,
  `introduction` varchar(255) DEFAULT NULL,
  `questionnaire_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`department_id`),
  KEY `questionnaire_id` (`questionnaire_id`),
  CONSTRAINT `department_ibfk_2` FOREIGN KEY (`questionnaire_id`) REFERENCES `questionnaire` (`questionnaire_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `department` */

insert  into `department`(`department_id`,`department_name`,`department_url`,`introduction`,`questionnaire_id`) values (5,'Java部门','http://www.java.com','Java部门简介',4);

/*Table structure for table `exam` */

DROP TABLE IF EXISTS `exam`;

CREATE TABLE `exam` (
  `exam_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `exam_name` varchar(255) DEFAULT NULL,
  `exam_status` varchar(255) DEFAULT NULL,
  `department_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`exam_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `exam` */

insert  into `exam`(`exam_id`,`exam_name`,`exam_status`,`department_id`) values (4,'ios第一轮考核','overed',5),(5,'ios第2轮考核','overed',5),(6,'Java第一轮考核','overed',5);

/*Table structure for table `questionnaire` */

DROP TABLE IF EXISTS `questionnaire`;

CREATE TABLE `questionnaire` (
  `questionnaire_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `questionnaire_name` varchar(255) DEFAULT NULL,
  `begin_time` date DEFAULT NULL,
  `over_time` date DEFAULT NULL,
  `department_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`questionnaire_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `questionnaire` */

insert  into `questionnaire`(`questionnaire_id`,`questionnaire_name`,`begin_time`,`over_time`,`department_id`) values (3,'问卷1','2021-04-19','2021-04-19',5),(4,'问卷2','2021-04-19','2021-04-22',5);

/*Table structure for table `questions` */

DROP TABLE IF EXISTS `questions`;

CREATE TABLE `questions` (
  `question_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `questionnaire_id` bigint(20) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`question_id`),
  KEY `questionnaire_id` (`questionnaire_id`),
  CONSTRAINT `questions_ibfk_1` FOREIGN KEY (`questionnaire_id`) REFERENCES `questionnaire` (`questionnaire_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `questions` */

insert  into `questions`(`question_id`,`questionnaire_id`,`subject`) values (7,3,'问题1'),(8,3,'问题2'),(9,4,'问题1'),(10,4,'问题2');

/*Table structure for table `stu_department` */

DROP TABLE IF EXISTS `stu_department`;

CREATE TABLE `stu_department` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `stu_id` bigint(20) DEFAULT NULL,
  `department_id` bigint(20) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

/*Data for the table `stu_department` */

insert  into `stu_department`(`id`,`stu_id`,`department_id`,`status`) values (12,7,5,'failed');

/*Table structure for table `stu_exam` */

DROP TABLE IF EXISTS `stu_exam`;

CREATE TABLE `stu_exam` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `stu_id` bigint(20) DEFAULT NULL,
  `exam_id` bigint(20) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `stu_exam` */

insert  into `stu_exam`(`id`,`stu_id`,`exam_id`,`status`) values (3,7,4,'passed'),(4,7,5,'passed');

/*Table structure for table `student` */

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `stu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `stu_name` varchar(255) DEFAULT NULL,
  `stu_num` varchar(255) DEFAULT NULL,
  `stu_email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`stu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `student` */

insert  into `student`(`stu_id`,`stu_name`,`stu_num`,`stu_email`) values (7,'石致彬','031902514','1099441055@qq.com');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
