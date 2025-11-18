package com.ruoyi.common.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信工具类
 * 
 * @author ruoyi
 * @date 2025-11-13
 */
public class WeChatUtil {
    
    private static final Logger log = LoggerFactory.getLogger(WeChatUtil.class);
    
    /**
     * 微信小程序登录接口地址
     */
    private static final String WECHAT_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";
    
    /**
     * 通过微信code获取openid和session_key
     * 
     * @param appId 小程序appid
     * @param appSecret 小程序secret
     * @param code 微信登录凭证
     * @return 包含openid和session_key的Map
     */
    public static Map<String, String> getOpenIdAndSessionKey(String appId, String appSecret, String code) {
        Map<String, String> result = new HashMap<>();
        
        try {
            // 检查参数是否为空
            if (appId == null || appId.isEmpty()) {
                log.error("微信appid不能为空");
                result.put("error", "微信appid不能为空");
                return result;
            }
            
            if (appSecret == null || appSecret.isEmpty()) {
                log.error("微信secret不能为空");
                result.put("error", "微信secret不能为空");
                return result;
            }
            
            if (code == null || code.isEmpty()) {
                log.error("微信code不能为空");
                result.put("error", "微信code不能为空");
                return result;
            }
            
            // 构造请求URL
            String urlStr = WECHAT_LOGIN_URL + 
                           "?appid=" + appId + 
                           "&secret=" + appSecret + 
                           "&js_code=" + code + 
                           "&grant_type=authorization_code";
            
            log.info("微信登录请求URL: {}", urlStr);
            
            // 发送HTTP请求
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(20000); // 增加连接超时到20秒
            conn.setReadTimeout(20000);    // 增加读取超时到20秒
            
            // 检查响应码
            int responseCode = conn.getResponseCode();
            log.info("微信登录响应码: {}", responseCode);
            
            // 读取响应
            BufferedReader in;
            if (responseCode >= 200 && responseCode < 300) {
                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } else {
                in = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
            }
            
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            
            String responseStr = response.toString();
            log.info("微信登录响应: {}", responseStr);
            
            // 解析JSON响应
            JSONObject jsonObject = JSON.parseObject(responseStr);
            
            // 检查是否有错误
            if (jsonObject.containsKey("errcode") && jsonObject.getIntValue("errcode") != 0) {
                String errMsg = jsonObject.getString("errmsg");
                log.error("微信登录失败，错误码：{}，错误信息：{}", jsonObject.getString("errcode"), errMsg);
                result.put("error", errMsg);
                return result;
            }
            
            // 获取openid和session_key
            String openid = jsonObject.getString("openid");
            String sessionKey = jsonObject.getString("session_key");
            
            result.put("openid", openid);
            result.put("sessionKey", sessionKey);
            
        } catch (Exception e) {
            log.error("调用微信接口获取openid失败", e);
            result.put("error", "调用微信接口失败：" + e.getMessage());
        }
        
        return result;
    }
}