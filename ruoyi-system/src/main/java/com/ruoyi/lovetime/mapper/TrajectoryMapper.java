package com.ruoyi.lovetime.mapper;

import com.ruoyi.common.core.domain.lovetime.Trajectory;
import org.apache.ibatis.annotations.Param;
import java.util.Date;
import java.util.List;

/**
 * 轨迹Mapper接口
 * 
 * @author ruoyi
 * @date 2025-11-19
 */
public interface TrajectoryMapper {
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
    public List<Trajectory> selectTrajectoryByUserId(@Param("userId") Long userId);
    
    /**
     * 根据用户ID和时间范围查询轨迹列表
     * 
     * @param userId 用户ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 轨迹集合
     */
    public List<Trajectory> selectTrajectoryByUserIdAndDateRange(@Param("userId") Long userId, 
                                                                @Param("startDate") Date startDate, 
                                                                @Param("endDate") Date endDate);
    
    /**
     * 根据ID查询轨迹
     * 
     * @param id 轨迹ID
     * @return 轨迹
     */
    public Trajectory selectTrajectoryById(Long id);
    
    /**
     * 根据用户ID查询最新的轨迹点
     * 
     * @param userId 用户ID
     * @return 轨迹
     */
    public Trajectory selectLatestTrajectoryByUserId(@Param("userId") Long userId);
    
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
     * 删除轨迹
     * 
     * @param id 轨迹ID
     * @return 结果
     */
    public int deleteTrajectoryById(Long id);
    
    /**
     * 批量删除轨迹
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTrajectoryByIds(Long[] ids);
}