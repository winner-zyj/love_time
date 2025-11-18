-- 综合数据库测试脚本

-- 1. 检查表结构
DESCRIBE at_couple_relationships;

-- 2. 检查 couple_id 字段是否存在
SELECT 
    COLUMN_NAME,
    COLUMN_TYPE,
    IS_NULLABLE,
    COLUMN_DEFAULT,
    COLUMN_COMMENT
FROM 
    INFORMATION_SCHEMA.COLUMNS 
WHERE 
    TABLE_SCHEMA = 'ry-vue' 
    AND TABLE_NAME = 'at_couple_relationships'
ORDER BY ORDINAL_POSITION;

-- 3. 检查索引
SHOW INDEX FROM at_couple_relationships;

-- 4. 测试插入操作
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
    2001, 
    2002, 
    'active', 
    2001, 
    2002, 
    '数据库测试关系', 
    NOW(), 
    NOW()
);

-- 5. 获取插入的记录ID
SET @test_id = LAST_INSERT_ID();
SELECT @test_id as inserted_id;

-- 6. 测试更新 couple_id 字段
UPDATE at_couple_relationships 
SET couple_id = @test_id 
WHERE id = @test_id;

-- 7. 验证记录是否正确保存
SELECT * FROM at_couple_relationships WHERE id = @test_id;

-- 8. 测试查询操作
SELECT * FROM at_couple_relationships 
WHERE (user1_id = 2001 OR user2_id = 2001) 
AND status = 'active';

-- 9. 清理测试数据
DELETE FROM at_couple_relationships WHERE id = @test_id;

-- 10. 验证清理结果
SELECT COUNT(*) as remaining_count FROM at_couple_relationships WHERE id = @test_id;