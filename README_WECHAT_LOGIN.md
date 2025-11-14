# 微信小程序登录功能说明

## 功能概述

本项目实现了微信小程序的授权登录功能，遵循微信官方的登录流程规范。

## 微信登录流程

1. 用户在小程序中选择使用微信授权登录功能
2. 小程序调用 wx.login 接口，向微信服务器发起登录请求
3. 微信服务器验证小程序的合法性，如果合法，会返回一个临时登录凭证 code 给小程序
4. 小程序将收到的 code 发送到后台服务器
5. 后台服务器接收到 code 后，使用自己的 AppID 和 AppSecret，以及收到的 code，调用微信接口向微信服务器发送请求，获取用户的唯一标识 openid 和会话密钥 session_key
6. 后台服务器根据 openid 和 session_key，进行用户身份的验证和处理，可以将用户信息存储在后台数据库中
7. 后台服务器将验证结果返回给小程序
8. 小程序根据收到的验证结果，进行相应的登录状态处理，如登录成功后，显示用户相关的个性化内容

## 接口说明

### 微信登录接口

- **接口地址**: `POST /api/login/wechat`
- **请求参数**:
  ```json
  {
    "code": "微信登录凭证",
    "nickName": "用户昵称",
    "avatarUrl": "用户头像URL"
  }
  ```
- **响应结果**:
  ```json
  {
    "code": 200,
    "msg": "登录成功",
    "data": {
      "success": true,
      "message": "登录成功",
      "user": {
        "id": 1,
        "openid": "用户的openid",
        "sessionKey": "用户的session_key",
        "code": "邀请码",
        "nickName": "用户昵称",
        "avatarUrl": "用户头像URL",
        "createdAt": "2025-11-13T12:00:00"
      },
      "token": "JWT令牌"
    }
  }
  ```

## 配置说明

在 `application.yml` 配置文件中添加微信相关配置：

```yaml
# 微信配置
wechat:
  appid: your_wechat_appid
  secret: your_wechat_secret
```

请将 `your_wechat_appid` 和 `your_wechat_secret` 替换为实际的微信小程序 AppID 和 AppSecret。

## 数据库设计

用户信息存储在 `at_users` 表中，包含以下字段：

- `id`: 用户ID
- `openid`: 微信用户的唯一标识
- `session_key`: 微信会话密钥
- `code`: 用户邀请码
- `nickName`: 用户昵称
- `avatarUrl`: 用户头像URL
- `created_at`: 创建时间

## 安全说明

1. 微信登录凭证(code)是一次性的，需要在获取后立即使用
2. 系统会为每个新用户生成唯一的8位邀请码
3. JWT token有效期为30分钟
4. 用户头像URL和昵称会在每次登录时更新
5. session_key存储在数据库中，用于后续的微信接口调用

## 注意事项

1. 请确保配置文件中的微信 AppID 和 AppSecret 正确无误
2. 确保服务器能够访问微信接口地址：https://api.weixin.qq.com/sns/jscode2session
3. 微信登录凭证(code)的有效期为5分钟，需要及时使用
4. session_key的安全性很重要，请不要泄露给前端