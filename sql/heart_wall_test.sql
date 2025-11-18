-- ----------------------------
-- 心形墙功能测试
-- ----------------------------

-- 1. 清理测试数据
DELETE FROM at_heart_wall_photos WHERE user_id IN (SELECT id FROM at_users WHERE openid LIKE 'test_openid_%');
DELETE FROM at_heart_wall_projects WHERE user_id IN (SELECT id FROM at_users WHERE openid LIKE 'test_openid_%');
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

-- 5. 插入测试心形墙项目
INSERT INTO `at_heart_wall_projects` (`user_id`, `project_name`, `description`, `photo_count`, `max_photos`, `cover_photo_url`, `is_public`, `created_at`, `updated_at`) VALUES
(@user1_id, '我们的回忆', '记录我们的美好时光', 0, 40, NULL, 0, NOW(), NOW()),
(@user1_id, '旅行足迹', '记录我们的旅行经历', 0, 40, NULL, 0, NOW(), NOW());

-- 6. 获取项目ID
SET @project1_id = (SELECT id FROM at_heart_wall_projects WHERE user_id = @user1_id AND project_name = '我们的回忆');
SET @project2_id = (SELECT id FROM at_heart_wall_projects WHERE user_id = @user1_id AND project_name = '旅行足迹');

-- 7. 插入测试照片
INSERT INTO `at_heart_wall_photos` (`project_id`, `user_id`, `photo_url`, `thumbnail_url`, `position_index`, `caption`, `taken_date`, `uploaded_at`, `updated_at`) VALUES
(@project1_id, @user1_id, 'http://example.com/photo1.jpg', 'http://example.com/thumb1.jpg', 1, '第一次约会', '2025-11-01', NOW(), NOW()),
(@project1_id, @user1_id, 'http://example.com/photo2.jpg', 'http://example.com/thumb2.jpg', 2, '海边漫步', '2025-11-05', NOW(), NOW()),
(@project2_id, @user1_id, 'http://example.com/photo3.jpg', 'http://example.com/thumb3.jpg', 1, '北京之旅', '2025-10-15', NOW(), NOW());

-- 8. 更新项目照片数量
UPDATE at_heart_wall_projects SET photo_count = 2 WHERE id = @project1_id;
UPDATE at_heart_wall_projects SET photo_count = 1 WHERE id = @project2_id;

-- 9. 查询验证
SELECT '=== 用户列表 ===' as info;
SELECT id, nickName, invite_code FROM at_users WHERE id IN (@user1_id, @user2_id);

SELECT '=== 情侣关系 ===' as info;
SELECT id, user1_id, user2_id, status FROM at_couple_relationships WHERE user1_id = @user1_id AND user2_id = @user2_id;

SELECT '=== 心形墙项目 ===' as info;
SELECT id, user_id, project_name, description, photo_count, max_photos, is_public FROM at_heart_wall_projects WHERE user_id = @user1_id;

SELECT '=== 心形墙照片 ===' as info;
SELECT id, project_id, photo_url, position_index, caption, taken_date FROM at_heart_wall_photos WHERE project_id IN (@project1_id, @project2_id) ORDER BY project_id, position_index;

SELECT '=== 测试完成 ===' as info;