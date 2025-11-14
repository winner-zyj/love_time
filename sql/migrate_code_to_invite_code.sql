-- 将现有用户的code字段值迁移到invite_code字段（仅在invite_code为空时）
UPDATE at_users 
SET invite_code = code 
WHERE invite_code IS NULL AND code IS NOT NULL;

-- 清空所有用户的code字段，因为code应该是临时登录凭证
UPDATE at_users 
SET code = NULL;