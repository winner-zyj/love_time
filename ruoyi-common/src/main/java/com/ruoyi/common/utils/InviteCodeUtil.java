package com.ruoyi.common.utils;

/**
 * 邀请码工具类
 * 
 * @author ruoyi
 * @date 2025-11-14
 */
public class InviteCodeUtil {
    
    /**
     * 生成唯一的6位大写英文字母和数字结合的邀请码
     * 
     * @param existsFunction 检查邀请码是否存在的函数
     * @return 邀请码
     */
    public static String generateUniqueInviteCode(java.util.function.Predicate<String> existsFunction) {
        String inviteCode;
        int attempts = 0;
        do {
            // 生成6位大写英文字母和数字结合的邀请码
            String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 6; i++) {
                int index = (int) (Math.random() * chars.length());
                sb.append(chars.charAt(index));
            }
            inviteCode = sb.toString();
            attempts++;
            
            // 防止无限循环，最多尝试100次
            if (attempts > 100) {
                throw new RuntimeException("无法生成唯一的邀请码");
            }
        } while (existsFunction.test(inviteCode));
        
        return inviteCode;
    }
}