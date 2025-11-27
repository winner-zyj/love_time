package com.ruoyi.web.controller.lovetime;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.exception.file.FileSizeLimitExceededException;
import com.ruoyi.common.exception.file.InvalidExtensionException;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.lovetime.service.IHeartWallProjectService;
import com.ruoyi.lovetime.service.IHeartWallPhotoService;
import com.ruoyi.lovetime.service.ICoupleRelationshipService;
import com.ruoyi.common.core.domain.lovetime.HeartWallProject;
import com.ruoyi.common.core.domain.lovetime.HeartWallPhoto;
import com.ruoyi.common.core.domain.lovetime.CoupleRelationship;

/**
 * 心形墙Controller
 * 
 * @author ruoyi
 * @date 2025-11-18
 */
@RestController
@RequestMapping("/api/heart-wall")
// 确保Controller能够处理multipart请求
public class HeartWallController {
    
    @Autowired
    private IHeartWallProjectService heartWallProjectService;
    
    @Autowired
    private IHeartWallPhotoService heartWallPhotoService;
    
    @Autowired
    private ICoupleRelationshipService coupleRelationshipService;
    
    @Autowired
    private TokenService tokenService;
    
    /**
     * 创建项目
     */
    @PostMapping("/projects")
    public AjaxResult createProject(@RequestBody CreateProjectRequest request, HttpServletRequest httpServletRequest) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(httpServletRequest);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }
            
            // 验证项目名称
            if (request.getProjectName() == null || request.getProjectName().trim().isEmpty()) {
                return AjaxResult.error("项目名称不能为空");
            }
            
            // 创建项目
            HeartWallProject project = new HeartWallProject();
            project.setUserId(loginUser.getUserId());
            project.setProjectName(request.getProjectName().trim());
            project.setDescription(request.getDescription() != null ? request.getDescription().trim() : null);
            project.setIsPublic(request.getIsPublic() != null ? request.getIsPublic() : false);
            project.setMaxPhotos(request.getMaxPhotos() != null ? request.getMaxPhotos() : 40);
            project.setPhotoCount(0);
            project.setCreatedAt(new Date());
            project.setUpdatedAt(new Date());
            
            // 保存项目
            heartWallProjectService.insertHeartWallProject(project);
            
            // 构造返回数据
            Map<String, Object> result = new HashMap<>();
            result.put("projectId", project.getId());
            result.put("projectName", project.getProjectName());
            result.put("description", project.getDescription());
            result.put("isPublic", project.getIsPublic());
            result.put("maxPhotos", project.getMaxPhotos());
            result.put("photoCount", project.getPhotoCount());
            result.put("createdAt", project.getCreatedAt());
            result.put("updatedAt", project.getUpdatedAt());
            
            return AjaxResult.success("创建成功", result);
        } catch (Exception e) {
            return AjaxResult.error("创建项目失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取项目列表
     */
    @GetMapping("/projects")
    public AjaxResult getProjects(HttpServletRequest request) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(request);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }
            
            // 查询用户的心形墙项目列表
            List<HeartWallProject> projects = heartWallProjectService.selectHeartWallProjectsByUserId(loginUser.getUserId());
            
            return AjaxResult.success(projects);
        } catch (Exception e) {
            return AjaxResult.error("获取项目列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取项目详情
     */
    @GetMapping("/projects/{projectId}")
    public AjaxResult getProjectDetails(@PathVariable Long projectId, HttpServletRequest httpServletRequest) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(httpServletRequest);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }
            
            // 查询项目
            HeartWallProject project = heartWallProjectService.selectHeartWallProjectById(projectId);
            if (project == null) {
                return AjaxResult.error("项目不存在");
            }
            
            // 验证用户权限（只能查看自己的项目）
            if (!project.getUserId().equals(loginUser.getUserId())) {
                return AjaxResult.error("无权限访问该项目");
            }
            
            // 查询项目下的照片列表
            List<HeartWallPhoto> photos = heartWallPhotoService.selectHeartWallPhotosByProjectId(projectId);
            
            // 构造返回数据
            Map<String, Object> result = new HashMap<>();
            result.put("project", project);
            result.put("photos", photos);
            
            return AjaxResult.success(result);
        } catch (Exception e) {
            return AjaxResult.error("获取项目详情失败: " + e.getMessage());
        }
    }
    
    /**
     * 更新项目
     */
    @PutMapping("/projects/{projectId}")
    public AjaxResult updateProject(@PathVariable Long projectId, @RequestBody UpdateProjectRequest request, HttpServletRequest httpServletRequest) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(httpServletRequest);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }
            
            // 查询项目
            HeartWallProject project = heartWallProjectService.selectHeartWallProjectById(projectId);
            if (project == null) {
                return AjaxResult.error("项目不存在");
            }
            
            // 验证用户权限（只能更新自己的项目）
            if (!project.getUserId().equals(loginUser.getUserId())) {
                return AjaxResult.error("无权限更新该项目");
            }
            
            // 更新项目信息
            if (request.getProjectName() != null) {
                project.setProjectName(request.getProjectName().trim());
            }
            if (request.getDescription() != null) {
                project.setDescription(request.getDescription().trim());
            }
            if (request.getIsPublic() != null) {
                project.setIsPublic(request.getIsPublic());
            }
            if (request.getMaxPhotos() != null) {
                project.setMaxPhotos(request.getMaxPhotos());
            }
            project.setUpdatedAt(new Date());
            
            // 保存更新
            heartWallProjectService.updateHeartWallProject(project);
            
            // 构造返回数据
            Map<String, Object> result = new HashMap<>();
            result.put("projectId", project.getId());
            result.put("projectName", project.getProjectName());
            result.put("description", project.getDescription());
            result.put("isPublic", project.getIsPublic());
            result.put("maxPhotos", project.getMaxPhotos());
            result.put("photoCount", project.getPhotoCount());
            result.put("createdAt", project.getCreatedAt());
            result.put("updatedAt", project.getUpdatedAt());
            
            return AjaxResult.success("更新成功", result);
        } catch (Exception e) {
            return AjaxResult.error("更新项目失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除项目
     */
    @DeleteMapping("/projects/{projectId}")
    public AjaxResult deleteProject(@PathVariable Long projectId, HttpServletRequest httpServletRequest) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(httpServletRequest);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }
            
            // 查询项目
            HeartWallProject project = heartWallProjectService.selectHeartWallProjectById(projectId);
            if (project == null) {
                return AjaxResult.error("项目不存在");
            }
            
            // 验证用户权限（只能删除自己的项目）
            if (!project.getUserId().equals(loginUser.getUserId())) {
                return AjaxResult.error("无权限删除该项目");
            }
            
            // 删除项目下的所有照片
            heartWallPhotoService.deleteHeartWallPhotosByProjectId(projectId);
            
            // 删除项目
            heartWallProjectService.deleteHeartWallProjectById(projectId);
            
            // 构造返回数据
            Map<String, Object> result = new HashMap<>();
            result.put("deleted", true);
            
            return AjaxResult.success("删除成功", result);
        } catch (Exception e) {
            return AjaxResult.error("删除项目失败: " + e.getMessage());
        }
    }
    
    /**
     * 上传照片（方式一：前端已有图片 URL）
     */
