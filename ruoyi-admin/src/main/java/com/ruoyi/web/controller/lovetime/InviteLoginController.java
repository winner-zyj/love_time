package com.ruoyi.web.controller.lovetime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.lovetime.User;
import com.ruoyi.common.core.domain.lovetime.InviteLoginRequest;
import com.ruoyi.common.core.domain.lovetime.WechatLoginResponse;
import com.ruoyi.common.utils.InviteCodeUtil;
import com.ruoyi.lovetime.service.IUserService;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.domain.entity.SysUser;

/**
 * 邀请码登录Controller
 * 
 * @author ruoyi
 * @date 2025-11-14
 */
@RestController
@RequestMapping("/api/login")
@Anonymous
public class InviteLoginController {

    private static final Logger log = LoggerFactory.getLogger(InviteLoginController.class);

    @Autowired
    private IUserService userService;
    
    @Autowired
    private TokenService tokenService;

    /**
     * 使用邀请码登录
     */
    @Anonymous
    @PostMapping("/invite")
    public AjaxResult inviteLogin(@RequestBody InviteLoginRequest loginRequest) {
        try {
            log.info("开始邀请码登录流程，邀请码: {}", loginRequest.getInviteCode());
            
            // 1. 根据邀请码查询邀请人
            User inviter = userService.selectUserByInviteCode(loginRequest.getInviteCode());
            
            // 检查邀请人是否存在
            if (inviter == null) {
                return AjaxResult.error("邀请码无效");
            }
            
            // 2. 创建新用户
            User newUser = new User();
            newUser.setNickName(loginRequest.getNickName());
            newUser.setAvatarUrl(loginRequest.getAvatarUrl());
            
            // 生成唯一的6位大写英文字母和数字结合的邀请码
            String inviteCode = InviteCodeUtil.generateUniqueInviteCode(userService::existsByInviteCode);
            newUser.setInviteCode(inviteCode);
            
            // 插入新用户
            userService.insertUser(newUser);
            
            // 重新查询以获取完整的用户信息（包括ID）
            newUser = userService.selectUserByInviteCode(inviteCode);
            
            // 3. 构造LoginUser对象
            SysUser sysUser = new SysUser();
            sysUser.setUserId(newUser.getId());
            sysUser.setUserName(newUser.getNickName());
            sysUser.setAvatar(newUser.getAvatarUrl());
            
            LoginUser loginUser = new LoginUser(newUser.getId(), null, sysUser, null);
            
            // 4. 创建token并存储到redis
            String token = tokenService.createToken(loginUser);

            // 5. 构造响应结果
            WechatLoginResponse response = new WechatLoginResponse(true, "登录成功", newUser, token);

            return AjaxResult.success(response);
        } catch (Exception e) {
            log.error("邀请码登录过程中发生异常", e);
            return AjaxResult.error("登录失败: " + e.getMessage());
        }
    }
}