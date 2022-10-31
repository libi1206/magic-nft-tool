package com.libi.web3;

import com.alibaba.fastjson.JSON;
import com.libi.Application;
import com.libi.configurer.properties.WebConfig;
import com.libi.configurer.properties.model.ChainConfig;
import com.libi.web3.contract.MyContract;
import io.reactivex.disposables.Disposable;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;

/**
 * @author libi@hyperchain.cn
 * @date 2022/8/28 2:46 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestWeb3 {
    @Autowired
    private WebConfig webConfig;

    @SneakyThrows
    @Test
    public void testWeb3() {
        // 构建客户端
        ChainConfig chainConfig = webConfig.getChainConfig();
        Web3j web3j = Web3j.build(new HttpService(chainConfig.getChainUrl()));
        // 发送类似于ping的指令
        Web3ClientVersion ver = web3j.web3ClientVersion().send();
        System.out.println(ver.getWeb3ClientVersion());
        // 发送以太币交易
        Credentials credentials = Credentials.create(chainConfig.getAdminAccountPrivateKey());
        TransactionReceipt send = Transfer.sendFunds(web3j, credentials, "0xBF99220b0C76f59A1922BA339A2CF95f3835AE7f", BigDecimal.ONE, Convert.Unit.ETHER).send();
        System.out.println("发送结果： \n " + JSON.toJSONString(send, true));
    }

    @SneakyThrows
    @Test
    public void testContract() {
        // 构建客户端
        ChainConfig chainConfig = webConfig.getChainConfig();
        Web3j web3j = Web3j.build(new HttpService(chainConfig.getChainUrl()));
        Credentials credentials = Credentials.create(chainConfig.getAdminAccountPrivateKey());
        // 部署
        RemoteCall<MyContract> deploy = MyContract.deploy(web3j, credentials, new StaticGasProvider(BigInteger.valueOf(1L), BigInteger.valueOf(6721975)));
        System.out.println("部署结果：" + JSON.toJSONString(deploy));
        // 调用
        MyContract myContract = deploy.send();
        RemoteFunctionCall<String> call1 = myContract.get();
        System.out.println("调用结果1： " + call1.send());
        // 调用
        RemoteFunctionCall<TransactionReceipt> call2 = myContract.set("Web3J_test_233");
        System.out.println("调用结果2： " + call2.send());
        // 调用
        RemoteFunctionCall<String> call3 = myContract.get();
        System.out.println("调用结果3： " + call3.send());

    }

    public void testWallet() {
        Credentials credentials = Credentials.create("", "");
    }

    @Test
    public void test() {
        ChainConfig chainConfig = webConfig.getChainConfig();
        Web3j web3j = Web3j.build(new HttpService(chainConfig.getChainUrl()));
        Disposable subscribe = web3j.transactionFlowable().subscribe(tx -> {
            System.out.println(JSON.toJSONString(tx));
            if (tx.getTo().equalsIgnoreCase("0x90B8F749b34e1223cEd6e3D3C66E518d4a762824")) {
                System.out.println("bingo!");
            }
        });
        new Scanner(System.in).nextInt();
    }

}
