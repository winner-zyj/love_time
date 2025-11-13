# 恋语时光微信登录功能说明

## 功能概述

本项目在若依框架基础上实现了微信小程序的微信登录功能，包括用户注册、登录和JWT token生成。

## API接口

### 微信登录接口

**接口地址**: `POST /api/login/wechat`

**请求参数**:
```json
{
  "code": "微信登录凭证",
  "nickName": "用户昵称",
  "avatarUrl": "用户头像URL"
}
```

**响应结果**:
```json
{
  "success": true,
  "message": "登录成功",
  "data": {
    "user": {
      "id": 1,
      "openid": "微信openid",
      "code": "邀请码",
      "nickName": "用户昵称",
      "avatarUrl": "用户头像URL",
      "createdAt": "创建时间"
    },
    "token": "JWT token"
  }
}
```

## 数据库表结构

### users表
```sql
CREATE TABLE `users`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `openid` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '微信openid',
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邀请码',
  `nickName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `avatarUrl` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户头像URL',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `openid`(`openid` ASC) USING BTREE,
  INDEX `idx_created_at`(`created_at` ASC) USING BTREE,
  INDEX `idx_openid`(`openid` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '简化用户表' ROW_FORMAT = Dynamic;
```

## 核心代码结构

```
ruoyi-admin/
└── src/main/java/com/ruoyi/web/controller/lovetime/
    ├── WechatLoginController.java    # 微信登录控制器
    └── TestController.java           # 测试控制器

ruoyi-common/
└── src/main/java/com/ruoyi/common/utils/
    └── JwtUtil.java                  # JWT工具类

ruoyi-system/
├── src/main/java/com/ruoyi/lovetime/
│   ├── domain/
│   │   └── User.java               # 用户实体类
│   ├── mapper/
│   │   └── UserMapper.java         # 用户Mapper接口
│   └── service/
│       ├── IUserService.java       # 用户Service接口
│       └── impl/
│           └── UserServiceImpl.java # 用户Service实现类
└── src/main/resources/mapper/lovetime/
    └── UserMapper.xml              # 用户Mapper XML映射文件

ruoyi-common/
└── src/main/java/com/ruoyi/common/core/domain/lovetime/
    ├── WechatLoginRequest.java     # 微信登录请求DTO
    └── WechatLoginResponse.java    # 微信登录响应DTO
```

## 使用说明

1. 确保数据库中已创建`lovetime`数据库和`users`表
2. 启动若依项目
3. 调用`POST /api/login/wechat`接口进行微信登录
4. 系统会自动创建新用户或更新现有用户信息
5. 返回JWT token用于后续接口认证

## 测试接口

**测试数据库连接**: `GET /api/test/db`

## 注意事项

1. 微信登录凭证(code)是一次性的，需要在获取后立即使用
2. 系统会为每个新用户生成唯一的8位邀请码
3. JWT token有效期为30分钟
4. 用户头像URL和昵称会在每次登录时更新