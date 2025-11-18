package com.ruoyi.lovetime.mapper;

import com.ruoyi.common.core.domain.lovetime.HeartWallPhoto;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 心形墙照片Mapper接口
 * 
 * @author ruoyi
 * @date 2025-11-18
 */
public interface HeartWallPhotoMapper {
    /**
     * 查询心形墙照片列表
     * 
     * @param heartWallPhoto 心形墙照片
     * @return 心形墙照片集合
     */
    public List<HeartWallPhoto> selectHeartWallPhotoList(HeartWallPhoto heartWallPhoto);
    
    /**
     * 根据项目ID查询心形墙照片列表
     * 
     * @param projectId 项目ID
     * @return 心形墙照片集合
     */
    public List<HeartWallPhoto> selectHeartWallPhotosByProjectId(@Param("projectId") Long projectId);
    
    /**
     * 根据ID查询心形墙照片
     * 
     * @param id 心形墙照片ID
     * @return 心形墙照片
     */
    public HeartWallPhoto selectHeartWallPhotoById(Long id);
    
    /**
     * 根据项目ID和位置索引查询心形墙照片
     * 
     * @param projectId 项目ID
     * @param positionIndex 位置索引
     * @return 心形墙照片
     */
    public HeartWallPhoto selectHeartWallPhotoByProjectAndPosition(@Param("projectId") Long projectId, @Param("positionIndex") Integer positionIndex);
    
    /**
     * 新增心形墙照片
     * 
     * @param heartWallPhoto 心形墙照片
     * @return 结果
     */
    public int insertHeartWallPhoto(HeartWallPhoto heartWallPhoto);
    
    /**
     * 修改心形墙照片
     * 
     * @param heartWallPhoto 心形墙照片
     * @return 结果
     */
    public int updateHeartWallPhoto(HeartWallPhoto heartWallPhoto);
    
    /**
     * 删除心形墙照片
     * 
     * @param id 心形墙照片ID
     * @return 结果
     */
    public int deleteHeartWallPhotoById(Long id);
    
    /**
     * 批量删除心形墙照片
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteHeartWallPhotoByIds(Long[] ids);
    
    /**
     * 根据项目ID删除心形墙照片
     * 
     * @param projectId 项目ID
     * @return 结果
     */
    public int deleteHeartWallPhotosByProjectId(@Param("projectId") Long projectId);
}