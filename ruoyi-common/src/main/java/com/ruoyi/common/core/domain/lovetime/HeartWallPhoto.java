package com.ruoyi.common.core.domain.lovetime;

import java.util.Date;

/**
 * 心形墙照片实体类
 * 
 * @author ruoyi
 * @date 2025-11-18
 */
public class HeartWallPhoto {
    /** 照片ID */
    private Long id;
    
    /** 项目ID */
    private Long projectId;
    
    /** 上传用户ID */
    private Long userId;
    
    /** 照片URL */
    private String photoUrl;
    
    /** 缩略图URL */
    private String thumbnailUrl;
    
    /** 照片位置索引（1-40） */
    private Integer positionIndex;
    
    /** 照片说明 */
    private String caption;
    
    /** 拍摄日期 */
    private Date takenDate;
    
    /** 上传时间 */
    private Date uploadedAt;
    
    /** 更新时间 */
    private Date updatedAt;
    
    // Getters and Setters
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getProjectId() {
        return projectId;
    }
    
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getPhotoUrl() {
        return photoUrl;
    }
    
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
    
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
    
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
    
    public Integer getPositionIndex() {
        return positionIndex;
    }
    
    public void setPositionIndex(Integer positionIndex) {
        this.positionIndex = positionIndex;
    }
    
    public String getCaption() {
        return caption;
    }
    
    public void setCaption(String caption) {
        this.caption = caption;
    }
    
    public Date getTakenDate() {
        return takenDate;
    }
    
    public void setTakenDate(Date takenDate) {
        this.takenDate = takenDate;
    }
    
    public Date getUploadedAt() {
        return uploadedAt;
    }
    
    public void setUploadedAt(Date uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
    
    public Date getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}