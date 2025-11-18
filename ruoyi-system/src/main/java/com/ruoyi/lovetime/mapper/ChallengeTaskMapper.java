package com.ruoyi.lovetime.mapper;

import com.ruoyi.common.core.domain.lovetime.ChallengeTask;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 挑战任务Mapper接口
 * 
 * @author ruoyi
 * @date 2025-11-18
 */
public interface ChallengeTaskMapper {
    /**
     * 查询挑战任务列表
     * 
     * @param challengeTask 挑战任务
     * @return 挑战任务集合
     */
    public List<ChallengeTask> selectChallengeTaskList(ChallengeTask challengeTask);
    
    /**
     * 查询预设挑战任务列表（按taskIndex升序）
     * 
     * @return 挑战任务集合
     */
    public List<ChallengeTask> selectPresetChallengeTasks();
    
    /**
     * 查询用户自定义挑战任务列表（按创建时间倒序）
     * 
     * @param userId 用户ID
     * @return 挑战任务集合
     */
    public List<ChallengeTask> selectCustomChallengeTasksByUserId(@Param("userId") Long userId);
    
    /**
     * 根据ID查询挑战任务
     * 
     * @param id 挑战任务ID
     * @return 挑战任务
     */
    public ChallengeTask selectChallengeTaskById(Long id);
    
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
     * 删除挑战任务
     * 
     * @param id 挑战任务ID
     * @return 结果
     */
    public int deleteChallengeTaskById(Long id);
    
    /**
     * 批量删除挑战任务
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteChallengeTaskByIds(Long[] ids);
}