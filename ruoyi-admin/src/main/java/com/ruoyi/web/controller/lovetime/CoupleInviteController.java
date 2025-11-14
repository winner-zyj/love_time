package com.ruoyi.web.controller.lovetime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.lovetime.User;
import com.ruoyi.common.core.domain.lovetime.CoupleRelationship;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.InviteCodeUtil;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.lovetime.service.IUserService;
import com.ruoyi.lovetime.service.ICoupleRelationshipService;
import javax.servlet.http.HttpServletRequest;

/**
 * 情侣邀请码Controller
 * 
 * @author ruoyi
 * @date 2025-11-14
 */
@RestController
@RequestMapping("/api/couple")
public class CoupleInviteController {

    private static final Logger log = LoggerFactory.getLogger(CoupleInviteController.class);

    @Autowired
    private IUserService userService;
    
    @Autowired
    private ICoupleRelationshipService coupleRelationshipService;
    
    @Autowired
    private TokenService tokenService;

    /**
     * 为当前用户生成情侣邀请码
     */
    @PostMapping("/invite/create")
    public AjaxResult createCoupleInviteCode(HttpServletRequest request) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(request);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }
            
            // 获取用户信息
            User user = userService.selectUserById(loginUser.getUserId());
            if (user == null) {
                return AjaxResult.error("用户不存在");
            }
            
            // 生成唯一的6位大写英文字母和数字结合的邀请码
            String inviteCode = InviteCodeUtil.generateUniqueInviteCode(userService::existsByInviteCode);
            
            // 更新用户邀请码
            user.setInviteCode(inviteCode);
            userService.updateUser(user);
            
            // 返回邀请码
            return AjaxResult.success("邀请码生成成功", inviteCode);
        } catch (Exception e) {
            log.error("生成情侣邀请码过程中发生异常", e);
            return AjaxResult.error("生成邀请码失败: " + e.getMessage());
        }
    }
    
    /**
     * 验证邀请码是否有效
     */
    @Anonymous
    @GetMapping("/invite/validate")
    public AjaxResult validateInviteCode(@RequestParam("code") String code) {
        try {
            // 检查邀请码是否为空
            if (code == null || code.trim().isEmpty()) {
                return AjaxResult.error("邀请码不能为空");
            }
            
            // 查询是否有用户使用该邀请码
            User user = userService.selectUserByInviteCode(code);
            if (user == null) {
                return AjaxResult.error("邀请码无效");
            }
            
            // 检查该用户是否已经有情侣关系
            CoupleRelationship relationship = coupleRelationshipService.selectCoupleRelationshipByUserId(user.getId());
            if (relationship != null) {
                return AjaxResult.error("该用户已存在情侣关系");
            }
            
            // 邀请码有效
            return AjaxResult.success("邀请码有效");
        } catch (Exception e) {
            log.error("验证邀请码过程中发生异常", e);
            return AjaxResult.error("验证失败: " + e.getMessage());
        }
    }
    
    /**
     * 检查当前用户是否有情侣关系
     */
    @GetMapping("/status")
    public AjaxResult checkCoupleStatus(HttpServletRequest request) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(request);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }
            
            // 获取用户信息
            User user = userService.selectUserById(loginUser.getUserId());
            if (user == null) {
                return AjaxResult.error("用户不存在");
            }
            
            // 检查用户是否有情侣关系
            // 通过查询at_couple_relationships表判断用户是否有情侣关系
            CoupleRelationship relationship = coupleRelationshipService.selectCoupleRelationshipByUserId(user.getId());
            boolean hasCouple = relationship != null;
            
            return AjaxResult.success("查询成功", hasCouple);
        } catch (Exception e) {
            log.error("检查情侣关系状态过程中发生异常", e);
            return AjaxResult.error("查询失败: " + e.getMessage());
        }
    }
}