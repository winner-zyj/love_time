package com.ruoyi.common.core.domain.lovetime;

/**
 * 邀请码登录请求参数
 * 
 * @author ruoyi
 * @date 2025-11-14
 */
public class InviteLoginRequest {
    /**
     * 邀请码
     */
    private String inviteCode;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户头像URL
     */
    private String avatarUrl;

    public InviteLoginRequest() {
    }

    public InviteLoginRequest(String inviteCode, String nickName, String avatarUrl) {
        this.inviteCode = inviteCode;
        this.nickName = nickName;
        this.avatarUrl = avatarUrl;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
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
        return "InviteLoginRequest{" +
                "inviteCode='" + inviteCode + '\'' +
                ", nickName='" + nickName + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}