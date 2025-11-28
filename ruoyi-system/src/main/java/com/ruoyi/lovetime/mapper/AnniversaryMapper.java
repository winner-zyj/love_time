package com.ruoyi.lovetime.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.common.core.domain.lovetime.Anniversary;

/**
 * 纪念日Mapper接口
 * 
 * @author ruoyi
 * @date 2025-11-28
 */
public interface AnniversaryMapper {
    
    /**
     * 根据ID查询纪念日
     * 
     * @param id 纪念日ID
     * @return 纪念日
     */
    public Anniversary selectAnniversaryById(Long id);
    
    /**
     * 根据用户ID查询纪念日列表
     * 
     * @param userId 用户ID
     * @return 纪念日集合
     */
    public List<Anniversary> selectAnniversaryListByUserId(Long userId);
    
    /**
     * 新增纪念日
     * 
     * @param anniversary 纪念日
     * @return 结果
     */
    public int insertAnniversary(Anniversary anniversary);
    
    /**
     * 修改纪念日
     * 
     * @param anniversary 纪念日
     * @return 结果
     */
    public int updateAnniversary(Anniversary anniversary);
    
    /**
     * 删除纪念日
     * 
     * @param id 纪念日ID
     * @return 结果
     */
    public int deleteAnniversaryById(Long id);
    
    /**
     * 批量删除纪念日
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAnniversaryByIds(Long[] ids);
}