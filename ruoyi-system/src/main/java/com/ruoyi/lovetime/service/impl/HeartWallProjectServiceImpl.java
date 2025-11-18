package com.ruoyi.lovetime.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.lovetime.mapper.HeartWallProjectMapper;
import com.ruoyi.lovetime.service.IHeartWallProjectService;
import com.ruoyi.common.core.domain.lovetime.HeartWallProject;

/**
 * 心形墙项目Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-11-18
 */
@Service
public class HeartWallProjectServiceImpl implements IHeartWallProjectService {
    @Autowired
    private HeartWallProjectMapper heartWallProjectMapper;
    
    /**
     * 查询心形墙项目
     * 
     * @param id 心形墙项目ID
     * @return 心形墙项目
     */
    @Override
    public HeartWallProject selectHeartWallProjectById(Long id) {
        return heartWallProjectMapper.selectHeartWallProjectById(id);
    }
    
    /**
     * 查询心形墙项目列表
     * 
     * @param heartWallProject 心形墙项目
     * @return 心形墙项目
     */
    @Override
    public List<HeartWallProject> selectHeartWallProjectList(HeartWallProject heartWallProject) {
        return heartWallProjectMapper.selectHeartWallProjectList(heartWallProject);
    }
    
    /**
     * 查询用户的心形墙项目列表
     * 
     * @param userId 用户ID
     * @return 心形墙项目集合
     */
    @Override
    public List<HeartWallProject> selectHeartWallProjectsByUserId(Long userId) {
        return heartWallProjectMapper.selectHeartWallProjectsByUserId(userId);
    }
    
    /**
     * 新增心形墙项目
     * 
     * @param heartWallProject 心形墙项目
     * @return 结果
     */
    @Override
    public int insertHeartWallProject(HeartWallProject heartWallProject) {
        return heartWallProjectMapper.insertHeartWallProject(heartWallProject);
    }
    
    /**
     * 修改心形墙项目
     * 
     * @param heartWallProject 心形墙项目
     * @return 结果
     */
    @Override
    public int updateHeartWallProject(HeartWallProject heartWallProject) {
        return heartWallProjectMapper.updateHeartWallProject(heartWallProject);
    }
    
    /**
     * 批量删除心形墙项目
     * 
     * @param ids 需要删除的心形墙项目ID
     * @return 结果
     */
    @Override
    public int deleteHeartWallProjectByIds(Long[] ids) {
        return heartWallProjectMapper.deleteHeartWallProjectByIds(ids);
    }
    
    /**
     * 删除心形墙项目信息
     * 
     * @param id 心形墙项目ID
     * @return 结果
     */
    @Override
    public int deleteHeartWallProjectById(Long id) {
        return heartWallProjectMapper.deleteHeartWallProjectById(id);
    }
}