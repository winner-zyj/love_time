package com.ruoyi.common.core.domain.lovetime;

/**
 * 删除任务请求参数
 * 
 * @author ruoyi
 * @date 2025-11-18
 */
public class DeleteTaskRequest {
    /**
     * 任务ID
     */
    private Long taskId;

    public DeleteTaskRequest() {
    }

    public DeleteTaskRequest(Long taskId) {
        this.taskId = taskId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "DeleteTaskRequest{" +
                "taskId=" + taskId +
                '}';
    }
}