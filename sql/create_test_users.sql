-- 创建测试用户以避免外键约束问题

-- 检查用户是否已存在
SELECT id, nick_name FROM at_users WHERE id IN (1001, 1002, 2001, 2002, 3001, 3002);

-- 如果用户不存在，则创建测试用户
INSERT IGNORE INTO at_users (id, openid, nickName, avatarUrl, created_at, updated_at) 
VALUES 
(1001, 'test_openid_1001', '测试用户1001', 'https://example.com/avatar1.png', NOW(), NOW()),
(1002, 'test_openid_1002', '测试用户1002', 'https://example.com/avatar2.png', NOW(), NOW()),
(2001, 'test_openid_2001', '测试用户2001', 'https://example.com/avatar3.png', NOW(), NOW()),
(2002, 'test_openid_2002', '测试用户2002', 'https://example.com/avatar4.png', NOW(), NOW()),
(3001, 'test_openid_3001', '测试用户3001', 'https://example.com/avatar5.png', NOW(), NOW()),
(3002, 'test_openid_3002', '测试用户3002', 'https://example.com/avatar6.png', NOW(), NOW());

-- 验证用户创建结果
SELECT id, nick_name, openid FROM at_users WHERE id IN (1001, 1002, 2001, 2002, 3001, 3002);

-- 现在可以安全地测试情侣关系插入
INSERT INTO at_couple_relationships (
    user1_id, 
    user2_id, 
    status, 
    initiator_id, 
    receiver_id, 
    relationship_name, 
    created_at, 
    updated_at
) VALUES (
    1001, 
    1002, 
    'active', 
    1001, 
    1002, 
    '测试情侣关系', 
    NOW(), 
    NOW()
);

-- 获取插入的记录ID
SET @last_id = LAST_INSERT_ID();

-- 更新 couple_id 字段
UPDATE at_couple_relationships 
SET couple_id = @last_id 
WHERE id = @last_id;

-- 验证记录是否正确保存
SELECT * FROM at_couple_relationships WHERE id = @last_id;

-- 清理测试数据
DELETE FROM at_couple_relationships WHERE id = @last_id;
DELETE FROM at_users WHERE id IN (1001, 1002, 2001, 2002, 3001, 3002);