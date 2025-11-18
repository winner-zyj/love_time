package com.ruoyi.web.controller.lovetime;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.lovetime.QnaQuestion;
import com.ruoyi.common.core.domain.lovetime.QnaAnswer;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.lovetime.service.IQnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 甜蜜问答Controller
 * 
 * @author ruoyi
 * @date 2025-11-18
 */
@RestController
@RequestMapping("/api/qna")
public class QnaController {
    
    @Autowired
    private IQnaService qnaService;
    
    @Autowired
    private TokenService tokenService;
    
    /**
     * 获取问题列表
     */
    @GetMapping("/questions")
    public AjaxResult getQuestions(HttpServletRequest request) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(request);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }
            
            Map<String, java.util.List<QnaQuestion>> result = qnaService.getQuestions(loginUser.getUserId());
            return AjaxResult.success(result);
        } catch (Exception e) {
            return AjaxResult.error("获取问题列表失败: " + e.getMessage());
        }
    }
    
    /**
     * 提交答案
     */
    @PostMapping("/answer/submit")
    public AjaxResult submitAnswer(@RequestBody QnaAnswer answer, HttpServletRequest request) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(request);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }
            
            QnaAnswer result = qnaService.submitAnswer(answer, loginUser.getUserId());
            
            // 构造返回数据
            Map<String, Object> responseData = new java.util.HashMap<>();
            responseData.put("answerId", result.getId());
            
            // 查询对方答案（如果存在）
            Map<String, Object> partnerAnswerInfo = qnaService.getPartnerAnswer(result.getQuestionId(), loginUser.getUserId());
            if (partnerAnswerInfo != null && (Boolean) partnerAnswerInfo.get("hasAnswered")) {
                responseData.put("partnerAnswer", partnerAnswerInfo.get("answer"));
                responseData.put("hasPartnerAnswered", true);
            } else {
                responseData.put("partnerAnswer", null);
                responseData.put("hasPartnerAnswered", false);
            }
            
            responseData.put("bindTime", result.getAnsweredAt());
            responseData.put("createdAt", result.getAnsweredAt());
            
            return AjaxResult.success(responseData);
        } catch (Exception e) {
            return AjaxResult.error("提交答案失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取历史回答
     */
    @GetMapping("/history")
    public AjaxResult getHistory(@RequestParam(required = false, defaultValue = "1") Integer page,
                                 @RequestParam(required = false, defaultValue = "20") Integer pageSize,
                                 HttpServletRequest request) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(request);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }
            
            Map<String, Object> result = qnaService.getHistory(loginUser.getUserId(), page, pageSize);
            return AjaxResult.success(result);
        } catch (Exception e) {
            return AjaxResult.error("获取历史回答失败: " + e.getMessage());
        }
    }
    
    /**
     * 获取对方答案
     */
    @GetMapping("/partner")
    public AjaxResult getPartnerAnswer(@RequestParam Long questionId, HttpServletRequest request) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(request);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }
            
            Map<String, Object> result = qnaService.getPartnerAnswer(questionId, loginUser.getUserId());
            return AjaxResult.success(result);
        } catch (Exception e) {
            return AjaxResult.error("获取对方答案失败: " + e.getMessage());
        }
    }
    
    /**
     * 添加自定义问题
     */
    @PostMapping("/question/add")
    public AjaxResult addCustomQuestion(@RequestBody Map<String, String> requestBody, HttpServletRequest request) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(request);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }
            
            String questionText = requestBody.get("text");
            if (questionText == null || questionText.trim().isEmpty()) {
                return AjaxResult.error("问题内容不能为空");
            }
            
            QnaQuestion result = qnaService.addCustomQuestion(questionText, loginUser.getUserId());
            return AjaxResult.success(result);
        } catch (Exception e) {
            return AjaxResult.error("添加自定义问题失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除自定义问题
     */
    @PostMapping("/question/delete")
    public AjaxResult deleteCustomQuestion(@RequestBody Map<String, Long> requestBody, HttpServletRequest request) {
        try {
            // 获取当前登录用户
            LoginUser loginUser = tokenService.getLoginUser(request);
            if (loginUser == null) {
                return AjaxResult.error("用户未登录");
            }
            
            Long questionId = requestBody.get("questionId");
            if (questionId == null) {
                return AjaxResult.error("问题ID不能为空");
            }
            
            boolean result = qnaService.deleteCustomQuestion(questionId, loginUser.getUserId());
            return AjaxResult.success("删除成功");
        } catch (Exception e) {
            return AjaxResult.error("删除自定义问题失败: " + e.getMessage());
        }
    }
}