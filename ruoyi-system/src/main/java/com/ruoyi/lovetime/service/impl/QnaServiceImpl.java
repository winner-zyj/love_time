package com.ruoyi.lovetime.service.impl;

import com.ruoyi.lovetime.service.IQnaService;
import com.ruoyi.common.core.domain.lovetime.QnaQuestion;
import com.ruoyi.common.core.domain.lovetime.QnaAnswer;
import com.ruoyi.lovetime.mapper.QnaMapper;
import com.ruoyi.lovetime.service.ICoupleRelationshipService;
import com.ruoyi.common.core.domain.lovetime.CoupleRelationship;
import com.ruoyi.common.core.domain.lovetime.User;
import com.ruoyi.lovetime.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;

/**
 * 甜蜜问答服务实现类
 * 
 * @author ruoyi
 * @date 2025-11-18
 */
@Service
public class QnaServiceImpl implements IQnaService {
    
    @Autowired
    private QnaMapper qnaMapper;
    
    @Autowired
    private ICoupleRelationshipService coupleRelationshipService;
    
    @Autowired
    private IUserService userService;
    
    /**
     * 获取问题列表（包括预设问题和用户自定义问题）
     * 
     * @param userId 用户ID
     * @return 问题列表Map，包含defaultQuestions和customQuestions
     */
    @Override
    public Map<String, List<QnaQuestion>> getQuestions(Long userId) {
        Map<String, List<QnaQuestion>> result = new HashMap<>();
        
        // 获取预设问题（按orderIndex升序）
        List<QnaQuestion> defaultQuestions = qnaMapper.selectDefaultQuestions();
        result.put("defaultQuestions", defaultQuestions);
        
        // 获取用户自定义问题（按创建时间倒序）
        List<QnaQuestion> customQuestions = qnaMapper.selectCustomQuestionsByUserId(userId);
        result.put("customQuestions", customQuestions);
        
        return result;
    }
    
    /**
     * 提交答案
     * 
     * @param answer 答案对象
     * @param userId 用户ID
     * @return 提交的答案信息
     */
    @Override
    @Transactional
    public QnaAnswer submitAnswer(QnaAnswer answer, Long userId) {
        // 验证用户是否已绑定情侣关系
        CoupleRelationship relationship = coupleRelationshipService.selectCoupleRelationshipByUserId(userId);
        if (relationship == null) {
            throw new RuntimeException("未绑定情侣关系");
        }
        
        // 检查问题是否存在且未被当前用户重复回答
        QnaQuestion question = qnaMapper.selectQuestionById(answer.getQuestionId());
        if (question == null) {
            throw new RuntimeException("问题不存在");
        }
        
        // 检查用户是否已回答过该问题
        QnaAnswer existingAnswer = qnaMapper.selectAnswerByUserAndQuestion(userId, answer.getQuestionId());
        if (existingAnswer != null) {
            throw new RuntimeException("您已回答过该问题");
        }
        
        // 检查答案是否为空
        if (answer.getAnswer() == null || answer.getAnswer().trim().isEmpty()) {
            throw new RuntimeException("答案不能为空");
        }
        
        // 检查答案长度（建议≤1000字符）
        if (answer.getAnswer().length() > 1000) {
            throw new RuntimeException("答案长度不能超过1000个字符");
        }
        
        // 设置用户ID和回答时间
        answer.setUserId(userId);
        answer.setAnsweredAt(new Date());
        answer.setUpdatedAt(new Date());
        
        // 如果questionText为空，从问题中获取
        if (answer.getQuestionText() == null || answer.getQuestionText().trim().isEmpty()) {
            answer.setQuestionText(question.getText());
        }
        
        // 插入答案
        qnaMapper.insertAnswer(answer);
        
        // 设置返回信息
        answer.setQuestion(question);
        
        return answer;
    }
    
    /**
     * 获取历史回答
     * 
     * @param userId 用户ID
     * @param page 页码
     * @param pageSize 每页大小
     * @return 历史回答列表和分页信息
     */
    @Override
    public Map<String, Object> getHistory(Long userId, Integer page, Integer pageSize) {
        // 设置默认分页参数
        if (page == null || page <= 0) {
            page = 1;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = 20;
        }
        // 限制最大pageSize
        if (pageSize > 100) {
            pageSize = 100;
        }
        
        // 计算偏移量
        int offset = (page - 1) * pageSize;
        
        // 查询总数
        int total = qnaMapper.countAnswersByUserId(userId);
        
        // 查询列表
        List<QnaAnswer> list = qnaMapper.selectAnswersByUserId(userId, offset, pageSize);
        
        // 构造返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("total", total);
        result.put("page", page);
        result.put("pageSize", pageSize);
        result.put("list", list);
        
        return result;
    }
    
