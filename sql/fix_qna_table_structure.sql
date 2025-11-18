-- ----------------------------
-- 修正甜蜜问答表结构
-- ----------------------------

-- 修改at_questions表的category字段注释
ALTER TABLE `at_questions` 
MODIFY COLUMN `category` enum('preset','custom') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'preset' COMMENT '问题类型：preset=预设问题, custom=自定义问题';

-- 确保at_questions表有正确的索引
ALTER TABLE `at_questions` 
ADD INDEX `idx_category` (`category`),
ADD INDEX `idx_created_by` (`created_by`);

-- 确保at_answers表有正确的索引
ALTER TABLE `at_answers` 
ADD INDEX `idx_question_id` (`question_id`),
ADD INDEX `idx_user_id` (`user_id`),
ADD INDEX `idx_answered_at` (`answered_at`);
