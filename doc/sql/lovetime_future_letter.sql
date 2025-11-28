-- ----------------------------
-- Table structure for at_future_letter
-- ----------------------------
DROP TABLE IF EXISTS `at_future_letter`;
CREATE TABLE `at_future_letter` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '信件ID',
  `sender_id` bigint(20) NOT NULL COMMENT '发送者用户ID',
  `receiver_id` bigint(20) NOT NULL COMMENT '接收者用户ID（情侣关系中的对方）',
  `title` varchar(100) NOT NULL COMMENT '信件主题',
  `content` text NOT NULL COMMENT '信件内容（最大1000字）',
  `delivery_method` varchar(20) DEFAULT '情侣对方' COMMENT '发送方式：情侣对方',
  `scheduled_date` date DEFAULT NULL COMMENT '预计发送日期',
  `scheduled_time` time DEFAULT NULL COMMENT '预计发送时间',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '更新时间',
  `status` varchar(20) DEFAULT '草稿' COMMENT '状态：草稿/已安排/已发送/已读/已取消',
  `sent_at` datetime DEFAULT NULL COMMENT '实际发送时间',
  `read_at` datetime DEFAULT NULL COMMENT '阅读时间',
  `background_image` varchar(255) DEFAULT NULL COMMENT '背景图片URL',
  `background_opacity` decimal(3,2) DEFAULT '1.00' COMMENT '背景图片透明度 (0.0-1.0)',
  `background_width` int(11) DEFAULT NULL COMMENT '背景图片宽度',
  `background_height` int(11) DEFAULT NULL COMMENT '背景图片高度',
  `font_style` varchar(50) DEFAULT 'default' COMMENT '字体样式',
  `is_deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除：0-否，1-是',
  `deleted_at` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`),
  KEY `idx_sender_id` (`sender_id`),
  KEY `idx_receiver_id` (`receiver_id`),
  KEY `idx_status_scheduled` (`status`, `scheduled_date`, `scheduled_time`),
  KEY `idx_receiver_status` (`receiver_id`, `status`),
  KEY `idx_sender_status` (`sender_id`, `status`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='未来情书表';