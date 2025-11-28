package com.ruoyi.lovetime.service;

import com.ruoyi.common.core.domain.lovetime.FutureLetter;
import java.util.List;

/**
 * 未来情书Service接口
 * 
 * @author ruoyi
 * @date 2025-11-19
 */
public interface IFutureLetterService {
    /**
     * 查询未来情书
     * 
     * @param id 未来情书ID
     * @return 未来情书
     */
    public FutureLetter selectFutureLetterById(Long id);

    /**
     * 查询未来情书列表
     * 
     * @param futureLetter 未来情书
     * @return 未来情书集合
     */
    public List<FutureLetter> selectFutureLetterList(FutureLetter futureLetter);

    /**
     * 根据发送者ID查询未来情书列表
     * 
     * @param senderId 发送者ID
     * @param status 状态
     * @return 未来情书集合
     */
    public List<FutureLetter> selectFutureLettersBySenderId(Long senderId, String status);

    /**
     * 根据接收者ID查询未来情书列表
     * 
     * @param receiverId 接收者ID
     * @param status 状态
     * @return 未来情书集合
     */
    public List<FutureLetter> selectFutureLettersByReceiverId(Long receiverId, String status);

    /**
     * 查询待发送的未来情书
     * 
     * @return 待发送的未来情书集合
     */
    public List<FutureLetter> selectScheduledLettersToSend();

    /**
     * 新增未来情书
     * 
     * @param futureLetter 未来情书
     * @return 结果
     */
    public int insertFutureLetter(FutureLetter futureLetter);

    /**
     * 修改未来情书
     * 
     * @param futureLetter 未来情书
     * @return 结果
     */
    public int updateFutureLetter(FutureLetter futureLetter);

    /**
     * 批量删除未来情书
     * 
     * @param ids 需要删除的未来情书ID
     * @return 结果
     */
    public int deleteFutureLetterByIds(Long[] ids);

    /**
     * 删除未来情书信息
     * 
     * @param id 未来情书ID
     * @return 结果
     */
    public int deleteFutureLetterById(Long id);
}