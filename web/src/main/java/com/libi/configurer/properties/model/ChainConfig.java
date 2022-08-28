package com.libi.configurer.properties.model;

import lombok.Data;

/**
 * @author libi@hyperchain.cn
 * @date 2022/8/28 2:16 PM
 */
@Data
public class ChainConfig {
    private String chainUrl;
    private String adminAccountAddress;
    private String adminAccountPrivateKey;
    private String contractAddress;
}
