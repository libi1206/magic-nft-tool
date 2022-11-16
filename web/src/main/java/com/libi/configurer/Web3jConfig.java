package com.libi.configurer;

import com.alibaba.fastjson.JSON;
import com.libi.configurer.properties.WebConfig;
import com.libi.configurer.properties.model.ChainConfig;
import com.libi.contract.NftToolPass;
import com.libi.listener.TxListener;
import io.reactivex.disposables.Disposable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;
import java.util.Set;

/**
 * @author libi@hyperchain.cn
 * @date 2022/10/17 8:34 PM
 */
@Configuration
@Slf4j
public class Web3jConfig {
    @Autowired
    private WebConfig webConfig;

    /**
     * web3j链接
     * @return
     */
    @Bean
    public Web3j web3j() {
        ChainConfig chainConfig = webConfig.getChainConfig();
        log.info("链接的区块链地址： {}", chainConfig.getChainUrl());
        return Web3j.build(new HttpService(chainConfig.getChainUrl()));
    }

    /**
     * 管理员用户令牌
     * @return
     */
    @Bean
    public Credentials adminUserAccount() {
        ChainConfig chainConfig = webConfig.getChainConfig();
        return Credentials.create(chainConfig.getAdminAccountPrivateKey());
    }


    /**
     * 合约调用模块
     * @param web3j
     * @param credentials
     * @return
     */
    @Bean
    public NftToolPass nftToolPass(
            Web3j web3j,
            Credentials credentials
    ) {
        return NftToolPass.load(webConfig.getChainConfig().getContractAddress(), web3j, credentials, new StaticGasProvider(BigInteger.valueOf(1L), BigInteger.valueOf(6721975)));
    }
//    不再使用交易监听这种效率低体验差的做法
//    @Bean
//    public Disposable listenTx(Web3j web3j) {
//        Disposable subscribe = web3j.transactionFlowable().subscribe(txListener);
//        return subscribe;
//    }
}
