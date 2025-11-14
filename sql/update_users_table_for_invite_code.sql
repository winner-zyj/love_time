-- 更新at_users表结构，添加invite_code字段并调整code字段

-- 添加invite_code字段
ALTER TABLE at_users 
ADD COLUMN invite_code varchar(64) DEFAULT NULL COMMENT '邀请码' AFTER code;

-- 修改code字段为临时登录凭证
ALTER TABLE at_users 
MODIFY COLUMN code varchar(128) DEFAULT NULL COMMENT '临时登录凭证';

-- 为invite_code字段添加唯一索引
ALTER TABLE at_users 
ADD UNIQUE KEY uk_invite_code (invite_code);

-- 如果需要将现有的code字段数据迁移到invite_code字段，可以运行以下语句：
-- UPDATE at_users SET invite_code = code WHERE invite_code IS NULL;