/*
 Navicat Premium Data Transfer

 Source Server         : db
 Source Server Type    : MySQL
 Source Server Version : 80033
 Source Host           : localhost:3306
 Source Schema         : mi_bms

 Target Server Type    : MySQL
 Target Server Version : 80033
 File Encoding         : 65001

 Date: 10/07/2024 22:17:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for battery
-- ----------------------------
DROP TABLE IF EXISTS `battery`;
CREATE TABLE `battery`  (
  `battery_type` int NOT NULL COMMENT '电池类型',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '电池名称',
  `create_time` date NOT NULL COMMENT '创建时间',
  `update_time` date NOT NULL COMMENT '更新时间',
  `delete` int NOT NULL COMMENT '逻辑删除',
  PRIMARY KEY (`battery_type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of battery
-- ----------------------------
INSERT INTO `battery` VALUES (1, '三元电池', '2024-06-19', '2024-06-19', 0);
INSERT INTO `battery` VALUES (2, '铁锂电池', '2024-06-19', '2024-06-19', 0);

-- ----------------------------
-- Table structure for rule
-- ----------------------------
DROP TABLE IF EXISTS `rule`;
CREATE TABLE `rule`  (
  `id` int NOT NULL COMMENT ' 序号',
  `warn_id` int NOT NULL COMMENT '规则编号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '报警名称',
  `battery_type` int NOT NULL COMMENT '电池类型',
  `detail` varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '预警规则',
  `create_time` date NOT NULL COMMENT '创建时间',
  `update_time` date NOT NULL COMMENT '更新时间',
  `delete` int NOT NULL COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rule
-- ----------------------------
INSERT INTO `rule` VALUES (1, 1, '电压差报警', 1, '[{\r\n\"expression\":\"5<=(Mx-Mi)\",\r\n\"level\":\"0\"\r\n},{\r\n\"expression\":\"3<=(Mx-Mi)&&(Mx-Mi)<5\",\r\n\"level\":\"1\"\r\n},{\r\n\"expression\":\"1<=(Mx-Mi)&&(Mx-Mi)<3\",\r\n\"level\":\"2\"\r\n},{\r\n\"expression\":\"0.6<=(Mx-Mi)&&(Mx-Mi)<1\",\r\n\"level\":\"3\"\r\n},{\r\n\"expression\":\"0.2<=(Mx-Mi)&&(Mx-Mi)<0.6\",\r\n\"level\":\"4\"\r\n},{\r\n\"expression\":\"(Mx-Mi)<0.2\",\r\n\"level\":\"不报警\"\r\n}]', '2024-06-20', '2024-06-20', 0);
INSERT INTO `rule` VALUES (2, 1, '电压差报警', 2, '[{\r\n\"expression\":\"2<=(Mx-Mi)\",\r\n\"level\":\"0\"\r\n},{\r\n\"expression\":\"1<=(Mx-Mi)&&(Mx-Mi)<2\",\r\n\"level\":\"1\"\r\n},{\r\n\"expression\":\"0.7<=(Mx-Mi)&&(Mx-Mi)<1\",\r\n\"level\":\"2\"\r\n},{\r\n\"expression\":\"0.4<=(Mx-Mi)&&(Mx-Mi)<0.7\",\r\n\"level\":\"3\"\r\n},{\r\n\"expression\":\"0.2<=(Mx-Mi)&&(Mx-Mi)<0.4\",\r\n\"level\":\"4\"\r\n},{\r\n\"expression\":\"(Mx-Mi)<0.2\",\r\n\"level\":\"不报警\"\r\n}]', '2024-06-20', '2024-06-20', 0);
INSERT INTO `rule` VALUES (3, 2, '电流差报警', 1, '[{\r\n\"expression\":\"3<=(Ix-Ii)\",\r\n\"level\":\"0\"\r\n},{\r\n\"expression\":\"1<=(Ix-Ii)&&(Ix-Ii)<3\",\r\n\"level\":\"1\"\r\n},{\r\n\"expression\":\"0.2<=(Ix-Ii)&&(Ix-Ii)<1\",\r\n\"level\":\"2\"\r\n},{\r\n\"expression\":\"(Ix-Ii)<0.2\",\r\n\"level\":\"不报警\"\r\n}]', '2024-06-20', '2024-06-20', 0);
INSERT INTO `rule` VALUES (4, 2, '电流差报警', 2, '[{\r\n\"expression\":\"1<=(Ix-Ii)\",\r\n\"level\":\"0\"\r\n},{\r\n\"expression\":\"0.5<=(Ix-Ii)&&(Ix-Ii)<1\",\r\n\"level\":\"1\"\r\n},{\r\n\"expression\":\"0.2<=(Ix-Ii)&&(Ix-Ii)<0.5\",\r\n\"level\":\"2\"\r\n},{\r\n\"expression\":\"(Ix-Ii)<0.2\",\r\n\"level\":\"不报警\"\r\n}]', '2024-06-20', '2024-06-20', 0);

-- ----------------------------
-- Table structure for vehicle
-- ----------------------------
DROP TABLE IF EXISTS `vehicle`;
CREATE TABLE `vehicle`  (
  `vid` char(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '车辆识别码',
  `carId` int NOT NULL COMMENT '车架编号',
  `battery_type` int NOT NULL COMMENT '电车类型：1、三元锂电池；2、铁锂电池',
  `total_distance` double NOT NULL COMMENT '总里程',
  `battery_health` int NOT NULL COMMENT '电池健康（%）',
  `create_time` date NOT NULL COMMENT '创建时间',
  `update_time` date NOT NULL COMMENT '更新时间',
  `delete` int NOT NULL COMMENT '逻辑删除',
  PRIMARY KEY (`vid`) USING BTREE,
  INDEX `battery_type`(`battery_type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of vehicle
-- ----------------------------
INSERT INTO `vehicle` VALUES ('1106201369713219', 2, 2, 100, 100, '2024-06-20', '2024-06-20', 0);
INSERT INTO `vehicle` VALUES ('1106201753040241', 1, 1, 2000, 99, '2024-06-20', '2024-06-20', 0);
INSERT INTO `vehicle` VALUES ('1106201866858669', 3, 1, 300, 98, '2024-06-20', '2024-06-20', 0);

SET FOREIGN_KEY_CHECKS = 1;
