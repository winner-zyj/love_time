-- 测试数据库插入操作
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
    1, 
    2, 
    'active', 
    1, 
    2, 
    '测试关系', 
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