package com.ruoyi.common.core.domain.lovetime;

/**
 * 完成任务请求参数
 * 
 * @author ruoyi
 * @date 2025-11-18
 */
public class CompleteTaskRequest {
    /**
     * 任务ID
     */
    private Long taskId;

    /**
     * 是否完成
     */
    private Boolean completed;

    /**
     * 照片URL
     */
    private String photoUrl;

    /**
     * 备注
     */
    private String note;

    public CompleteTaskRequest() {
    }

    public CompleteTaskRequest(Long taskId, Boolean completed, String photoUrl, String note) {
        this.taskId = taskId;
        this.completed = completed;
        this.photoUrl = photoUrl;
        this.note = note;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
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

    @Override
    public String toString() {
        return "CompleteTaskRequest{" +
                "taskId=" + taskId +
                ", completed=" + completed +
                ", photoUrl='" + photoUrl + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}