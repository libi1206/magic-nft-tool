package com.libi.configurer;

import com.alibaba.fastjson.JSON;
import com.libi.configurer.properties.WebConfig;
import com.libi.configurer.properties.model.ChainConfig;
import com.libi.listener.TxListener;
import io.reactivex.disposables.Disposable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.util.Set;

/**
 * @author libi@hyperchain.cn
 * @date 2022/10/17 8:34 PM
 */
@Configuration
public class Web3jConfig {
    @Autowired
    private WebConfig webConfig;
    @Autowired
    private TxListener txListener;

    @Bean
    public Web3j web3j() {
        ChainConfig chainConfig = webConfig.getChainConfig();
        Web3j web3j = Web3j.build(new HttpService(chainConfig.getChainUrl()));
        return web3j;
    }

    @Bean
    public Disposable listenTx(Web3j web3j) {
        Disposable subscribe = web3j.transactionFlowable().subscribe(txListener);
        return subscribe;
    }
}
