package com.ruoyi.common.core.domain.lovetime;

import java.util.Date;

/**
 * 甜蜜问答答案实体类
 * 
 * @author ruoyi
 * @date 2025-11-18
 */
public class QnaAnswer {
    /** 答案ID */
    private Long id;
    
    /** 问题ID */
    private Long questionId;
    
    /** 问题文本（冗余字段） */
    private String questionText;
    
    /** 用户ID */
    private Long userId;
    
    /** 答案内容 */
    private String answer;
    
    /** 回答时间 */
    private Date answeredAt;
    
    /** 更新时间 */
    private Date updatedAt;
    
    /** 问题信息 */
    private QnaQuestion question;
    
    /** 用户信息 */
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
    
    public String getQuestionText() {
        return questionText;
    }
    
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getAnsweredAt() {
        return answeredAt;
    }

    public void setAnsweredAt(Date answeredAt) {
        this.answeredAt = answeredAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public QnaQuestion getQuestion() {
        return question;
    }
    
    public void setQuestion(QnaQuestion question) {
        this.question = question;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
}