-- 检查 at_couple_relationships 表结构
DESCRIBE at_couple_relationships;

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
    AND COLUMN_NAME = 'couple_id';

-- 如果 couple_id 字段不存在，则添加它
ALTER TABLE at_couple_relationships 
ADD COLUMN couple_id BIGINT DEFAULT NULL COMMENT '情侣ID';

-- 为 couple_id 字段添加索引
CREATE INDEX idx_couple_relationships_couple_id ON at_couple_relationships(couple_id);