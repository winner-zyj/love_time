-- ----------------------------
-- 更新at_challenge_records表结构，添加新字段以支持"一百件小事"功能的增强
-- ----------------------------

-- 添加新字段
ALTER TABLE `at_challenge_records` 
ADD COLUMN `location` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '完成地点' AFTER `note`,
ADD COLUMN `completed_date` date NULL DEFAULT NULL COMMENT '完成日期' AFTER `location`,
ADD COLUMN `completed_time` time NULL DEFAULT NULL COMMENT '完成时间' AFTER `completed_date`,
ADD COLUMN `weather` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '完成时的天气' AFTER `completed_time`,
ADD COLUMN `feeling` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '完成时的感受' AFTER `weather`;

-- 添加索引以提高查询性能
ALTER TABLE `at_challenge_records` 
ADD INDEX `idx_completed_date`(`completed_date` ASC) USING BTREE,
ADD INDEX `idx_location`(`location` ASC) USING BTREE;