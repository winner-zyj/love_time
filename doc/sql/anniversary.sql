-- ----------------------------
-- Table structure for at_anniversaries
-- ----------------------------
DROP TABLE IF EXISTS `at_anniversaries`;
CREATE TABLE `at_anniversaries` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '纪念日ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `title` varchar(100) NOT NULL COMMENT '纪念日标题',
  `date` date NOT NULL COMMENT '纪念日日期',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标名称',
  `color` varchar(20) DEFAULT NULL COMMENT '图标颜色',
  `remind` tinyint(1) DEFAULT '0' COMMENT '是否开启提醒',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='纪念日表';