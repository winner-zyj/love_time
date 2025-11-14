package com.ruoyi.lovetime.service;

import com.ruoyi.common.core.domain.lovetime.CoupleRelationship;
import java.util.List;

/**
 * 情侣关系Service接口
 * 
 * @author ruoyi
 * @date 2025-11-14
 */
public interface ICoupleRelationshipService 
{
    /**
     * 查询情侣关系
     * 
     * @param id 关系ID
     * @return 情侣关系
     */
    public CoupleRelationship selectCoupleRelationshipById(Long id);

    /**
     * 根据用户ID查询情侣关系
     * 
     * @param userId 用户ID
     * @return 情侣关系
     */
    public CoupleRelationship selectCoupleRelationshipByUserId(Long userId);

    /**
     * 查询情侣关系列表
     * 
     * @param coupleRelationship 情侣关系
     * @return 情侣关系集合
     */
    public List<CoupleRelationship> selectCoupleRelationshipList(CoupleRelationship coupleRelationship);

    /**
     * 新增情侣关系
     * 
     * @param coupleRelationship 情侣关系
     * @return 结果
     */
    public int insertCoupleRelationship(CoupleRelationship coupleRelationship);

    /**
     * 修改情侣关系
     * 
     * @param coupleRelationship 情侣关系
     * @return 结果
     */
    public int updateCoupleRelationship(CoupleRelationship coupleRelationship);

    /**
     * 批量删除情侣关系
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCoupleRelationshipByIds(Long[] ids);

    /**
     * 删除情侣关系信息
     * 
     * @param id 关系ID
     * @return 结果
     */
    public int deleteCoupleRelationshipById(Long id);
}