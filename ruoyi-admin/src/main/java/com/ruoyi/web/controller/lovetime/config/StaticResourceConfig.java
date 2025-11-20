package com.ruoyi.web.controller.lovetime.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 静态资源映射配置
 * 
 * @author ruoyi
 * @date 2025-11-20
 */
@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 映射字体文件路径
        registry.addResourceHandler("/fonts/**")
                .addResourceLocations("classpath:/static/fonts/", "file:./static/fonts/");
    }
}