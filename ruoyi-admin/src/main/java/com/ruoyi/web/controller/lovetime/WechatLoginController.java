package com.ruoyi.web.controller.lovetime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.lovetime.WechatLoginRequest;
import com.ruoyi.common.core.domain.lovetime.WechatLoginResponse;
import com.ruoyi.common.core.domain.lovetime.User;
import com.ruoyi.common.utils.InviteCodeUtil;
import com.ruoyi.lovetime.service.IUserService;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.WeChatUtil;
import java.util.Map;

/**
 * 微信登录Controller
 * 
 * @author ruoyi
 * @date 2025-11-13
 */
@RestController
@RequestMapping("/api/login")
@Anonymous
public class WechatLoginController {

    private static final Logger log = LoggerFactory.getLogger(WechatLoginController.class);

    @Autowired
    private IUserService userService;
    
    @Autowired
    private TokenService tokenService;
    
    @Value("${wechat.appid}")
    private String appId;
    
    @Value("${wechat.secret}")
    private String secret;

    /**
     * 微信登录
     */
    @Anonymous
    @PostMapping("/wechat")
    public AjaxResult wechatLogin(@RequestBody WechatLoginRequest loginRequest) {
        try {
            log.info("开始微信登录流程，appid: {}, code: {}", appId, loginRequest.getCode());
            
            // 1. 调用微信接口，通过code获取openid和session_key
            Map<String, String> wechatResult = WeChatUtil.getOpenIdAndSessionKey(appId, secret, loginRequest.getCode());
            
            // 检查是否获取成功
            if (wechatResult.containsKey("error")) {
                log.error("微信登录失败: {}", wechatResult.get("error"));
                return AjaxResult.error("微信登录失败: " + wechatResult.get("error"));
            }
            
            String openid = wechatResult.get("openid");
            String sessionKey = wechatResult.get("sessionKey");
            
            log.info("成功获取微信用户信息，openid: {}", openid);
            
            // 2. 根据openid查询用户是否存在
            User user = userService.selectUserByOpenid(openid);

            // 3. 如果用户不存在，则创建新用户
            if (user == null) {
                user = new User();
                user.setOpenid(openid);
                user.setSessionKey(sessionKey);
                user.setNickName(loginRequest.getNickName());
                user.setAvatarUrl(loginRequest.getAvatarUrl());
                
                // 设置临时登录凭证
                user.setCode(loginRequest.getCode());
                
                // 生成唯一的6位大写英文字母和数字结合的邀请码
                String inviteCode = InviteCodeUtil.generateUniqueInviteCode(userService::existsByInviteCode);
                user.setInviteCode(inviteCode);
                
                // 插入新用户
                userService.insertUser(user);

                // 重新查询以获取完整的用户信息（包括ID）
                user = userService.selectUserByOpenid(openid);
            } else {
                // 4. 如果用户存在，更新用户信息
                user.setSessionKey(sessionKey);
                user.setNickName(loginRequest.getNickName());
                user.setAvatarUrl(loginRequest.getAvatarUrl());
                // 更新临时登录凭证
                user.setCode(loginRequest.getCode());
                userService.updateUser(user);
            }

            // 5. 构造LoginUser对象
            SysUser sysUser = new SysUser();
            sysUser.setUserId(user.getId());
            sysUser.setUserName(user.getNickName());
            sysUser.setAvatar(user.getAvatarUrl());
            
            LoginUser loginUser = new LoginUser(user.getId(), null, sysUser, null);
            
            // 6. 创建token并存储到redis
            String token = tokenService.createToken(loginUser);

            // 7. 构造响应结果
            WechatLoginResponse response = new WechatLoginResponse(true, "登录成功", user, token);

            return AjaxResult.success(response);
        } catch (Exception e) {
            log.error("微信登录过程中发生异常", e);
            return AjaxResult.error("登录失败: " + e.getMessage());
        }
    }
}