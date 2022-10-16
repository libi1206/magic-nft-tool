package com.libi.model;

import com.libi.bean.NftPassOrder;
import com.libi.constant.OrderStatus;
import lombok.Data;

@Data
public class TxCheckRsp {
    private Boolean complete;
    private String requirePayedEthNumber;
    private String ethUnit;

    public static TxCheckRsp of(NftPassOrder order) {
        TxCheckRsp rsp = new TxCheckRsp();
        rsp.complete = OrderStatus.COMPLETED.getCode().equals(order.getStatus());
        rsp.requirePayedEthNumber = (order.getTargetNum().subtract(order.getPayedNum())).toString();
        rsp.ethUnit = order.getUnit();
        return rsp;
    }
}
