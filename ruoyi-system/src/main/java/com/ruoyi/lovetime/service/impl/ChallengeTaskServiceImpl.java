package com.ruoyi.lovetime.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.lovetime.mapper.ChallengeTaskMapper;
import com.ruoyi.lovetime.service.IChallengeTaskService;
import com.ruoyi.common.core.domain.lovetime.ChallengeTask;

/**
 * 挑战任务Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-11-18
 */
@Service
public class ChallengeTaskServiceImpl implements IChallengeTaskService {
    @Autowired
    private ChallengeTaskMapper challengeTaskMapper;
    
    /**
     * 查询挑战任务
     * 
     * @param id 挑战任务ID
     * @return 挑战任务
     */
    @Override
    public ChallengeTask selectChallengeTaskById(Long id) {
        return challengeTaskMapper.selectChallengeTaskById(id);
    }
    
    /**
     * 查询挑战任务列表
     * 
     * @param challengeTask 挑战任务
     * @return 挑战任务
     */
    @Override
    public List<ChallengeTask> selectChallengeTaskList(ChallengeTask challengeTask) {
        return challengeTaskMapper.selectChallengeTaskList(challengeTask);
    }
    
    /**
     * 查询所有任务列表（预设按taskIndex升序，自定义按创建时间倒序）
     * 
     * @param userId 用户ID
     * @return 挑战任务集合
     */
    @Override
    public List<ChallengeTask> selectAllChallengeTasks(Long userId) {
        // 查询预设任务（按taskIndex升序）
        List<ChallengeTask> presetTasks = challengeTaskMapper.selectPresetChallengeTasks();
        
        // 查询用户自定义任务（按创建时间倒序）
        List<ChallengeTask> customTasks = challengeTaskMapper.selectCustomChallengeTasksByUserId(userId);
        
        // 合并两个列表
        presetTasks.addAll(customTasks);
        
        return presetTasks;
    }
    
    /**
     * 新增挑战任务
     * 
     * @param challengeTask 挑战任务
     * @return 结果
     */
    @Override
    public int insertChallengeTask(ChallengeTask challengeTask) {
        return challengeTaskMapper.insertChallengeTask(challengeTask);
    }
    
    /**
     * 修改挑战任务
     * 
     * @param challengeTask 挑战任务
     * @return 结果
     */
    @Override
    public int updateChallengeTask(ChallengeTask challengeTask) {
        return challengeTaskMapper.updateChallengeTask(challengeTask);
    }
    
    /**
     * 批量删除挑战任务
     * 
     * @param ids 需要删除的挑战任务ID
     * @return 结果
     */
    @Override
    public int deleteChallengeTaskByIds(Long[] ids) {
        return challengeTaskMapper.deleteChallengeTaskByIds(ids);
    }
    
    /**
     * 删除挑战任务信息
     * 
     * @param id 挑战任务ID
     * @return 结果
     */
    @Override
    public int deleteChallengeTaskById(Long id) {
        return challengeTaskMapper.deleteChallengeTaskById(id);
    }
}