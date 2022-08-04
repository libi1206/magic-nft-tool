package com.libi.compoment;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MinioManager {
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder().build();
    }
}
