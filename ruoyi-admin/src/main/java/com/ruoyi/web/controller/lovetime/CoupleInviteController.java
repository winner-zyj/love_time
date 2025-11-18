package com.ruoyi.web.controller.lovetime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
            log.info("开始验证邀请码: {}", code);
            
            // 检查邀请码是否为空
            if (code == null || code.trim().isEmpty()) {
                log.warn("邀请码为空");
                return AjaxResult.error("邀请码不能为空");
            }
            
            // 查询是否有用户使用该邀请码
            User user = userService.selectUserByInviteCode(code);
            if (user == null) {
                log.warn("未找到使用该邀请码的用户: {}", code);
                return AjaxResult.error("邀请码无效");
            }
            
            log.info("找到使用该邀请码的用户，用户ID: {}", user.getId());
            
            // 检查该用户是否已经有情侣关系
            CoupleRelationship relationship = coupleRelationshipService.selectCoupleRelationshipByUserId(user.getId());
            if (relationship != null) {
                log.warn("该用户已存在情侣关系，关系ID: {}", relationship.getId());
                return AjaxResult.error("该用户已存在情侣关系");
            }
            
            log.info("邀请码验证通过: {}", code);
            
            // 构造验证成功的响应数据
            java.util.Map<String, Object> result = new java.util.HashMap<>();
            result.put("code", code);
            
            // 添加创建者信息
            java.util.Map<String, Object> creator = new java.util.HashMap<>();
            creator.put("userId", user.getId());
            creator.put("nickName", user.getNickName());
            creator.put("avatarUrl", user.getAvatarUrl());
            result.put("creator", creator);
            
            // 设置过期时间（假设邀请码7天后过期）
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.add(java.util.Calendar.DAY_OF_MONTH, 7);
            result.put("expireAt", calendar.getTime());
            
            // 邀请码有效
            return AjaxResult.success("邀请码有效", result);
        } catch (Exception e) {
            log.error("验证邀请码过程中发生异常", e);
            return AjaxResult.error("验证失败: " + e.getMessage());
        }
    }
    
    /**
     * 接受邀请（绑定关系）
     */
    @PostMapping("/bind/accept")
    public AjaxResult acceptInvite(@RequestBody AcceptInviteRequest request, HttpServletRequest httpServletRequest) {
        try {
            log.info("开始处理情侣邀请绑定请求: {}", request.getInviteCode());
            
            // 获取当前登录用户（受邀方）
            LoginUser loginUser = tokenService.getLoginUser(httpServletRequest);
            if (loginUser == null) {
                log.warn("用户未登录");
                return AjaxResult.error("用户未登录");
            }
            
            // 获取受邀方用户信息
            User accepter = userService.selectUserById(loginUser.getUserId());
            if (accepter == null) {
                log.warn("用户不存在，用户ID: {}", loginUser.getUserId());
                return AjaxResult.error("用户不存在");
            }
            
            log.info("受邀方用户信息: ID={}, 昵称={}", accepter.getId(), accepter.getNickName());
            
            // 验证受邀方是否已经有情侣关系
            CoupleRelationship existingRelationship = coupleRelationshipService.selectCoupleRelationshipByUserId(accepter.getId());
            if (existingRelationship != null) {
                log.warn("用户已存在情侣关系，用户ID: {}", accepter.getId());
                return AjaxResult.error("您已存在情侣关系");
            }
            
            // 查询邀请方用户信息
            User inviter = userService.selectUserByInviteCode(request.getInviteCode());
            if (inviter == null) {
                log.warn("邀请码无效: {}", request.getInviteCode());
                return AjaxResult.error("邀请码无效");
            }
            
            log.info("邀请方用户信息: ID={}, 昵称={}", inviter.getId(), inviter.getNickName());
            
            // 验证邀请方是否已经有情侣关系
            CoupleRelationship inviterRelationship = coupleRelationshipService.selectCoupleRelationshipByUserId(inviter.getId());
            if (inviterRelationship != null) {
                log.warn("邀请方已存在情侣关系，用户ID: {}", inviter.getId());
                return AjaxResult.error("邀请方已存在情侣关系");
            }
            
            // 确保不是自己邀请自己
            if (accepter.getId().equals(inviter.getId())) {
                log.warn("不能邀请自己，用户ID: {}", accepter.getId());
                return AjaxResult.error("不能邀请自己");
            }
            
            // 验证用户是否真的存在于数据库中（解决外键约束问题）
            User verifyAccepter = userService.selectUserById(accepter.getId());
            User verifyInviter = userService.selectUserById(inviter.getId());
            
            if (verifyAccepter == null) {
                log.error("受邀方用户在数据库中不存在，用户ID: {}", accepter.getId());
                return AjaxResult.error("受邀方用户数据异常");
            }
            
            if (verifyInviter == null) {
                log.error("邀请方用户在数据库中不存在，用户ID: {}", inviter.getId());
                return AjaxResult.error("邀请方用户数据异常");
            }
            
            log.info("用户验证通过，受邀方ID: {}, 邀请方ID: {}", verifyAccepter.getId(), verifyInviter.getId());
            
            // 创建情侣关系
            CoupleRelationship relationship = new CoupleRelationship();
            // 确保user1_id < user2_id，保持一致性
            if (accepter.getId() < inviter.getId()) {
                relationship.setUser1Id(accepter.getId());
                relationship.setUser2Id(inviter.getId());
            } else {
                relationship.setUser1Id(inviter.getId());
                relationship.setUser2Id(accepter.getId());
            }
            
            relationship.setStatus("active");
            relationship.setInitiatorId(inviter.getId());
            relationship.setReceiverId(accepter.getId());
            relationship.setRelationshipName("我的宝贝");
            
            log.info("准备保存情侣关系: user1Id={}, user2Id={}, initiatorId={}, receiverId={}", 
                relationship.getUser1Id(), relationship.getUser2Id(), 
                relationship.getInitiatorId(), relationship.getReceiverId());
            
            // 先保存情侣关系以获取ID
            int insertResult = coupleRelationshipService.insertCoupleRelationship(relationship);
            log.info("情侣关系插入结果: {}", insertResult);
            
            if (insertResult <= 0) {
                log.error("情侣关系插入失败");
                return AjaxResult.error("情侣关系保存失败");
            }
            
            log.info("情侣关系已保存，关系ID: {}", relationship.getId());
            
            // 设置 couple_id 为关系自身的ID
            relationship.setCoupleId(relationship.getId());
            int updateResult = coupleRelationshipService.updateCoupleRelationship(relationship);
            log.info("情侣关系更新结果: {}", updateResult);
            
            if (updateResult <= 0) {
                log.error("情侣关系更新失败");
                return AjaxResult.error("情侣关系更新失败");
            }
            
            log.info("情侣关系已更新，coupleId: {}", relationship.getCoupleId());
            
            // 验证数据是否真正保存到数据库
            CoupleRelationship savedRelationship = coupleRelationshipService.selectCoupleRelationshipById(relationship.getId());
            if (savedRelationship != null) {
                log.info("验证情侣关系保存成功: ID={}, coupleId={}, user1Id={}, user2Id={}", 
                    savedRelationship.getId(), savedRelationship.getCoupleId(),
                    savedRelationship.getUser1Id(), savedRelationship.getUser2Id());
            } else {
                log.error("验证情侣关系保存失败: 数据库中未找到ID为{}的记录", relationship.getId());
                // 尝试再次查询以确认
                CoupleRelationship secondCheck = coupleRelationshipService.selectCoupleRelationshipById(relationship.getId());
                if (secondCheck != null) {
                    log.info("二次验证情侣关系保存成功: ID={}, coupleId={}", 
                        secondCheck.getId(), secondCheck.getCoupleId());
                } else {
                    log.error("二次验证情侣关系保存仍然失败: 数据库中未找到ID为{}的记录", relationship.getId());
                    // 检查数据库连接和事务状态
                    log.error("可能的原因: 1. 数据库连接问题 2. 事务回滚 3. 外键约束违反 4. 权限问题");
                    return AjaxResult.error("情侣关系保存失败：数据未持久化到数据库");
                }
            }
            
            // 构造返回结果
            java.util.Map<String, Object> result = new java.util.HashMap<>();
            result.put("coupleId", relationship.getId());
            result.put("relationshipId", relationship.getCoupleId()); // 添加 couple_id 字段
            
            // 添加对方用户信息
            java.util.Map<String, Object> partnerInfo = new java.util.HashMap<>();
            partnerInfo.put("userId", inviter.getId());
            partnerInfo.put("nickName", inviter.getNickName());
            partnerInfo.put("avatarUrl", inviter.getAvatarUrl());
            result.put("partnerInfo", partnerInfo);
            
            result.put("bindTime", relationship.getCreatedAt());
            
            log.info("情侣绑定成功，返回结果: {}", result);
            return AjaxResult.success("绑定成功", result);
        } catch (Exception e) {
            log.error("通过邀请码登录过程中发生异常", e);
            // 记录详细的异常信息
            log.error("异常类型: {}", e.getClass().getName());
            log.error("异常消息: {}", e.getMessage());
            // 记录堆栈跟踪
            for (StackTraceElement element : e.getStackTrace()) {
                log.error("堆栈跟踪: {}", element.toString());
                // 只记录前10个堆栈元素以避免日志过多
                if (element.toString().contains("CoupleInviteController") || 
                    element.toString().contains("CoupleRelationshipService") ||
                    element.toString().contains("CoupleRelationshipMapper")) {
                    break;
                }
            }
            return AjaxResult.error("绑定失败: " + e.getMessage());
        }
    }
    
    /**
     * 查询当前用户的绑定状态 (严格按照文档实现)
     */
    @GetMapping("/status")
    public AjaxResult getBindStatus(HttpServletRequest request) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(request);
            if (loginUser == null) {
                log.warn("用户未登录，无法查询绑定状态");
                return AjaxResult.error("用户未登录");
            }
            
            // 获取用户信息
            User user = userService.selectUserById(loginUser.getUserId());
            if (user == null) {
                log.warn("用户不存在，用户ID: {}", loginUser.getUserId());
                return AjaxResult.error("用户不存在");
            }
            
            log.info("开始查询用户绑定状态，用户ID: {}", user.getId());
            
            // 查询用户的情侣关系
            CoupleRelationship relationship = coupleRelationshipService.selectCoupleRelationshipByUserId(user.getId());
            
            java.util.Map<String, Object> result = new java.util.HashMap<>();
            
            if (relationship == null) {
                // 用户未绑定情侣
                log.info("用户未绑定情侣，用户ID: {}", user.getId());
                result.put("isBound", false);
            } else {
                // 用户已绑定情侣
                log.info("用户已绑定情侣，用户ID: {}, 情侣关系ID: {}", user.getId(), relationship.getId());
                result.put("isBound", true);
                result.put("coupleId", relationship.getId());
                result.put("relationshipId", relationship.getCoupleId()); // 添加 couple_id 字段
                
                // 获取情侣信息
                Long partnerId = relationship.getUser1Id().equals(user.getId()) ? 
                    relationship.getUser2Id() : relationship.getUser1Id();
                User partner = userService.selectUserById(partnerId);
                
                if (partner != null) {
                    java.util.Map<String, Object> partnerInfo = new java.util.HashMap<>();
                    partnerInfo.put("userId", partner.getId());
                    partnerInfo.put("nickName", partner.getNickName());
                    partnerInfo.put("avatarUrl", partner.getAvatarUrl());
                    result.put("partnerInfo", partnerInfo);
                }
                
                result.put("bindTime", relationship.getCreatedAt());
                
                // 确定用户角色
                String role = relationship.getInitiatorId().equals(user.getId()) ? "initiator" : "accepter";
                result.put("role", role);
            }
            
            log.info("查询绑定状态完成，用户ID: {}, 结果: {}", user.getId(), result);
            return AjaxResult.success("查询成功", result);
        } catch (Exception e) {
            log.error("查询绑定状态过程中发生异常", e);
            return AjaxResult.error("查询失败: " + e.getMessage());
        }
    }
    
    /**
     * 解绑关系
     */
    @PostMapping("/unbind")
    public AjaxResult unbindCouple(HttpServletRequest request) {
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
            
            // 查询用户的情侣关系
            CoupleRelationship relationship = coupleRelationshipService.selectCoupleRelationshipByUserId(user.getId());
            if (relationship == null) {
                return AjaxResult.error("您没有情侣关系");
            }
            
            // 更新关系状态为已解绑
            relationship.setStatus("unbound");
            relationship.setBrokenAt(new java.util.Date());
            int updateResult = coupleRelationshipService.updateCoupleRelationship(relationship);
            
            if (updateResult > 0) {
                log.info("情侣关系解绑成功，用户ID: {}, 关系ID: {}", user.getId(), relationship.getId());
                return AjaxResult.success("解绑成功");
            } else {
                log.error("情侣关系解绑失败，用户ID: {}, 关系ID: {}", user.getId(), relationship.getId());
                return AjaxResult.error("解绑失败");
            }
        } catch (Exception e) {
            log.error("解绑情侣关系过程中发生异常", e);
            return AjaxResult.error("解绑失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取相爱天数
     */
    @GetMapping("/love-days")
    public AjaxResult getLoveDays(HttpServletRequest request) {
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
            
            // 查询用户的情侣关系
            CoupleRelationship relationship = coupleRelationshipService.selectCoupleRelationshipByUserId(user.getId());
            if (relationship == null) {
                return AjaxResult.error("您没有情侣关系");
            }
            
            // 计算相爱天数
            java.util.Date startDate = relationship.getConfirmedAt() != null ? 
                relationship.getConfirmedAt() : relationship.getCreatedAt();
            
            if (startDate == null) {
                return AjaxResult.error("无法获取关系开始时间");
            }
            
            long diffInMillies = Math.abs(new java.util.Date().getTime() - startDate.getTime());
            long loveDays = java.util.concurrent.TimeUnit.DAYS.convert(diffInMillies, java.util.concurrent.TimeUnit.MILLISECONDS);
            
            java.util.Map<String, Object> result = new java.util.HashMap<>();
            result.put("loveDays", loveDays);
            result.put("startDate", startDate);
            
            return AjaxResult.success("获取成功", result);
        } catch (Exception e) {
            log.error("获取相爱天数过程中发生异常", e);
            return AjaxResult.error("获取失败: " + e.getMessage());
        }
    }
    
    /**
     * 接受邀请请求类
     */
    public static class AcceptInviteRequest {
        private String inviteCode;
        
        public String getInviteCode() {
            return inviteCode;
        }
        
        public void setInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
        }
    }
}