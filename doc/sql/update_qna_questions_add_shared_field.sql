-- 为at_questions表添加is_shared字段
ALTER TABLE `at_questions` 
ADD COLUMN `is_shared` tinyint(1) NULL DEFAULT 0 COMMENT '是否共享给情侣' AFTER `is_active`;

-- 创建索引以提高查询性能
CREATE INDEX `idx_shared` ON `at_questions` (`is_shared`);

-- 更新现有自定义问题，设置为共享状态
UPDATE `at_questions` 
SET `is_shared` = 1 
WHERE `category` = 'custom';

-- 更新现有预设问题，设置为共享状态
UPDATE `at_questions` 
SET `is_shared` = 1 
WHERE `category` = 'preset';