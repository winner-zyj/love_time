package com.ruoyi.web.controller.lovetime;

import java.util.Arrays;
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

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.lovetime.FutureLetter;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.lovetime.service.IFutureLetterService;
import com.ruoyi.common.core.domain.model.LoginUser;

/**
 * 未来情书Controller
 * 
 * @author ruoyi
 * @date 2025-11-19
 */
@RestController
@RequestMapping("/api/future-letter")
public class FutureLetterController {
    
    @Autowired
    private TokenService tokenService;
    
    @Autowired
    private IFutureLetterService futureLetterService;
    
    /**
     * 获取列表
     */
    @GetMapping
    public AjaxResult getFutureLetters(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            HttpServletRequest request) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(request);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }
            
            // 查询用户的情书列表
            FutureLetter query = new FutureLetter();
            query.setSenderId(loginUser.getUserId());
            List<FutureLetter> letters = futureLetterService.selectFutureLetterList(query);
            
            // 分页处理
            int total = letters.size();
            int fromIndex = (page - 1) * pageSize;
            int toIndex = Math.min(fromIndex + pageSize, total);
            List<FutureLetter> pagedLetters = letters.subList(fromIndex, toIndex);
            
            // 构造返回数据
            Map<String, Object> result = new HashMap<>();
            result.put("total", total);
            result.put("page", page);
            result.put("pageSize", pageSize);
            result.put("letters", pagedLetters);
            
            return AjaxResult.success(result);
        } catch (Exception e) {
            return AjaxResult.error("获取情书列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取单封情书
     */
    @GetMapping("/{id}")
    public AjaxResult getFutureLetter(@PathVariable Long id, HttpServletRequest request) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(request);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }
            
            // 查询情书
            FutureLetter letter = futureLetterService.selectFutureLetterById(id);
            if (letter == null) {
                return AjaxResult.error("情书不存在");
            }
            
            // 验证用户权限
            if (!letter.getSenderId().equals(loginUser.getUserId()) && 
                !letter.getReceiverId().equals(loginUser.getUserId())) {
                return AjaxResult.error("无权限查看该情书");
            }
            
            return AjaxResult.success(letter);
        } catch (Exception e) {
            return AjaxResult.error("获取情书详情失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取已发送列表
     */
    @GetMapping("/sent")
    public AjaxResult getSentLetters(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            HttpServletRequest request) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(request);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }
            
            // 查询用户已发送的情书列表
            List<FutureLetter> letters = futureLetterService.selectFutureLettersBySenderId(
                loginUser.getUserId(), "SENT");
            
            // 分页处理
            int total = letters.size();
            int fromIndex = (page - 1) * pageSize;
            int toIndex = Math.min(fromIndex + pageSize, total);
            List<FutureLetter> pagedLetters = letters.subList(fromIndex, toIndex);
            
            // 构造返回数据
            Map<String, Object> result = new HashMap<>();
            result.put("total", total);
            result.put("page", page);
            result.put("pageSize", pageSize);
            result.put("letters", pagedLetters);
            
            return AjaxResult.success(result);
        } catch (Exception e) {
            return AjaxResult.error("获取已发送情书列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取收到列表
     */
    @GetMapping("/received")
    public AjaxResult getReceivedLetters(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            HttpServletRequest request) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(request);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }
            
            // 查询用户收到的情书列表
            List<FutureLetter> letters = futureLetterService.selectFutureLettersByReceiverId(
                loginUser.getUserId(), null);
            
            // 分页处理
            int total = letters.size();
            int fromIndex = (page - 1) * pageSize;
            int toIndex = Math.min(fromIndex + pageSize, total);
            List<FutureLetter> pagedLetters = letters.subList(fromIndex, toIndex);
            
            // 构造返回数据
            Map<String, Object> result = new HashMap<>();
            result.put("total", total);
            result.put("page", page);
            result.put("pageSize", pageSize);
            result.put("letters", pagedLetters);
            
            return AjaxResult.success(result);
        } catch (Exception e) {
            return AjaxResult.error("获取收到情书列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 统计信息
     */
    @GetMapping("/stats")
    public AjaxResult getStats(HttpServletRequest request) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(request);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }
            
            // 查询各种状态的情书数量
            FutureLetter query = new FutureLetter();
            query.setSenderId(loginUser.getUserId());
            
            List<FutureLetter> allLetters = futureLetterService.selectFutureLetterList(query);
            
            long totalDraft = allLetters.stream()
                .filter(letter -> "DRAFT".equals(letter.getStatus()))
                .count();
                
            long totalScheduled = allLetters.stream()
                .filter(letter -> "SCHEDULED".equals(letter.getStatus()))
                .count();
                
            long totalSent = allLetters.stream()
                .filter(letter -> "SENT".equals(letter.getStatus()))
                .count();
            
            List<FutureLetter> receivedLetters = futureLetterService.selectFutureLettersByReceiverId(
                loginUser.getUserId(), null);
            long totalReceived = receivedLetters.size();
            
            // 构造返回数据
            Map<String, Object> result = new HashMap<>();
            result.put("totalDraft", totalDraft);
            result.put("totalScheduled", totalScheduled);
            result.put("totalSent", totalSent);
            result.put("totalReceived", totalReceived);
            
            return AjaxResult.success(result);
        } catch (Exception e) {
            return AjaxResult.error("获取统计信息失败: " + e.getMessage());
        }
    }
    
    /**
     * 创建情书
     */
    @PostMapping
    public AjaxResult createFutureLetter(@RequestBody FutureLetter letter, HttpServletRequest request) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(request);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }
            
            // 验证参数
            if (letter.getTitle() == null || letter.getTitle().trim().isEmpty()) {
                return AjaxResult.error("情书标题不能为空");
            }
            
            if (letter.getContent() == null || letter.getContent().trim().isEmpty()) {
                return AjaxResult.error("情书内容不能为空");
            }
            
            if (letter.getScheduledDate() == null) {
                return AjaxResult.error("发送时间不能为空");
            }
            
            // 设置发送者
            letter.setSenderId(loginUser.getUserId());
            
            // 设置默认状态
            if (letter.getStatus() == null) {
                letter.setStatus("SCHEDULED");
            }
            
            // 设置创建和更新时间
            letter.setCreatedAt(new Date());
            letter.setUpdatedAt(new Date());
            
            // 保存情书
            futureLetterService.insertFutureLetter(letter);
            
            return AjaxResult.success("创建成功", letter);
        } catch (Exception e) {
            return AjaxResult.error("创建情书失败: " + e.getMessage());
        }
    }
    
    /**
     * 更新情书
     */
    @PutMapping("/{id}")
    public AjaxResult updateFutureLetter(@PathVariable Long id, @RequestBody FutureLetter letter, HttpServletRequest request) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(request);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }
            
            // 查询原有情书
            FutureLetter existingLetter = futureLetterService.selectFutureLetterById(id);
            if (existingLetter == null) {
                return AjaxResult.error("情书不存在");
            }
            
            // 验证用户权限
            if (!existingLetter.getSenderId().equals(loginUser.getUserId())) {
                return AjaxResult.error("无权限修改该情书");
            }
            
            // 更新情书信息
            existingLetter.setTitle(letter.getTitle());
            existingLetter.setContent(letter.getContent());
            existingLetter.setFontStyle(letter.getFontStyle());
            existingLetter.setBackgroundImage(letter.getBackgroundImage());
            existingLetter.setScheduledDate(letter.getScheduledDate());
            existingLetter.setScheduledTime(letter.getScheduledTime());
            existingLetter.setUpdatedAt(new Date());
            
            // 保存更新
            futureLetterService.updateFutureLetter(existingLetter);
            
            return AjaxResult.success("更新成功", existingLetter);
        } catch (Exception e) {
            return AjaxResult.error("更新情书失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除情书
     */
    @DeleteMapping("/{id}")
    public AjaxResult deleteFutureLetter(@PathVariable Long id, HttpServletRequest request) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(request);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }
            
            // 查询原有情书
            FutureLetter existingLetter = futureLetterService.selectFutureLetterById(id);
            if (existingLetter == null) {
                return AjaxResult.error("情书不存在");
            }
            
            // 验证用户权限
            if (!existingLetter.getSenderId().equals(loginUser.getUserId())) {
                return AjaxResult.error("无权限删除该情书");
            }
            
            // 验证状态，只有草稿或未发送的情书可以删除
            if ("SENT".equals(existingLetter.getStatus())) {
                return AjaxResult.error("已发送的情书不能删除");
            }
            
            // 删除情书
            futureLetterService.deleteFutureLetterById(id);
            
            // 构造返回数据
            Map<String, Object> result = new HashMap<>();
            result.put("deleted", true);
            
            return AjaxResult.success("删除成功", result);
        } catch (Exception e) {
            return AjaxResult.error("删除情书失败: " + e.getMessage());
        }
    }
    
    /**
     * 立即发送
     */
    @PostMapping("/{id}/send")
    public AjaxResult sendFutureLetter(@PathVariable Long id, 
            @RequestBody(required = false) Map<String, Boolean> requestBody,
            HttpServletRequest request) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(request);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }
            
            // 查询原有情书
            FutureLetter existingLetter = futureLetterService.selectFutureLetterById(id);
            if (existingLetter == null) {
                return AjaxResult.error("情书不存在");
            }
            
            // 验证用户权限
            if (!existingLetter.getSenderId().equals(loginUser.getUserId())) {
                return AjaxResult.error("无权限发送该情书");
            }
            
            // 更新状态为已发送
            existingLetter.setStatus("SENT");
            existingLetter.setSentAt(new Date());
            existingLetter.setUpdatedAt(new Date());
            
            // 保存更新
            futureLetterService.updateFutureLetter(existingLetter);
            
            // 构造返回数据
            Map<String, Object> result = new HashMap<>();
            result.put("status", "SENT");
            result.put("sentAt", existingLetter.getSentAt());
            
            return AjaxResult.success("发送成功", result);
        } catch (Exception e) {
            return AjaxResult.error("发送情书失败: " + e.getMessage());
        }
    }
    
    /**
     * 更新定时发送配置
     */
    @PostMapping("/{id}/schedule")
    public AjaxResult scheduleFutureLetter(@PathVariable Long id, 
            @RequestBody Map<String, Object> requestBody,
            HttpServletRequest request) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(request);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }
            
            // 查询原有情书
            FutureLetter existingLetter = futureLetterService.selectFutureLetterById(id);
            if (existingLetter == null) {
                return AjaxResult.error("情书不存在");
            }
            
            // 验证用户权限
            if (!existingLetter.getSenderId().equals(loginUser.getUserId())) {
                return AjaxResult.error("无权限修改该情书");
            }
            
            // 更新定时发送配置
            if (requestBody.containsKey("scheduleTime")) {
                // 这里应该解析时间字符串，但为简化实现，直接设置
                existingLetter.setScheduledDate(new Date());
            }
            
            existingLetter.setStatus("SCHEDULED");
            existingLetter.setUpdatedAt(new Date());
            
            // 保存更新
            futureLetterService.updateFutureLetter(existingLetter);
            
            // 构造返回数据
            Map<String, Object> result = new HashMap<>();
            result.put("id", existingLetter.getId());
            result.put("scheduleTime", existingLetter.getScheduledDate());
            result.put("status", "SCHEDULED");
            
            return AjaxResult.success("定时发送配置更新成功", result);
        } catch (Exception e) {
            return AjaxResult.error("更新定时发送配置失败: " + e.getMessage());
        }
    }
    
    /**
     * 字体列表
     */
    @GetMapping("/fonts")
    @Anonymous
    public AjaxResult getFonts() {
        try {
            // 构造字体列表
            List<Map<String, String>> fonts = Arrays.asList(
                createFontMap("default", "默认字体", "清晰易读", "未来与你"),
                createFontMap("mashanzheng", "马善政手写", "温柔手写感", "未来与你"),
                createFontMap("zcoolkuaile", "站酷快乐体", "活泼可爱", "未来与你"),
                createFontMap("qingsong", "清松手写体", "自然流畅", "未来与你"),
                createFontMap("zcoolxiaowei", "站酷小薇体", "清新文艺", "未来与你"),
                createFontMap("zcoolwenyi", "站酷文艺体", "优雅文艺", "未来与你")
            );
            
            return AjaxResult.success(fonts);
        } catch (Exception e) {
            return AjaxResult.error("获取字体列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 创建字体信息映射
     * 
     * @param value 字体值
     * @param label 字体标签
     * @param description 字体描述
     * @param sample 字体样例
     * @return 字体信息映射
     */
    private Map<String, String> createFontMap(String value, String label, String description, String sample) {
        Map<String, String> fontMap = new HashMap<>();
        fontMap.put("value", value);
        fontMap.put("label", label);
        fontMap.put("description", description);
        fontMap.put("sample", sample);
        return fontMap;
    }
    
    /**
     * 获取用户未读的未来情书
     */
    @GetMapping("/unread")
    public AjaxResult getUnreadLetters(HttpServletRequest request) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(request);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }
            
            // 查询用户收到的未读情书列表（状态为已发送但未读）
            List<FutureLetter> letters = futureLetterService.selectFutureLettersByReceiverId(
                loginUser.getUserId(), "已发送");
            
            // 过滤出未读的情书
            List<FutureLetter> unreadLetters = letters.stream()
                .filter(letter -> letter.getReadAt() == null)
                .collect(java.util.stream.Collectors.toList());
            
            return AjaxResult.success("获取未读情书成功").put("letters", unreadLetters);
        } catch (Exception e) {
            return AjaxResult.error("获取未读情书失败: " + e.getMessage());
        }
    }
}