-- 修复外键约束问题的脚本

-- 1. 检查现有的外键约束
SELECT 
    CONSTRAINT_NAME,
    TABLE_NAME,
    COLUMN_NAME,
    REFERENCED_TABLE_NAME,
    REFERENCED_COLUMN_NAME
FROM 
    INFORMATION_SCHEMA.KEY_COLUMN_USAGE 
WHERE 
    TABLE_SCHEMA = 'ry-vue' 
    AND TABLE_NAME = 'at_couple_relationships'
    AND REFERENCED_TABLE_NAME IS NOT NULL;

-- 2. 检查 at_users 表中是否存在相关用户
SELECT id, nick_name, openid FROM at_users 
WHERE id IN (
    SELECT DISTINCT user1_id FROM at_couple_relationships 
    WHERE user1_id IS NOT NULL
    UNION
    SELECT DISTINCT user2_id FROM at_couple_relationships 
    WHERE user2_id IS NOT NULL
);

-- 3. 查找违反外键约束的记录
SELECT cr.id, cr.user1_id, cr.user2_id, 
       u1.id as user1_exists, u2.id as user2_exists
FROM at_couple_relationships cr
LEFT JOIN at_users u1 ON cr.user1_id = u1.id
LEFT JOIN at_users u2 ON cr.user2_id = u2.id
WHERE u1.id IS NULL OR u2.id IS NULL;

-- 4. 如果需要，可以临时禁用外键检查来清理数据
-- SET FOREIGN_KEY_CHECKS = 0;
-- DELETE FROM at_couple_relationships WHERE id IN (违反约束的记录ID);
-- SET FOREIGN_KEY_CHECKS = 1;

-- 5. 验证修复后的数据
SELECT COUNT(*) as valid_relationships FROM at_couple_relationships cr
INNER JOIN at_users u1 ON cr.user1_id = u1.id
INNER JOIN at_users u2 ON cr.user2_id = u2.id;