package com.ruoyi.lovetime.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.lovetime.mapper.CoupleRelationshipMapper;
import com.ruoyi.common.core.domain.lovetime.CoupleRelationship;
import com.ruoyi.lovetime.service.ICoupleRelationshipService;

/**
 * 情侣关系Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-11-14
 */
@Service
public class CoupleRelationshipServiceImpl implements ICoupleRelationshipService
{
    private static final Logger log = LoggerFactory.getLogger(CoupleRelationshipServiceImpl.class);
    
    @Autowired
    private CoupleRelationshipMapper coupleRelationshipMapper;

    /**
     * 查询情侣关系
     * 
     * @param id 关系ID
     * @return 情侣关系
     */
    @Override
    public CoupleRelationship selectCoupleRelationshipById(Long id)
    {
        log.info("查询情侣关系，ID: {}", id);
        CoupleRelationship relationship = coupleRelationshipMapper.selectCoupleRelationshipById(id);
        log.info("查询情侣关系结果: {}", relationship != null ? "找到" : "未找到");
        return relationship;
    }

    /**
     * 根据用户ID查询情侣关系
     * 
     * @param userId 用户ID
     * @return 情侣关系
     */
    @Override
    public CoupleRelationship selectCoupleRelationshipByUserId(Long userId)
    {
        log.info("根据用户ID查询情侣关系，用户ID: {}", userId);
        CoupleRelationship relationship = coupleRelationshipMapper.selectCoupleRelationshipByUserId(userId);
        log.info("根据用户ID查询情侣关系结果: {}", relationship != null ? "找到" : "未找到");
        return relationship;
    }

    /**
     * 查询情侣关系列表
     * 
     * @param coupleRelationship 情侣关系
     * @return 情侣关系
     */
    @Override
    public List<CoupleRelationship> selectCoupleRelationshipList(CoupleRelationship coupleRelationship)
    {
        log.info("查询情侣关系列表");
        List<CoupleRelationship> relationships = coupleRelationshipMapper.selectCoupleRelationshipList(coupleRelationship);
        log.info("查询情侣关系列表结果，数量: {}", relationships != null ? relationships.size() : 0);
        return relationships;
    }

    /**
     * 新增情侣关系
     * 
     * @param coupleRelationship 情侣关系
     * @return 结果
     */
    @Override
    @Transactional
    public int insertCoupleRelationship(CoupleRelationship coupleRelationship)
    {
        log.info("新增情侣关系: user1Id={}, user2Id={}, status={}, initiatorId={}, receiverId={}",
            coupleRelationship.getUser1Id(), coupleRelationship.getUser2Id(),
            coupleRelationship.getStatus(), coupleRelationship.getInitiatorId(),
            coupleRelationship.getReceiverId());
        
        int result = coupleRelationshipMapper.insertCoupleRelationship(coupleRelationship);
        
        log.info("新增情侣关系结果: {}, 生成的ID: {}", result, coupleRelationship.getId());
        return result;
    }

    /**
     * 修改情侣关系
     * 
     * @param coupleRelationship 情侣关系
     * @return 结果
     */
    @Override
    @Transactional
    public int updateCoupleRelationship(CoupleRelationship coupleRelationship)
    {
        log.info("修改情侣关系，ID: {}, coupleId: {}", coupleRelationship.getId(), coupleRelationship.getCoupleId());
        
        int result = coupleRelationshipMapper.updateCoupleRelationship(coupleRelationship);
        
        log.info("修改情侣关系结果: {}", result);
        return result;
    }

    /**
     * 批量删除情侣关系
     * 
     * @param ids 需要删除的情侣关系ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteCoupleRelationshipByIds(Long[] ids)
    {
        log.info("批量删除情侣关系，IDs数量: {}", ids != null ? ids.length : 0);
        
        int result = coupleRelationshipMapper.deleteCoupleRelationshipByIds(ids);
        
        log.info("批量删除情侣关系结果: {}", result);
        return result;
    }

    /**
     * 删除情侣关系信息
     * 
     * @param id 情侣关系ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteCoupleRelationshipById(Long id)
    {
        log.info("删除情侣关系，ID: {}", id);
        
        int result = coupleRelationshipMapper.deleteCoupleRelationshipById(id);
        
        log.info("删除情侣关系结果: {}", result);
        return result;
    }
}