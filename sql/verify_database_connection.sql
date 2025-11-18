-- 检查数据库连接状态
SHOW VARIABLES LIKE 'autocommit';

-- 检查当前连接的事务隔离级别
SELECT @@transaction_isolation;

-- 检查数据库版本
SELECT VERSION();

-- 检查当前数据库
SELECT DATABASE();

-- 检查表是否存在
SHOW TABLES LIKE 'at_couple_relationships';

-- 检查表引擎
SELECT ENGINE 
FROM information_schema.TABLES 
WHERE TABLE_SCHEMA = 'ry-vue' 
AND TABLE_NAME = 'at_couple_relationships';