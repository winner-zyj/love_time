package com.ruoyi.lovetime.mapper;

import com.ruoyi.common.core.domain.lovetime.ChallengeRecord;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 用户挑战记录Mapper接口
 * 
 * @author ruoyi
 * @date 2025-11-18
 */
public interface ChallengeRecordMapper {
    /**
     * 查询用户挑战记录列表
     * 
     * @param challengeRecord 用户挑战记录
     * @return 用户挑战记录集合
     */
    public List<ChallengeRecord> selectChallengeRecordList(ChallengeRecord challengeRecord);
    
    /**
     * 根据用户ID和任务ID查询挑战记录
     * 
     * @param userId 用户ID
     * @param taskId 任务ID
     * @return 用户挑战记录
     */
    public ChallengeRecord selectChallengeRecordByUserAndTask(@Param("userId") Long userId, @Param("taskId") Long taskId);
    
    /**
     * 根据ID查询用户挑战记录
     * 
     * @param id 用户挑战记录ID
     * @return 用户挑战记录
     */
    public ChallengeRecord selectChallengeRecordById(Long id);
    
    /**
     * 新增用户挑战记录
     * 
     * @param challengeRecord 用户挑战记录
     * @return 结果
     */
    public int insertChallengeRecord(ChallengeRecord challengeRecord);
    
    /**
     * 修改用户挑战记录
     * 
     * @param challengeRecord 用户挑战记录
     * @return 结果
     */
    public int updateChallengeRecord(ChallengeRecord challengeRecord);
    
    /**
     * 删除用户挑战记录
     * 
     * @param id 用户挑战记录ID
     * @return 结果
     */
    public int deleteChallengeRecordById(Long id);
    
    /**
     * 批量删除用户挑战记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteChallengeRecordByIds(Long[] ids);
    
    /**
     * 统计用户完成的任务数量
     * 
     * @param userId 用户ID
     * @return 完成的任务数量
     */
    public int countCompletedTasksByUserId(@Param("userId") Long userId);
    
    /**
     * 统计用户收藏的任务数量
     * 
     * @param userId 用户ID
     * @return 收藏的任务数量
     */
    public int countFavoritedTasksByUserId(@Param("userId") Long userId);
    
    /**
     * 统计用户总任务数量（预设+自定义）
     * 
     * @param userId 用户ID
     * @return 总任务数量
     */
    public int countTotalTasksByUserId(@Param("userId") Long userId);
}