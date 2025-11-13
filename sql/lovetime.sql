-- 甜蜜时光数据库初始化脚本

-- 创建数据库
CREATE DATABASE IF NOT EXISTS lovetime DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE lovetime;

-- 删除已存在的表
DROP TABLE IF EXISTS `at_users`;

-- 创建用户表
CREATE TABLE `at_users`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `openid` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '微信openid',
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邀请码',
  `nickName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `avatarUrl` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户头像URL',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `openid`(`openid` ASC) USING BTREE,
  INDEX `idx_created_at`(`created_at` ASC) USING BTREE,
  INDEX `idx_openid`(`openid` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '简化用户表' ROW_FORMAT = Dynamic;

-- 插入示例数据
INSERT INTO `at_users` VALUES (1, 'test_openid_1', 'TESTCODE1', '测试用户1', 'https://example.com/avatar1.jpg', '2025-11-13 10:00:00');
INSERT INTO `at_users` VALUES (2, 'test_openid_2', 'TESTCODE2', '测试用户2', 'https://example.com/avatar2.jpg', '2025-11-13 10:00:00');

-- 创建 couple_relationships 表
CREATE TABLE `couple_relationships`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '关系ID',
  `user1_id` bigint NOT NULL COMMENT '用户1 ID',
  `user2_id` bigint NOT NULL COMMENT '用户2 ID',
  `status` enum('pending','active','rejected','broken') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT 'pending' COMMENT '关系状态：待确认/已绑定/已拒绝/已解绑',
  `initiator_id` bigint NOT NULL COMMENT '发起绑定的用户ID',
  `receiver_id` bigint NOT NULL COMMENT '接收请求的用户ID',
  `relationship_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '关系昵称（如：我的宝贝）',
  `anniversary_date` date NULL DEFAULT NULL COMMENT '纪念日（恋爱开始日期）',
  `request_message` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '绑定请求留言',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `confirmed_at` timestamp NULL DEFAULT NULL COMMENT '确认绑定时间',
  `rejected_at` timestamp NULL DEFAULT NULL COMMENT '拒绝时间',
  `broken_at` timestamp NULL DEFAULT NULL COMMENT '解绑时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user1`(`user1_id` ASC) USING BTREE,
  INDEX `idx_user2`(`user2_id` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  INDEX `idx_initiator`(`initiator_id` ASC) USING BTREE,
  INDEX `idx_receiver`(`receiver_id` ASC) USING BTREE,
  CONSTRAINT `fk_couple_relationships_initiator` FOREIGN KEY (`initiator_id`) REFERENCES `at_users` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_couple_relationships_receiver` FOREIGN KEY (`receiver_id`) REFERENCES `at_users` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_couple_relationships_user1` FOREIGN KEY (`user1_id`) REFERENCES `at_users` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_couple_relationships_user2` FOREIGN KEY (`user2_id`) REFERENCES `at_users` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `chk_couple_relationships_user_order` CHECK (`user1_id` < `user2_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '情侣关系表' ROW_FORMAT = Dynamic;