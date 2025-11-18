-- ----------------------------
-- 测试甜蜜问答功能
-- ----------------------------

-- 插入测试用户
INSERT INTO `at_users` (`openid`, `nickName`, `avatarUrl`, `invite_code`) VALUES
('test_openid_1', '测试用户1', 'http://example.com/avatar1.jpg', 'TEST001'),
('test_openid_2', '测试用户2', 'http://example.com/avatar2.jpg', 'TEST002');

-- 获取用户ID
SET @user1_id = (SELECT id FROM at_users WHERE openid = 'test_openid_1');
SET @user2_id = (SELECT id FROM at_users WHERE openid = 'test_openid_2');

-- 插入测试情侣关系
INSERT INTO `at_couple_relationships` (`user1_id`, `user2_id`, `status`, `initiator_id`, `receiver_id`, `relationship_name`, `created_at`, `confirmed_at`) 
VALUES (@user1_id, @user2_id, 'active', @user1_id, @user2_id, '测试情侣', NOW(), NOW());

-- 插入测试自定义问题
INSERT INTO `at_questions` (`question_text`, `category`, `created_by`, `order_index`, `is_active`) VALUES
('这是我们自定义的问题', 'custom', @user1_id, 999, 1);

-- 获取问题ID
SET @question_id = (SELECT id FROM at_questions WHERE question_text = '这是我们自定义的问题');

-- 插入测试答案
INSERT INTO `at_answers` (`question_id`, `user_id`, `answer_text`, `answered_at`) VALUES
(@question_id, @user1_id, '这是用户1的答案', NOW());

-- 查询验证
SELECT '用户列表' as info;
SELECT id, nickName, invite_code FROM at_users WHERE id IN (@user1_id, @user2_id);

SELECT '情侣关系' as info;
SELECT id, user1_id, user2_id, status FROM at_couple_relationships WHERE user1_id = @user1_id AND user2_id = @user2_id;

SELECT '问题列表' as info;
SELECT id, question_text, category, created_by FROM at_questions WHERE category = 'custom' AND created_by = @user1_id;

SELECT '答案列表' as info;
SELECT id, question_id, user_id, answer_text FROM at_answers WHERE question_id = @question_id;
