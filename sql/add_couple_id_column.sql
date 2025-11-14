-- 为at_users表添加couple_id字段
ALTER TABLE at_users 
ADD COLUMN couple_id bigint(20) DEFAULT NULL COMMENT '情侣ID';