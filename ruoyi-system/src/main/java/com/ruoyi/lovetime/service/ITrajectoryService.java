package com.ruoyi.lovetime.service;

import com.ruoyi.common.core.domain.lovetime.Trajectory;
import java.util.Date;
import java.util.List;

/**
 * 轨迹Service接口
 * 
 * @author ruoyi
 * @date 2025-11-19
 */
public interface ITrajectoryService {
    /**
     * 查询轨迹
     * 
     * @param id 轨迹ID
     * @return 轨迹
     */
    public Trajectory selectTrajectoryById(Long id);

    /**
     * 查询轨迹列表
     * 
     * @param trajectory 轨迹
     * @return 轨迹集合
     */
    public List<Trajectory> selectTrajectoryList(Trajectory trajectory);

    /**
     * 根据用户ID查询轨迹列表
     * 
     * @param userId 用户ID
     * @return 轨迹集合
     */
    public List<Trajectory> selectTrajectoryByUserId(Long userId);

    /**
     * 根据用户ID和时间范围查询轨迹列表
     * 
     * @param userId 用户ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 轨迹集合
     */
    public List<Trajectory> selectTrajectoryByUserIdAndDateRange(Long userId, Date startDate, Date endDate);

    /**
     * 根据用户ID查询最新的轨迹点
     * 
     * @param userId 用户ID
     * @return 轨迹
     */
    public Trajectory selectLatestTrajectoryByUserId(Long userId);

    /**
     * 新增轨迹
     * 
     * @param trajectory 轨迹
     * @return 结果
     */
    public int insertTrajectory(Trajectory trajectory);

    /**
     * 修改轨迹
     * 
     * @param trajectory 轨迹
     * @return 结果
     */
    public int updateTrajectory(Trajectory trajectory);

    /**
     * 批量删除轨迹
     * 
     * @param ids 需要删除的轨迹ID
     * @return 结果
     */
    public int deleteTrajectoryByIds(Long[] ids);

    /**
     * 删除轨迹信息
     * 
     * @param id 轨迹ID
     * @return 结果
     */
    public int deleteTrajectoryById(Long id);
}