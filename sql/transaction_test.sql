-- 事务测试脚本

-- 1. 检查当前的自动提交状态
SELECT @@autocommit as autocommit_status;

-- 2. 开始事务
START TRANSACTION;

-- 3. 插入测试数据
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
    '事务测试关系', 
    NOW(), 
    NOW()
);

-- 4. 获取插入的记录ID
SET @test_transaction_id = LAST_INSERT_ID();
SELECT @test_transaction_id as inserted_id;

-- 5. 更新 couple_id 字段
UPDATE at_couple_relationships 
SET couple_id = @test_transaction_id 
WHERE id = @test_transaction_id;

-- 6. 验证数据在事务中是否存在
SELECT * FROM at_couple_relationships WHERE id = @test_transaction_id;

-- 7. 回滚事务（不提交）
ROLLBACK;

-- 8. 验证数据是否真的被回滚
SELECT * FROM at_couple_relationships WHERE id = @test_transaction_id;

-- 9. 再次开始事务并提交
START TRANSACTION;

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
    '事务测试关系2', 
    NOW(), 
    NOW()
);

SET @test_transaction_id2 = LAST_INSERT_ID();

UPDATE at_couple_relationships 
SET couple_id = @test_transaction_id2 
WHERE id = @test_transaction_id2;

-- 10. 提交事务
COMMIT;

-- 11. 验证数据是否真的被提交
SELECT * FROM at_couple_relationships WHERE id = @test_transaction_id2;

-- 12. 清理测试数据
DELETE FROM at_couple_relationships WHERE id = @test_transaction_id2;