    /**
     * 获取对方答案
     * 
     * @param questionId 问题ID
     * @param userId 当前用户ID
     * @return 对方答案信息
     */
    @Override
    public Map<String, Object> getPartnerAnswer(Long questionId, Long userId) {
        Map<String, Object> result = new HashMap<>();
        
        // 验证用户是否已绑定情侣关系
        CoupleRelationship relationship = coupleRelationshipService.selectCoupleRelationshipByUserId(userId);
        if (relationship == null) {
            throw new RuntimeException("未绑定情侣关系");
        }
        
        // 获取对方ID
        Long partnerId = getPartnerId(userId, relationship);
        
        // 查询对方答案
        QnaAnswer partnerAnswer = qnaMapper.selectAnswerByUserAndQuestion(partnerId, questionId);
        
        if (partnerAnswer != null) {
            result.put("hasAnswered", true);
            result.put("answer", partnerAnswer.getAnswer());
            result.put("answeredAt", partnerAnswer.getAnsweredAt());
        } else {
            result.put("hasAnswered", false);
            result.put("answer", null);
            result.put("answeredAt", null);
        }
        
        return result;
    }
    
    /**
     * 添加自定义问题
     * 
     * @param questionText 问题文本
     * @param userId 用户ID
     * @return 添加的问题对象
     */
    @Override
    @Transactional
    public QnaQuestion addCustomQuestion(String questionText, Long userId) {
        // 验证用户是否已绑定情侣关系
        CoupleRelationship relationship = coupleRelationshipService.selectCoupleRelationshipByUserId(userId);
        if (relationship == null) {
            throw new RuntimeException("未绑定情侣关系");
        }
        
        // 检查问题文本是否为空
        if (questionText == null || questionText.trim().isEmpty()) {
            throw new RuntimeException("问题内容不能为空");
        }
        
        // 检查问题长度（建议≤100字符）
        if (questionText.length() > 100) {
            throw new RuntimeException("问题内容长度不能超过100个字符");
        }
        
        // 创建问题对象
        QnaQuestion question = new QnaQuestion();
        question.setText(questionText);
        question.setIsDefault("custom"); // 自定义问题
        question.setUserId(userId);
        question.setOrderIndex(999); // 自定义问题默认排序
        question.setIsActive(true);
        question.setCreatedAt(new Date());
        question.setUpdatedAt(new Date());
        
        // 插入问题
        qnaMapper.insertQuestion(question);
        
        return question;
    }
    
    /**
     * 删除自定义问题
     * 
     * @param questionId 问题ID
     * @param userId 用户ID
     * @return 删除结果
     */
    @Override
    @Transactional
    public boolean deleteCustomQuestion(Long questionId, Long userId) {
        // 查询问题
        QnaQuestion question = qnaMapper.selectQuestionById(questionId);
        if (question == null) {
            throw new RuntimeException("问题不存在");
        }
        
        // 检查是否为预设问题
        if (question.isPreset()) {
            throw new RuntimeException("不能删除预设问题");
        }
        
        // 检查是否为当前用户创建的问题
        if (!question.getUserId().equals(userId)) {
            throw new RuntimeException("只能删除自己创建的问题");
        }
        
        // 检查问题是否已有回答
        int answerCount = qnaMapper.countAnswersByQuestionId(questionId);
        if (answerCount > 0) {
            // 软删除：设置为非激活状态
            question.setIsActive(false);
            question.setUpdatedAt(new Date());
            qnaMapper.updateQuestion(question);
        } else {
            // 直接删除
            qnaMapper.deleteQuestionById(questionId);
        }
        
        return true;
    }
    
    /**
     * 获取情侣关系中的对方ID
     * 
     * @param userId 当前用户ID
     * @param relationship 情侣关系
     * @return 对方用户ID
     */
    private Long getPartnerId(Long userId, CoupleRelationship relationship) {
        if (relationship.getUser1Id().equals(userId)) {
            return relationship.getUser2Id();
        } else {
            return relationship.getUser1Id();
        }
    }
}