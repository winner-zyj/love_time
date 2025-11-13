package com.ruoyi.web.controller.lovetime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.lovetime.WechatLoginRequest;
import com.ruoyi.common.core.domain.lovetime.WechatLoginResponse;
import com.ruoyi.common.core.domain.lovetime.User;
import com.ruoyi.lovetime.service.IUserService;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.domain.entity.SysUser;

/**
 * 微信登录Controller
 * 
 * @author ruoyi
 * @date 2025-11-13
 */
@RestController
@RequestMapping("/api/login")
public class WechatLoginController {

    @Autowired
    private IUserService userService;
    
    @Autowired
    private TokenService tokenService;

    /**
     * 微信登录
     */
    @PostMapping("/wechat")
    public AjaxResult wechatLogin(@RequestBody WechatLoginRequest loginRequest) {
        try {
            // 1. 根据openid查询用户是否存在
            User user = userService.selectUserByOpenid(loginRequest.getCode());

            // 2. 如果用户不存在，则创建新用户
            if (user == null) {
                user = new User();
                user.setOpenid(loginRequest.getCode());
                user.setNickName(loginRequest.getNickName());
                user.setAvatarUrl(loginRequest.getAvatarUrl());
                
                // 生成邀请码
                String inviteCode = generateInviteCode();
                user.setCode(inviteCode);
                
                // 插入新用户
                userService.insertUser(user);
                
                // 重新查询以获取完整的用户信息（包括ID）
                user = userService.selectUserByOpenid(loginRequest.getCode());
            } else {
                // 3. 如果用户存在，更新用户信息
                user.setNickName(loginRequest.getNickName());
                user.setAvatarUrl(loginRequest.getAvatarUrl());
                userService.updateUser(user);
            }

            // 4. 构造LoginUser对象
            SysUser sysUser = new SysUser();
            sysUser.setUserId(user.getId());
            sysUser.setUserName(user.getNickName());
            sysUser.setAvatar(user.getAvatarUrl());
            
            LoginUser loginUser = new LoginUser(user.getId(), null, sysUser, null);
            
            // 5. 创建token并存储到redis
            String token = tokenService.createToken(loginUser);

            // 6. 构造响应结果
            WechatLoginResponse response = new WechatLoginResponse(true, "登录成功", user, token);

            return AjaxResult.success(response);
        } catch (Exception e) {
            return AjaxResult.error("登录失败: " + e.getMessage());
        }
    }

    /**
     * 生成邀请码
     * @return 邀请码
     */
    private String generateInviteCode() {
        // 生成8位随机邀请码
        String chars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int index = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }
}