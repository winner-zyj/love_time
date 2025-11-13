package com.ruoyi.lovetime.service;

import com.ruoyi.common.core.domain.lovetime.User;
import java.util.List;

/**
 * 用户Service接口
 * 
 * @author ruoyi
 * @date 2025-11-13
 */
public interface IUserService 
{
    /**
     * 查询用户
     * 
     * @param id 用户ID
     * @return 用户
     */
    public User selectUserById(Long id);

    /**
     * 根据openid查询用户
     * 
     * @param openid 微信openid
     * @return 用户
     */
    public User selectUserByOpenid(String openid);

    /**
     * 查询用户列表
     * 
     * @param user 用户
     * @return 用户集合
     */
    public List<User> selectUserList(User user);

    /**
     * 新增用户
     * 
     * @param user 用户
     * @return 结果
     */
    public int insertUser(User user);

    /**
     * 修改用户
     * 
     * @param user 用户
     * @return 结果
     */
    public int updateUser(User user);

    /**
     * 批量删除用户
     * 
     * @param ids 需要删除的用户ID
     * @return 结果
     */
    public int deleteUserByIds(Long[] ids);

    /**
     * 删除用户信息
     * 
     * @param id 用户ID
     * @return 结果
     */
    public int deleteUserById(Long id);
}