package com.ruoyi.lovetime.mapper;

import com.ruoyi.common.core.domain.lovetime.User;
import java.util.List;

/**
 * 用户Mapper接口
 * 
 * @author ruoyi
 * @date 2025-11-13
 */
public interface UserMapper 
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
     * 根据临时登录凭证查询用户
     * 
     * @param code 临时登录凭证
     * @return 用户
     */
    public User selectUserByCode(String code);

    /**
     * 根据邀请码查询用户
     * 
     * @param inviteCode 邀请码
     * @return 用户
     */
    public User selectUserByInviteCode(String inviteCode);

    /**
     * 检查邀请码是否存在
     * 
     * @param inviteCode 邀请码
     * @return 是否存在
     */
    public int existsByInviteCode(String inviteCode);

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
     * 删除用户
     * 
     * @param id 用户ID
     * @return 结果
     */
    public int deleteUserById(Long id);

    /**
     * 批量删除用户
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteUserByIds(Long[] ids);
}