-- ----------------------------
-- 更新未来情书表结构
-- 只删除 scheduled_date 列并将 scheduled_time 改为 datetime 类型
-- ----------------------------

-- 删除 scheduled_date 列
ALTER TABLE `at_future_letter` 
DROP COLUMN `scheduled_date`;

-- 修改 scheduled_time 列类型为 datetime
ALTER TABLE `at_future_letter` 
MODIFY COLUMN `scheduled_time` datetime DEFAULT NULL COMMENT '预计发送时间';