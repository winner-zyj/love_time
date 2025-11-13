package com.ruoyi.common.core.domain.lovetime;

import com.ruoyi.common.core.domain.lovetime.User;

/**
 * 微信登录响应结果
 * 
 * @author ruoyi
 * @date 2025-11-13
 */
public class WechatLoginResponse {
    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 用户信息
     */
    private User user;

    /**
     * JWT token
     */
    private String token;

    public WechatLoginResponse() {
    }

    public WechatLoginResponse(boolean success, String message, User user, String token) {
        this.success = success;
        this.message = message;
        this.user = user;
        this.token = token;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "WechatLoginResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", user=" + user +
                ", token='" + token + '\'' +
                '}';
    }
}