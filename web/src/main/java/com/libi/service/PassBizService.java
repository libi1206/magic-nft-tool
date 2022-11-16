package com.libi.service;

import com.libi.bean.NftPassOrder;
import com.libi.model.*;
import com.libi.response.BaseResult;

import java.math.BigInteger;

public interface PassBizService {
    /**
     * 检查通行证
     * @param checkPassReq
     * @return
     */
    BaseResult<CheckPassRsp> checkPass(CheckPassReq checkPassReq);

    /**
     * 购买通行证，进行订单提交
     * @param preOrderReq
     * @return
     */
    BaseResult<PreOrderRsp> preOrder(PreOrderReq preOrderReq);

    /**
     * 交易检查
     * @param txCheckReq
     * @return
     */
    BaseResult<TxCheckRsp> txCheck(TxCheckReq txCheckReq);

    /**
     * 获取通行证等级
     * @return
     */
    BaseResult<PassRankRsp> passRank();

    /**
     * 检测到用户支付
     * 检查用户的订单，进行通行证发放
     *
     * @param order
     * @param value
     * @param unit
     * @return 如果可以检测到订单,返回更新后的订单
     */
    NftPassOrder payedAndCheckOrder(NftPassOrder order, BigInteger value, String unit);


    /**
     * 进行通行证的发放
     * @param walletAddress
     * @param rankId
     */
    void passExtracted(String walletAddress, Long rankId);


    /**
     * 进行通行证的发放
     * 按天发放
     * @param walletAddress
     * @param days
     */
    void passExtractedByDays(String walletAddress, Integer days);
    }
