package com.ruoyi.lovetime.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.lovetime.mapper.ChallengeProgressMapper;
import com.ruoyi.lovetime.mapper.ChallengeRecordMapper;
import com.ruoyi.lovetime.service.IChallengeProgressService;
import com.ruoyi.common.core.domain.lovetime.ChallengeProgress;

/**
 * 用户挑战进度Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-11-18
 */
@Service
public class ChallengeProgressServiceImpl implements IChallengeProgressService {
    @Autowired
    private ChallengeProgressMapper challengeProgressMapper;
    
    @Autowired
    private ChallengeRecordMapper challengeRecordMapper;
    
    /**
     * 查询用户挑战进度
     * 
     * @param userId 用户ID
     * @return 用户挑战进度
     */
    @Override
    public ChallengeProgress selectChallengeProgressByUserId(Long userId) {
        return challengeProgressMapper.selectChallengeProgressByUserId(userId);
    }
    
    /**
     * 查询用户挑战进度
     * 
     * @param id 用户挑战进度ID
     * @return 用户挑战进度
     */
    @Override
    public ChallengeProgress selectChallengeProgressById(Long id) {
        return challengeProgressMapper.selectChallengeProgressById(id);
    }
    
    /**
     * 新增用户挑战进度
     * 
     * @param challengeProgress 用户挑战进度
     * @return 结果
     */
    @Override
    public int insertChallengeProgress(ChallengeProgress challengeProgress) {
        return challengeProgressMapper.insertChallengeProgress(challengeProgress);
    }
    
    /**
     * 修改用户挑战进度
     * 
     * @param challengeProgress 用户挑战进度
     * @return 结果
     */
    @Override
    public int updateChallengeProgress(ChallengeProgress challengeProgress) {
        return challengeProgressMapper.updateChallengeProgress(challengeProgress);
    }
    
    /**
     * 批量删除用户挑战进度
     * 
     * @param ids 需要删除的用户挑战进度ID
     * @return 结果
     */
    @Override
    public int deleteChallengeProgressByIds(Long[] ids) {
        return challengeProgressMapper.deleteChallengeProgressByIds(ids);
    }
    
    /**
     * 删除用户挑战进度信息
     * 
     * @param id 用户挑战进度ID
     * @return 结果
     */
    @Override
    public int deleteChallengeProgressById(Long id) {
        return challengeProgressMapper.deleteChallengeProgressById(id);
    }
    
    /**
     * 更新用户挑战进度
     * 
     * @param userId 用户ID
     * @return 用户挑战进度
     */
    @Override
    public ChallengeProgress updateChallengeProgressByUserId(Long userId) {
        // 统计用户完成的任务数量
        int completedCount = challengeRecordMapper.countCompletedTasksByUserId(userId);
        
        // 统计用户收藏的任务数量
        int favoritedCount = challengeRecordMapper.countFavoritedTasksByUserId(userId);
        
        // 统计用户总任务数量（预设+自定义）
        int totalTasks = challengeRecordMapper.countTotalTasksByUserId(userId);
        
        // 计算完成率（百分比，小数一位）
        double completionRate = 0.0;
        if (totalTasks > 0) {
            completionRate = new BigDecimal((double) completedCount / totalTasks * 100)
                .setScale(1, RoundingMode.HALF_UP).doubleValue();
        }
        
        // 查询用户现有的进度记录
        ChallengeProgress progress = challengeProgressMapper.selectChallengeProgressByUserId(userId);
        
        if (progress == null) {
            // 如果没有进度记录，则创建新记录
            progress = new ChallengeProgress();
            progress.setUserId(userId);
            progress.setTotalTasks(totalTasks);
            progress.setCompletedCount(completedCount);
            progress.setFavoritedCount(favoritedCount);
            progress.setCompletionRate(completionRate);
            progress.setLastActiveAt(new Date());
            progress.setCreatedAt(new Date());
            challengeProgressMapper.insertChallengeProgress(progress);
        } else {
            // 如果已有进度记录，则更新记录
            progress.setTotalTasks(totalTasks);
            progress.setCompletedCount(completedCount);
            progress.setFavoritedCount(favoritedCount);
            progress.setCompletionRate(completionRate);
            progress.setLastActiveAt(new Date());
            challengeProgressMapper.updateChallengeProgress(progress);
        }
        
        return progress;
    }
}