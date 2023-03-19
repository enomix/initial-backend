/*
 Navicat Premium Data Transfer

 Source Server         : db1
 Source Server Type    : MySQL
 Source Server Version : 50710 (5.7.10-log)
 Source Host           : localhost:3306
 Source Schema         : my_initial_db

 Target Server Type    : MySQL
 Target Server Version : 50710 (5.7.10-log)
 File Encoding         : 65001

 Date: 20/03/2023 02:17:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for post
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `gender` tinyint(4) NOT NULL DEFAULT '0' COMMENT '性别（0-男, 1-女）',
  `education` varchar(512) DEFAULT NULL COMMENT '学历',
  `place` varchar(512) DEFAULT NULL COMMENT '地点',
  `job` varchar(512) DEFAULT NULL COMMENT '职业',
  `contact` varchar(512) DEFAULT NULL COMMENT '联系方式',
  `loveExp` varchar(512) DEFAULT NULL COMMENT '感情经历',
  `content` text COMMENT '内容（个人介绍）',
  `photo` varchar(1024) DEFAULT NULL COMMENT '照片地址',
  `reviewStatus` int(11) NOT NULL DEFAULT '0' COMMENT '状态（0-待审核, 1-通过, 2-拒绝）',
  `reviewMessage` varchar(512) DEFAULT NULL COMMENT '审核信息',
  `viewNum` int(11) NOT NULL DEFAULT '0' COMMENT '浏览数',
  `thumbNum` int(11) NOT NULL DEFAULT '0' COMMENT '点赞数',
  `userId` bigint(20) NOT NULL COMMENT '创建用户 id',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `isDelete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='帖子';

-- ----------------------------
-- Records of post
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `fileName` varchar(255) DEFAULT NULL COMMENT '文件名称',
  `type` varchar(255) DEFAULT NULL COMMENT '文件类型',
  `size` bigint(20) DEFAULT NULL COMMENT '文件大小(kb)',
  `url` varchar(255) DEFAULT NULL COMMENT '下载链接',
  `isDelete` tinyint(4) DEFAULT '0' COMMENT '是否删除',
  `isEnable` tinyint(4) DEFAULT '0' COMMENT '是否禁用链接',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_file
-- ----------------------------
BEGIN;
INSERT INTO `sys_file` (`id`, `fileName`, `type`, `size`, `url`, `isDelete`, `isEnable`) VALUES (19, '2023-01-053cd69a60191e4952972720a85ef7da83.jpg', 'jpg', 920, 'http://localhost:9090/file/2023-01-053cd69a60191e4952972720a85ef7da83.jpg', 0, 1);
INSERT INTO `sys_file` (`id`, `fileName`, `type`, `size`, `url`, `isDelete`, `isEnable`) VALUES (20, '2023-01-05220bad4fc47440b8a91111214367334b.jpg', 'jpg', 920, 'http://localhost:9090/file/2023-01-05220bad4fc47440b8a91111214367334b.jpg', 0, 1);
INSERT INTO `sys_file` (`id`, `fileName`, `type`, `size`, `url`, `isDelete`, `isEnable`) VALUES (21, '2023-01-058c6bac6fad5d4d9b9323a9eed0132337.jpg', 'jpg', 3358, 'http://localhost:9090/file/2023-01-058c6bac6fad5d4d9b9323a9eed0132337.jpg', 0, 1);
INSERT INTO `sys_file` (`id`, `fileName`, `type`, `size`, `url`, `isDelete`, `isEnable`) VALUES (22, '2023-01-05a61b11c02ed34ff28cc7fed8f5e057c5.pdf', 'pdf', 9088, 'http://localhost:9090/file/2023-01-05a61b11c02ed34ff28cc7fed8f5e057c5.pdf', 0, 1);
INSERT INTO `sys_file` (`id`, `fileName`, `type`, `size`, `url`, `isDelete`, `isEnable`) VALUES (23, '2023-01-05f46a4bda994748bc875b507fb057bdb5.md', 'md', 13, 'http://localhost:9090/file/2023-01-05f46a4bda994748bc875b507fb057bdb5.md', 0, 1);
INSERT INTO `sys_file` (`id`, `fileName`, `type`, `size`, `url`, `isDelete`, `isEnable`) VALUES (24, '2023-01-051b508d4139fc45ccb3cd38fbdec66bf4.png', 'png', 3908, 'http://localhost:9090/file/2023-01-051b508d4139fc45ccb3cd38fbdec66bf4.png', 0, 1);
INSERT INTO `sys_file` (`id`, `fileName`, `type`, `size`, `url`, `isDelete`, `isEnable`) VALUES (25, '2023-01-12adfa8ee4695d485cbb5f6d7ae683b191.png', 'png', 29, 'http://localhost:9090/file/2023-01-12adfa8ee4695d485cbb5f6d7ae683b191.png', 0, 0);
INSERT INTO `sys_file` (`id`, `fileName`, `type`, `size`, `url`, `isDelete`, `isEnable`) VALUES (26, '2023-01-125c1c2bfb16a042df88b3a280ffd392eb.png', 'png', 29, 'http://localhost:9090/file/2023-01-125c1c2bfb16a042df88b3a280ffd392eb.png', 0, 0);
INSERT INTO `sys_file` (`id`, `fileName`, `type`, `size`, `url`, `isDelete`, `isEnable`) VALUES (27, '2023-01-124173f91f27694ddfb4c374ab4bda90b2.png', 'png', 29, 'http://localhost:9090/file/2023-01-124173f91f27694ddfb4c374ab4bda90b2.png', 0, 0);
INSERT INTO `sys_file` (`id`, `fileName`, `type`, `size`, `url`, `isDelete`, `isEnable`) VALUES (28, '2023-01-123eff899a9ddd46c59a6f0c0c8ae95570.png', 'png', 29, 'http://localhost:9090/file/2023-01-123eff899a9ddd46c59a6f0c0c8ae95570.png', 0, 0);
INSERT INTO `sys_file` (`id`, `fileName`, `type`, `size`, `url`, `isDelete`, `isEnable`) VALUES (29, '2023-01-12d10c368f0bf246549ad391d6ccffb3e3.jpg', 'jpg', 920, 'http://localhost:9090/file/2023-01-12d10c368f0bf246549ad391d6ccffb3e3.jpg', 0, 0);
INSERT INTO `sys_file` (`id`, `fileName`, `type`, `size`, `url`, `isDelete`, `isEnable`) VALUES (30, '2023-01-184dc0ea753a8a4f23a19c4e79f8ab4423.png', 'png', 29, 'http://localhost:9090/file/2023-01-184dc0ea753a8a4f23a19c4e79f8ab4423.png', 0, 0);
INSERT INTO `sys_file` (`id`, `fileName`, `type`, `size`, `url`, `isDelete`, `isEnable`) VALUES (31, '2023-01-1800e8d4fd32c6414cb48f18d8c52d919c.png', 'png', 29, 'http://localhost:9090/file/2023-01-1800e8d4fd32c6414cb48f18d8c52d919c.png', 0, 0);
INSERT INTO `sys_file` (`id`, `fileName`, `type`, `size`, `url`, `isDelete`, `isEnable`) VALUES (32, '2023-01-185ab6ff591332447ea2224d4c0070a2c8.png', 'png', 29, 'http://localhost:9090/file/2023-01-185ab6ff591332447ea2224d4c0070a2c8.png', 0, 0);
INSERT INTO `sys_file` (`id`, `fileName`, `type`, `size`, `url`, `isDelete`, `isEnable`) VALUES (33, '2023-01-184179392d64a4486d8378749d0d054ac4.jpg', 'jpg', 920, 'http://localhost:9090/file/2023-01-184179392d64a4486d8378749d0d054ac4.jpg', 0, 0);
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '名称',
  `menuCode` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '菜单编码',
  `parentId` bigint(20) DEFAULT NULL COMMENT '父节点',
  `nodeType` tinyint(4) NOT NULL DEFAULT '1' COMMENT '节点类型，1菜单目录，2菜单页面，3页面按钮',
  `iconUrl` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '图标地址',
  `sort` int(11) NOT NULL DEFAULT '1' COMMENT '排序号',
  `linkUrl` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '页面对应的地址',
  `path` varchar(2500) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '前端vue文件保存的地址',
  `level` int(11) NOT NULL DEFAULT '0' COMMENT '层次',
  `auth` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT 'user' COMMENT '访问菜单所需的权限',
  `isDelete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除 1：已删除；0：未删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_parent_id` (`parentId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` (`id`, `name`, `menuCode`, `parentId`, `nodeType`, `iconUrl`, `sort`, `linkUrl`, `path`, `level`, `auth`, `isDelete`) VALUES (1, '系统管理', '1', NULL, 1, 'el-icon-s-custom', 1, 'manage', '', 1, 'user', 0);
INSERT INTO `sys_menu` (`id`, `name`, `menuCode`, `parentId`, `nodeType`, `iconUrl`, `sort`, `linkUrl`, `path`, `level`, `auth`, `isDelete`) VALUES (2, '用户管理', '2', 1, 2, 'el-icon-user', 1, 'user', 'User', 2, 'admin', 0);
INSERT INTO `sys_menu` (`id`, `name`, `menuCode`, `parentId`, `nodeType`, `iconUrl`, `sort`, `linkUrl`, `path`, `level`, `auth`, `isDelete`) VALUES (3, '文件管理', '3', 1, 2, 'el-icon-files', 1, 'file', 'File', 2, 'user', 0);
INSERT INTO `sys_menu` (`id`, `name`, `menuCode`, `parentId`, `nodeType`, `iconUrl`, `sort`, `linkUrl`, `path`, `level`, `auth`, `isDelete`) VALUES (4, '菜单管理', '4', 1, 2, 'el-icon-menu', 1, 'menu', 'SysMenu', 2, 'admin', 0);
INSERT INTO `sys_menu` (`id`, `name`, `menuCode`, `parentId`, `nodeType`, `iconUrl`, `sort`, `linkUrl`, `path`, `level`, `auth`, `isDelete`) VALUES (6, '用户删除按钮', '5', 6, 3, 'el-icon-menu', 1, 'menu', 'SysMenu', 3, 'user', 1);
INSERT INTO `sys_menu` (`id`, `name`, `menuCode`, `parentId`, `nodeType`, `iconUrl`, `sort`, `linkUrl`, `path`, `level`, `auth`, `isDelete`) VALUES (7, '文件下载按钮', '6', NULL, 3, 'el-icon-files', 1, 'menu', 'SysMenu', 3, 'user', 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `roleId` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户角色',
  `menuId` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`),
  KEY `idx_role_id` (`roleId`) USING BTREE,
  KEY `idx_menu_id` (`menuId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色菜单关系表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menu` (`id`, `roleId`, `menuId`) VALUES (1, 'admin', 1);
INSERT INTO `sys_role_menu` (`id`, `roleId`, `menuId`) VALUES (2, 'admin', 2);
INSERT INTO `sys_role_menu` (`id`, `roleId`, `menuId`) VALUES (3, 'admin', 3);
INSERT INTO `sys_role_menu` (`id`, `roleId`, `menuId`) VALUES (4, 'admin', 4);
INSERT INTO `sys_role_menu` (`id`, `roleId`, `menuId`) VALUES (5, 'admin', 5);
INSERT INTO `sys_role_menu` (`id`, `roleId`, `menuId`) VALUES (6, 'admin', 6);
INSERT INTO `sys_role_menu` (`id`, `roleId`, `menuId`) VALUES (7, 'user', 1);
INSERT INTO `sys_role_menu` (`id`, `roleId`, `menuId`) VALUES (8, 'user', 2);
INSERT INTO `sys_role_menu` (`id`, `roleId`, `menuId`) VALUES (9, 'user', 3);
INSERT INTO `sys_role_menu` (`id`, `roleId`, `menuId`) VALUES (10, 'user', 4);
INSERT INTO `sys_role_menu` (`id`, `roleId`, `menuId`) VALUES (11, 'user', 5);
INSERT INTO `sys_role_menu` (`id`, `roleId`, `menuId`) VALUES (12, 'user', 6);
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `userName` varchar(256) DEFAULT NULL COMMENT '用户昵称',
  `userAccount` varchar(256) NOT NULL COMMENT '账号',
  `userAvatar` varchar(1024) DEFAULT NULL COMMENT '用户头像',
  `gender` tinyint(4) DEFAULT NULL COMMENT '性别',
  `userRole` varchar(256) NOT NULL DEFAULT 'user' COMMENT '用户角色：user / admin',
  `userPassword` varchar(512) NOT NULL COMMENT '密码',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `isDelete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_userAccount` (`userAccount`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COMMENT='用户';

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` (`id`, `userName`, `userAccount`, `userAvatar`, `gender`, `userRole`, `userPassword`, `createTime`, `updateTime`, `isDelete`) VALUES (1, '张三d', 'test', 'http://localhost:9090/file/2023-01-12d10c368f0bf246549ad391d6ccffb3e3.jpg', 1, 'admin', 'b03d227f78c0c79334fca76e7b8eb46a', '2022-12-17 23:36:31', '2023-02-05 03:15:28', 0);
INSERT INTO `user` (`id`, `userName`, `userAccount`, `userAvatar`, `gender`, `userRole`, `userPassword`, `createTime`, `updateTime`, `isDelete`) VALUES (2, '李四d', 'test1', 'http://localhost:9090/file/2023-01-12d10c368f0bf246549ad391d6ccffb3e3.jpg', 1, 'user', '18470a9957b7b522c131289da4a8f903', '2022-12-20 19:05:59', '2023-02-05 03:37:30', 0);
INSERT INTO `user` (`id`, `userName`, `userAccount`, `userAvatar`, `gender`, `userRole`, `userPassword`, `createTime`, `updateTime`, `isDelete`) VALUES (3, '王五d', 'test2', 'http://localhost:9090/file/2023-01-12d10c368f0bf246549ad391d6ccffb3e3.jpg', 0, 'user', '18470a9957b7b522c131289da4a8f903', '2022-12-20 21:52:03', '2023-02-05 03:37:30', 0);
INSERT INTO `user` (`id`, `userName`, `userAccount`, `userAvatar`, `gender`, `userRole`, `userPassword`, `createTime`, `updateTime`, `isDelete`) VALUES (4, '赵六六d', 'test3', 'http://localhost:9090/file/2023-01-12d10c368f0bf246549ad391d6ccffb3e3.jpg', 0, 'user', '18470a9957b7b522c131289da4a8f903', '2022-12-20 21:57:04', '2023-02-05 03:37:30', 0);
INSERT INTO `user` (`id`, `userName`, `userAccount`, `userAvatar`, `gender`, `userRole`, `userPassword`, `createTime`, `updateTime`, `isDelete`) VALUES (5, '洪七d', 'test4', 'http://localhost:9090/file/2023-01-12d10c368f0bf246549ad391d6ccffb3e3.jpg', 1, 'user', 'b03d227f78c0c79334fca76e7b8eb46a', '2022-12-20 22:01:01', '2023-02-05 03:37:30', 0);
INSERT INTO `user` (`id`, `userName`, `userAccount`, `userAvatar`, `gender`, `userRole`, `userPassword`, `createTime`, `updateTime`, `isDelete`) VALUES (6, '老八', 'test5', 'http://localhost:9090/file/2023-01-12d10c368f0bf246549ad391d6ccffb3e3.jpg', 0, 'user', '18470a9957b7b522c131289da4a8f903', '2022-12-20 22:05:12', '2023-02-05 03:37:30', 0);
INSERT INTO `user` (`id`, `userName`, `userAccount`, `userAvatar`, `gender`, `userRole`, `userPassword`, `createTime`, `updateTime`, `isDelete`) VALUES (7, '小九', 'test6', 'http://localhost:9090/file/2023-01-12d10c368f0bf246549ad391d6ccffb3e3.jpg', 1, 'user', '18470a9957b7b522c131289da4a8f903', '2022-12-20 22:17:01', '2023-02-03 23:02:57', 0);
INSERT INTO `user` (`id`, `userName`, `userAccount`, `userAvatar`, `gender`, `userRole`, `userPassword`, `createTime`, `updateTime`, `isDelete`) VALUES (8, 'test123', '123321123', 'http://localhost:9090/file/2023-01-12d10c368f0bf246549ad391d6ccffb3e3.jpg', 0, 'admin', '123321123', '2022-12-21 02:17:41', '2023-02-03 23:02:57', 0);
INSERT INTO `user` (`id`, `userName`, `userAccount`, `userAvatar`, `gender`, `userRole`, `userPassword`, `createTime`, `updateTime`, `isDelete`) VALUES (9, '唐僧', 'test111', 'http://localhost:9090/file/2023-01-12d10c368f0bf246549ad391d6ccffb3e3.jpg', 1, 'user', '', '2022-12-31 17:27:46', '2023-02-03 23:02:57', 0);
INSERT INTO `user` (`id`, `userName`, `userAccount`, `userAvatar`, `gender`, `userRole`, `userPassword`, `createTime`, `updateTime`, `isDelete`) VALUES (10, '孙悟空', 'test222', 'http://localhost:9090/file/2023-01-12d10c368f0bf246549ad391d6ccffb3e3.jpg', 1, 'user', '123456', '2022-12-31 17:46:28', '2023-02-03 23:02:57', 0);
INSERT INTO `user` (`id`, `userName`, `userAccount`, `userAvatar`, `gender`, `userRole`, `userPassword`, `createTime`, `updateTime`, `isDelete`) VALUES (11, '猪八戒', 'test333', 'http://localhost:9090/file/2023-01-12d10c368f0bf246549ad391d6ccffb3e3.jpg', 1, 'user', '123456', '2022-12-31 17:48:33', '2023-02-03 23:02:57', 0);
INSERT INTO `user` (`id`, `userName`, `userAccount`, `userAvatar`, `gender`, `userRole`, `userPassword`, `createTime`, `updateTime`, `isDelete`) VALUES (12, '白龙马', 'test444', 'http://localhost:9090/file/2023-01-12d10c368f0bf246549ad391d6ccffb3e3.jpg', 1, 'user', '123456', '2022-12-31 17:50:55', '2023-02-03 23:02:56', 0);
INSERT INTO `user` (`id`, `userName`, `userAccount`, `userAvatar`, `gender`, `userRole`, `userPassword`, `createTime`, `updateTime`, `isDelete`) VALUES (13, '观音菩萨', 'test5555', 'http://localhost:9090/file/2023-01-12d10c368f0bf246549ad391d6ccffb3e3.jpg', 0, 'user', '123456', '2022-12-31 17:52:22', '2023-02-05 03:37:30', 0);
INSERT INTO `user` (`id`, `userName`, `userAccount`, `userAvatar`, `gender`, `userRole`, `userPassword`, `createTime`, `updateTime`, `isDelete`) VALUES (20, '玉皇大帝3333', 'test6666', 'http://localhost:9090/file/2023-01-12d10c368f0bf246549ad391d6ccffb3e3.jpg', 1, 'admin', '1233333333', '2022-12-31 18:01:04', '2023-02-03 23:02:57', 0);
INSERT INTO `user` (`id`, `userName`, `userAccount`, `userAvatar`, `gender`, `userRole`, `userPassword`, `createTime`, `updateTime`, `isDelete`) VALUES (24, '如来佛祖', 'test7777', 'http://localhost:9090/file/2023-01-12d10c368f0bf246549ad391d6ccffb3e3.jpg', 1, 'user', '123321', '2022-12-31 23:24:29', '2023-02-03 23:02:57', 0);
INSERT INTO `user` (`id`, `userName`, `userAccount`, `userAvatar`, `gender`, `userRole`, `userPassword`, `createTime`, `updateTime`, `isDelete`) VALUES (25, '文殊菩萨', 'test8888', 'http://localhost:9090/file/2023-01-12d10c368f0bf246549ad391d6ccffb3e3.jpg', 1, 'user', '123321', '2022-12-31 23:26:09', '2023-02-03 23:02:57', 0);
INSERT INTO `user` (`id`, `userName`, `userAccount`, `userAvatar`, `gender`, `userRole`, `userPassword`, `createTime`, `updateTime`, `isDelete`) VALUES (26, '李世民', 'test10', 'http://localhost:9090/file/2023-01-12d10c368f0bf246549ad391d6ccffb3e3.jpg', 1, 'admin', 'b03d227f78c0c79334fca76e7b8eb46a', '2022-12-17 23:36:31', '2023-02-03 23:02:57', 0);
INSERT INTO `user` (`id`, `userName`, `userAccount`, `userAvatar`, `gender`, `userRole`, `userPassword`, `createTime`, `updateTime`, `isDelete`) VALUES (27, '秦叔宝', 'test11', 'http://localhost:9090/file/2023-01-12d10c368f0bf246549ad391d6ccffb3e3.jpg', 1, 'user', '18470a9957b7b522c131289da4a8f903', '2022-12-20 19:05:59', '2023-02-03 23:02:57', 0);
INSERT INTO `user` (`id`, `userName`, `userAccount`, `userAvatar`, `gender`, `userRole`, `userPassword`, `createTime`, `updateTime`, `isDelete`) VALUES (28, '尉迟恭', 'test12', 'http://localhost:9090/file/2023-01-12d10c368f0bf246549ad391d6ccffb3e3.jpg', 0, 'user', '18470a9957b7b522c131289da4a8f903', '2022-12-20 21:52:03', '2023-02-03 23:02:57', 0);
INSERT INTO `user` (`id`, `userName`, `userAccount`, `userAvatar`, `gender`, `userRole`, `userPassword`, `createTime`, `updateTime`, `isDelete`) VALUES (29, '宇文成都', 'test13', 'http://localhost:9090/file/2023-01-12d10c368f0bf246549ad391d6ccffb3e3.jpg', 0, 'user', '18470a9957b7b522c131289da4a8f903', '2022-12-20 21:57:04', '2023-02-03 23:02:57', 0);
INSERT INTO `user` (`id`, `userName`, `userAccount`, `userAvatar`, `gender`, `userRole`, `userPassword`, `createTime`, `updateTime`, `isDelete`) VALUES (30, '程咬金', 'test14', 'http://localhost:9090/file/2023-01-12d10c368f0bf246549ad391d6ccffb3e3.jpg', 1, 'user', 'b03d227f78c0c79334fca76e7b8eb46a', '2022-12-20 22:01:01', '2023-02-03 23:02:57', 0);
INSERT INTO `user` (`id`, `userName`, `userAccount`, `userAvatar`, `gender`, `userRole`, `userPassword`, `createTime`, `updateTime`, `isDelete`) VALUES (31, '林冲', 'test15', 'http://localhost:9090/file/2023-01-12d10c368f0bf246549ad391d6ccffb3e3.jpg', 1, 'admin', 'b03d227f78c0c79334fca76e7b8eb46a', '2022-12-17 23:36:31', '2023-02-03 23:02:57', 0);
INSERT INTO `user` (`id`, `userName`, `userAccount`, `userAvatar`, `gender`, `userRole`, `userPassword`, `createTime`, `updateTime`, `isDelete`) VALUES (32, '武松', 'test16', 'http://localhost:9090/file/2023-01-12d10c368f0bf246549ad391d6ccffb3e3.jpg', 1, 'user', '18470a9957b7b522c131289da4a8f903', '2022-12-20 19:05:59', '2023-02-03 23:02:57', 0);
INSERT INTO `user` (`id`, `userName`, `userAccount`, `userAvatar`, `gender`, `userRole`, `userPassword`, `createTime`, `updateTime`, `isDelete`) VALUES (33, '公孙胜', 'test17', 'http://localhost:9090/file/2023-01-12d10c368f0bf246549ad391d6ccffb3e3.jpg', 0, 'user', '18470a9957b7b522c131289da4a8f903', '2022-12-20 21:52:03', '2023-02-03 23:02:57', 0);
INSERT INTO `user` (`id`, `userName`, `userAccount`, `userAvatar`, `gender`, `userRole`, `userPassword`, `createTime`, `updateTime`, `isDelete`) VALUES (34, '李逵', 'test18', 'http://localhost:9090/file/2023-01-12d10c368f0bf246549ad391d6ccffb3e3.jpg', 0, 'user', '18470a9957b7b522c131289da4a8f903', '2022-12-20 21:57:04', '2023-02-03 23:02:57', 0);
INSERT INTO `user` (`id`, `userName`, `userAccount`, `userAvatar`, `gender`, `userRole`, `userPassword`, `createTime`, `updateTime`, `isDelete`) VALUES (35, '鲁智深', 'test19', 'http://localhost:9090/file/2023-01-12d10c368f0bf246549ad391d6ccffb3e3.jpg', 1, 'user', 'b03d227f78c0c79334fca76e7b8eb46a', '2022-12-20 22:01:01', '2023-02-03 23:02:56', 0);
INSERT INTO `user` (`id`, `userName`, `userAccount`, `userAvatar`, `gender`, `userRole`, `userPassword`, `createTime`, `updateTime`, `isDelete`) VALUES (36, '武松1', 'test321', 'http://localhost:9090/file/2023-01-12d10c368f0bf246549ad391d6ccffb3e3.jpg', 1, 'admin', '18470a9957b7b522c131289da4a8f903', '2023-01-03 16:19:51', '2023-02-06 15:48:34', 0);
INSERT INTO `user` (`id`, `userName`, `userAccount`, `userAvatar`, `gender`, `userRole`, `userPassword`, `createTime`, `updateTime`, `isDelete`) VALUES (37, '张顺', 'test3211', 'http://localhost:9090/file/2023-01-184179392d64a4486d8378749d0d054ac4.jpg', 1, 'admin', '18470a9957b7b522c131289da4a8f903', '2023-01-03 18:54:38', '2023-01-18 16:38:21', 0);
INSERT INTO `user` (`id`, `userName`, `userAccount`, `userAvatar`, `gender`, `userRole`, `userPassword`, `createTime`, `updateTime`, `isDelete`) VALUES (38, '时迁', 'test32111', 'http://localhost:9090/file/2023-01-12d10c368f0bf246549ad391d6ccffb3e3.jpg', 1, 'admin', '7b3c7480564739726108fede7eafa91b', '2023-01-03 19:23:47', '2023-01-18 16:40:31', 0);
INSERT INTO `user` (`id`, `userName`, `userAccount`, `userAvatar`, `gender`, `userRole`, `userPassword`, `createTime`, `updateTime`, `isDelete`) VALUES (39, '方腊', 'testredis', 'http://localhost:9090/file/2023-01-12d10c368f0bf246549ad391d6ccffb3e3.jpg', 1, 'admin', 'b03d227f78c0c79334fca76e7b8eb46a', '2023-01-18 00:21:01', '2023-01-18 16:40:06', 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
