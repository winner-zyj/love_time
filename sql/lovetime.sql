-- ----------------------------
-- Table structure for at_users
-- ----------------------------
DROP TABLE IF EXISTS `at_users`;
CREATE TABLE `at_users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `openid` varchar(128) DEFAULT NULL COMMENT '微信openid',
  `session_key` varchar(128) DEFAULT NULL COMMENT '微信session_key',
  `code` varchar(128) DEFAULT NULL COMMENT '临时登录凭证',
  `invite_code` varchar(64) DEFAULT NULL COMMENT '邀请码',
  `couple_id` bigint(20) DEFAULT NULL COMMENT '情侣ID',
  `nickName` varchar(64) DEFAULT NULL COMMENT '用户昵称',
  `avatarUrl` varchar(255) DEFAULT NULL COMMENT '用户头像URL',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`),
  UNIQUE KEY `uk_invite_code` (`invite_code`),
  UNIQUE KEY `uk_openid` (`openid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户表';