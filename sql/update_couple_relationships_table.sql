-- 添加 couple_id 字段到 at_couple_relationships 表
-- 检查字段是否存在，如果不存在则添加
ALTER TABLE at_couple_relationships 
ADD COLUMN couple_id BIGINT DEFAULT NULL COMMENT '情侣ID';

-- 为 couple_id 字段添加索引以提高查询性能
CREATE INDEX idx_couple_relationships_couple_id ON at_couple_relationships(couple_id);

-- 更新现有记录，将 couple_id 设置为关系ID本身
UPDATE at_couple_relationships SET couple_id = id WHERE couple_id IS NULL;

-- 验证字段是否添加成功
DESCRIBE at_couple_relationships;