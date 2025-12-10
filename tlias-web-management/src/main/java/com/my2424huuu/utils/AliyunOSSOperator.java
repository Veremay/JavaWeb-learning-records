package com.my2424huuu.utils;

import com.aliyun.oss.*;
import com.aliyun.oss.common.auth.DefaultCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@Component
public class AliyunOSSOperator {

    @Autowired
    private AliyunOSSProperties aliyunOSSProperties;

    public String upload(byte[] content, String originalFilename) throws Exception {
        String endpoint = aliyunOSSProperties.getEndpoint();
        String bucketName = aliyunOSSProperties.getBucketName();
        String region = aliyunOSSProperties.getRegion();

        // 优先从环境变量读取凭证，如果环境变量不存在，则使用配置文件中的值
        String accessKeyId = System.getenv("OSS_ACCESS_KEY_ID");
        String accessKeySecret = System.getenv("OSS_ACCESS_KEY_SECRET");
        
        if (accessKeyId == null || accessKeyId.isEmpty()) {
            accessKeyId = aliyunOSSProperties.getAccessKeyId();
        }
        if (accessKeySecret == null || accessKeySecret.isEmpty()) {
            accessKeySecret = aliyunOSSProperties.getAccessKeySecret();
        }
        
        // 验证配置
        if (accessKeyId == null || accessKeySecret == null || 
            accessKeyId.isEmpty() || accessKeySecret.isEmpty()) {
            log.error("OSS访问凭证未配置！");
            log.error("请使用以下方式之一配置：");
            log.error("1. 设置环境变量：OSS_ACCESS_KEY_ID 和 OSS_ACCESS_KEY_SECRET（推荐，生产环境）");
            log.error("2. 创建 application-local.yml 文件并配置（本地开发，已加入 .gitignore）");
            throw new IllegalStateException("OSS访问凭证未配置，请查看日志了解配置方法");
        }
        
        // 使用从配置中读取的凭证创建凭证提供者
        DefaultCredentialsProvider credentialsProvider = new DefaultCredentialsProvider(
                accessKeyId, accessKeySecret);

        // 填写Object完整路径，例如2024/06/1.png。Object完整路径中不能包含Bucket名称。
        //获取当前系统日期的字符串,格式为 yyyy/MM
        String dir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM"));
        //生成一个新的不重复的文件名
        String newFileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
        String objectName = dir + "/" + newFileName;

        // 创建OSSClient实例。
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
        OSS ossClient = OSSClientBuilder.create()
                .endpoint(endpoint)
                .credentialsProvider(credentialsProvider)
                .clientConfiguration(clientBuilderConfiguration)
                .region(region)
                .build();

        try {
            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(content));
        } finally {
            ossClient.shutdown();
        }

        return endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1] + "/" + objectName;
    }

}
