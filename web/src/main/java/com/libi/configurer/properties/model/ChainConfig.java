package com.libi.configurer.properties.model;

import lombok.Data;

/**
 * @author libi@hyperchain.cn
 * @date 2022/8/28 2:16 PM
 */
@Data
public class ChainConfig {
    /**
     * 链地址
     */
    private String chainUrl;
    /**
     * 管理员账户地址
     */
    private String adminAccountAddress;
    /**
     * 管理员账户私钥
     */
    private String adminAccountPrivateKey;
    /**
     * NFT合约地址
     */
    private String contractAddress;
    /**
     * 打钱的用户地址
     */
    private String targetAddress;
}
