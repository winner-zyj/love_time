-- ----------------------------
-- 完整的甜蜜问答功能测试
-- ----------------------------

-- 1. 清理测试数据
DELETE FROM at_answers WHERE user_id IN (SELECT id FROM at_users WHERE openid LIKE 'test_openid_%');
DELETE FROM at_questions WHERE created_by IN (SELECT id FROM at_users WHERE openid LIKE 'test_openid_%');
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

-- 5. 插入预设问题（确保至少有10个预设问题）
INSERT INTO `at_questions` (`question_text`, `category`, `created_by`, `order_index`, `is_active`) VALUES
('我们第一次见面是在哪里？', 'preset', NULL, 1, 1),
('你最喜欢我哪一点？', 'preset', NULL, 2, 1),
('你觉得我们之间最美好的回忆是什么？', 'preset', NULL, 3, 1),
('如果可以重新选择，你还会选择和我在一起吗？', 'preset', NULL, 4, 1),
('你认为爱情中最重要的是什么？', 'preset', NULL, 5, 1),
('你觉得我们之间最大的默契是什么？', 'preset', NULL, 6, 1),
('你最想和我一起去哪里旅行？', 'preset', NULL, 7, 1),
('你觉得我们之间最需要改进的地方是什么？', 'preset', NULL, 8, 1),
('你最难忘的一次约会是什么时候？', 'preset', NULL, 9, 1),
('你希望我们的未来是什么样子的？', 'preset', NULL, 10, 1);

-- 6. 插入测试自定义问题
INSERT INTO `at_questions` (`question_text`, `category`, `created_by`, `order_index`, `is_active`) VALUES
('这是我们自定义的问题1', 'custom', @user1_id, 999, 1),
('这是我们自定义的问题2', 'custom', @user1_id, 999, 1);

-- 7. 获取问题ID
SET @preset_question_id = (SELECT id FROM at_questions WHERE question_text = '我们第一次见面是在哪里？');
SET @custom_question_id = (SELECT id FROM at_questions WHERE question_text = '这是我们自定义的问题1');

-- 8. 插入测试答案
INSERT INTO `at_answers` (`question_id`, `user_id`, `answer_text`, `answered_at`) VALUES
(@preset_question_id, @user1_id, '我们第一次见面是在咖啡厅', NOW()),
(@custom_question_id, @user1_id, '这是用户1的答案', NOW());

-- 9. 查询验证
SELECT '=== 用户列表 ===' as info;
SELECT id, nickName, invite_code FROM at_users WHERE id IN (@user1_id, @user2_id);

SELECT '=== 情侣关系 ===' as info;
SELECT id, user1_id, user2_id, status FROM at_couple_relationships WHERE user1_id = @user1_id AND user2_id = @user2_id;

SELECT '=== 预设问题 ===' as info;
SELECT id, question_text, category, order_index FROM at_questions WHERE category = 'preset' ORDER BY order_index LIMIT 5;

SELECT '=== 自定义问题 ===' as info;
SELECT id, question_text, category, created_by FROM at_questions WHERE category = 'custom' AND created_by = @user1_id;

SELECT '=== 答案列表 ===' as info;
SELECT a.id, a.question_id, q.question_text, a.user_id, u.nickName, a.answer_text 
FROM at_answers a
JOIN at_questions q ON a.question_id = q.id
JOIN at_users u ON a.user_id = u.id
WHERE a.question_id IN (@preset_question_id, @custom_question_id);

SELECT '=== 测试完成 ===' as info;
