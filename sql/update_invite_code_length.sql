-- 更新邀请码字段长度以支持6位中英文邀请码
ALTER TABLE at_users 
MODIFY COLUMN code varchar(64) NOT NULL COMMENT '邀请码';

-- 确保唯一索引存在
ALTER TABLE at_users 
ADD UNIQUE KEY uk_code (code);