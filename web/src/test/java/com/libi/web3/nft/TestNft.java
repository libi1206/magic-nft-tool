package com.libi.web3.nft;

import com.alibaba.fastjson.JSON;
import com.libi.Application;
import com.libi.configurer.properties.WebConfig;
import com.libi.configurer.properties.model.ChainConfig;
import com.libi.contract.NftToolPass;
import com.libi.web3.contract.MyContract;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

/**
 * @author libi@hyperchain.cn
 * @date 2022/11/15 11:28
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestNft {
    @Autowired
    private WebConfig webConfig;

    @SneakyThrows
    @Test
    public void deploy() {
        // 构建客户端
        ChainConfig chainConfig = webConfig.getChainConfig();
        Web3j web3j = Web3j.build(new HttpService(chainConfig.getChainUrl()));
        Credentials credentials = Credentials.create(chainConfig.getAdminAccountPrivateKey());
        // 部署
        RemoteCall<NftToolPass> deploy = NftToolPass.deploy(web3j, credentials, new StaticGasProvider(BigInteger.valueOf(20000000000L), BigInteger.valueOf(6721975L)),
                webConfig.getChainConfig().getTargetAddress(),
                "https://www.baidu.com",
                BigInteger.valueOf(100L),
                BigInteger.valueOf(3L));
        System.out.println("部署结果：" + JSON.toJSONString(deploy));
        NftToolPass myContract = deploy.send();
        System.out.println(JSON.toJSONString(myContract));


//        // 调用
//        RemoteFunctionCall<String> call1 = myContract.get();
//        System.out.println("调用结果1： " + call1.send());
//        // 调用
//        RemoteFunctionCall<TransactionReceipt> call2 = myContract.set("Web3J_test_233");
//        System.out.println("调用结果2： " + call2.send());
//        // 调用
//        RemoteFunctionCall<String> call3 = myContract.get();
//        System.out.println("调用结果3： " + call3.send());

    }

    @SneakyThrows
    @Test
    public void test (){
        // 构建客户端
        ChainConfig chainConfig = webConfig.getChainConfig();
        Web3j web3j = Web3j.build(new HttpService(chainConfig.getChainUrl()));
        Credentials credentials = Credentials.create(chainConfig.getAdminAccountPrivateKey());

        // 构建调用模块
        NftToolPass nftToolPass = NftToolPass.load(webConfig.getChainConfig().getContractAddress(), web3j, credentials, new StaticGasProvider(BigInteger.valueOf(1L), BigInteger.valueOf(6721975)));
        // 构建配置
        TransactionReceipt setupNonAuctionSaleInfo = nftToolPass.setupNonAuctionSaleInfo(
                BigInteger.valueOf(100L),
                BigInteger.valueOf(System.currentTimeMillis()/1000),
                BigInteger.valueOf(100L),
                BigInteger.valueOf(System.currentTimeMillis()/1000)
        ).send();
        // 管理员发放NFT
        TransactionReceipt mintAdmin = nftToolPass.mintAdmin("0x59557b4EedCfcDad9A57aeD14445E070262501f9", "http://baidu.com").send();
        // 查询数量
        BigInteger balance = nftToolPass.balanceOf("0x59557b4EedCfcDad9A57aeD14445E070262501f9").send();
        System.out.println(balance);
        // 公共的mint
        TransactionReceipt send = nftToolPass.mint(BigInteger.valueOf(100L)).send();
        send = nftToolPass.mint(BigInteger.valueOf(100L)).send();
        send = nftToolPass.mint(BigInteger.valueOf(100L)).send();
        System.out.println(JSON.toJSONString(send));
        // 查询自己的mint
        balance = nftToolPass.balanceOf(chainConfig.getAdminAccountAddress()).send();
        System.out.println(balance);
        //
    }
}
