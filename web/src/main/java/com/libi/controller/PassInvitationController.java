package com.libi.controller;

import com.libi.model.InvitationCodeRsp;
import com.libi.model.InvitationReq;
import com.libi.response.BaseResult;
import com.libi.response.BaseResultFactory;
import com.libi.service.PassBizService;
import com.libi.service.PassInvitationBizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author libi@hyperchain.cn
 * @date 2022/11/10 10:15 AM
 */
@RestController
@RequestMapping("/pass")
public class PassInvitationController {
    @Autowired
    PassInvitationBizService invitationBizService;

    @GetMapping("/invitationCode")
    public BaseResult<InvitationCodeRsp> getInvitationCode(
            @RequestParam String walletAddress
    ) {
        InvitationCodeRsp rsp = invitationBizService.queryInvitation(walletAddress);
        return BaseResultFactory.produceSuccess(rsp);
    }

    @PostMapping("/invitation")
    public BaseResult<Object> invitation(
            @RequestBody InvitationReq invitationReq
    ) {
        invitationBizService.invitation(invitationReq);
        return BaseResultFactory.produceSuccessEmpty();
    }
}
