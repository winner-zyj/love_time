-- ----------------------------
-- 完整的挑战任务功能测试
-- ----------------------------

-- 1. 清理测试数据
DELETE FROM at_challenge_records WHERE user_id IN (SELECT id FROM at_users WHERE openid LIKE 'test_openid_%');
DELETE FROM at_challenge_tasks WHERE created_by IN (SELECT id FROM at_users WHERE openid LIKE 'test_openid_%');
DELETE FROM at_challenge_progress WHERE user_id IN (SELECT id FROM at_users WHERE openid LIKE 'test_openid_%');
DELETE FROM at_couple_relationships WHERE user1_id IN (SELECT id FROM at_users WHERE openid LIKE 'test_openid_%') 
                                       OR user2_id IN (SELECT id FROM at_users WHERE openid LIKE 'test_openid_%');
DELETE FROM at_users WHERE openid LIKE 'test_openid_%';

-- 2. 插入测试用户
INSERT INTO `at_users` (`openid`, `nickName`, `avatarUrl`, `invite_code`) VALUES
('test_openid_1', '测试用户1', 'http://example.com/avatar1.jpg', 'TEST001'),
('test_openid_2', '测试用户2', 'http://example.com/avatar2.jpg', 'TEST002');

-- 3. 获取用户ID
SET @user1_id = (SELECT id FROM at_users WHERE openid = 'test_openid_1');
SET @user2_id = (SELECT id FROM at_users WHERE openid = 'test_openid_2');

-- 4. 插入测试情侣关系
INSERT INTO `at_couple_relationships` (`user1_id`, `user2_id`, `status`, `initiator_id`, `receiver_id`, `relationship_name`, `created_at`, `confirmed_at`) 
VALUES (@user1_id, @user2_id, 'active', @user1_id, @user2_id, '测试情侣', NOW(), NOW());

-- 5. 插入测试自定义任务
INSERT INTO `at_challenge_tasks` (`task_name`, `task_description`, `task_index`, `category`, `created_by`, `icon_url`, `is_active`, `created_at`, `updated_at`) VALUES
('测试自定义任务1', '这是第一个自定义任务', NULL, 'custom', @user1_id, NULL, 1, NOW(), NOW()),
('测试自定义任务2', '这是第二个自定义任务', NULL, 'custom', @user1_id, NULL, 1, NOW(), NOW());

-- 6. 获取任务ID
SET @preset_task_id = (SELECT id FROM at_challenge_tasks WHERE category = 'preset' LIMIT 1);
SET @custom_task_id = (SELECT id FROM at_challenge_tasks WHERE category = 'custom' AND created_by = @user1_id LIMIT 1);

-- 7. 插入测试记录
INSERT INTO `at_challenge_records` (`task_id`, `user_id`, `status`, `photo_url`, `note`, `is_favorited`, `completed_at`, `created_at`, `updated_at`) VALUES
(@preset_task_id, @user1_id, 'completed', 'http://example.com/photo1.jpg', '完成了第一个预设任务', 1, NOW(), NOW(), NOW()),
(@custom_task_id, @user1_id, 'pending', NULL, NULL, 0, NULL, NOW(), NOW());

-- 8. 查询验证
SELECT '=== 用户列表 ===' as info;
SELECT id, nickName, invite_code FROM at_users WHERE id IN (@user1_id, @user2_id);

SELECT '=== 情侣关系 ===' as info;
SELECT id, user1_id, user2_id, status FROM at_couple_relationships WHERE user1_id = @user1_id AND user2_id = @user2_id;

SELECT '=== 预设任务 ===' as info;
SELECT id, task_name, task_index, category FROM at_challenge_tasks WHERE category = 'preset' ORDER BY task_index LIMIT 5;

SELECT '=== 自定义任务 ===' as info;
SELECT id, task_name, category, created_by FROM at_challenge_tasks WHERE category = 'custom' AND created_by = @user1_id;

SELECT '=== 用户记录 ===' as info;
SELECT r.id, r.task_id, t.task_name, r.user_id, u.nickName, r.status, r.is_favorited 
FROM at_challenge_records r
JOIN at_challenge_tasks t ON r.task_id = t.id
JOIN at_users u ON r.user_id = u.id
WHERE r.user_id = @user1_id;

SELECT '=== 测试完成 ===' as info;