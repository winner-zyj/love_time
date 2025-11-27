package com.ruoyi.framework.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC配置
 * 
 * @author ruoyi
 */
@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {
    
    // 这个配置类确保Web MVC功能被正确启用
    // 特别是在某些容器环境中可能需要显式启用
    
    // WebMvcConfigurer接口提供了多种配置方法
    // 包括对multipart处理的支持
}