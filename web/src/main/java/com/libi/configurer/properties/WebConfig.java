package com.libi.configurer.properties;

import com.libi.configurer.properties.model.ChainConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

/**
 * @author libi@hyperchain.cn
 * @date 2022/8/28 2:14 PM
 */
@ConfigurationProperties(prefix = "nft.web")
@Configuration
@Data
public class WebConfig {
    @NestedConfigurationProperty
    private ChainConfig chainConfig;
}
