package com.ruoyi.common.core.domain.lovetime;

import java.util.Date;

/**
 * 情侣关系对象 at_couple_relationships
 * 
 * @author ruoyi
 * @date 2025-11-14
 */
public class CoupleRelationship {
    private static final long serialVersionUID = 1L;

    /** 关系ID */
    private Long id;

    /** 用户1 ID */
    private Long user1Id;

    /** 用户2 ID */
    private Long user2Id;

    /** 关系状态：待确认/已绑定/已拒绝/已解绑 */
    private String status;

    /** 发起绑定的用户ID */
    private Long initiatorId;

    /** 接收请求的用户ID */
    private Long receiverId;

    /** 关系昵称（如：我的宝贝） */
    private String relationshipName;

    /** 纪念日（恋爱开始日期） */
    private Date anniversaryDate;

    /** 绑定请求留言 */
    private String requestMessage;

    /** 情侣ID */
    private Long coupleId;

    /** 创建时间 */
    private Date createdAt;

    /** 确认绑定时间 */
    private Date confirmedAt;

    /** 拒绝时间 */
    private Date rejectedAt;

    /** 解绑时间 */
    private Date brokenAt;

    /** 更新时间 */
    private Date updatedAt;

    public CoupleRelationship() {
    }

    public CoupleRelationship(Long id, Long user1Id, Long user2Id, String status, Long initiatorId, Long receiverId, String relationshipName, Date anniversaryDate, String requestMessage, Long coupleId, Date createdAt, Date confirmedAt, Date rejectedAt, Date brokenAt, Date updatedAt) {
        this.id = id;
        this.user1Id = user1Id;
        this.user2Id = user2Id;
        this.status = status;
        this.initiatorId = initiatorId;
        this.receiverId = receiverId;
        this.relationshipName = relationshipName;
        this.anniversaryDate = anniversaryDate;
        this.requestMessage = requestMessage;
        this.coupleId = coupleId;
        this.createdAt = createdAt;
        this.confirmedAt = confirmedAt;
        this.rejectedAt = rejectedAt;
        this.brokenAt = brokenAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser1Id() {
        return user1Id;
    }

    public void setUser1Id(Long user1Id) {
        this.user1Id = user1Id;
    }

    public Long getUser2Id() {
        return user2Id;
    }

    public void setUser2Id(Long user2Id) {
        this.user2Id = user2Id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getInitiatorId() {
        return initiatorId;
    }

    public void setInitiatorId(Long initiatorId) {
        this.initiatorId = initiatorId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getRelationshipName() {
        return relationshipName;
    }

    public void setRelationshipName(String relationshipName) {
        this.relationshipName = relationshipName;
    }

    public Date getAnniversaryDate() {
        return anniversaryDate;
    }

    public void setAnniversaryDate(Date anniversaryDate) {
        this.anniversaryDate = anniversaryDate;
    }

    public String getRequestMessage() {
        return requestMessage;
    }

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }

    public Long getCoupleId() {
        return coupleId;
    }

    public void setCoupleId(Long coupleId) {
        this.coupleId = coupleId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getConfirmedAt() {
        return confirmedAt;
    }

    public void setConfirmedAt(Date confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    public Date getRejectedAt() {
        return rejectedAt;
    }

    public void setRejectedAt(Date rejectedAt) {
        this.rejectedAt = rejectedAt;
    }

    public Date getBrokenAt() {
        return brokenAt;
    }

    public void setBrokenAt(Date brokenAt) {
        this.brokenAt = brokenAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "CoupleRelationship{" +
                "id=" + id +
                ", user1Id=" + user1Id +
                ", user2Id=" + user2Id +
                ", status='" + status + '\'' +
                ", initiatorId=" + initiatorId +
                ", receiverId=" + receiverId +
                ", relationshipName='" + relationshipName + '\'' +
                ", anniversaryDate=" + anniversaryDate +
                ", requestMessage='" + requestMessage + '\'' +
                ", coupleId=" + coupleId +
                ", createdAt=" + createdAt +
                ", confirmedAt=" + confirmedAt +
                ", rejectedAt=" + rejectedAt +
                ", brokenAt=" + brokenAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}