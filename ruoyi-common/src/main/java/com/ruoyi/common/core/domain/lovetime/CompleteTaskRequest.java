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

    /**
     * 完成地点
     */
    private String location;

    /**
     * 完成日期，格式 YYYY-MM-DD
     */
    private String completedDate;

    /**
     * 完成时间，格式 HH:MM
     */
    private String completedTime;

    /**
     * 完成时的天气
     */
    private String weather;

    /**
     * 完成时的感受
     */
    private String feeling;

    public CompleteTaskRequest() {
    }

    public CompleteTaskRequest(Long taskId, Boolean completed, String photoUrl, String note, String location, String completedDate, String completedTime, String weather, String feeling) {
        this.taskId = taskId;
        this.completed = completed;
        this.photoUrl = photoUrl;
        this.note = note;
        this.location = location;
        this.completedDate = completedDate;
        this.completedTime = completedTime;
        this.weather = weather;
        this.feeling = feeling;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(String completedDate) {
        this.completedDate = completedDate;
    }

    public String getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(String completedTime) {
        this.completedTime = completedTime;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getFeeling() {
        return feeling;
    }

    public void setFeeling(String feeling) {
        this.feeling = feeling;
    }

    @Override
    public String toString() {
        return "CompleteTaskRequest{" +
                "taskId=" + taskId +
                ", completed=" + completed +
                ", photoUrl='" + photoUrl + '\'' +
                ", note='" + note + '\'' +
                ", location='" + location + '\'' +
                ", completedDate='" + completedDate + '\'' +
                ", completedTime='" + completedTime + '\'' +
                ", weather='" + weather + '\'' +
                ", feeling='" + feeling + '\'' +
                '}';
    }
}