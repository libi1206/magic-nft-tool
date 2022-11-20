package com.libi.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.libi.bean.*;
import com.libi.configurer.properties.WebConfig;
import com.libi.constant.EthUnit;
import com.libi.constant.OrderStatus;
import com.libi.constant.PassPermanentTag;
import com.libi.constent.Code;
import com.libi.exception.BusinessException;
import com.libi.manager.ContractManager;
import com.libi.model.*;
import com.libi.response.BaseResult;
import com.libi.response.BaseResultFactory;
import com.libi.service.*;
import com.libi.util.EthUnitUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.Transaction;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class PassBizServiceImpl implements PassBizService {
    @Autowired
    private NftPassRankService passRankService;
    @Autowired
    private NftPassService nftPassService;
    @Autowired
    private NftPassOrderService orderService;
    @Autowired
    private WebConfig webConfig;
    @Autowired
    private WhiteWalletService whiteWalletService;
    @Autowired
    private ContractManager contractManager;
    @Autowired
    private NftTxRecordService txRecordService;
    @Autowired
    private PassBizService passBizService;

    @SneakyThrows
    @Override
    public BaseResult<CheckPassRsp> checkPass(CheckPassReq checkPassReq) {
        // 查询数据库，查询订阅和早期用户
        NftPass nftPass = nftPassService.queryPass(checkPassReq.getWalletAddress());
        WhiteWallet early = whiteWalletService.getByWalletId(checkPassReq.getWalletAddress());
        // 查询链，查询NFT持有情况
//        BigInteger nftCount = contractManager.getNftToolPass().balanceOf(checkPassReq.getWalletAddress()).send();
        BigInteger nftCount = BigInteger.ZERO;
        CheckPassRsp of = CheckPassRsp.of(nftPass, early, nftCount);
        return BaseResultFactory.produceSuccess(of);
    }

    @Override
    public BaseResult<PreOrderRsp> preOrder(PreOrderReq preOrderReq) {
        // 查询rank
        NftPassRank rank = passRankService.getById(preOrderReq.getRankId());
        // 插入订单
        NftPassOrder order = new NftPassOrder();
        order.setWalletAddress(preOrderReq.getWalletAddress().toLowerCase());
        order.setTargetAddress(webConfig.getChainConfig().getTargetAddress());
        order.setTargetNum(rank.getEthNum());
        order.setUnit(rank.getEthUnit());
        order.setRankId(rank.getId());
        order.setStatus(OrderStatus.CREATE.getCode());
        orderService.save(order);
        PreOrderRsp of = PreOrderRsp.of(order, rank);
        return BaseResultFactory.produceSuccess(of);
    }

    @Override
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public BaseResult<TxCheckRsp> txCheck(TxCheckReq txCheckReq) {
        // 查询order
        NftPassOrder order = orderService.getById(txCheckReq.getOrderId());
        if (ObjectUtils.isEmpty(order)) {
            throw new BusinessException(Code.ERROR.getCode(), "Order Not Find");
        }
        // 查询交易
        EthTransaction txRsp = contractManager.getWeb3j().ethGetTransactionByHash(txCheckReq.getTxHash()).send();
        Transaction transaction = txRsp.getResult();
        if (ObjectUtils.isEmpty(transaction)) {
            throw new BusinessException(Code.ERROR.getCode(), "TxHash not find");
        }
        // 是否打款到目标用户
        if (order.getTargetAddress().equalsIgnoreCase(transaction.getTo())) {
            log.info("【检测到转账】监测到地址为 {} 的用户像目标账户转账 {} wei", transaction.getFrom(), transaction.getValue());
            // 存入交易记录
            NftTxRecord nftTxRecord = new NftTxRecord();
            nftTxRecord.setTxHash(transaction.getHash());
            nftTxRecord.setFromAddress(transaction.getFrom());
            nftTxRecord.setToAddress(transaction.getTo());
            nftTxRecord.setTxJson(JSON.toJSONString(transaction));
            nftTxRecord.setEthNum(transaction.getValue().toString());
            nftTxRecord.setEthUnit("wei");
            txRecordService.save(nftTxRecord);
            // 查询订单，发放通行证
            NftPassOrder updatedOrder = passBizService.payedAndCheckOrder(order, transaction.getValue(), "wei");
            if (ObjectUtils.isNotEmpty(updatedOrder)) {
                order = updatedOrder;
                nftTxRecord.setOrderId(updatedOrder.getId());
                txRecordService.updateById(nftTxRecord);
            }
        }
        return BaseResultFactory.produceSuccess(TxCheckRsp.of(order));
    }

    @Override
    public BaseResult<PassRankRsp> passRank() {
        List<NftPassRank> allRank = passRankService.getAllRank();
        PassRankRsp of = PassRankRsp.of(allRank);
        return BaseResultFactory.produceSuccess(of);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public NftPassOrder payedAndCheckOrder(NftPassOrder order, BigInteger value, String unit) {
        if (ObjectUtils.isEmpty(order)) {
            throw new BusinessException(Code.ERROR.getCode(), "Order Not Find");
        }
        BigInteger payed = EthUnitUtil.castToWei(order.getPayedNum(), order.getUnit());
        BigInteger target = EthUnitUtil.castToWei(order.getTargetNum(), order.getUnit());
        BigInteger thisPay = EthUnitUtil.castToWei(new BigDecimal(value), unit);

        BigInteger nowPayed = payed.add(thisPay);
        order.setPayedNum(new BigDecimal(nowPayed));
        order.setUnit(EthUnit.wei.name());
        if (nowPayed.compareTo(target) >= 0) {
            log.info("订单id {} 的订单支付完成，开始发放通行证给 {}", order.getId(), order.getWalletAddress());
            passExtracted(order.getWalletAddress(), order.getRankId());
            order.setStatus(OrderStatus.COMPLETED.getCode());
        } else {
            BigInteger subtract = target.subtract(nowPayed);
            log.info("订单id {} 的订单支付了，但是还没有支付完成,还差 {} wei ,钱包ID {}", order.getId(), subtract, order.getWalletAddress());
        }
        orderService.updateById(order);
        return order;
    }

    @Override
    public void passExtracted(String walletAddress, Long rankId) {
        // 计算时间
        Date date;
        // 查询有没有生效中的通行证
        NftPass oldPass = nftPassService.queryPass(walletAddress);
        if (ObjectUtils.isEmpty(oldPass) || oldPass.getPermanentTag().equals(PassPermanentTag.PERMANENT.getCode())) {
            date = new Date();
        } else {
            date = oldPass.getLimitTime();
        }

        // 进行nftPass发放
        NftPassRank rank = passRankService.getById(rankId);
        NftPass nftPass = new NftPass();
        nftPass.setWalletAddress(walletAddress);
        nftPass.setPermanentTag(rank.getDays() <= 0 ? PassPermanentTag.PERMANENT.getCode() : PassPermanentTag.NORMAL.getCode());
        nftPass.setLimitTime(DateUtil.offsetDay(date, rank.getDays()));
        nftPassService.save(nftPass);
    }

    @Override
    public void passExtractedByDays(String walletAddress, Integer days) {
        // 计算时间
        Date date;
        // 查询有没有生效中的通行证
        NftPass oldPass = nftPassService.queryPass(walletAddress);
        if (ObjectUtils.isEmpty(oldPass) || oldPass.getPermanentTag().equals(PassPermanentTag.PERMANENT.getCode())) {
            date = new Date();
        } else {
            date = oldPass.getLimitTime();
        }

        // 进行nftPass发放
        NftPass nftPass = new NftPass();
        nftPass.setWalletAddress(walletAddress);
        nftPass.setPermanentTag(PassPermanentTag.NORMAL.getCode());
        nftPass.setLimitTime(DateUtil.offsetDay(date, days));
        nftPassService.save(nftPass);
    }
}
