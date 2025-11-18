package com.ruoyi.lovetime.service;

import com.ruoyi.common.core.domain.lovetime.ChallengeTask;
import java.util.List;

/**
 * 挑战任务Service接口
 * 
 * @author ruoyi
 * @date 2025-11-18
 */
public interface IChallengeTaskService {
    /**
     * 查询挑战任务
     * 
     * @param id 挑战任务ID
     * @return 挑战任务
     */
    public ChallengeTask selectChallengeTaskById(Long id);
    
    /**
     * 查询挑战任务列表
     * 
     * @param challengeTask 挑战任务
     * @return 挑战任务集合
     */
    public List<ChallengeTask> selectChallengeTaskList(ChallengeTask challengeTask);
    
    /**
     * 查询所有任务列表（预设按taskIndex升序，自定义按创建时间倒序）
     * 
     * @param userId 用户ID
     * @return 挑战任务集合
     */
    public List<ChallengeTask> selectAllChallengeTasks(Long userId);
    
    /**
     * 新增挑战任务
     * 
     * @param challengeTask 挑战任务
     * @return 结果
     */
    public int insertChallengeTask(ChallengeTask challengeTask);
    
    /**
     * 修改挑战任务
     * 
     * @param challengeTask 挑战任务
     * @return 结果
     */
    public int updateChallengeTask(ChallengeTask challengeTask);
    
    /**
     * 批量删除挑战任务
     * 
     * @param ids 需要删除的挑战任务ID
     * @return 结果
     */
    public int deleteChallengeTaskByIds(Long[] ids);
    
    /**
     * 删除挑战任务信息
     * 
     * @param id 挑战任务ID
     * @return 结果
     */
    public int deleteChallengeTaskById(Long id);
}