package com.libi.configurer.properties;

import com.libi.configurer.properties.model.MinioConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

/**
 * @author libi@hyperchain.cn
 * @date 2022/8/5 3:33 PM
 * 公共配置
 */
@ConfigurationProperties(prefix = "nft.common")
@Configuration
@Data
public class CommonConfig {
    @NestedConfigurationProperty
    private MinioConfig minioConfig;
}
