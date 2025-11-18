package com.ruoyi.common.core.domain.lovetime;

import java.util.Date;

/**
 * 用户挑战记录实体类
 * 
 * @author ruoyi
 * @date 2025-11-18
 */
public class ChallengeRecord {
    /** 记录ID */
    private Long id;
    
    /** 任务ID */
    private Long taskId;
    
    /** 用户ID */
    private Long userId;
    
    /** 完成状态：pending=未完成, completed=已完成 */
    private String status;
    
    /** 上传的照片URL */
    private String photoUrl;
    
    /** 备注说明 */
    private String note;
    
    /** 是否收藏 */
    private Boolean isFavorited;
    
    /** 完成时间 */
    private Date completedAt;
    
    /** 创建时间 */
    private Date createdAt;
    
    /** 更新时间 */
    private Date updatedAt;
    
    // Getters and Setters
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getTaskId() {
        return taskId;
    }
    
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getPhotoUrl() {
        return photoUrl;
    }
    
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
    
    public String getNote() {
        return note;
    }
    
    public void setNote(String note) {
        this.note = note;
    }
    
    public Boolean getIsFavorited() {
        return isFavorited;
    }
    
    public void setIsFavorited(Boolean isFavorited) {
        this.isFavorited = isFavorited;
    }
    
    public Date getCompletedAt() {
        return completedAt;
    }
    
    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
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
}