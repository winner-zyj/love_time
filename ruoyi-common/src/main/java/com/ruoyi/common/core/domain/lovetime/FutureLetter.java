package com.ruoyi.common.core.domain.lovetime;

import java.util.Date;

/**
 * 未来情书实体类
 * 
 * @author ruoyi
 * @date 2025-11-19
 */
public class FutureLetter {
    /** 信件ID */
    private Long id;
    
    /** 发送者用户ID */
    private Long senderId;
    
    /** 接收者用户ID（情侣关系中的对方） */
    private Long receiverId;
    
    /** 信件主题 */
    private String title;
    
    /** 信件内容（最大1000字） */
    private String content;
    
    /** 发送方式：情侣对方 */
    private String deliveryMethod;
    
    /** 预计发送日期 */
    private Date scheduledDate;
    
    /** 预计发送时间 */
    private Date scheduledTime;
    
    /** 创建时间 */
    private Date createdAt;
    
    /** 更新时间 */
    private Date updatedAt;
    
    /** 状态：草稿/已安排/已发送/已读/已取消 */
    private String status;
    
    /** 实际发送时间 */
    private Date sentAt;
    
    /** 阅读时间 */
    private Date readAt;
    
    /** 背景图片URL */
    private String backgroundImage;
    
    /** 背景图片透明度 (0.0-1.0) */
    private Double backgroundOpacity;
    
    /** 背景图片宽度 */
    private Integer backgroundWidth;
    
    /** 背景图片高度 */
    private Integer backgroundHeight;
    
    /** 字体样式 */
    private String fontStyle;
    
    /** 是否删除：0-否，1-是 */
    private Boolean isDeleted;
    
    /** 删除时间 */
    private Date deletedAt;
    
    // Getters and Setters
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getSenderId() {
        return senderId;
    }
    
    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }
    
    public Long getReceiverId() {
        return receiverId;
    }
    
    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getDeliveryMethod() {
        return deliveryMethod;
    }
    
    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }
    
    public Date getScheduledDate() {
        return scheduledDate;
    }
    
    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }
    
    public Date getScheduledTime() {
        return scheduledTime;
    }
    
    public void setScheduledTime(Date scheduledTime) {
        this.scheduledTime = scheduledTime;
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
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Date getSentAt() {
        return sentAt;
    }
    
    public void setSentAt(Date sentAt) {
        this.sentAt = sentAt;
    }
    
    public Date getReadAt() {
        return readAt;
    }
    
    public void setReadAt(Date readAt) {
        this.readAt = readAt;
    }
    
    public String getBackgroundImage() {
        return backgroundImage;
    }
    
    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }
    
    public Double getBackgroundOpacity() {
        return backgroundOpacity;
    }
    
    public void setBackgroundOpacity(Double backgroundOpacity) {
        this.backgroundOpacity = backgroundOpacity;
    }
    
    public Integer getBackgroundWidth() {
        return backgroundWidth;
    }
    
    public void setBackgroundWidth(Integer backgroundWidth) {
        this.backgroundWidth = backgroundWidth;
    }
    
    public Integer getBackgroundHeight() {
        return backgroundHeight;
    }
    
    public void setBackgroundHeight(Integer backgroundHeight) {
        this.backgroundHeight = backgroundHeight;
    }
    
    public String getFontStyle() {
        return fontStyle;
    }
    
    public void setFontStyle(String fontStyle) {
        this.fontStyle = fontStyle;
    }
    
    public Boolean getIsDeleted() {
        return isDeleted;
    }
    
    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
    
    public Date getDeletedAt() {
        return deletedAt;
    }
    
    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }
}