package com.ruoyi.lovetime.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.lovetime.mapper.FutureLetterMapper;
import com.ruoyi.lovetime.service.IFutureLetterService;
import com.ruoyi.common.core.domain.lovetime.FutureLetter;

/**
 * 未来情书Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-11-19
 */
@Service
public class FutureLetterServiceImpl implements IFutureLetterService {
    @Autowired
    private FutureLetterMapper futureLetterMapper;

    /**
     * 查询未来情书
     * 
     * @param id 未来情书ID
     * @return 未来情书
     */
    @Override
    public FutureLetter selectFutureLetterById(Long id) {
        return futureLetterMapper.selectFutureLetterById(id);
    }

    /**
     * 查询未来情书列表
     * 
     * @param futureLetter 未来情书
     * @return 未来情书集合
     */
    @Override
    public List<FutureLetter> selectFutureLetterList(FutureLetter futureLetter) {
        return futureLetterMapper.selectFutureLetterList(futureLetter);
    }

    /**
     * 根据发送者ID查询未来情书列表
     * 
     * @param senderId 发送者ID
     * @param status 状态
     * @return 未来情书集合
     */
    @Override
    public List<FutureLetter> selectFutureLettersBySenderId(Long senderId, String status) {
        return futureLetterMapper.selectFutureLettersBySenderId(senderId, status);
    }

    /**
     * 根据接收者ID查询未来情书列表
     * 
     * @param receiverId 接收者ID
     * @param status 状态
     * @return 未来情书集合
     */
    @Override
    public List<FutureLetter> selectFutureLettersByReceiverId(Long receiverId, String status) {
        return futureLetterMapper.selectFutureLettersByReceiverId(receiverId, status);
    }

    /**
     * 查询待发送的未来情书
     * 
     * @return 待发送的未来情书集合
     */
    @Override
    public List<FutureLetter> selectScheduledLettersToSend() {
        return futureLetterMapper.selectScheduledLettersToSend();
    }

    /**
     * 新增未来情书
     * 
     * @param futureLetter 未来情书
     * @return 结果
     */
    @Override
    public int insertFutureLetter(FutureLetter futureLetter) {
        return futureLetterMapper.insertFutureLetter(futureLetter);
    }

    /**
     * 修改未来情书
     * 
     * @param futureLetter 未来情书
     * @return 结果
     */
    @Override
    public int updateFutureLetter(FutureLetter futureLetter) {
        return futureLetterMapper.updateFutureLetter(futureLetter);
    }

    /**
     * 批量删除未来情书
     * 
     * @param ids 需要删除的未来情书ID
     * @return 结果
     */
    @Override
    public int deleteFutureLetterByIds(Long[] ids) {
        return futureLetterMapper.deleteFutureLetterByIds(ids);
    }

    /**
     * 删除未来情书信息
     * 
     * @param id 未来情书ID
     * @return 结果
     */
    @Override
    public int deleteFutureLetterById(Long id) {
        return futureLetterMapper.deleteFutureLetterById(id);
    }
}