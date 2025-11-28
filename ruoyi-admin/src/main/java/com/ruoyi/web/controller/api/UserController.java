package com.ruoyi.web.controller.api;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.file.FileSizeLimitExceededException;
import com.ruoyi.common.exception.file.InvalidExtensionException;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.system.service.ISysUserService;

/**
 * API用户信息控制器
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController {
    
    @Autowired
    private ISysUserService userService;

    /**
     * 用户头像上传
     */
    @Log(title = "用户头像", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/avatar/upload", consumes = {"multipart/form-data", "multipart/form-data;*"})
    public AjaxResult avatar(@RequestParam("avatarfile") MultipartFile file, HttpServletRequest request) throws IOException {
        if (file.isEmpty()) {
            return error("上传头像文件不能为空");
        }
        // 获取当前用户
        SysUser currentUser = getLoginUser().getUser();
        // 验证文件大小（限制在5MB以内）
        long maxSize = 5 * 1024 * 1024L; // 5MB
        if (file.getSize() > maxSize) {
            return error("文件大小不能超过5MB");
        }
        // 定义允许的图片格式
        String[] allowedExtension = { "jpg", "jpeg", "png" };
        // 上传文件路径 - 使用用户头像专用路径
        String filePath = RuoYiConfig.getProfile() + "/uploads/avatar";
        try {
            // 使用若依框架的文件上传工具上传文件
            String fileName = FileUploadUtils.upload(filePath, file, allowedExtension, true);
            // 构造返回数据 - 返回完整的URL路径
            String avatarUrl = buildAvatarUrl(request, fileName);
            // 更新用户头像
            if (userService.updateUserAvatar(currentUser.getUserId(), avatarUrl)) {
                AjaxResult ajax = success();
                ajax.put("imgUrl", avatarUrl);
                return ajax;
            }
            return error("上传头像失败");
        } catch (FileSizeLimitExceededException e) {
            return error("文件大小不能超过5MB");
        } catch (InvalidExtensionException e) {
            return error("仅支持jpg、jpeg、png格式的图片");
        } catch (IOException e) {
            return error("上传头像失败: " + e.getMessage());
        }
    }

    /**
     * 构建完整的头像URL
     *
     * @param request HTTP请求
     * @param fileName 文件名（相对路径）
     * @return 完整的头像访问URL
     */
    private String buildAvatarUrl(HttpServletRequest request, String fileName) {
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
}