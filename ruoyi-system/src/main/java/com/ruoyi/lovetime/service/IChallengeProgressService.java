package com.ruoyi.lovetime.service;

import com.ruoyi.common.core.domain.lovetime.ChallengeProgress;

/**
 * 用户挑战进度Service接口
 * 
 * @author ruoyi
 * @date 2025-11-18
 */
public interface IChallengeProgressService {
    /**
     * 查询用户挑战进度
     * 
     * @param userId 用户ID
     * @return 用户挑战进度
     */
    public ChallengeProgress selectChallengeProgressByUserId(Long userId);
    
    /**
     * 查询用户挑战进度
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
     * 批量删除用户挑战进度
     * 
     * @param ids 需要删除的用户挑战进度ID
     * @return 结果
     */
    public int deleteChallengeProgressByIds(Long[] ids);
    
    /**
     * 删除用户挑战进度信息
     * 
     * @param id 用户挑战进度ID
     * @return 结果
     */
    public int deleteChallengeProgressById(Long id);
    
    /**
     * 更新用户挑战进度
     * 
     * @param userId 用户ID
     * @return 用户挑战进度
     */
    public ChallengeProgress updateChallengeProgressByUserId(Long userId);
}