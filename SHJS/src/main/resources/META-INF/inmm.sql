/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.6.11 : Database - inmm
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`inmm` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `inmm`;

/*Table structure for table `sl_area` */

DROP TABLE IF EXISTS `sl_area`;

CREATE TABLE `sl_area` (
  `area_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '地区ID',
  `area_name` varchar(50) NOT NULL COMMENT '地区名称',
  `parent_id` int(11) DEFAULT NULL COMMENT '上级地区ID',
  `area_grade` int(1) NOT NULL COMMENT '地区等级',
  `building_x` float DEFAULT NULL COMMENT '平面图X坐标',
  `building_y` float DEFAULT NULL COMMENT '平面图Y坐标',
  `area_type` int(1) NOT NULL COMMENT '地区类型（0-行政，1-建筑）',
  `area_order` int(2) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`area_id`),
  KEY `FK_sl_area` (`parent_id`),
  CONSTRAINT `FK_sl_area` FOREIGN KEY (`parent_id`) REFERENCES `sl_area` (`area_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=gbk CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

/*Data for the table `sl_area` */

insert  into `sl_area`(`area_id`,`area_name`,`parent_id`,`area_grade`,`building_x`,`building_y`,`area_type`,`area_order`) values (1,'北京监狱管理局',NULL,1,NULL,NULL,1,1),(2,'北监',1,2,NULL,NULL,1,1),(3,'二监',1,2,NULL,NULL,1,2),(4,'医务楼',2,3,NULL,NULL,2,1),(5,'延庆',1,2,NULL,NULL,1,3),(6,'良乡',1,2,NULL,NULL,1,4),(7,'未管',1,2,NULL,NULL,1,5),(8,'女监',1,2,NULL,NULL,1,6),(9,'天河',1,2,NULL,NULL,1,7),(10,'柳林',1,2,NULL,NULL,1,8),(11,'前进',1,2,NULL,NULL,1,9),(12,'潮白',1,2,NULL,NULL,1,10),(13,'清园',1,2,NULL,NULL,1,11),(14,'金钟',1,2,NULL,NULL,1,12);

/*Table structure for table `sl_call` */

DROP TABLE IF EXISTS `sl_call`;

CREATE TABLE `sl_call` (
  `call_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编码',
  `imsi` varchar(16) NOT NULL COMMENT '发起通话手机卡号',
  `targ_num` varchar(16) NOT NULL COMMENT '对方号码',
  `record_date` datetime NOT NULL COMMENT '通话时间',
  `operator` int(11) NOT NULL COMMENT '所属运营商',
  `posinfo` varchar(50) NOT NULL COMMENT '告警位置',
  `cancel_state` int(1) DEFAULT '0' COMMENT '是否解除',
  `cancel_user_id` int(1) DEFAULT NULL COMMENT '解除人',
  `cancel_cause` text COMMENT '解除原因',
  `area_id` int(11) DEFAULT NULL COMMENT '归属监狱ID',
  PRIMARY KEY (`call_id`),
  KEY `FK_sl_tel` (`area_id`),
  CONSTRAINT `FK_sl_tel` FOREIGN KEY (`area_id`) REFERENCES `sl_area` (`area_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=gbk CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

/*Data for the table `sl_call` */

insert  into `sl_call`(`call_id`,`imsi`,`targ_num`,`record_date`,`operator`,`posinfo`,`cancel_state`,`cancel_user_id`,`cancel_cause`,`area_id`) values (1,'13800000001','13800000002','2015-02-22 10:20:20',1,'医务楼3',0,NULL,NULL,2),(2,'13800000001','13800000002','2015-02-23 10:20:20',1,'医务楼3',0,NULL,NULL,2),(3,'13800000001','13800000002','2015-02-22 10:20:20',1,'医务楼3',0,NULL,NULL,2),(4,'13800000001','13800000002','2015-02-21 10:20:20',1,'医务楼3',0,NULL,NULL,2);

/*Table structure for table `sl_invade` */

DROP TABLE IF EXISTS `sl_invade`;

CREATE TABLE `sl_invade` (
  `invade_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编码',
  `imsi` varchar(16) NOT NULL COMMENT '非法手机卡号',
  `imei` varchar(16) NOT NULL COMMENT '非法手机串号',
  `record_date` datetime NOT NULL COMMENT '侦测时间',
  `operator` int(11) NOT NULL COMMENT '所属运营商',
  `posinfo` varchar(50) NOT NULL COMMENT '告警位置',
  `cancel_state` int(1) DEFAULT '0' COMMENT '是否解除',
  `cancel_user_id` int(11) DEFAULT NULL COMMENT '解除人ID',
  `cancel_cause` text COMMENT '解除原因',
  `area_id` int(11) DEFAULT NULL COMMENT '归属监狱ID',
  PRIMARY KEY (`invade_id`),
  KEY `FK_sl_invade` (`area_id`),
  CONSTRAINT `FK_sl_invade` FOREIGN KEY (`area_id`) REFERENCES `sl_area` (`area_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=gbk;

/*Data for the table `sl_invade` */

insert  into `sl_invade`(`invade_id`,`imsi`,`imei`,`record_date`,`operator`,`posinfo`,`cancel_state`,`cancel_user_id`,`cancel_cause`,`area_id`) values (1,'13800000001','123123123','2015-02-22 10:20:20',1,'医务楼3',0,NULL,NULL,2),(2,'13800000001','123123123','2015-02-22 10:20:20',1,'医务楼4',0,NULL,NULL,2),(3,'13800000001','123123123','2015-02-22 10:20:20',1,'医务楼3',0,NULL,NULL,2),(4,'13800000001','123123123','2015-02-22 10:20:20',1,'医务楼4',0,NULL,NULL,2),(5,'13800000001','123123123','2015-02-22 10:20:20',1,'医务楼3',0,NULL,NULL,2),(6,'13800000001','123123123','2015-02-22 10:20:20',1,'医务楼4',0,NULL,NULL,2);

/*Table structure for table `sl_map_config` */

DROP TABLE IF EXISTS `sl_map_config`;

CREATE TABLE `sl_map_config` (
  `config_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '配置方案ID',
  `config_url` varchar(200) DEFAULT NULL COMMENT '配置文件地址',
  `config_user_code` varchar(50) NOT NULL COMMENT '配置人',
  `config_date` datetime NOT NULL COMMENT '配置时间',
  `config_content` text COMMENT '配置内容（非url方式）',
  `config_type` int(1) NOT NULL COMMENT '0-url,1-content',
  `in_use` int(1) NOT NULL COMMENT '0-不在用,1-在用',
  `area_id` int(11) DEFAULT NULL COMMENT '归属监狱ID',
  PRIMARY KEY (`config_id`),
  KEY `FK_sl_map_config` (`area_id`),
  CONSTRAINT `FK_sl_map_config` FOREIGN KEY (`area_id`) REFERENCES `sl_area` (`area_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=gbk CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

/*Data for the table `sl_map_config` */

insert  into `sl_map_config`(`config_id`,`config_url`,`config_user_code`,`config_date`,`config_content`,`config_type`,`in_use`,`area_id`) values (12,NULL,'nzjy','2015-02-03 12:18:09','<mxGraphModel><root><Workflow label=\"SUNWAVE\" description=\"\" href=\"\" id=\"0\"><mxCell/></Workflow><Layer label=\"Default Layer\" id=\"1\"><mxCell parent=\"0\"/></Layer><Task label=\"女子监狱\" description=\"女监平面图\" href=\"\" id=\"2\"><mxCell style=\"bgr_ycjy\" vertex=\"1\" parent=\"1\"><mxGeometry x=\"10\" y=\"3\" width=\"720\" height=\"427\" as=\"geometry\"/></mxCell></Task><Symbol label=\"工厂\" description=\"\" href=\"\" id=\"3\"><mxCell style=\"symbol;image=mxeditors/images/symbols/terminate.png\" vertex=\"1\" parent=\"1\"><mxGeometry x=\"490\" y=\"60\" width=\"32\" height=\"32\" as=\"geometry\"/></mxCell></Symbol><Symbol label=\"医务楼\" description=\"\" href=\"\" id=\"4\"><mxCell style=\"symbol;image=mxeditors/images/symbols/terminate.png\" vertex=\"1\" parent=\"1\"><mxGeometry x=\"380\" y=\"150\" width=\"32\" height=\"32\" as=\"geometry\"/></mxCell></Symbol><Symbol label=\"一监舍\" description=\"\" href=\"\" id=\"5\"><mxCell style=\"symbol;image=mxeditors/images/symbols/terminate.png\" vertex=\"1\" parent=\"1\"><mxGeometry x=\"150\" y=\"144\" width=\"30\" height=\"32\" as=\"geometry\"/></mxCell></Symbol><Edge label=\"追踪轨迹1\" description=\"\" id=\"7\"><mxCell edge=\"1\" parent=\"1\" source=\"5\" target=\"4\"><mxGeometry relative=\"1\" as=\"geometry\"/></mxCell></Edge><Edge label=\"追踪轨迹2\" description=\"\" id=\"8\"><mxCell edge=\"1\" parent=\"1\" source=\"4\" target=\"3\"><mxGeometry relative=\"1\" as=\"geometry\"/></mxCell></Edge><Edge label=\"追踪轨迹3\" description=\"\" id=\"9\"><mxCell style=\"straightEdge\" edge=\"1\" parent=\"1\" source=\"3\" target=\"5\"><mxGeometry relative=\"1\" as=\"geometry\"><Array as=\"points\"><mxPoint x=\"340\" y=\"50\"/></Array></mxGeometry></mxCell></Edge></root></mxGraphModel>',1,0,8),(13,NULL,'nzjy','2015-02-03 13:29:00','<mxGraphModel><root><Workflow label=\"SUNWAVE\" description=\"\" href=\"\" id=\"0\"><mxCell/></Workflow><Layer label=\"Default Layer\" id=\"1\"><mxCell parent=\"0\"/></Layer><Task label=\"女子监狱\" description=\"女监平面图\" href=\"\" id=\"2\"><mxCell style=\"bgr_ycjy\" parent=\"1\" vertex=\"1\"><mxGeometry x=\"10\" y=\"3\" width=\"720\" height=\"427\" as=\"geometry\"/></mxCell></Task><Symbol label=\"工厂\" description=\"\" href=\"\" id=\"3\"><mxCell style=\"symbol;image=mxeditors/images/symbols/terminate.png\" parent=\"1\" vertex=\"1\"><mxGeometry x=\"522\" y=\"92\" width=\"32\" height=\"32\" as=\"geometry\"/></mxCell></Symbol><Symbol label=\"医务楼\" description=\"\" href=\"\" id=\"4\"><mxCell style=\"symbol;image=mxeditors/images/symbols/terminate.png\" parent=\"1\" vertex=\"1\"><mxGeometry x=\"380\" y=\"150\" width=\"32\" height=\"32\" as=\"geometry\"/></mxCell></Symbol><Symbol label=\"一监舍\" description=\"\" href=\"\" id=\"5\"><mxCell style=\"symbol;image=mxeditors/images/symbols/terminate.png\" parent=\"1\" vertex=\"1\"><mxGeometry x=\"150\" y=\"144\" width=\"30\" height=\"32\" as=\"geometry\"/></mxCell></Symbol><Edge label=\"追踪轨迹1\" description=\"\" id=\"7\"><mxCell parent=\"1\" source=\"5\" target=\"4\" edge=\"1\"><mxGeometry relative=\"1\" as=\"geometry\"/></mxCell></Edge><Edge label=\"追踪轨迹2\" description=\"\" id=\"8\"><mxCell parent=\"1\" source=\"4\" target=\"3\" edge=\"1\"><mxGeometry relative=\"1\" as=\"geometry\"/></mxCell></Edge><Edge label=\"追踪轨迹3\" description=\"\" id=\"9\"><mxCell style=\"straightEdge\" parent=\"1\" source=\"3\" target=\"5\" edge=\"1\"><mxGeometry relative=\"1\" as=\"geometry\"><Array as=\"points\"><mxPoint x=\"340\" y=\"50\"/></Array></mxGeometry></mxCell></Edge></root></mxGraphModel>',1,1,8);

/*Table structure for table `sl_msg` */

DROP TABLE IF EXISTS `sl_msg`;

CREATE TABLE `sl_msg` (
  `msg_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编码',
  `imsi` varchar(16) NOT NULL COMMENT '发送短信手机sim卡号',
  `targ_num` varchar(16) NOT NULL COMMENT '接收短信号码',
  `record_date` datetime NOT NULL COMMENT '侦测时间',
  `operator` int(11) NOT NULL COMMENT '所属运营商',
  `posinfo` varchar(50) NOT NULL COMMENT '告警位置',
  `msg_content` text COMMENT '短信内容',
  `cancel_state` int(1) DEFAULT '0' COMMENT '是否解除',
  `cancel_user_id` int(11) DEFAULT NULL COMMENT '解除人ID',
  `cancel_cause` text COMMENT '解除原因',
  `area_id` int(11) DEFAULT NULL COMMENT '归属监狱ID',
  PRIMARY KEY (`msg_id`),
  KEY `FK_sl_msg` (`area_id`),
  CONSTRAINT `FK_sl_msg` FOREIGN KEY (`area_id`) REFERENCES `sl_area` (`area_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=gbk CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

/*Data for the table `sl_msg` */

insert  into `sl_msg`(`msg_id`,`imsi`,`targ_num`,`record_date`,`operator`,`posinfo`,`msg_content`,`cancel_state`,`cancel_user_id`,`cancel_cause`,`area_id`) values (1,'13800000001','13800000002','2015-01-22 10:20:20',1,'医务楼3','发送一条短信',0,NULL,NULL,2),(2,'13800000001','13800000002','2015-02-24 10:20:20',1,'医务楼3','发送一条短信',0,NULL,NULL,2),(3,'13800000001','13800000002','2015-01-21 10:20:20',1,'医务楼3','发送一条短信',0,NULL,NULL,2),(4,'13800000001','13800000002','2015-02-22 10:20:20',1,'医务楼3','发送一条短信',0,NULL,NULL,2),(5,'13800000001','13800000002','2015-01-25 10:20:20',1,'医务楼3','发送一条短信',0,NULL,NULL,2),(6,'13800000001','13800000002','2015-01-22 10:20:20',1,'医务楼3','发送一条短信',0,NULL,NULL,2);

/*Table structure for table `sl_phone` */

DROP TABLE IF EXISTS `sl_phone`;

CREATE TABLE `sl_phone` (
  `phone_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编码',
  `phone_num` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `owner_name` varchar(50) DEFAULT NULL COMMENT '拥有人',
  `is_white` int(1) DEFAULT NULL COMMENT '是否白名单',
  `area_id` int(11) DEFAULT NULL COMMENT '归属监狱ID',
  PRIMARY KEY (`phone_id`),
  KEY `FK_sl_w_list` (`area_id`),
  CONSTRAINT `FK_sl_w_list` FOREIGN KEY (`area_id`) REFERENCES `sl_area` (`area_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `sl_phone` */

/*Table structure for table `sys_permission` */

DROP TABLE IF EXISTS `sys_permission`;

CREATE TABLE `sys_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限名称',
  `permission_name` varchar(50) DEFAULT NULL COMMENT '权限名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC;

/*Data for the table `sys_permission` */

insert  into `sys_permission`(`id`,`permission_name`) values (1,'add'),(2,'del'),(3,'update'),(4,'query');

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(50) DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`role_name`) values (1,'admin'),(2,'manager'),(3,'normal');

/*Table structure for table `sys_role_permission` */

DROP TABLE IF EXISTS `sys_role_permission`;

CREATE TABLE `sys_role_permission` (
  `role_id` int(11) DEFAULT NULL,
  `permission_id` int(11) DEFAULT NULL,
  KEY `FK_sys_role_permission` (`role_id`),
  KEY `FK_sys_permission` (`permission_id`),
  CONSTRAINT `FK_sys_permission` FOREIGN KEY (`permission_id`) REFERENCES `sys_permission` (`id`),
  CONSTRAINT `FK_sys_role_permission` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_role_permission` */

insert  into `sys_role_permission`(`role_id`,`permission_id`) values (1,1),(1,2),(1,3),(1,4),(2,1),(2,3),(2,4),(3,4);

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `user_show_name` varchar(200) NOT NULL COMMENT '用户显示名',
  `password` varchar(200) NOT NULL COMMENT '密码',
  `area_id` int(11) DEFAULT NULL COMMENT '归属地区',
  `user_type` int(1) NOT NULL COMMENT '用户类型',
  `salt` varchar(100) DEFAULT NULL COMMENT '盐',
  `locked` tinyint(1) DEFAULT NULL COMMENT '用户锁定',
  PRIMARY KEY (`id`),
  KEY `FK_sl_user` (`area_id`),
  CONSTRAINT `FK_sl_user` FOREIGN KEY (`area_id`) REFERENCES `sl_area` (`area_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=gbk;

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`username`,`user_show_name`,`password`,`area_id`,`user_type`,`salt`,`locked`) values (1,'su','超级管理员','10c60d8ac2c61aa0eff24d3688de975f',1,0,'d1de732d4f903e009540a2e2faa272f9',NULL),(2,'admin','北京监狱管理局','334b3c5ec4b8a77f70e0dda3cf38705f',1,1,'45c4496ef7309bb0094776576d2f219a',NULL),(3,'nzjy','女子监狱','48acfe8f2115fa955f18ded8415a6b61',8,2,'fdf33a26674199fc13d885bf9bd41538',NULL);

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  KEY `NewIndex1` (`user_id`),
  KEY `NewIndex2` (`role_id`),
  CONSTRAINT `FK_sys_role` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`),
  CONSTRAINT `FK_sys_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`user_id`,`role_id`) values (1,1),(2,2),(3,3);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
