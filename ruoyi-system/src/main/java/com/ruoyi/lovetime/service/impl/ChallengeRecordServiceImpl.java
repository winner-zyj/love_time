package com.ruoyi.lovetime.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruoyi.lovetime.mapper.ChallengeRecordMapper;
import com.ruoyi.lovetime.service.IChallengeRecordService;
import com.ruoyi.lovetime.service.IChallengeTaskService;
import com.ruoyi.common.core.domain.lovetime.ChallengeRecord;
import com.ruoyi.common.core.domain.lovetime.ChallengeTask;

/**
 * 用户挑战记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-11-18
 */
@Service
public class ChallengeRecordServiceImpl implements IChallengeRecordService {
    @Autowired
    private ChallengeRecordMapper challengeRecordMapper;
    
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");
    
    @Autowired
    private IChallengeTaskService challengeTaskService;
    
    /**
     * 查询用户挑战记录
     * 
     * @param id 用户挑战记录ID
     * @return 用户挑战记录
     */
    @Override
    public ChallengeRecord selectChallengeRecordById(Long id) {
        return challengeRecordMapper.selectChallengeRecordById(id);
    }
    
    /**
     * 查询用户挑战记录列表
     * 
     * @param challengeRecord 用户挑战记录
     * @return 用户挑战记录集合
     */
    @Override
    public List<ChallengeRecord> selectChallengeRecordList(ChallengeRecord challengeRecord) {
        return challengeRecordMapper.selectChallengeRecordList(challengeRecord);
    }
    
    /**
     * 根据用户ID和任务ID查询挑战记录
     * 
     * @param userId 用户ID
     * @param taskId 任务ID
     * @return 用户挑战记录
     */
    @Override
    public ChallengeRecord selectChallengeRecordByUserAndTask(Long userId, Long taskId) {
        return challengeRecordMapper.selectChallengeRecordByUserAndTask(userId, taskId);
    }
    
    /**
     * 新增用户挑战记录
     * 
     * @param challengeRecord 用户挑战记录
     * @return 结果
     */
    @Override
    public int insertChallengeRecord(ChallengeRecord challengeRecord) {
        return challengeRecordMapper.insertChallengeRecord(challengeRecord);
    }
    
    /**
     * 修改用户挑战记录
     * 
     * @param challengeRecord 用户挑战记录
     * @return 结果
     */
    @Override
    public int updateChallengeRecord(ChallengeRecord challengeRecord) {
        return challengeRecordMapper.updateChallengeRecord(challengeRecord);
    }
    
    /**
     * 批量删除用户挑战记录
     * 
     * @param ids 需要删除的用户挑战记录ID
     * @return 结果
     */
    @Override
    public int deleteChallengeRecordByIds(Long[] ids) {
        return challengeRecordMapper.deleteChallengeRecordByIds(ids);
    }
    
    /**
     * 删除用户挑战记录信息
     * 
     * @param id 用户挑战记录ID
     * @return 结果
     */
    @Override
    public int deleteChallengeRecordById(Long id) {
        return challengeRecordMapper.deleteChallengeRecordById(id);
    }
    
    /**
     * 解析日期字符串为Date对象
     * 
     * @param dateStr 日期字符串，格式为 yyyy-MM-dd
     * @return Date对象或null（如果解析失败）
     */
    private Date parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        
        try {
            return DATE_FORMAT.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }
    
    /**
     * 解析时间字符串为Date对象
     * 
     * @param timeStr 时间字符串，格式为 HH:mm
     * @return Date对象或null（如果解析失败）
     */
    private Date parseTime(String timeStr) {
        if (timeStr == null || timeStr.isEmpty()) {
            return null;
        }
        
        try {
            return TIME_FORMAT.parse(timeStr);
        } catch (ParseException e) {
            return null;
        }
    }
    
    /**
     * 标记任务完成/取消
     * 
     * @param userId 用户ID
     * @param taskId 任务ID
     * @param completed 是否完成
     * @param photoUrl 照片URL（可选）
     * @param note 备注（可选）
     * @param location 完成地点（可选）
     * @param completedDate 完成日期（可选）
     * @param completedTime 完成时间（可选）
     * @param weather 完成时的天气（可选）
     * @param feeling 完成时的感受（可选）
     * @return 用户挑战记录
     */
    @Override
    @Transactional
    public ChallengeRecord completeTask(Long userId, Long taskId, Boolean completed, String photoUrl, String note, String location, String completedDate, String completedTime, String weather, String feeling) {
        // 验证任务是否存在
        ChallengeTask task = challengeTaskService.selectChallengeTaskById(taskId);
        if (task == null) {
            throw new RuntimeException("任务不存在");
        }
        
        // 查询用户是否已有该任务的记录
        ChallengeRecord record = challengeRecordMapper.selectChallengeRecordByUserAndTask(userId, taskId);
        
        if (record == null) {
            // 如果没有记录，则创建新记录
            record = new ChallengeRecord();
            record.setTaskId(taskId);
            record.setUserId(userId);
            record.setStatus(completed ? "completed" : "pending");
            record.setPhotoUrl(completed ? photoUrl : null);
            record.setNote(completed ? note : null);
            record.setLocation(completed ? location : null);
            record.setCompletedDate(completed ? parseDate(completedDate) : null);
            record.setCompletedTime(completed ? parseTime(completedTime) : null);
            record.setWeather(completed ? weather : null);
            record.setFeeling(completed ? feeling : null);
            record.setIsFavorited(false);
            record.setCompletedAt(completed ? new Date() : null);
            record.setCreatedAt(new Date());
            record.setUpdatedAt(new Date());
            challengeRecordMapper.insertChallengeRecord(record);
        } else {
            // 如果已有记录，则更新记录
            record.setStatus(completed ? "completed" : "pending");
            record.setPhotoUrl(completed ? photoUrl : null);
            record.setNote(completed ? note : null);
            record.setLocation(completed ? location : null);
            record.setCompletedDate(completed ? parseDate(completedDate) : null);
            record.setCompletedTime(completed ? parseTime(completedTime) : null);
            record.setWeather(completed ? weather : null);
            record.setFeeling(completed ? feeling : null);
            record.setCompletedAt(completed ? new Date() : null);
            record.setUpdatedAt(new Date());
            challengeRecordMapper.updateChallengeRecord(record);
        }
        
        return record;
    }
    
    /**
     * 收藏/取消收藏任务
     * 
     * @param userId 用户ID
     * @param taskId 任务ID
     * @param favorited 是否收藏
     * @return 用户挑战记录
     */
    @Override
    @Transactional
    public ChallengeRecord favoriteTask(Long userId, Long taskId, Boolean favorited) {
        // 验证任务是否存在
        ChallengeTask task = challengeTaskService.selectChallengeTaskById(taskId);
        if (task == null) {
            throw new RuntimeException("任务不存在");
        }
        
        // 查询用户是否已有该任务的记录
        ChallengeRecord record = challengeRecordMapper.selectChallengeRecordByUserAndTask(userId, taskId);
        
        if (record == null) {
            // 如果没有记录，则创建新记录
            record = new ChallengeRecord();
            record.setTaskId(taskId);
            record.setUserId(userId);
            record.setStatus("pending");
            record.setIsFavorited(favorited);
            record.setCreatedAt(new Date());
            record.setUpdatedAt(new Date());
            challengeRecordMapper.insertChallengeRecord(record);
        } else {
            // 如果已有记录，则更新记录
            record.setIsFavorited(favorited);
            record.setUpdatedAt(new Date());
            challengeRecordMapper.updateChallengeRecord(record);
        }
        
        return record;
    }
}