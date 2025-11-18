package com.ruoyi.common.core.domain.lovetime;

import java.util.Date;

/**
 * 挑战任务实体类
 * 
 * @author ruoyi
 * @date 2025-11-18
 */
public class ChallengeTask {
    /** 任务ID */
    private Long id;
    
    /** 任务名称 */
    private String taskName;
    
    /** 任务描述 */
    private String taskDescription;
    
    /** 任务序号（预设任务1-100，自定义任务为NULL） */
    private Integer taskIndex;
    
    /** 任务类型：preset=预设任务, custom=自定义任务 */
    private String category;
    
    /** 创建者用户ID（自定义任务时必填） */
    private Long createdBy;
    
    /** 任务图标URL */
    private String iconUrl;
    
    /** 是否启用 */
    private Boolean isActive;
    
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
    
    public String getTaskName() {
        return taskName;
    }
    
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    
    public String getTaskDescription() {
        return taskDescription;
    }
    
    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }
    
    public Integer getTaskIndex() {
        return taskIndex;
    }
    
    public void setTaskIndex(Integer taskIndex) {
        this.taskIndex = taskIndex;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public Long getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
    
    public String getIconUrl() {
        return iconUrl;
    }
    
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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