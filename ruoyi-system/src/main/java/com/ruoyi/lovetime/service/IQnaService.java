package com.ruoyi.lovetime.service;

import com.ruoyi.common.core.domain.lovetime.QnaQuestion;
import com.ruoyi.common.core.domain.lovetime.QnaAnswer;
import java.util.List;
import java.util.Map;

/**
 * 甜蜜问答服务接口
 * 
 * @author ruoyi
 * @date 2025-11-18
 */
public interface IQnaService {
    
    /**
     * 获取问题列表（包括预设问题和用户自定义问题）
     * 
     * @param userId 用户ID
     * @return 问题列表Map，包含defaultQuestions和customQuestions
     */
    Map<String, List<QnaQuestion>> getQuestions(Long userId);
    
    /**
     * 提交答案
     * 
     * @param answer 答案对象
     * @param userId 用户ID
     * @return 提交的答案信息
     */
    QnaAnswer submitAnswer(QnaAnswer answer, Long userId);
    
    /**
     * 获取历史回答
     * 
     * @param userId 用户ID
     * @param page 页码
     * @param pageSize 每页大小
     * @return 历史回答列表和分页信息
     */
    Map<String, Object> getHistory(Long userId, Integer page, Integer pageSize);
    
    /**
     * 获取对方答案
     * 
     * @param questionId 问题ID
     * @param userId 当前用户ID
     * @return 对方答案信息
     */
    Map<String, Object> getPartnerAnswer(Long questionId, Long userId);
    
    /**
     * 添加自定义问题
     * 
     * @param questionText 问题文本
     * @param userId 用户ID
     * @return 添加的问题对象
     */
    QnaQuestion addCustomQuestion(String questionText, Long userId);
    
    /**
     * 删除自定义问题
     * 
     * @param questionId 问题ID
     * @param userId 用户ID
     * @return 删除结果
     */
    boolean deleteCustomQuestion(Long questionId, Long userId);
}