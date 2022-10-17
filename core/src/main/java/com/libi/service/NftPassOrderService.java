package com.libi.service;

import com.libi.bean.NftPassOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 通行证订单 服务类
 * </p>
 *
 * @author libi
 * @since 2022-10-16
 */
public interface NftPassOrderService extends IService<NftPassOrder> {

    /**
     * 查询还未支付的订单
     * @param walletAddress
     * @return
     */
    NftPassOrder selectPayingOrder(String walletAddress);
}
