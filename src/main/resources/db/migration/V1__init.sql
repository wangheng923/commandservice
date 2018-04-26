/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : command

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-04-10 14:28:33
*/

SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for `device`
-- ----------------------------
DROP TABLE IF EXISTS `device`;
CREATE TABLE `device` (
  `id`           INT(11)     NOT NULL,
  `serialNumber` VARCHAR(45) NOT NULL,
  `devcieName`   VARCHAR(45)      DEFAULT NULL,
  `unitCode`     VARCHAR(45)      DEFAULT NULL,
  `owner`        VARCHAR(45)      DEFAULT NULL,
  `location`     POINT            DEFAULT NULL,
  `createTime`   TIMESTAMP   NULL DEFAULT CURRENT_TIMESTAMP,
  `creator`      VARCHAR(45)      DEFAULT NULL,
  `description`  VARCHAR(500)     DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `device_id_index` (`id`) USING BTREE,
  UNIQUE KEY `device_serialNumber_index` (`serialNumber`) USING BTREE,
  UNIQUE KEY `device_unitCode_index` (`unitCode`) USING BTREE,
  UNIQUE KEY `device_owner_index` (`owner`) USING BTREE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Records of device
-- ----------------------------

-- ----------------------------
-- Table structure for `group`
-- ----------------------------
DROP TABLE IF EXISTS `group`;
CREATE TABLE `group` (
  `groupId`     INT(11)     NOT NULL AUTO_INCREMENT,
  `groupName`   VARCHAR(45) NOT NULL,
  `createTime`  TIMESTAMP   NULL     DEFAULT CURRENT_TIMESTAMP,
  `creator`     VARCHAR(45) NOT NULL,
  `description` VARCHAR(500)         DEFAULT NULL,
  PRIMARY KEY (`groupId`),
  UNIQUE KEY `group_groupId_index` (`groupId`) USING BTREE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Records of group
-- ----------------------------

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `roleCode`    INT(11)     NOT NULL,
  `roleName`    VARCHAR(45) NOT NULL,
  `roleLevel`   TINYINT(1)       DEFAULT NULL,
  `createTime`  TIMESTAMP   NULL DEFAULT CURRENT_TIMESTAMP,
  `creator`     VARCHAR(45)      DEFAULT NULL,
  `description` VARCHAR(500)     DEFAULT NULL,
  `fixed`       TINYINT(1)       DEFAULT '0',
  PRIMARY KEY (`roleCode`),
  UNIQUE KEY `role_roleCode_index` (`roleCode`) USING BTREE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Records of role
-- ----------------------------

-- ----------------------------
-- Table structure for `role_authority`
-- ----------------------------
DROP TABLE IF EXISTS `role_authority`;
CREATE TABLE `role_authority` (
  `roleCode`      INT(11)     NOT NULL,
  `authorityCode` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`roleCode`, `authorityCode`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Records of role_authority
-- ----------------------------

-- ----------------------------
-- Table structure for `unit`
-- ----------------------------
DROP TABLE IF EXISTS `unit`;
CREATE TABLE `unit` (
  `id`         INT(11)     NOT NULL AUTO_INCREMENT,
  `unitCode`   VARCHAR(45) NOT NULL,
  `unitName`   VARCHAR(45) NOT NULL,
  `unitLevel`  TINYINT(1)           DEFAULT NULL,
  `parent`     VARCHAR(45)          DEFAULT NULL,
  `status`     INT(11)              DEFAULT '1'
  COMMENT '0：注销，1：正常',
  `createTime` TIMESTAMP   NULL     DEFAULT CURRENT_TIMESTAMP,
  `fixed`      TINYINT(1)           DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unit_unitCode_index` (`unitCode`) USING BTREE,
  UNIQUE KEY `unit_id_index` (`id`) USING BTREE
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 8
  DEFAULT CHARSET = utf8
  COMMENT ='机构表';

-- ----------------------------
-- Records of unit
-- ----------------------------
INSERT INTO `unit` VALUES ('1', '1', '1', '1', '0', '1', '2018-04-09 19:08:56', '0');
INSERT INTO `unit` VALUES ('2', '2', '2', '2', '1', '1', '2018-04-09 19:09:11', '0');
INSERT INTO `unit` VALUES ('3', '3', '3', '2', '1', '1', '2018-04-09 19:09:20', '0');
INSERT INTO `unit` VALUES ('4', '4', '4', '3', '2', '1', '2018-04-09 19:09:33', '0');
INSERT INTO `unit` VALUES ('5', '5', '5', '4', '4', '1', '2018-04-09 19:09:47', '0');
INSERT INTO `unit` VALUES ('6', '6', '6', '3', '3', '1', '2018-04-09 19:10:07', '0');
INSERT INTO `unit` VALUES ('7', '7', '7', '4', '4', '1', '2018-04-09 19:10:24', '0');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id`         INT(11)     NOT NULL AUTO_INCREMENT,
  `account`    VARCHAR(45) NOT NULL,
  `name`       VARCHAR(45) NOT NULL,
  `password`   VARCHAR(32) NOT NULL,
  `unitCode`   VARCHAR(45)          DEFAULT NULL,
  `status`     TINYINT(1)           DEFAULT '0',
  `createTime` TIMESTAMP   NULL     DEFAULT CURRENT_TIMESTAMP,
  `creator`    VARCHAR(45)          DEFAULT NULL,
  `fixed`      TINYINT(1)           DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_index` (`id`) USING BTREE,
  UNIQUE KEY `user_accound_index` (`account`) USING BTREE,
  UNIQUE KEY `user_unitCode_index` (`unitCode`) USING BTREE
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '120', '小黑', '123', NULL, '0', '2018-04-09 16:47:27', NULL, '0');

-- ----------------------------
-- Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `roleCode` INT(11)     NOT NULL,
  `account`  VARCHAR(45) NOT NULL,
  PRIMARY KEY (`roleCode`, `account`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------

-- ----------------------------
-- Function structure for `getSubUnitCodeList`
-- ----------------------------
DROP FUNCTION IF EXISTS `getSubUnitCodeList`;
DELIMITER ;;
CREATE DEFINER =`root`@`127.0.0.1` FUNCTION `getSubUnitCodeList`(rootId VARCHAR(10000), formatStr INT)
  RETURNS VARCHAR(10000)
  CHARSET utf8
DETERMINISTIC
  BEGIN
    DECLARE sTemp VARCHAR(10000);
    DECLARE sTempChd VARCHAR(10000);

    SET sTemp = '$';
    SET sTempChd = rootId;

    WHILE sTempChd IS NOT NULL DO
      SET sTemp = CONCAT(sTemp, ',', sTempChd);
      SELECT group_concat(unitCode)
      INTO sTempChd
      FROM unit
      WHERE FIND_IN_SET(parent, sTempChd) > 0;
    END WHILE;

    IF formatStr = 1
    THEN
      SET sTemp = CONCAT(REPLACE(sTemp, ',', '\',\''), '\'');
      SET sTemp = substring(sTemp, 4);
    ELSE
      SET sTemp = substring(sTemp, 3);
    END IF;

    RETURN sTemp;
  END
;;
DELIMITER ;
