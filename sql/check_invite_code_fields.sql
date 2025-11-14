-- 检查at_users表的字段结构
DESCRIBE at_users;

-- 检查是否有重复的invite_code
SELECT invite_code, COUNT(*) as count 
FROM at_users 
WHERE invite_code IS NOT NULL 
GROUP BY invite_code 
HAVING COUNT(*) > 1;

-- 检查是否有重复的code
SELECT code, COUNT(*) as count 
FROM at_users 
WHERE code IS NOT NULL 
GROUP BY code 
HAVING COUNT(*) > 1;