package com.ruoyi.web.controller.lovetime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.lovetime.service.IChallengeTaskService;
import com.ruoyi.lovetime.service.IChallengeRecordService;
import com.ruoyi.lovetime.service.IChallengeProgressService;
import com.ruoyi.lovetime.service.ICoupleRelationshipService;
import com.ruoyi.common.core.domain.lovetime.ChallengeTask;
import com.ruoyi.common.core.domain.lovetime.ChallengeRecord;
import com.ruoyi.common.core.domain.lovetime.ChallengeProgress;
import com.ruoyi.common.core.domain.lovetime.CoupleRelationship;

/**
 * 挑战任务Controller
 * 
 * @author ruoyi
 * @date 2025-11-18
 */
@RestController
@RequestMapping("/api/challenge")
public class ChallengeController {
    
    @Autowired
    private IChallengeTaskService challengeTaskService;
    
    @Autowired
    private IChallengeRecordService challengeRecordService;
    
    @Autowired
    private IChallengeProgressService challengeProgressService;
    
    @Autowired
    private ICoupleRelationshipService coupleRelationshipService;
    
    @Autowired
    private TokenService tokenService;
    
    /**
     * 获取任务列表
     */
    @GetMapping("/tasks")
    public AjaxResult getTasks(HttpServletRequest request) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(request);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }
            
            // 查询所有任务（预设按taskIndex升序，自定义按创建时间倒序）
            List<ChallengeTask> tasks = challengeTaskService.selectAllChallengeTasks(loginUser.getUserId());
            
            // 为每个任务添加用户记录信息
            for (ChallengeTask task : tasks) {
                ChallengeRecord record = challengeRecordService.selectChallengeRecordByUserAndTask(
                    loginUser.getUserId(), task.getId());
                // 这里可以将record信息添加到task中，如果需要的话
            }
            
            return AjaxResult.success(tasks);
        } catch (Exception e) {
            return AjaxResult.error("获取任务列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取用户进度
     */
    @GetMapping("/progress")
    public AjaxResult getProgress(HttpServletRequest request) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(request);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }
            
            // 更新并获取用户挑战进度
            ChallengeProgress progress = challengeProgressService.updateChallengeProgressByUserId(loginUser.getUserId());
            
            return AjaxResult.success(progress);
        } catch (Exception e) {
            return AjaxResult.error("获取用户进度失败: " + e.getMessage());
        }
    }
    
    /**
     * 添加自定义任务
     */
    @PostMapping("/task/add")
    public AjaxResult addCustomTask(@RequestBody AddTaskRequest request, HttpServletRequest httpServletRequest) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(httpServletRequest);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }
            
            // 验证用户是否已绑定情侣关系
            CoupleRelationship relationship = coupleRelationshipService.selectCoupleRelationshipByUserId(loginUser.getUserId());
            if (relationship == null) {
                return AjaxResult.error("未绑定情侣关系");
            }
            
            // 验证任务名称
            if (request.getTaskName() == null || request.getTaskName().trim().isEmpty()) {
                return AjaxResult.error("任务名称不能为空");
            }
            
            if (request.getTaskName().length() > 50) {
                return AjaxResult.error("任务名称长度不能超过50个字符");
            }
            
            // 验证任务描述
            if (request.getTaskDescription() != null && request.getTaskDescription().length() > 200) {
                return AjaxResult.error("任务描述长度不能超过200个字符");
            }
            
            // 创建任务
            ChallengeTask task = new ChallengeTask();
            task.setTaskName(request.getTaskName().trim());
            task.setTaskDescription(request.getTaskDescription() != null ? request.getTaskDescription().trim() : null);
            task.setCategory("custom");
            task.setCreatedBy(loginUser.getUserId());
            task.setIsActive(true);
            task.setIconUrl(null); // 默认无图标
            
            // 保存任务
            challengeTaskService.insertChallengeTask(task);
            
            // 构造返回数据
            Map<String, Object> result = new HashMap<>();
            result.put("id", task.getId());
            result.put("taskName", task.getTaskName());
            result.put("category", task.getCategory());
            result.put("status", "pending");
            
            return AjaxResult.success("添加成功", result);
        } catch (Exception e) {
            return AjaxResult.error("添加任务失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除自定义任务
     */
    @PostMapping("/task/delete")
    public AjaxResult deleteCustomTask(@RequestBody DeleteTaskRequest request, HttpServletRequest httpServletRequest) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(httpServletRequest);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }
            
            // 验证任务ID
            if (request.getTaskId() == null) {
                return AjaxResult.error("任务ID不能为空");
            }
            
            // 查询任务
            ChallengeTask task = challengeTaskService.selectChallengeTaskById(request.getTaskId());
            if (task == null) {
                return AjaxResult.error("任务不存在");
            }
            
            // 验证是否为预设任务
            if ("preset".equals(task.getCategory())) {
                return AjaxResult.error("不能删除预设任务");
            }
            
            // 验证是否为当前用户创建的任务
            if (!task.getCreatedBy().equals(loginUser.getUserId())) {
                return AjaxResult.error("只能删除自己创建的任务");
            }
            
            // 删除任务
            challengeTaskService.deleteChallengeTaskById(request.getTaskId());
            
            return AjaxResult.success("删除成功");
        } catch (Exception e) {
            return AjaxResult.error("删除任务失败: " + e.getMessage());
        }
    }
    
    /**
     * 标记任务完成/取消
     */
    @PostMapping("/complete")
    public AjaxResult completeTask(@RequestBody CompleteTaskRequest request, HttpServletRequest httpServletRequest) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(httpServletRequest);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }
            
            // 验证任务ID
            if (request.getTaskId() == null) {
                return AjaxResult.error("任务ID不能为空");
            }
            
            // 验证completed参数
            if (request.getCompleted() == null) {
                return AjaxResult.error("completed参数不能为空");
            }
            
            // 验证备注长度
            if (request.getNote() != null && request.getNote().length() > 500) {
                return AjaxResult.error("备注长度不能超过500个字符");
            }
            
            // 标记任务完成/取消
            ChallengeRecord record = challengeRecordService.completeTask(
                loginUser.getUserId(), 
                request.getTaskId(), 
                request.getCompleted(), 
                request.getPhotoUrl(), 
                request.getNote());
            
            // 构造返回数据
            Map<String, Object> result = new HashMap<>();
            result.put("taskId", record.getTaskId());
            result.put("status", record.getStatus());
            result.put("photoUrl", record.getPhotoUrl());
            result.put("note", record.getNote());
            result.put("completedAt", record.getCompletedAt());
            
            return AjaxResult.success("操作成功", result);
        } catch (Exception e) {
            return AjaxResult.error("操作失败: " + e.getMessage());
        }
    }
    
    /**
     * 上传完成照片
     */
    @PostMapping("/upload")
    public AjaxResult uploadPhoto(HttpServletRequest request) {
        try {
            // 这里应该处理文件上传逻辑
            // 由于这是一个示例，我们返回一个模拟的photoUrl
            Map<String, Object> result = new HashMap<>();
            result.put("photoUrl", "https://example.com/uploaded-photo.jpg");
            
            return AjaxResult.success("上传成功", result);
        } catch (Exception e) {
            return AjaxResult.error("上传失败: " + e.getMessage());
        }
    }
    
    /**
     * 收藏 / 取消收藏任务
     */
    @PostMapping("/favorite")
    public AjaxResult favoriteTask(@RequestBody FavoriteTaskRequest request, HttpServletRequest httpServletRequest) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(httpServletRequest);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }
            
            // 验证任务ID
            if (request.getTaskId() == null) {
                return AjaxResult.error("任务ID不能为空");
            }
            
            // 验证favorited参数
            if (request.getFavorited() == null) {
                return AjaxResult.error("favorited参数不能为空");
            }
            
            // 收藏/取消收藏任务
            ChallengeRecord record = challengeRecordService.favoriteTask(
                loginUser.getUserId(), 
                request.getTaskId(), 
                request.getFavorited());
            
            // 构造返回数据
            Map<String, Object> result = new HashMap<>();
            result.put("taskId", record.getTaskId());
            result.put("isFavorited", record.getIsFavorited());
            
            return AjaxResult.success("操作成功", result);
        } catch (Exception e) {
            return AjaxResult.error("操作失败: " + e.getMessage());
        }
    }
    
    /**
     * 添加任务请求类
     */
    public static class AddTaskRequest {
        private String taskName;
        private String taskDescription;
        
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
    }
    
    /**
     * 删除任务请求类
     */
    public static class DeleteTaskRequest {
        private Long taskId;
        
        public Long getTaskId() {
            return taskId;
        }
        
        public void setTaskId(Long taskId) {
            this.taskId = taskId;
        }
    }
    
    /**
     * 完成任务请求类
     */
    public static class CompleteTaskRequest {
        private Long taskId;
        private Boolean completed;
        private String photoUrl;
        private String note;
        
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
    }
    
    /**
     * 收藏任务请求类
     */
    public static class FavoriteTaskRequest {
        private Long taskId;
        private Boolean favorited;
        
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
    }
}