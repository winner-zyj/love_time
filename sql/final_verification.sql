-- 最终验证脚本

-- 1. 检查数据库连接状态
SELECT @@autocommit as autocommit_status;
SELECT @@transaction_isolation as transaction_isolation_level;

-- 2. 检查表结构和字段
DESCRIBE at_couple_relationships;

-- 3. 检查 couple_id 字段详情
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
    AND COLUMN_NAME = 'couple_id';

-- 4. 检查现有数据
SELECT COUNT(*) as total_records FROM at_couple_relationships;

-- 5. 查看最近的几条记录
SELECT * FROM at_couple_relationships ORDER BY created_at DESC LIMIT 5;

-- 6. 测试事务操作
START TRANSACTION;

-- 插入测试数据
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
    3001, 
    3002, 
    'active', 
    3001, 
    3002, 
    '事务测试关系', 
    NOW(), 
    NOW()
);

-- 获取插入的ID
SET @test_transaction_id = LAST_INSERT_ID();

-- 更新 couple_id
UPDATE at_couple_relationships 
SET couple_id = @test_transaction_id 
WHERE id = @test_transaction_id;

-- 验证数据
SELECT * FROM at_couple_relationships WHERE id = @test_transaction_id;

-- 提交事务
COMMIT;

-- 7. 验证提交后的数据
SELECT * FROM at_couple_relationships WHERE id = @test_transaction_id;

-- 8. 清理测试数据
DELETE FROM at_couple_relationships WHERE id = @test_transaction_id;

-- 9. 最终验证
SELECT COUNT(*) as final_count FROM at_couple_relationships WHERE id = @test_transaction_id;