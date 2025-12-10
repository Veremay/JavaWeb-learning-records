package com.my2424huuu.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.DefaultCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.ClientBuilderConfiguration;
import com.my2424huuu.utils.AliyunOSSProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云OSS配置类
 * 在Spring Boot应用中使用时，可以通过 @Autowired 注入 OSS 客户端
 */
@Slf4j
@Configuration
public class AliyunOssConfig {

    @Autowired
    private AliyunOSSProperties ossProperties;

    /**
     * 创建OSS客户端Bean
     * 优先从环境变量读取凭证，如果环境变量不存在，则使用配置文件中的值
     */
    @Bean
    public OSS ossClient() {
        // 优先从环境变量读取
        String accessKeyId = System.getenv("OSS_ACCESS_KEY_ID");
        String accessKeySecret = System.getenv("OSS_ACCESS_KEY_SECRET");
        
        // 如果环境变量不存在，使用配置文件中的值
        if (accessKeyId == null || accessKeyId.isEmpty()) {
            accessKeyId = ossProperties.getAccessKeyId();
        }
        if (accessKeySecret == null || accessKeySecret.isEmpty()) {
            accessKeySecret = ossProperties.getAccessKeySecret();
        }
        
        if (accessKeyId == null || accessKeySecret == null) {
            log.error("OSS访问凭证未配置！请设置环境变量 OSS_ACCESS_KEY_ID 和 OSS_ACCESS_KEY_SECRET，或在 application.yml 中配置");
            throw new IllegalStateException("OSS访问凭证未配置");
        }
        
        log.info("正在创建OSS客户端，Endpoint: {}", ossProperties.getEndpoint());
        
        DefaultCredentialsProvider credentialsProvider = new DefaultCredentialsProvider(
                accessKeyId, accessKeySecret);
        
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
        
        return OSSClientBuilder.create()
                .endpoint(ossProperties.getEndpoint())
                .credentialsProvider(credentialsProvider)
                .clientConfiguration(clientBuilderConfiguration)
                .region(ossProperties.getRegion())
                .build();
    }
}

