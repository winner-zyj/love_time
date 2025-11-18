package com.ruoyi.lovetime.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.lovetime.mapper.HeartWallPhotoMapper;
import com.ruoyi.lovetime.service.IHeartWallPhotoService;
import com.ruoyi.common.core.domain.lovetime.HeartWallPhoto;

/**
 * 心形墙照片Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-11-18
 */
@Service
public class HeartWallPhotoServiceImpl implements IHeartWallPhotoService {
    @Autowired
    private HeartWallPhotoMapper heartWallPhotoMapper;
    
    /**
     * 查询心形墙照片
     * 
     * @param id 心形墙照片ID
     * @return 心形墙照片
     */
    @Override
    public HeartWallPhoto selectHeartWallPhotoById(Long id) {
        return heartWallPhotoMapper.selectHeartWallPhotoById(id);
    }
    
    /**
     * 查询心形墙照片列表
     * 
     * @param heartWallPhoto 心形墙照片
     * @return 心形墙照片
     */
    @Override
    public List<HeartWallPhoto> selectHeartWallPhotoList(HeartWallPhoto heartWallPhoto) {
        return heartWallPhotoMapper.selectHeartWallPhotoList(heartWallPhoto);
    }
    
    /**
     * 根据项目ID查询心形墙照片列表
     * 
     * @param projectId 项目ID
     * @return 心形墙照片集合
     */
    @Override
    public List<HeartWallPhoto> selectHeartWallPhotosByProjectId(Long projectId) {
        return heartWallPhotoMapper.selectHeartWallPhotosByProjectId(projectId);
    }
    
    /**
     * 新增心形墙照片
     * 
     * @param heartWallPhoto 心形墙照片
     * @return 结果
     */
    @Override
    public int insertHeartWallPhoto(HeartWallPhoto heartWallPhoto) {
        return heartWallPhotoMapper.insertHeartWallPhoto(heartWallPhoto);
    }
    
    /**
     * 修改心形墙照片
     * 
     * @param heartWallPhoto 心形墙照片
     * @return 结果
     */
    @Override
    public int updateHeartWallPhoto(HeartWallPhoto heartWallPhoto) {
        return heartWallPhotoMapper.updateHeartWallPhoto(heartWallPhoto);
    }
    
    /**
     * 批量删除心形墙照片
     * 
     * @param ids 需要删除的心形墙照片ID
     * @return 结果
     */
    @Override
    public int deleteHeartWallPhotoByIds(Long[] ids) {
        return heartWallPhotoMapper.deleteHeartWallPhotoByIds(ids);
    }
    
    /**
     * 删除心形墙照片信息
     * 
     * @param id 心形墙照片ID
     * @return 结果
     */
    @Override
    public int deleteHeartWallPhotoById(Long id) {
        return heartWallPhotoMapper.deleteHeartWallPhotoById(id);
    }
    
    /**
     * 根据项目ID和位置索引查询心形墙照片
     * 
     * @param projectId 项目ID
     * @param positionIndex 位置索引
     * @return 心形墙照片
     */
    @Override
    public HeartWallPhoto selectHeartWallPhotoByProjectAndPosition(Long projectId, Integer positionIndex) {
        return heartWallPhotoMapper.selectHeartWallPhotoByProjectAndPosition(projectId, positionIndex);
    }
    
    /**
     * 根据项目ID删除心形墙照片
     * 
     * @param projectId 项目ID
     * @return 结果
     */
    @Override
    public int deleteHeartWallPhotosByProjectId(Long projectId) {
        return heartWallPhotoMapper.deleteHeartWallPhotosByProjectId(projectId);
    }
}