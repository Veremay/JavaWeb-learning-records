package com.my2424huuu.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliyunOSSProperties {
    private String endpoint;
    private String bucketName;
    private String region;

    /**
     * Access Key ID
     * 优先从环境变量 OSS_ACCESS_KEY_ID 读取
     */
    private String accessKeyId;

    /**
     * Access Key Secret
     * 优先从环境变量 OSS_ACCESS_KEY_SECRET 读取
     */
    private String accessKeySecret;
}
