package com.libi.service.impl;

import com.libi.bean.NftPass;
import com.libi.bean.NftPassOrder;
import com.libi.bean.NftPassRank;
import com.libi.configurer.properties.WebConfig;
import com.libi.constant.OrderStatus;
import com.libi.model.*;
import com.libi.response.BaseResult;
import com.libi.response.BaseResultFactory;
import com.libi.service.NftPassOrderService;
import com.libi.service.NftPassRankService;
import com.libi.service.NftPassService;
import com.libi.service.PassBizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
        NftPass nftPass = nftPassService.queryPass(checkPassReq.getWalletAddress());
        CheckPassRsp of = CheckPassRsp.of(nftPass);
        return BaseResultFactory.produceSuccess(of);
    }

    @Override
    public BaseResult<PreOrderRsp> preOrder(PreOrderReq preOrderReq) {
        // 查询rank
        NftPassRank rank = passRankService.getById(preOrderReq.getRankId());
        // 插入订单
        NftPassOrder order = new NftPassOrder();
        order.setWelletAddress(preOrderReq.getWalletAddress());
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
        return null;
    }

    @Override
    public BaseResult<PassRankRsp> passRank() {
        List<NftPassRank> allRank = passRankService.getAllRank();
        PassRankRsp of = PassRankRsp.of(allRank);
        return BaseResultFactory.produceSuccess(of);
    }
}
