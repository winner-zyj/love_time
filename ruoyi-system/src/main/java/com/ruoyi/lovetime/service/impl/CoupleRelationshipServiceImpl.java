package com.ruoyi.lovetime.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        return coupleRelationshipMapper.selectCoupleRelationshipById(id);
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
        return coupleRelationshipMapper.selectCoupleRelationshipByUserId(userId);
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
        return coupleRelationshipMapper.selectCoupleRelationshipList(coupleRelationship);
    }

    /**
     * 新增情侣关系
     * 
     * @param coupleRelationship 情侣关系
     * @return 结果
     */
    @Override
    public int insertCoupleRelationship(CoupleRelationship coupleRelationship)
    {
        return coupleRelationshipMapper.insertCoupleRelationship(coupleRelationship);
    }

    /**
     * 修改情侣关系
     * 
     * @param coupleRelationship 情侣关系
     * @return 结果
     */
    @Override
    public int updateCoupleRelationship(CoupleRelationship coupleRelationship)
    {
        return coupleRelationshipMapper.updateCoupleRelationship(coupleRelationship);
    }

    /**
     * 批量删除情侣关系
     * 
     * @param ids 需要删除的情侣关系ID
     * @return 结果
     */
    @Override
    public int deleteCoupleRelationshipByIds(Long[] ids)
    {
        return coupleRelationshipMapper.deleteCoupleRelationshipByIds(ids);
    }

    /**
     * 删除情侣关系信息
     * 
     * @param id 情侣关系ID
     * @return 结果
     */
    @Override
    public int deleteCoupleRelationshipById(Long id)
    {
        return coupleRelationshipMapper.deleteCoupleRelationshipById(id);
    }
}