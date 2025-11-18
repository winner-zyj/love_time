-- 检查 at_couple_relationships 表的外键约束
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

-- 检查 at_users 表结构
DESCRIBE at_users;

-- 检查 at_couple_relationships 表结构
DESCRIBE at_couple_relationships;

-- 检查 at_users 表中是否存在相关用户
SELECT id, nick_name FROM at_users WHERE id IN (1001, 1002, 2001, 2002, 3001, 3002);

-- 检查现有的情侣关系数据
SELECT * FROM at_couple_relationships LIMIT 10;