package com.ruoyi.lovetime.service;

import com.ruoyi.common.core.domain.lovetime.ChallengeRecord;
import java.util.List;

/**
 * 用户挑战记录Service接口
 * 
 * @author ruoyi
 * @date 2025-11-18
 */
public interface IChallengeRecordService {
    /**
     * 查询用户挑战记录
     * 
     * @param id 用户挑战记录ID
     * @return 用户挑战记录
     */
    public ChallengeRecord selectChallengeRecordById(Long id);
    
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
    public ChallengeRecord selectChallengeRecordByUserAndTask(Long userId, Long taskId);
    
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
     * 批量删除用户挑战记录
     * 
     * @param ids 需要删除的用户挑战记录ID
     * @return 结果
     */
    public int deleteChallengeRecordByIds(Long[] ids);
    
    /**
     * 删除用户挑战记录信息
     * 
     * @param id 用户挑战记录ID
     * @return 结果
     */
    public int deleteChallengeRecordById(Long id);
    
    /**
     * 标记任务完成/取消
     * 
     * @param userId 用户ID
     * @param taskId 任务ID
     * @param completed 是否完成
     * @param photoUrl 照片URL（可选）
     * @param note 备注（可选）
     * @return 用户挑战记录
     */
    public ChallengeRecord completeTask(Long userId, Long taskId, Boolean completed, String photoUrl, String note);
    
    /**
     * 收藏/取消收藏任务
     * 
     * @param userId 用户ID
     * @param taskId 任务ID
     * @param favorited 是否收藏
     * @return 用户挑战记录
     */
    public ChallengeRecord favoriteTask(Long userId, Long taskId, Boolean favorited);
}