-- 测试脚本：验证 createdAt 字段修复是否有效

-- 1. 插入一条测试数据
INSERT INTO `at_future_letter` 
(`sender_id`, `receiver_id`, `title`, `content`, `delivery_method`, `scheduled_time`, `created_at`, `updated_at`, `status`) 
VALUES 
(1, 2, 'Test Letter', 'This is a test letter', 'PARTNER', '2025-12-01 18:00:00', '2025-12-01 09:09:05', '2025-12-01 09:09:05', 'UNSCHEDULED');

-- 2. 查询插入的数据
SELECT id, sender_id, receiver_id, title, created_at, updated_at 
FROM `at_future_letter` 
WHERE title = 'Test Letter';

-- 3. 更新测试数据
UPDATE `at_future_letter` 
SET title = 'Updated Test Letter', updated_at = '2025-12-01 10:00:00'
WHERE title = 'Test Letter';

-- 4. 查询更新后的数据
SELECT id, sender_id, receiver_id, title, created_at, updated_at 
FROM `at_future_letter` 
WHERE title = 'Updated Test Letter';

-- 5. 清理测试数据
DELETE FROM `at_future_letter` 
WHERE title LIKE '%Test Letter%';