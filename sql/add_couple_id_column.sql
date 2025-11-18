-- 添加 couple_id 字段到 at_couple_relationships 表
ALTER TABLE at_couple_relationships ADD COLUMN couple_id BIGINT DEFAULT NULL COMMENT '情侣ID';

-- 添加索引以提高查询性能
CREATE INDEX idx_couple_relationships_couple_id ON at_couple_relationships(couple_id);