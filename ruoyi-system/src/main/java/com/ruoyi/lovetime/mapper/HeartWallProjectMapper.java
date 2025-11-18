package com.ruoyi.lovetime.mapper;

import com.ruoyi.common.core.domain.lovetime.HeartWallProject;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 心形墙项目Mapper接口
 * 
 * @author ruoyi
 * @date 2025-11-18
 */
public interface HeartWallProjectMapper {
    /**
     * 查询心形墙项目列表
     * 
     * @param heartWallProject 心形墙项目
     * @return 心形墙项目集合
     */
    public List<HeartWallProject> selectHeartWallProjectList(HeartWallProject heartWallProject);
    
    /**
     * 查询用户的心形墙项目列表
     * 
     * @param userId 用户ID
     * @return 心形墙项目集合
     */
    public List<HeartWallProject> selectHeartWallProjectsByUserId(@Param("userId") Long userId);
    
    /**
     * 根据ID查询心形墙项目
     * 
     * @param id 心形墙项目ID
     * @return 心形墙项目
     */
    public HeartWallProject selectHeartWallProjectById(Long id);
    
    /**
     * 新增心形墙项目
     * 
     * @param heartWallProject 心形墙项目
     * @return 结果
     */
    public int insertHeartWallProject(HeartWallProject heartWallProject);
    
    /**
     * 修改心形墙项目
     * 
     * @param heartWallProject 心形墙项目
     * @return 结果
     */
    public int updateHeartWallProject(HeartWallProject heartWallProject);
    
    /**
     * 删除心形墙项目
     * 
     * @param id 心形墙项目ID
     * @return 结果
     */
    public int deleteHeartWallProjectById(Long id);
    
    /**
     * 批量删除心形墙项目
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteHeartWallProjectByIds(Long[] ids);
}