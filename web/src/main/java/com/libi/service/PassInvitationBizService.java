package com.libi.service;

import com.libi.model.InvitationCodeRsp;
import com.libi.model.InvitationReq;

/**
 * @author libi@hyperchain.cn
 * @date 2022/11/10 10:37 AM
 */
public interface PassInvitationBizService {
    /**
     * 查询用户的邀请码
     * @param walletAddress
     * @return
     */
    InvitationCodeRsp queryInvitation(String walletAddress);

    /**
     * 进行邀请
     * @param invitationReq
     */
    void invitation(InvitationReq invitationReq);
}
