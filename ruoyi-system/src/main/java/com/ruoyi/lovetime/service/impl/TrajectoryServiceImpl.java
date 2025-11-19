package com.ruoyi.lovetime.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.lovetime.mapper.TrajectoryMapper;
import com.ruoyi.lovetime.service.ITrajectoryService;
import com.ruoyi.common.core.domain.lovetime.Trajectory;

/**
 * 轨迹Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-11-19
 */
@Service
public class TrajectoryServiceImpl implements ITrajectoryService {
    @Autowired
    private TrajectoryMapper trajectoryMapper;

    /**
     * 查询轨迹
     * 
     * @param id 轨迹ID
     * @return 轨迹
     */
    @Override
    public Trajectory selectTrajectoryById(Long id) {
        return trajectoryMapper.selectTrajectoryById(id);
    }

    /**
     * 查询轨迹列表
     * 
     * @param trajectory 轨迹
     * @return 轨迹集合
     */
    @Override
    public List<Trajectory> selectTrajectoryList(Trajectory trajectory) {
        return trajectoryMapper.selectTrajectoryList(trajectory);
    }

    /**
     * 根据用户ID查询轨迹列表
     * 
     * @param userId 用户ID
     * @return 轨迹集合
     */
    @Override
    public List<Trajectory> selectTrajectoryByUserId(Long userId) {
        return trajectoryMapper.selectTrajectoryByUserId(userId);
    }

    /**
     * 根据用户ID和时间范围查询轨迹列表
     * 
     * @param userId 用户ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 轨迹集合
     */
    @Override
    public List<Trajectory> selectTrajectoryByUserIdAndDateRange(Long userId, Date startDate, Date endDate) {
        return trajectoryMapper.selectTrajectoryByUserIdAndDateRange(userId, startDate, endDate);
    }

    /**
     * 根据用户ID查询最新的轨迹点
     * 
     * @param userId 用户ID
     * @return 轨迹
     */
    @Override
    public Trajectory selectLatestTrajectoryByUserId(Long userId) {
        return trajectoryMapper.selectLatestTrajectoryByUserId(userId);
    }

    /**
     * 新增轨迹
     * 
     * @param trajectory 轨迹
     * @return 结果
     */
    @Override
    public int insertTrajectory(Trajectory trajectory) {
        return trajectoryMapper.insertTrajectory(trajectory);
    }

    /**
     * 修改轨迹
     * 
     * @param trajectory 轨迹
     * @return 结果
     */
    @Override
    public int updateTrajectory(Trajectory trajectory) {
        return trajectoryMapper.updateTrajectory(trajectory);
    }

    /**
     * 批量删除轨迹
     * 
     * @param ids 需要删除的轨迹ID
     * @return 结果
     */
    @Override
    public int deleteTrajectoryByIds(Long[] ids) {
        return trajectoryMapper.deleteTrajectoryByIds(ids);
    }

    /**
     * 删除轨迹信息
     * 
     * @param id 轨迹ID
     * @return 结果
     */
    @Override
    public int deleteTrajectoryById(Long id) {
        return trajectoryMapper.deleteTrajectoryById(id);
    }
}