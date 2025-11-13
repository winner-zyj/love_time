package com.ruoyi.common.core.domain.lovetime;

/**
 * 微信登录请求参数
 * 
 * @author ruoyi
 * @date 2025-11-13
 */
public class WechatLoginRequest {
    /**
     * 微信登录凭证
     */
    private String code;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户头像URL
     */
    private String avatarUrl;

    public WechatLoginRequest() {
    }

    public WechatLoginRequest(String code, String nickName, String avatarUrl) {
        this.code = code;
        this.nickName = nickName;
        this.avatarUrl = avatarUrl;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String toString() {
        return "WechatLoginRequest{" +
                "code='" + code + '\'' +
                ", nickName='" + nickName + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}