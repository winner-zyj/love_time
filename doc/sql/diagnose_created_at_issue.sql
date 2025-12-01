-- 诊断脚本：检查 createdAt 字段问题

-- 1. 检查表结构
DESCRIBE `at_future_letter`;

-- 2. 插入一条测试数据，显式指定 created_at 值
INSERT INTO `at_future_letter` 
(`sender_id`, `receiver_id`, `title`, `content`, `delivery_method`, `scheduled_time`, `created_at`, `updated_at`, `status`) 
VALUES 
(999, 998, 'Diagnosis Test Letter', 'This is a diagnosis test letter', 'PARTNER', '2025-12-01 18:00:00', '2025-12-01 09:09:05', '2025-12-01 09:09:05', 'DRAFT');

-- 3. 立即查询插入的数据，检查 created_at 是否正确保存
SELECT id, sender_id, receiver_id, title, created_at, updated_at 
FROM `at_future_letter` 
WHERE title = 'Diagnosis Test Letter';

-- 4. 等待几秒后再次查询，检查 created_at 是否被意外修改
SELECT id, sender_id, receiver_id, title, created_at, updated_at 
FROM `at_future_letter` 
WHERE title = 'Diagnosis Test Letter';

-- 5. 更新记录，检查 created_at 是否保持不变
UPDATE `at_future_letter` 
SET title = 'Updated Diagnosis Test Letter' 
WHERE title = 'Diagnosis Test Letter';

-- 6. 查询更新后的数据，验证 created_at 未被修改
SELECT id, sender_id, receiver_id, title, created_at, updated_at 
FROM `at_future_letter` 
WHERE title = 'Updated Diagnosis Test Letter';

-- 7. 清理测试数据
DELETE FROM `at_future_letter` 
WHERE sender_id = 999;