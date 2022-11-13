package com.libi.model;

import lombok.Data;

/**
 * @author libi@hyperchain.cn
 * @date 2022/11/10 10:39 AM
 */
@Data
public class InvitationReq {
    private String invitationCode;
    private String newWalletAddress;
}
