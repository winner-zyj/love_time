-- 添加共享字段到at_questions表
ALTER TABLE `at_questions` 
ADD COLUMN `is_shared` tinyint(1) NULL DEFAULT 0 COMMENT '是否共享给情侣' AFTER `is_active`;

-- 创建索引以提高查询性能
ALTER TABLE `at_questions` 
ADD INDEX `idx_shared`(`is_shared` ASC);