package com.ruoyi.lovetime.service.impl;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.domain.lovetime.FutureLetter;
import com.ruoyi.lovetime.service.IFutureLetterService;

/**
 * 未来情书定时任务服务
 * 
 * @author ruoyi
 * @date 2025-11-28
 */
@Service
public class FutureLetterSchedulerServiceImpl {
    
    private static final Logger log = LoggerFactory.getLogger(FutureLetterSchedulerServiceImpl.class);
    
    @Autowired
    private IFutureLetterService futureLetterService;
    
    /**
     * 定时检查并发送到期的未来情书
     * 每分钟执行一次
     */
    @Scheduled(cron = "0 * * * * ?")
    public void sendScheduledLetters() {
        try {
            log.info("开始检查待发送的未来情书...");
            
            // 查询所有状态为"已安排"且发送时间已到的未来情书
            List<FutureLetter> scheduledLetters = futureLetterService.selectScheduledLettersToSend();
            
            if (scheduledLetters != null && !scheduledLetters.isEmpty()) {
                log.info("找到 {} 封待发送的未来情书", scheduledLetters.size());
                
                for (FutureLetter letter : scheduledLetters) {
                    try {
                        // 更新情书状态为"已发送"
                        letter.setStatus("已发送");
                        // 确保 sent_at 和 scheduled_time 保持一致
                        letter.setSentAt(letter.getScheduledTime());
                        
                        // 不再支持更新功能
                        log.warn("更新功能已禁用，无法发送未来情书，ID: {}, 标题: {}", letter.getId(), letter.getTitle());
                    } catch (Exception e) {
                        log.error("处理未来情书时发生错误，ID: {}, 标题: {}", letter.getId(), letter.getTitle(), e);
                    }
                }
            } else {
                log.info("没有找到待发送的未来情书");
            }
        } catch (Exception e) {
            log.error("检查待发送未来情书时发生错误", e);
        }
    }
}