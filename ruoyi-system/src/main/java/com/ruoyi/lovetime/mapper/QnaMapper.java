package com.ruoyi.lovetime.mapper;

import com.ruoyi.common.core.domain.lovetime.QnaQuestion;
import com.ruoyi.common.core.domain.lovetime.QnaAnswer;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 甜蜜问答Mapper接口
 * 
 * @author ruoyi
 * @date 2025-11-18
 */
public interface QnaMapper {
    
    /**
     * 查询预设问题（按orderIndex升序，仅返回激活的问题）
     * 
     * @return 预设问题列表
     */
    List<QnaQuestion> selectDefaultQuestions();
    
    /**
     * 查询用户自定义问题（按创建时间倒序，仅返回激活的问题）
     * 
     * @param userId 用户ID
     * @return 用户自定义问题列表
     */
    List<QnaQuestion> selectCustomQuestionsByUserId(@Param("userId") Long userId);
    
    /**
     * 根据ID查询问题
     * 
     * @param questionId 问题ID
     * @return 问题对象
     */
    QnaQuestion selectQuestionById(@Param("id") Long questionId);
    
    /**
     * 插入问题
     * 
     * @param question 问题对象
     * @return 插入结果
     */
    int insertQuestion(QnaQuestion question);
    
    /**
     * 更新问题
     * 
     * @param question 问题对象
     * @return 更新结果
     */
    int updateQuestion(QnaQuestion question);
    
    /**
     * 根据ID删除问题
     * 
     * @param questionId 问题ID
     * @return 删除结果
     */
    int deleteQuestionById(@Param("id") Long questionId);
    
    /**
     * 根据用户ID和问题ID查询答案
     * 
     * @param userId 用户ID
     * @param questionId 问题ID
     * @return 答案对象
     */
    QnaAnswer selectAnswerByUserAndQuestion(@Param("userId") Long userId, @Param("questionId") Long questionId);
    
    /**
     * 插入答案
     * 
     * @param answer 答案对象
     * @return 插入结果
     */
    int insertAnswer(QnaAnswer answer);
    
    /**
     * 根据用户ID查询答案数量
     * 
     * @param userId 用户ID
     * @return 答案数量
     */
    int countAnswersByUserId(@Param("userId") Long userId);
    
    /**
     * 根据用户ID查询答案列表（分页）
     * 
     * @param userId 用户ID
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 答案列表
     */
    List<QnaAnswer> selectAnswersByUserId(@Param("userId") Long userId, @Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * 根据问题ID查询答案数量
     * 
     * @param questionId 问题ID
     * @return 答案数量
     */
    int countAnswersByQuestionId(@Param("questionId") Long questionId);
}