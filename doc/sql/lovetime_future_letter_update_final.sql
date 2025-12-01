-- ----------------------------
-- 更新未来情书表结构
-- 删除 scheduled_date 列并将 scheduled_time 改为 datetime 类型
-- ----------------------------

-- 删除 scheduled_date 列
ALTER TABLE `at_future_letter` 
DROP COLUMN `scheduled_date`;

-- 修改 scheduled_time 列类型为 datetime
ALTER TABLE `at_future_letter` 
MODIFY COLUMN `scheduled_time` datetime DEFAULT NULL COMMENT '预计发送时间';

-- 删除旧的索引
ALTER TABLE `at_future_letter` 
DROP INDEX `idx_status_scheduled`;

-- 添加新的索引，使用反引号包围列名以避免关键字冲突
ALTER TABLE `at_future_letter` 
ADD INDEX `idx_status_scheduled` (`status`, `scheduled_time`);