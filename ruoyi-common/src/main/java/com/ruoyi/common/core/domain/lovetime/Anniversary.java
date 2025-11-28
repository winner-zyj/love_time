package com.ruoyi.common.core.domain.lovetime;

import java.util.Date;

/**
 * 纪念日实体类
 * 
 * @author ruoyi
 * @date 2025-11-28
 */
public class Anniversary {
    /** 纪念日ID */
    private Long id;
    
    /** 用户ID */
    private Long userId;
    
    /** 纪念日标题 */
    private String title;
    
    /** 纪念日日期 */
    private Date date;
    
    /** 图标名称 */
    private String icon;
    
    /** 图标颜色 */
    private String color;
    
    /** 是否开启提醒 */
    private Boolean remind;
    
    /** 创建时间 */
    private Date createTime;
    
    /** 更新时间 */
    private Date updateTime;
    
    // Getters and Setters
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public String getIcon() {
        return icon;
    }
    
    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public Boolean getRemind() {
        return remind;
    }
    
    public void setRemind(Boolean remind) {
        this.remind = remind;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public Date getUpdateTime() {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}