//    @PostMapping("/photos")
//    public AjaxResult uploadPhoto(@RequestBody UploadPhotoRequest request, HttpServletRequest httpServletRequest) {
//        try {
//            // 获取当前登录用户
//            LoginUser loginUser = tokenService.getLoginUser(httpServletRequest);
//            if (loginUser == null) {
//                return AjaxResult.error("用户未登录");
//            }
//
//            // 验证项目ID
//            if (request.getProjectId() == null) {
//                return AjaxResult.error("项目ID不能为空");
//            }
//
//            // 验证照片URL
//            if (request.getPhotoUrl() == null || request.getPhotoUrl().trim().isEmpty()) {
//                return AjaxResult.error("照片URL不能为空");
//            }
//
//            // 验证位置索引
//            if (request.getPositionIndex() == null) {
//                return AjaxResult.error("位置索引不能为空");
//            }
//
//            // 查询项目
//            HeartWallProject project = heartWallProjectService.selectHeartWallProjectById(request.getProjectId());
//            if (project == null) {
//                return AjaxResult.error("项目不存在");
//            }
//
//            // 验证用户权限（只能在自己的项目中上传照片）
//            if (!project.getUserId().equals(loginUser.getUserId())) {
//                return AjaxResult.error("无权限在该项目中上传照片");
//            }
//
//            // 验证位置索引是否超出范围
//            if (request.getPositionIndex() < 1 || request.getPositionIndex() > project.getMaxPhotos()) {
//                return AjaxResult.error("位置索引超出范围");
//            }
//
//            // 检查该位置是否已有照片
//            HeartWallPhoto existingPhoto = heartWallPhotoService.selectHeartWallPhotoByProjectAndPosition(
//                request.getProjectId(), request.getPositionIndex());
//            if (existingPhoto != null) {
//                return AjaxResult.error("该位置已存在照片，请先删除原有照片");
//            }
//
//            // 创建照片记录
//            HeartWallPhoto photo = new HeartWallPhoto();
//            photo.setProjectId(request.getProjectId());
//            photo.setUserId(loginUser.getUserId());
//            // 存储相对路径到数据库
//            photo.setPhotoUrl(request.getPhotoUrl().trim());
//            photo.setThumbnailUrl(request.getThumbnailUrl() != null ? request.getThumbnailUrl().trim() : null);
//            photo.setPositionIndex(request.getPositionIndex());
//            photo.setCaption(request.getCaption() != null ? request.getCaption().trim() : null);
//            photo.setTakenDate(request.getTakenDate());
//            photo.setUploadedAt(new Date());
//            photo.setUpdatedAt(new Date());
//
//            // 保存照片
//            heartWallPhotoService.insertHeartWallPhoto(photo);
//
//            // 更新项目照片数量
//            project.setPhotoCount(project.getPhotoCount() + 1);
//            project.setUpdatedAt(new Date());
//            heartWallProjectService.updateHeartWallProject(project);
//
//            // 构造返回数据
//            Map<String, Object> result = new HashMap<>();
//            result.put("photoId", photo.getId());
//            result.put("projectId", photo.getProjectId());
//            result.put("photoUrl", photo.getPhotoUrl());
//            result.put("thumbnailUrl", photo.getThumbnailUrl());
//            result.put("positionIndex", photo.getPositionIndex());
//            result.put("caption", photo.getCaption());
//            result.put("takenDate", photo.getTakenDate());
//            result.put("uploadedAt", photo.getUploadedAt());
//
//            return AjaxResult.success("上传成功", result);
//        } catch (Exception e) {
//            return AjaxResult.error("上传照片失败: " + e.getMessage());
//        }
//    }
//
    /**
     * 上传照片（方式二：直接上传文件）
     * Modified to properly handle file uploads and store relative paths in database
     */
    @PostMapping(value = "/photos/upload", consumes = {"multipart/form-data", "multipart/form-data;*"})
    // 确保方法能够处理multipart请求
    public AjaxResult uploadPhotoFile(@RequestParam("file") MultipartFile file, 
                                      @RequestParam("projectId") Long projectId,
                                      @RequestParam("positionIndex") Integer positionIndex,
                                      @RequestParam(value = "caption", required = false) String caption,
                                      @RequestParam(value = "takenDate", required = false) Date takenDate,
                                      HttpServletRequest httpServletRequest) {
        try {
            // 检查请求是否为multipart类型
            if (!isMultipartRequest(httpServletRequest)) {
                return AjaxResult.error("请求类型不正确，应为multipart/form-data");
            }
            
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(httpServletRequest);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }

            // 验证文件是否为空
            if (file == null || file.isEmpty()) {
                return AjaxResult.error("请选择要上传的文件");
            }

            // 验证项目ID
            if (projectId == null) {
                return AjaxResult.error("项目ID不能为空");
            }

            // 验证位置索引
            if (positionIndex == null) {
                return AjaxResult.error("位置索引不能为空");
            }

            // 查询项目
            HeartWallProject project = heartWallProjectService.selectHeartWallProjectById(projectId);
            if (project == null) {
                return AjaxResult.error("项目不存在");
            }

            // 验证用户权限（只能在自己的项目中上传照片）
            if (!project.getUserId().equals(loginUser.getUserId())) {
                return AjaxResult.error("无权限在该项目中上传照片");
            }

            // 验证位置索引是否超出范围
            if (positionIndex < 1 || positionIndex > project.getMaxPhotos()) {
                return AjaxResult.error("位置索引超出范围");
            }

            // 检查该位置是否已有照片
            HeartWallPhoto existingPhoto = heartWallPhotoService.selectHeartWallPhotoByProjectAndPosition(
                projectId, positionIndex);
            if (existingPhoto != null) {
                return AjaxResult.error("该位置已存在照片，请先删除原有照片");
            }

            // 验证文件大小（限制在5MB以内）
            long maxSize = 5 * 1024 * 1024L; // 5MB
            if (file.getSize() > maxSize) {
                return AjaxResult.error("文件大小不能超过5MB");
            }

            // 定义允许的图片格式
            String[] allowedExtension = { "jpg", "jpeg", "png" };

            // 上传文件路径 - 使用心形墙专用路径
            String filePath = RuoYiConfig.getProfile() + "/uploads/heartwall";

            // 使用若依框架的文件上传工具上传文件
            String fileName = FileUploadUtils.upload(filePath, file, allowedExtension, true);

            // 构造返回数据 - 返回完整的URL路径
            String photoUrl = buildFullImageUrl(httpServletRequest, fileName);
            System.out.println("aaa"+photoUrl);
            // 创建照片记录
            HeartWallPhoto photo = new HeartWallPhoto();
            photo.setProjectId(projectId);
            photo.setUserId(loginUser.getUserId());
            // 存储相对路径到数据库
            photo.setPhotoUrl(fileName);
            photo.setThumbnailUrl(null); // 可以根据需要添加缩略图逻辑
            photo.setPositionIndex(positionIndex);
            photo.setCaption(caption != null ? caption.trim() : null);
            photo.setTakenDate(takenDate);
            photo.setUploadedAt(new Date());
            photo.setUpdatedAt(new Date());

            // 保存照片
            heartWallPhotoService.insertHeartWallPhoto(photo);

            // 更新项目照片数量
            project.setPhotoCount(project.getPhotoCount() + 1);
            project.setUpdatedAt(new Date());
            heartWallProjectService.updateHeartWallProject(project);

            // 构造返回数据
            Map<String, Object> result = new HashMap<>();
            result.put("photoId", photo.getId());
            result.put("projectId", photo.getProjectId());
            result.put("photoUrl", photoUrl); // 返回完整URL给前端
            result.put("thumbnailUrl", photo.getThumbnailUrl());
            result.put("positionIndex", photo.getPositionIndex());
            result.put("caption", photo.getCaption());
            result.put("takenDate", photo.getTakenDate());
            result.put("uploadedAt", photo.getUploadedAt());

            return AjaxResult.success("上传成功", result);
        } catch (FileSizeLimitExceededException e) {
            return AjaxResult.error("文件大小不能超过5MB");
        } catch (InvalidExtensionException e) {
            return AjaxResult.error("仅支持jpg、jpeg、png格式的图片");
        } catch (Exception e) {
            return AjaxResult.error("上传照片失败: " + e.getMessage());
        }
    }
    
    /**
     * 构建完整的图片URL
     * 
     * @param request HTTP请求
     * @param fileName 文件名（相对路径）
     * @return 完整的图片访问URL
     */
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
     * 检查请求是否为multipart类型
     * 
     * @param request HTTP请求
     * @return 是否为multipart请求
     */
    private boolean isMultipartRequest(HttpServletRequest request) {
        String contentType = request.getContentType();
        return contentType != null && contentType.toLowerCase().startsWith("multipart/");
    }
    
    /**
     * 删除照片
     */
    @DeleteMapping("/photos/{photoId}")
    public AjaxResult deletePhoto(@PathVariable Long photoId, HttpServletRequest httpServletRequest) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(httpServletRequest);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }
            
            // 查询照片
            HeartWallPhoto photo = heartWallPhotoService.selectHeartWallPhotoById(photoId);
            if (photo == null) {
                return AjaxResult.error("照片不存在");
            }
            
            // 查询项目
            HeartWallProject project = heartWallProjectService.selectHeartWallProjectById(photo.getProjectId());
            if (project == null) {
                return AjaxResult.error("项目不存在");
            }
            
            // 验证用户权限（只能删除自己项目中的照片）
            if (!project.getUserId().equals(loginUser.getUserId())) {
                return AjaxResult.error("无权限删除该照片");
            }
            
            // 删除照片
            heartWallPhotoService.deleteHeartWallPhotoById(photoId);
            
            // 更新项目照片数量
            project.setPhotoCount(Math.max(0, project.getPhotoCount() - 1));
            project.setUpdatedAt(new Date());
            heartWallProjectService.updateHeartWallProject(project);
            
            // 构造返回数据
            Map<String, Object> result = new HashMap<>();
            result.put("deleted", true);
            
            return AjaxResult.success("删除成功", result);
        } catch (Exception e) {
            return AjaxResult.error("删除照片失败: " + e.getMessage());
        }
    }
    
    /**
     * 更新照片
     */
    @PutMapping("/photos/{photoId}")
    public AjaxResult updatePhoto(@PathVariable Long photoId,
                                  @RequestParam(value = "caption", required = false) String caption,
                                  @RequestParam(value = "takenDate", required = false) Date takenDate,
                                  HttpServletRequest httpServletRequest) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(httpServletRequest);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }
            
            // 查询照片
            HeartWallPhoto photo = heartWallPhotoService.selectHeartWallPhotoById(photoId);
            if (photo == null) {
                return AjaxResult.error("照片不存在");
            }
            
            // 查询项目
            HeartWallProject project = heartWallProjectService.selectHeartWallProjectById(photo.getProjectId());
            if (project == null) {
                return AjaxResult.error("项目不存在");
            }
            
            // 验证用户权限（只能更新自己项目中的照片）
            if (!project.getUserId().equals(loginUser.getUserId())) {
                return AjaxResult.error("无权限更新该照片");
            }
            
            // 更新照片信息
            if (caption != null) {
                photo.setCaption(caption.trim());
            }
            if (takenDate != null) {
                photo.setTakenDate(takenDate);
            }
            photo.setUpdatedAt(new Date());
            
            // 保存更新
            heartWallPhotoService.updateHeartWallPhoto(photo);
            
            // 构造返回数据
            Map<String, Object> result = new HashMap<>();
            result.put("photoId", photo.getId());
            result.put("projectId", photo.getProjectId());
            result.put("photoUrl", buildFullImageUrl(httpServletRequest, photo.getPhotoUrl()));
            result.put("thumbnailUrl", photo.getThumbnailUrl());
            result.put("positionIndex", photo.getPositionIndex());
            result.put("caption", photo.getCaption());
            result.put("takenDate", photo.getTakenDate());
            result.put("uploadedAt", photo.getUploadedAt());
            result.put("updatedAt", photo.getUpdatedAt());
            
            return AjaxResult.success("更新成功", result);
        } catch (Exception e) {
            return AjaxResult.error("更新照片失败: " + e.getMessage());
        }
    }
    
    /**
     * 创建项目请求类
     */
    public static class CreateProjectRequest {
        private String projectName;
        private String description;
        private Boolean isPublic;
        private Integer maxPhotos;
        
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
        
        public Boolean getIsPublic() {
            return isPublic;
        }
        
        public void setIsPublic(Boolean isPublic) {
            this.isPublic = isPublic;
        }
        
        public Integer getMaxPhotos() {
            return maxPhotos;
        }
        
        public void setMaxPhotos(Integer maxPhotos) {
            this.maxPhotos = maxPhotos;
        }
    }
    
    /**
     * 更新项目请求类
     */
    public static class UpdateProjectRequest {
        private String projectName;
        private String description;
        private Boolean isPublic;
        private Integer maxPhotos;
        
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
        
        public Boolean getIsPublic() {
            return isPublic;
        }
        
        public void setIsPublic(Boolean isPublic) {
            this.isPublic = isPublic;
        }
        
        public Integer getMaxPhotos() {
            return maxPhotos;
        }
        
        public void setMaxPhotos(Integer maxPhotos) {
            this.maxPhotos = maxPhotos;
        }
    }
    
    /**
     * 上传照片请求类
     */
    public static class UploadPhotoRequest {
        private Long projectId;
        private String photoUrl;
        private String thumbnailUrl;
        private Integer positionIndex;
        private String caption;
        private Date takenDate;
        
        public Long getProjectId() {
            return projectId;
        }
        
        public void setProjectId(Long projectId) {
            this.projectId = projectId;
        }
        
        public String getPhotoUrl() {
            return photoUrl;
        }
        
        public void setPhotoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
        }
        
        public String getThumbnailUrl() {
            return thumbnailUrl;
        }
        
        public void setThumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
        }
        
        public Integer getPositionIndex() {
            return positionIndex;
        }
        
        public void setPositionIndex(Integer positionIndex) {
            this.positionIndex = positionIndex;
        }
        
        public String getCaption() {
            return caption;
        }
        
        public void setCaption(String caption) {
            this.caption = caption;
        }
        
        public Date getTakenDate() {
            return takenDate;
        }
        
        public void setTakenDate(Date takenDate) {
            this.takenDate = takenDate;
        }
    }
}