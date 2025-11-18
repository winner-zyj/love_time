-- 验证 at_couple_relationships 表结构
DESCRIBE at_couple_relationships;

-- 检查 couple_id 字段是否存在
SELECT COLUMN_NAME, COLUMN_TYPE, IS_NULLABLE, COLUMN_DEFAULT, COLUMN_COMMENT
FROM INFORMATION_SCHEMA.COLUMNS 
WHERE TABLE_SCHEMA = 'ry-vue' 
AND TABLE_NAME = 'at_couple_relationships' 
AND COLUMN_NAME = 'couple_id';

-- 检查表中是否有数据
SELECT COUNT(*) as total_records FROM at_couple_relationships;

-- 查看表中的前几条记录
SELECT * FROM at_couple_relationships LIMIT 5;