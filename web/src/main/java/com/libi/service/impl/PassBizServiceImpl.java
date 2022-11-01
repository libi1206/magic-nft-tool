package com.libi.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.libi.bean.NftPass;
import com.libi.bean.NftPassOrder;
import com.libi.bean.NftPassRank;
import com.libi.configurer.properties.WebConfig;
import com.libi.constant.EthUnit;
import com.libi.constant.OrderStatus;
import com.libi.constant.PassPermanentTag;
import com.libi.model.*;
import com.libi.response.BaseResult;
import com.libi.response.BaseResultFactory;
import com.libi.service.NftPassOrderService;
import com.libi.service.NftPassRankService;
import com.libi.service.NftPassService;
import com.libi.service.PassBizService;
import com.libi.util.EthUnitUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    @Override
    public BaseResult<CheckPassRsp> checkPass(CheckPassReq checkPassReq) {
        String walletAddress = checkPassReq.getWalletAddress().toLowerCase();
        NftPass nftPass = nftPassService.queryPass(walletAddress);
        CheckPassRsp of = CheckPassRsp.of(nftPass);
        return BaseResultFactory.produceSuccess(of);
    }

    @Override
    public BaseResult<PreOrderRsp> preOrder(PreOrderReq preOrderReq) {
        // 查询rank
        NftPassRank rank = passRankService.getById(preOrderReq.getRankId());
        // 插入订单
        NftPassOrder order = new NftPassOrder();
        order.setWelletAddress(preOrderReq.getWalletAddress().toLowerCase());
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
    public BaseResult<TxCheckRsp> txCheck(TxCheckReq txCheckReq) {
        // 查询order
        NftPassOrder order = orderService.getById(txCheckReq.getOrderId());
        TxCheckRsp rsp = TxCheckRsp.of(order);
        return BaseResultFactory.produceSuccess(rsp);
    }

    @Override
    public BaseResult<PassRankRsp> passRank() {
        List<NftPassRank> allRank = passRankService.getAllRank();
        PassRankRsp of = PassRankRsp.of(allRank);
        return BaseResultFactory.produceSuccess(of);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public NftPassOrder payedAndCheckOrder(String walletAddress, BigInteger value, String unit) {
        NftPassOrder order = orderService.selectPayingOrder(walletAddress);
        if (ObjectUtils.isEmpty(order)) {
            log.warn("没有查询到钱包地址： {} 的相关订单，先暂时忽略", walletAddress);
            return null;
        }
        BigInteger payed = EthUnitUtil.castToWei(order.getPayedNum(), order.getUnit());
        BigInteger target = EthUnitUtil.castToWei(order.getTargetNum(), order.getUnit());
        BigInteger thisPay = EthUnitUtil.castToWei(new BigDecimal(value), unit);

        BigInteger nowPayed = payed.add(thisPay);
        order.setPayedNum(new BigDecimal(nowPayed));
        order.setUnit(EthUnit.wei.name());
        if (nowPayed.compareTo(target) >= 0) {
            log.info("订单id {} 的订单支付完成，开始发放通行证给 {}", order.getId(), walletAddress);
            passExtracted(walletAddress, order.getRankId());
            order.setStatus(OrderStatus.COMPLETED.getCode());
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
