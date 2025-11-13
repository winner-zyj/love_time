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

    /** 邀请码 */
    private String code;

    /** 用户昵称 */
    private String nickName;

    /** 用户头像URL */
    private String avatarUrl;

    /** 创建时间 */
    private Date createdAt;

    public User() {
    }

    public User(Long id, String openid, String code, String nickName, String avatarUrl, Date createdAt) {
        this.id = id;
        this.openid = openid;
        this.code = code;
        this.nickName = nickName;
        this.avatarUrl = avatarUrl;
        this.createdAt = createdAt;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", openid='" + openid + '\'' +
                ", code='" + code + '\'' +
                ", nickName='" + nickName + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}