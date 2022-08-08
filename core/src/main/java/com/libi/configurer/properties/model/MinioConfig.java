package com.libi.configurer.properties.model;

import lombok.Data;

/**
 * @author libi
 */
@Data
public class MinioConfig {
    private String endPoint;
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String serverUrl;
}
