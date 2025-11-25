package com.ruoyi.web.controller.lovetime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
import com.ruoyi.common.core.domain.lovetime.AddTaskRequest;
import com.ruoyi.common.core.domain.lovetime.DeleteTaskRequest;
import com.ruoyi.common.core.domain.lovetime.CompleteTaskRequest;
import com.ruoyi.common.core.domain.lovetime.FavoriteTaskRequest;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.exception.file.FileSizeLimitExceededException;
import com.ruoyi.common.exception.file.InvalidExtensionException;
import com.ruoyi.framework.config.ServerConfig;

/**
 * 挑战任务Controller
 * 
 * @author ruoyi
 * @date 2025-11-18
 */
@RestController
@RequestMapping("/api/challenge")
public class ChallengeController {
    
    private static final Logger logger = LoggerFactory.getLogger(ChallengeController.class);
    
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
    
    @Autowired
    private ServerConfig serverConfig;

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
     * 上传挑战任务照片
     */
    @PostMapping("/upload")
    public AjaxResult uploadPhoto(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(request);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }

            // 验证文件是否为空
            if (file == null || file.isEmpty()) {
                return AjaxResult.error("请选择要上传的文件");
            }

            // 验证文件大小（限制在5MB以内）
            long maxSize = 5 * 1024 * 1024L; // 5MB
            if (file.getSize() > maxSize) {
                return AjaxResult.error("文件大小不能超过5MB");
            }

            // 定义允许的图片格式
            String[] allowedExtension = { "jpg", "jpeg", "png" };

            // 上传文件路径 - 使用挑战任务专用路径
            String filePath = RuoYiConfig.getProfile() + "/uploads/challenge";

            // 使用若依框架的文件上传工具上传文件
            String fileName = FileUploadUtils.upload(filePath, file, allowedExtension, true);

            // 构造返回数据 - 返回完整的URL路径
            String fullImageUrl = buildFullImageUrl(request, fileName);
            AjaxResult ajax = AjaxResult.success("照片上传成功");
            ajax.put("photoUrl", fullImageUrl);
            
            return ajax;
        } catch (FileSizeLimitExceededException e) {
            logger.error("文件大小超过限制", e);
            return AjaxResult.error("文件大小不能超过5MB");
        } catch (InvalidExtensionException e) {
            logger.error("文件格式不支持", e);
            return AjaxResult.error("仅支持jpg、jpeg、png格式的图片");
        } catch (Exception e) {
            logger.error("上传文件时发生异常", e);
            return AjaxResult.error("服务器内部错误，请稍后重试");
        }
    }

    private String buildFullImageUrl(HttpServletRequest request, String fileName) {
        // 1. 获取协议（http/https）
        String scheme = request.getScheme();
        // 2. 获取域名或 IP（如 "smallpeppers.cn" 或 "192.168.1.100"）
        String serverName = request.getServerName();
        // 3. 获取端口（如 80、443、8886）
        int serverPort = request.getServerPort();
        // 4. 获取项目上下文路径（若依项目默认部署在根目录，此值为空）
        String contextPath = request.getContextPath();

        // 5. 拼接 URL（格式：协议://域名:端口/上下文路径/图片相对路径）
        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(serverName);
        // 只有非默认端口（80 或 443）才需要拼接端口号
        if ((scheme.equals("http") && serverPort != 80) || (scheme.equals("https") && serverPort != 443)) {
            url.append(":").append(serverPort);
        }
        url.append(contextPath)
                .append("/profile/") // 与上传目录对应（若依默认/profile是静态资源前缀）
                .append(fileName);

        return url.toString();
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
            result.put("favorited", record.getIsFavorited());
            
            return AjaxResult.success("操作成功", result);
        } catch (Exception e) {
            return AjaxResult.error("操作失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取挑战任务照片
     */
    @GetMapping("/photo/**")
    public void getChallengePhoto(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 从请求URL中提取文件路径
            String requestURI = request.getRequestURI();
            String contextPath = request.getContextPath();
            String apiPath = "/api/challenge/photo/";
            
            // 提取文件路径部分
            String filePathPart = requestURI.substring(requestURI.indexOf(apiPath) + apiPath.length());
            
            // 构建完整文件路径 - 使用您实际的文件存储路径
            String fullPath = "D:/ruoyi/uploadPath/uploads/challenge/" + filePathPart;
            File file = new File(fullPath);
            
            // 检查文件是否存在
            if (!file.exists()) {
                logger.warn("请求的文件不存在: {}", fullPath);
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            
            // 检查是否为文件（而不是目录）
            if (!file.isFile()) {
                logger.warn("请求的路径不是文件: {}", fullPath);
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            
            // 设置响应头
            String mimeType = Files.probeContentType(file.toPath());
            if (mimeType == null) {
                // 根据文件扩展名确定MIME类型
                String fileName = file.getName().toLowerCase();
                if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
                    mimeType = "image/jpeg";
                } else if (fileName.endsWith(".png")) {
                    mimeType = "image/png";
                } else {
                    mimeType = "application/octet-stream";
                }
            }
            
            response.setContentType(mimeType);
            response.setContentLength((int) file.length());
            response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
            
            // 写入文件内容到响应
            try (InputStream inputStream = new FileInputStream(file);
                 OutputStream outputStream = response.getOutputStream()) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
            }
        } catch (Exception e) {
            logger.error("获取图片时发生异常", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}