package com.ruoyi.lovetime.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.domain.lovetime.Anniversary;
import com.ruoyi.lovetime.mapper.AnniversaryMapper;
import com.ruoyi.lovetime.service.IAnniversaryService;

/**
 * 纪念日Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-11-28
 */
@Service
public class AnniversaryServiceImpl implements IAnniversaryService {
    
    @Autowired
    private AnniversaryMapper anniversaryMapper;
    
    /**
     * 根据ID查询纪念日
     * 
     * @param id 纪念日ID
     * @return 纪念日
     */
    @Override
    public Anniversary selectAnniversaryById(Long id) {
        return anniversaryMapper.selectAnniversaryById(id);
    }
    
    /**
     * 根据用户ID查询纪念日列表
     * 
     * @param userId 用户ID
     * @return 纪念日集合
     */
    @Override
    public List<Anniversary> selectAnniversaryListByUserId(Long userId) {
        return anniversaryMapper.selectAnniversaryListByUserId(userId);
    }
    
    /**
     * 新增纪念日
     * 
     * @param anniversary 纪念日
     * @return 结果
     */
    @Override
    public int insertAnniversary(Anniversary anniversary) {
        anniversary.setCreateTime(new Date());
        anniversary.setUpdateTime(new Date());
        return anniversaryMapper.insertAnniversary(anniversary);
    }
    
    /**
     * 修改纪念日
     * 
     * @param anniversary 纪念日
     * @return 结果
     */
    @Override
    public int updateAnniversary(Anniversary anniversary) {
        anniversary.setUpdateTime(new Date());
        return anniversaryMapper.updateAnniversary(anniversary);
    }
    
    /**
     * 删除纪念日
     * 
     * @param id 纪念日ID
     * @return 结果
     */
    @Override
    public int deleteAnniversaryById(Long id) {
        return anniversaryMapper.deleteAnniversaryById(id);
    }
    
    /**
     * 批量删除纪念日
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAnniversaryByIds(Long[] ids) {
        return anniversaryMapper.deleteAnniversaryByIds(ids);
    }
}