-- 更新at_users表结构，确保invite_code和code字段正确设置

-- 确保invite_code字段存在
ALTER TABLE at_users 
ADD COLUMN IF NOT EXISTS invite_code varchar(64) DEFAULT NULL COMMENT '邀请码';

-- 确保code字段是临时登录凭证
ALTER TABLE at_users 
MODIFY COLUMN code varchar(128) DEFAULT NULL COMMENT '临时登录凭证';

-- 为invite_code字段添加唯一索引（如果不存在）
ALTER TABLE at_users 
ADD UNIQUE KEY IF NOT EXISTS uk_invite_code (invite_code);

-- 为code字段添加唯一索引（如果不存在）
ALTER TABLE at_users 
ADD UNIQUE KEY IF NOT EXISTS uk_code (code);