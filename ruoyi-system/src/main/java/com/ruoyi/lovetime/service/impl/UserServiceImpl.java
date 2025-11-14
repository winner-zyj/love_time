package com.ruoyi.lovetime.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.lovetime.mapper.UserMapper;
import com.ruoyi.common.core.domain.lovetime.User;
import com.ruoyi.lovetime.service.IUserService;

/**
 * 用户Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-11-13
 */
@Service
public class UserServiceImpl implements IUserService
{
    @Autowired
    private UserMapper userMapper;

    /**
     * 查询用户
     * 
     * @param id 用户ID
     * @return 用户
     */
    @Override
    public User selectUserById(Long id)
    {
        return userMapper.selectUserById(id);
    }

    /**
     * 根据openid查询用户
     * 
     * @param openid 微信openid
     * @return 用户
     */
    @Override
    public User selectUserByOpenid(String openid)
    {
        return userMapper.selectUserByOpenid(openid);
    }

    /**
     * 根据临时登录凭证查询用户
     * 
     * @param code 临时登录凭证
     * @return 用户
     */
    @Override
    public User selectUserByCode(String code)
    {
        return userMapper.selectUserByCode(code);
    }

    /**
     * 根据邀请码查询用户
     * 
     * @param inviteCode 邀请码
     * @return 用户
     */
    @Override
    public User selectUserByInviteCode(String inviteCode)
    {
        return userMapper.selectUserByInviteCode(inviteCode);
    }

    /**
     * 检查邀请码是否存在
     * 
     * @param inviteCode 邀请码
     * @return 是否存在
     */
    @Override
    public boolean existsByInviteCode(String inviteCode)
    {
        return userMapper.existsByInviteCode(inviteCode) > 0;
    }

    /**
     * 查询用户列表
     * 
     * @param user 用户
     * @return 用户集合
     */
    @Override
    public List<User> selectUserList(User user)
    {
        return userMapper.selectUserList(user);
    }

    /**
     * 新增用户
     * 
     * @param user 用户
     * @return 结果
     */
    @Override
    public int insertUser(User user)
    {
        return userMapper.insertUser(user);
    }

    /**
     * 修改用户
     * 
     * @param user 用户
     * @return 结果
     */
    @Override
    public int updateUser(User user)
    {
        return userMapper.updateUser(user);
    }

    /**
     * 批量删除用户
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteUserByIds(Long[] ids)
    {
        return userMapper.deleteUserByIds(ids);
    }

    /**
     * 删除用户信息
     * 
     * @param id 用户ID
     * @return 结果
     */
    @Override
    public int deleteUserById(Long id)
    {
        return userMapper.deleteUserById(id);
    }
}