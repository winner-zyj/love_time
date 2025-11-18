package com.ruoyi.common.core.domain.lovetime;

import java.util.Date;

/**
 * 心形墙项目实体类
 * 
 * @author ruoyi
 * @date 2025-11-18
 */
public class HeartWallProject {
    /** 项目ID */
    private Long id;
    
    /** 创建用户ID */
    private Long userId;
    
    /** 项目名称 */
    private String projectName;
    
    /** 项目描述 */
    private String description;
    
    /** 已上传照片数量 */
    private Integer photoCount;
    
    /** 最大照片数量 */
    private Integer maxPhotos;
    
    /** 封面照片URL */
    private String coverPhotoUrl;
    
    /** 是否公开 */
    private Boolean isPublic;
    
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
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getProjectName() {
        return projectName;
    }
    
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Integer getPhotoCount() {
        return photoCount;
    }
    
    public void setPhotoCount(Integer photoCount) {
        this.photoCount = photoCount;
    }
    
    public Integer getMaxPhotos() {
        return maxPhotos;
    }
    
    public void setMaxPhotos(Integer maxPhotos) {
        this.maxPhotos = maxPhotos;
    }
    
    public String getCoverPhotoUrl() {
        return coverPhotoUrl;
    }
    
    public void setCoverPhotoUrl(String coverPhotoUrl) {
        this.coverPhotoUrl = coverPhotoUrl;
    }
    
    public Boolean getIsPublic() {
        return isPublic;
    }
    
    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
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