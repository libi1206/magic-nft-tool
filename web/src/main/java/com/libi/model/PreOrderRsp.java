package com.libi.model;

import com.libi.bean.NftPassOrder;
import com.libi.bean.NftPassRank;
import lombok.Data;

@Data
public class PreOrderRsp {
    private String targetAddress;
    private String ethNumber;
    private String ethUnit;
    private String orderId;

    public static PreOrderRsp of(NftPassOrder order, NftPassRank rank) {
        PreOrderRsp rsp = new PreOrderRsp();
        rsp.targetAddress = order.getTargetAddress();
        rsp.ethNumber = rank.getEthNum().toString();
        rsp.ethUnit = rank.getEthUnit();
        rsp.orderId = order.getId().toString();
        return rsp;
    }
}
