-- 为at_couple_relationships表添加couple_id字段
ALTER TABLE at_couple_relationships 
ADD COLUMN couple_id bigint(20) DEFAULT NULL COMMENT '情侣ID';