package com.ruoyi.common.core.domain.lovetime;

import java.util.Date;

/**
 * 用户挑战进度实体类
 * 
 * @author ruoyi
 * @date 2025-11-18
 */
public class ChallengeProgress {
    /** 进度ID */
    private Long id;
    
    /** 用户ID */
    private Long userId;
    
    /** 总任务数（包含预设和自定义） */
    private Integer totalTasks;
    
    /** 已完成数量 */
    private Integer completedCount;
    
    /** 收藏数量 */
    private Integer favoritedCount;
    
    /** 完成率（百分比） */
    private Double completionRate;
    
    /** 最后活跃时间 */
    private Date lastActiveAt;
    
    /** 创建时间 */
    private Date createdAt;
    
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
    
    public Integer getTotalTasks() {
        return totalTasks;
    }
    
    public void setTotalTasks(Integer totalTasks) {
        this.totalTasks = totalTasks;
    }
    
    public Integer getCompletedCount() {
        return completedCount;
    }
    
    public void setCompletedCount(Integer completedCount) {
        this.completedCount = completedCount;
    }
    
    public Integer getFavoritedCount() {
        return favoritedCount;
    }
    
    public void setFavoritedCount(Integer favoritedCount) {
        this.favoritedCount = favoritedCount;
    }
    
    public Double getCompletionRate() {
        return completionRate;
    }
    
    public void setCompletionRate(Double completionRate) {
        this.completionRate = completionRate;
    }
    
    public Date getLastActiveAt() {
        return lastActiveAt;
    }
    
    public void setLastActiveAt(Date lastActiveAt) {
        this.lastActiveAt = lastActiveAt;
    }
    
    public Date getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}