-- 更新at_users表结构，添加session_key和updated_at字段

-- 添加session_key字段
ALTER TABLE at_users 
ADD COLUMN session_key varchar(128) DEFAULT NULL COMMENT '微信session_key' AFTER openid;

-- 添加updated_at字段
ALTER TABLE at_users 
ADD COLUMN updated_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间';

-- 修改created_at字段类型（如果需要）
ALTER TABLE at_users 
MODIFY COLUMN created_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间';

-- 确保code字段长度足够（至少64字符以支持6位中英文邀请码）
ALTER TABLE at_users 
MODIFY COLUMN code varchar(64) NOT NULL COMMENT '邀请码';

-- 添加唯一索引（如果不存在）
ALTER TABLE at_users 
ADD UNIQUE KEY uk_code (code);

-- 添加索引（如果不存在）
ALTER TABLE at_users 
ADD UNIQUE KEY uk_openid (openid);