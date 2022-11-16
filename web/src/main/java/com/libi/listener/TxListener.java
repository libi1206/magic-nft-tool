package com.libi.listener;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.libi.bean.NftPassOrder;
import com.libi.bean.NftTxRecord;
import com.libi.configurer.properties.WebConfig;
import com.libi.service.NftTxRecordService;
import com.libi.service.PassBizService;
import io.reactivex.functions.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Transaction;

/**
 * @author libi@hyperchain.cn
 * @date 2022/10/17 8:30 PM
 * 交易监听器的内容,监听是否有打款给目标账户的交易，有就对订单进行排查
 *
 * 已废弃：不再使用交易监听这种效率低体验差的做法
 */
//@Component
@Slf4j
@Deprecated
public class TxListener implements Consumer<Transaction> {
    @Autowired
    private Web3j web3j;
    @Autowired
    private WebConfig webConfig;
    @Autowired
    private PassBizService passBizService;
    @Autowired
    private NftTxRecordService txRecordService;

    @Override
    public void accept(Transaction transaction) throws Exception {
//        if (webConfig.getChainConfig().getTargetAddress().equalsIgnoreCase(transaction.getTo())) {
//            log.info("【检测到转账】监测到地址为 {} 的用户像目标账户转账 {} wei", transaction.getFrom(), transaction.getValue());
//            // 存入交易记录
//            NftTxRecord nftTxRecord = new NftTxRecord();
//            nftTxRecord.setTxHash(transaction.getHash());
//            nftTxRecord.setFromAddress(transaction.getFrom());
//            nftTxRecord.setToAddress(transaction.getTo());
//            nftTxRecord.setTxJson(JSON.toJSONString(transaction));
//            nftTxRecord.setEthNum(transaction.getValue().toString());
//            nftTxRecord.setEthUnit("wei");
//            txRecordService.save(nftTxRecord);
//            // 查询订单，发放通行证
//            NftPassOrder order = passBizService.payedAndCheckOrder(transaction.getFrom(), transaction.getValue(), "wei");
//            if (ObjectUtils.isNotEmpty(order)) {
//                nftTxRecord.setOrderId(order.getId());
//                txRecordService.updateById(nftTxRecord);
//            }
//        }
    }
}
