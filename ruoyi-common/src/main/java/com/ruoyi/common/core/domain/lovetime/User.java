package com.ruoyi.common.core.domain.lovetime;

import java.util.Date;

/**
 * 用户对象 at_users
 * 
 * @author ruoyi
 * @date 2025-11-13
 */
public class User
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private Long id;

    /** 微信openid */
    private String openid;

    /** 微信session_key */
    private String sessionKey;

    /** 临时登录凭证 */
    private String code;

    /** 邀请码 (6位大写英文字母和数字结合) */
    private String inviteCode;

    /** 情侣ID */
    private Long coupleId;

    /** 用户昵称 */
    private String nickName;

    /** 用户头像URL */
    private String avatarUrl;

    /** 创建时间 */
    private Date createdAt;

    /** 更新时间 */
    private Date updatedAt;

    public User() {
    }

    public User(Long id, String openid, String sessionKey, String code, String inviteCode, Long coupleId, String nickName, String avatarUrl, Date createdAt, Date updatedAt) {
        this.id = id;
        this.openid = openid;
        this.sessionKey = sessionKey;
        this.code = code;
        this.inviteCode = inviteCode;
        this.coupleId = coupleId;
        this.nickName = nickName;
        this.avatarUrl = avatarUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public Long getCoupleId() {
        return coupleId;
    }

    public void setCoupleId(Long coupleId) {
        this.coupleId = coupleId;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", openid='" + openid + '\'' +
                ", sessionKey='" + sessionKey + '\'' +
                ", code='" + code + '\'' +
                ", inviteCode='" + inviteCode + '\'' +
                ", coupleId=" + coupleId +
                ", nickName='" + nickName + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}