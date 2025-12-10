package com.my2424huuu;

import com.aliyun.oss.*;
import com.aliyun.oss.common.auth.DefaultCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;

public class Demo {

    public static void main(String[] args) throws Exception {
        // 从环境变量中获取访问凭证（Spring Boot 标准做法）
        // 方式1：直接设置环境变量 OSS_ACCESS_KEY_ID 和 OSS_ACCESS_KEY_SECRET
        // 方式2：在 application.yml 中配置，使用 ${OSS_ACCESS_KEY_ID:default_value} 格式
        String accessKeyId = System.getenv("OSS_ACCESS_KEY_ID");
        String accessKeySecret = System.getenv("OSS_ACCESS_KEY_SECRET");
        
        // 如果环境变量不存在，可以设置默认值（仅用于开发测试）
        if (accessKeyId == null || accessKeyId.isEmpty()) {
            accessKeyId = "your_access_key_id_here";
        }
        if (accessKeySecret == null || accessKeySecret.isEmpty()) {
            accessKeySecret = "your_access_key_secret_here";
        }
        
        // 验证配置
        if ("your_access_key_id_here".equals(accessKeyId) || "your_access_key_secret_here".equals(accessKeySecret)) {
            System.err.println("警告：请设置环境变量 OSS_ACCESS_KEY_ID 和 OSS_ACCESS_KEY_SECRET");
            System.err.println("Windows PowerShell: $env:OSS_ACCESS_KEY_ID='your_key'");
            System.err.println("Linux/Mac: export OSS_ACCESS_KEY_ID=your_key");
        }
        
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-beijing.aliyuncs.com";
        
        // 创建凭证提供者
        DefaultCredentialsProvider credentialsProvider = new DefaultCredentialsProvider(
                accessKeyId, accessKeySecret);
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "javaweb-tutorial-01";
        // 填写Object完整路径，例如exampledir/exampleobject.txt。Object完整路径中不能包含Bucket名称。
        String objectName = "001.jpg";
        // 填写Bucket所在地域。以华东1（杭州）为例，Region填写为cn-hangzhou。
        String region = "cn-beijing";

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
            File file = new File("C:\\Users\\myhuuu\\Desktop\\image .png");
            byte[] content = Files.readAllBytes(file.toPath());

            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(content));
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}
