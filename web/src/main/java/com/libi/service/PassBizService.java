package com.libi.service;

import com.libi.bean.NftPassOrder;
import com.libi.bean.NftTxRecord;
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
     *
     * @param walletAddress
     * @param value
     * @param unit
     * @return 如果可以检测到订单
     */
    NftPassOrder apyOrder(String walletAddress, BigInteger value, String unit);
}
