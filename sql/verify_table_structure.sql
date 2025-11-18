-- 验证表结构脚本

-- 1. 检查 at_couple_relationships 表结构
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
    AND COLUMN_NAME = 'couple_id';

-- 3. 检查外键约束
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

-- 4. 检查 at_users 表结构
DESCRIBE at_users;

-- 5. 检查现有数据
SELECT COUNT(*) as total_relationships FROM at_couple_relationships;
SELECT COUNT(*) as total_users FROM at_users;

-- 6. 检查最近插入的记录
SELECT * FROM at_couple_relationships ORDER BY created_at DESC LIMIT 5;