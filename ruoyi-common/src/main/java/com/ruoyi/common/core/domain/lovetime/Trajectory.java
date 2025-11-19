package com.ruoyi.common.core.domain.lovetime;

import java.util.Date;

/**
 * 轨迹实体类
 * 
 * @author ruoyi
 * @date 2025-11-19
 */
public class Trajectory {
    /** 轨迹点ID */
    private Long id;
    
    /** 用户ID */
    private Long userId;
    
    /** 纬度 */
    private Double latitude;
    
    /** 经度 */
    private Double longitude;
    
    /** 访问时间 */
    private Date visitTime;
    
    /** 地址名称 */
    private String address;
    
    /** 地点名称 */
    private String placeName;
    
    /** 描述 */
    private String description;
    
    /** 照片URL */
    private String photoUrl;
    
    /** 是否共享给情侣 */
    private Boolean isShared;
    
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
    
    public Double getLatitude() {
        return latitude;
    }
    
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    
    public Double getLongitude() {
        return longitude;
    }
    
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    
    public Date getVisitTime() {
        return visitTime;
    }
    
    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getPlaceName() {
        return placeName;
    }
    
    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getPhotoUrl() {
        return photoUrl;
    }
    
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
    
    public Boolean getIsShared() {
        return isShared;
    }
    
    public void setIsShared(Boolean isShared) {
        this.isShared = isShared;
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