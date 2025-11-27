package com.ruoyi.framework.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

/**
 * 文件上传配置
 * 
 * @author ruoyi
 */
@Configuration
public class MultipartConfig {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 单个文件最大值
        factory.setMaxFileSize(DataSize.ofMegabytes(10));
        // 设置总上传数据大小
        factory.setMaxRequestSize(DataSize.ofMegabytes(20));
        // 设置文件大小阈值，超过此大小的文件将写入磁盘而不是内存
        factory.setFileSizeThreshold(DataSize.ofBytes(0));
        return factory.createMultipartConfig();
    }
}