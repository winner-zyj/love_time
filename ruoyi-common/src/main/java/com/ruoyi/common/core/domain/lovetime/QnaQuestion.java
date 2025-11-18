package com.ruoyi.common.core.domain.lovetime;

import java.util.Date;

/**
 * 甜蜜问答问题实体类
 * 
 * @author ruoyi
 * @date 2025-11-18
 */
public class QnaQuestion {
    /** 问题ID */
    private Long id;
    
    /** 问题文本 */
    private String text;
    
    /** 问题文本（冗余字段） */
    private String questionText;
    
    /** 是否为预设问题 */
    private String isDefault;
    
    /** 用户ID（自定义问题关联的用户） */
    private Long userId;
    
    /** 排序索引 */
    private Integer orderIndex;
    
    /** 是否激活 */
    private Boolean isActive;
    
    /** 创建时间 */
    private Date createdAt;
    
    /** 更新时间 */
    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    public String getQuestionText() {
        return questionText;
    }
    
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    /**
     * 判断是否为预设问题
     * @return true表示预设问题，false表示自定义问题
     */
    public boolean isPreset() {
        return "preset".equals(this.isDefault);
    }
    
    /**
     * 判断是否为自定义问题
     * @return true表示自定义问题，false表示预设问题
     */
    public boolean isCustom() {
        return "custom".equals(this.isDefault);
    }
}