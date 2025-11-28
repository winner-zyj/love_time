-- ----------------------------
-- 未来情书表结构更新       
-- ----------------------------

-- 添加索引以提高定时任务查询性能
-- 为了兼容性，这里提供检查索引是否存在后再添加的逻辑

-- 检查并添加 idx_status_scheduled 索引
SET @index_exists = (SELECT COUNT(*) FROM information_schema.statistics WHERE table_schema = DATABASE() AND table_name = 'at_future_letter' AND index_name = 'idx_status_scheduled');
SET @sql = IF(@index_exists = 0, 'ALTER TABLE `at_future_letter` ADD INDEX `idx_status_scheduled` (`status`, `scheduled_date`, `scheduled_time`)', 'SELECT 1');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 检查并添加 idx_receiver_status 索引
SET @index_exists = (SELECT COUNT(*) FROM information_schema.statistics WHERE table_schema = DATABASE() AND table_name = 'at_future_letter' AND index_name = 'idx_receiver_status');
SET @sql = IF(@index_exists = 0, 'ALTER TABLE `at_future_letter` ADD INDEX `idx_receiver_status` (`receiver_id`, `status`)', 'SELECT 1');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 检查并添加 idx_sender_status 索引
SET @index_exists = (SELECT COUNT(*) FROM information_schema.statistics WHERE table_schema = DATABASE() AND table_name = 'at_future_letter' AND index_name = 'idx_sender_status');
SET @sql = IF(@index_exists = 0, 'ALTER TABLE `at_future_letter` ADD INDEX `idx_sender_status` (`sender_id`, `status`)', 'SELECT 1');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- 确保状态字段有合适的默认值
ALTER TABLE `at_future_letter` 
MODIFY COLUMN `status` varchar(20) DEFAULT '草稿' COMMENT '状态：草稿/已安排/已发送/已读/已取消';