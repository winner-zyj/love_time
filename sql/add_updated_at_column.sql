-- 添加updated_at字段到at_users表
ALTER TABLE at_users 
ADD COLUMN updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间';

-- 确保code字段长度足够（至少64字符以支持6位中英文邀请码）
ALTER TABLE at_users 
MODIFY COLUMN code varchar(64) NOT NULL COMMENT '邀请码';

-- 如果需要为现有记录设置updated_at的默认值，可以运行以下语句：
-- UPDATE at_users SET updated_at = created_at WHERE updated_at IS NULL;