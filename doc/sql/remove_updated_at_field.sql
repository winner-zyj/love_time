-- ----------------------------
-- 删除 future_letter 表中的 updated_at 字段
-- ----------------------------

-- 删除 updated_at 列
ALTER TABLE `at_future_letter` 
DROP COLUMN `updated_at`;