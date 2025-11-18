-- 检查 at_couple_relationships 表结构
SHOW CREATE TABLE at_couple_relationships;

-- 检查 couple_id 字段是否存在
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
ORDER BY 
    ORDINAL_POSITION;

-- 检查索引
SHOW INDEX FROM at_couple_relationships;