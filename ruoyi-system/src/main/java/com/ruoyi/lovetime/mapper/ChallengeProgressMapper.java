package com.ruoyi.lovetime.mapper;

import com.ruoyi.common.core.domain.lovetime.ChallengeProgress;
import org.apache.ibatis.annotations.Param;

/**
 * 用户挑战进度Mapper接口
 * 
 * @author ruoyi
 * @date 2025-11-18
 */
public interface ChallengeProgressMapper {
    /**
     * 查询用户挑战进度
     * 
     * @param userId 用户ID
     * @return 用户挑战进度
     */
    public ChallengeProgress selectChallengeProgressByUserId(@Param("userId") Long userId);
    
    /**
     * 根据ID查询用户挑战进度
     * 
     * @param id 用户挑战进度ID
     * @return 用户挑战进度
     */
    public ChallengeProgress selectChallengeProgressById(Long id);
    
    /**
     * 新增用户挑战进度
     * 
     * @param challengeProgress 用户挑战进度
     * @return 结果
     */
    public int insertChallengeProgress(ChallengeProgress challengeProgress);
    
    /**
     * 修改用户挑战进度
     * 
     * @param challengeProgress 用户挑战进度
     * @return 结果
     */
    public int updateChallengeProgress(ChallengeProgress challengeProgress);
    
    /**
     * 删除用户挑战进度
     * 
     * @param id 用户挑战进度ID
     * @return 结果
     */
    public int deleteChallengeProgressById(Long id);
    
    /**
     * 批量删除用户挑战进度
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteChallengeProgressByIds(Long[] ids);
}