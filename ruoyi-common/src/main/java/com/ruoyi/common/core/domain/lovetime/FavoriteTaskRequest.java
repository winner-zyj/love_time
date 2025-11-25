package com.ruoyi.common.core.domain.lovetime;

/**
 * 收藏任务请求参数
 * 
 * @author ruoyi
 * @date 2025-11-18
 */
public class FavoriteTaskRequest {
    /**
     * 任务ID
     */
    private Long taskId;

    /**
     * 是否收藏
     */
    private Boolean favorited;

    public FavoriteTaskRequest() {
    }

    public FavoriteTaskRequest(Long taskId, Boolean favorited) {
        this.taskId = taskId;
        this.favorited = favorited;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Boolean getFavorited() {
        return favorited;
    }

    public void setFavorited(Boolean favorited) {
        this.favorited = favorited;
    }

    @Override
    public String toString() {
        return "FavoriteTaskRequest{" +
                "taskId=" + taskId +
                ", favorited=" + favorited +
                '}';
    }
}