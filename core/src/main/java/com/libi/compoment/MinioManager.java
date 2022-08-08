package com.libi.compoment;

import com.alibaba.fastjson.JSON;
import com.libi.configurer.properties.CommonConfig;
import io.minio.*;
import io.minio.http.Method;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class MinioManager {
    @Autowired
    private CommonConfig commonConfig;
    private MinioClient minioClient;

    @PostConstruct
    public void minioClient() {
        this.minioClient = MinioClient.builder()
                .endpoint(commonConfig.getMinioConfig().getEndPoint())
                .credentials(commonConfig.getMinioConfig().getAccessKey(), commonConfig.getMinioConfig().getSecretKey())
                .build();
        log.info("minio配置已加载 {}", JSON.toJSONString(commonConfig.getMinioConfig()));
    }

    /**
     * 文件上传，并且获取文件URL
     *
     * @param objectName
     * @param file
     * @return
     */
    @SneakyThrows
    public void uploadFile(String objectName, MultipartFile file) {
        ObjectWriteResponse objectWriteResponse = minioClient.putObject(PutObjectArgs.builder()
                .bucket(commonConfig.getMinioConfig().getBucket())
                .object(objectName).stream(file.getInputStream(), file.getSize(), -1)
                .build());
    }

    /**
     * 获取有时间限制的Object URL
     *
     * @param objectName
     * @param expireTime
     * @param timeUnit
     * @return
     */
    @SneakyThrows
    public String getPresignedFileUrl(String objectName, Integer expireTime, TimeUnit timeUnit) {
        String url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .bucket(commonConfig.getMinioConfig().getBucket())
                .expiry(expireTime, timeUnit)
                .method(Method.GET)
                .object(objectName)
                .build());
        return url;
    }

    public String getFileUrl(String objectName) {
        String url = commonConfig.getMinioConfig().getServerUrl() + "/" + commonConfig.getMinioConfig().getBucket() + "/" + objectName;
        return url;
    }
}
