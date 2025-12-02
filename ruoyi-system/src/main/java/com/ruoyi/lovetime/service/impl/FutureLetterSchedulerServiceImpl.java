package com.ruoyi.lovetime.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;

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
    
    // 用于跟踪已处理的信件ID，避免重复处理
    private static final Set<Long> processedLetters = new HashSet<>();
    
    // 上次清理时间
    private static long lastCleanupTime = System.currentTimeMillis();
    
    @Autowired
    private IFutureLetterService futureLetterService;
    
    /**
     * 初始化方法，在应用启动时调用
     */
    @PostConstruct
    public void init() {
        log.info("未来情书定时任务服务初始化完成");
    }
    
    /**
     * 定时检查并发送到期的未来情书
     * 每10秒执行一次，提高时间精度
     */
    @Scheduled(cron = "*/10 * * * * ?")
    public void sendScheduledLetters() {
        try {
            log.info("开始检查待发送的未来情书...");
            
            // 查询所有状态为"未发送"且预定发送时间已到但尚未发送的未来情书
            List<FutureLetter> scheduledLetters = futureLetterService.selectScheduledLettersToSend();
            
            // 定期清理已处理的信件集合，防止内存泄漏（每小时清理一次）
            long cleanupTime = System.currentTimeMillis();
            if (cleanupTime - lastCleanupTime > 3600000) { // 1小时 = 3600000毫秒
                int beforeSize = processedLetters.size();
                processedLetters.clear();
                lastCleanupTime = cleanupTime;
                log.info("清理已处理信件集合，清理前数量: {}, 清理后数量: 0", beforeSize);
            }
            
            if (scheduledLetters != null && !scheduledLetters.isEmpty()) {
                log.info("找到 {} 封待发送的未来情书", scheduledLetters.size());
                
                for (FutureLetter letter : scheduledLetters) {
                    try {
                        // 检查是否已经处理过这封信
                        if (processedLetters.contains(letter.getId())) {
                            log.debug("信件ID: {} 已经处理过，跳过", letter.getId());
                            continue;
                        }
                        
                        // 检查当前时间是否真的到达了发送时间（精确到秒）
                        Date currentTime = new Date();
                        if (currentTime.before(letter.getScheduledTime())) {
                            log.debug("信件ID: {} 还未到达发送时间，跳过处理。当前时间: {}, 预定时间: {}", 
                                letter.getId(), currentTime, letter.getScheduledTime());
                            continue;
                        }
                        
                        // 添加额外的时间检查，确保只在预定时间附近发送
                        long timeDifference = currentTime.getTime() - letter.getScheduledTime().getTime();
                        // 只允许在预定时间前后30秒内发送
                        if (Math.abs(timeDifference) > 30000) { // 30秒 = 30000毫秒
                            log.debug("信件ID: {} 还未到达发送时间窗口或已超过时间窗口，跳过处理。当前时间: {}, 预定时间: {}, 时间差: {}ms", 
                                letter.getId(), currentTime, letter.getScheduledTime(), timeDifference);
                            continue;
                        }
                        
                        // 标记为已处理
                        processedLetters.add(letter.getId());
                        
                        // 发送未来情书（这里只是记录日志，实际发送逻辑可以在其他地方实现）
                        Date sendTime = new Date();
                        long actualTimeDifference = sendTime.getTime() - letter.getScheduledTime().getTime();
                        log.info("成功发送未来情书，ID: {}, 标题: {}, 预定发送时间: {}, 实际发送时间: {}, 时间差: {}ms", 
                                letter.getId(), letter.getTitle(), letter.getScheduledTime(), sendTime, actualTimeDifference);
                        
                        // 这里可以添加实际的发送逻辑，比如通知接收者等
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