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
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.lovetime.WechatLoginRequest;
import com.ruoyi.common.core.domain.lovetime.User;
import com.ruoyi.common.utils.InviteCodeUtil;
import com.ruoyi.lovetime.service.IUserService;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.WeChatUtil;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

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
            
            // 记录请求参数
            log.info("微信登录请求参数: nickName={}, avatarUrl={}", loginRequest.getNickName(), loginRequest.getAvatarUrl());
            
            // 1. 调用微信接口，通过code获取openid和session_key
            long startTime = System.currentTimeMillis();
            Map<String, String> wechatResult = WeChatUtil.getOpenIdAndSessionKey(appId, secret, loginRequest.getCode());
            long endTime = System.currentTimeMillis();
            
            log.info("调用微信接口耗时: {}ms", endTime - startTime);
            
            // 检查是否获取成功
            if (wechatResult.containsKey("error")) {
                log.error("微信登录失败: {}", wechatResult.get("error"));
                return AjaxResult.error("微信登录失败: " + wechatResult.get("error"));
            }
            
            String openid = wechatResult.get("openid");
            String sessionKey = wechatResult.get("sessionKey");
            
            log.info("成功获取微信用户信息，openid: {}", openid);
            
            boolean isNewUser = false;
            
            // 2. 根据openid查询用户是否存在
            log.info("开始查询用户信息，openid: {}", openid);
            User user = userService.selectUserByOpenid(openid);
            log.info("用户查询完成，user: {}", user);

            // 3. 如果用户不存在，则创建新用户
            if (user == null) {
                log.info("用户不存在，开始创建新用户");
                isNewUser = true;
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
                log.info("开始插入新用户");
                userService.insertUser(user);
                log.info("新用户插入完成");

                // 重新查询以获取完整的用户信息（包括ID）
                log.info("重新查询用户信息");
                user = userService.selectUserByOpenid(openid);
                log.info("重新查询用户信息完成，userId: {}", user.getId());
            } else {
                log.info("用户已存在，开始更新用户信息");
                // 4. 如果用户存在，更新用户信息
                user.setSessionKey(sessionKey);
                user.setNickName(loginRequest.getNickName());
                user.setAvatarUrl(loginRequest.getAvatarUrl());
                // 更新临时登录凭证
                user.setCode(loginRequest.getCode());
                userService.updateUser(user);
                log.info("用户信息更新完成");
            }

            // 5. 构造LoginUser对象
            log.info("开始构造LoginUser对象");
            SysUser sysUser = new SysUser();
            sysUser.setUserId(user.getId());
            sysUser.setUserName(user.getNickName());
            sysUser.setAvatar(user.getAvatarUrl());
            
            LoginUser loginUser = new LoginUser(user.getId(), null, sysUser, null);
            
            // 6. 创建token并存储到redis
            log.info("开始创建token");
            String token = tokenService.createToken(loginUser);
            log.info("token创建完成: {}", token);

            // 7. 构造响应结果，严格按照文档格式
            Map<String, Object> data = new java.util.HashMap<>();
            data.put("token", Constants.TOKEN_PREFIX + token);  // 添加Bearer前缀
            data.put("openid", openid);
            data.put("session_key", sessionKey);
            data.put("userId", user.getId());
            data.put("isNewUser", isNewUser);

            log.info("微信登录流程完成，返回响应");
            return AjaxResult.success("登录成功", data);
        } catch (Exception e) {
            log.error("微信登录过程中发生异常", e);
            return AjaxResult.error("登录失败: " + e.getMessage());
        }
    }
    
    /**
     * 退出登录
     */
    @PostMapping("/logout")
    public AjaxResult logout(HttpServletRequest request) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(request);
            if (loginUser != null) {
                // 从Redis中删除用户的token
                tokenService.delLoginUser(loginUser.getToken());
            }
            
            // 返回成功响应
            return AjaxResult.success("退出成功");
        } catch (Exception e) {
            log.error("退出登录过程中发生异常", e);
            return AjaxResult.error("退出失败: " + e.getMessage());
        }
    }
}