package com.libi.model;

import lombok.Data;

@Data
public class TxCheckReq {
    private String txHash;
    private String orderId;
}
