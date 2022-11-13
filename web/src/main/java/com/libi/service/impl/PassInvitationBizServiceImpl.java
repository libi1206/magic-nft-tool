package com.libi.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.libi.bean.NftPassInvitation;
import com.libi.bean.NftPassInvitationRecord;
import com.libi.model.InvitationCodeRsp;
import com.libi.model.InvitationReq;
import com.libi.service.NftPassInvitationRecordService;
import com.libi.service.NftPassInvitationService;
import com.libi.service.PassBizService;
import com.libi.service.PassInvitationBizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @author libi@hyperchain.cn
 * @date 2022/11/10 10:44 AM
 */
@Service
public class PassInvitationBizServiceImpl implements PassInvitationBizService {
    @Autowired
    private NftPassInvitationService nftPassInvitationService;
    @Autowired
    private NftPassInvitationRecordService nftPassInvitationRecordService;
    @Autowired
    private PassBizService passBizService;

    public InvitationCodeRsp queryInvitation(String walletAddress) {
        NftPassInvitation invitation = nftPassInvitationService.getByWalletAddress(walletAddress);
        if (ObjectUtils.isEmpty(invitation)) {
            invitation = new NftPassInvitation();
            invitation.setWalletAddress(walletAddress);
            invitation.setInvitationCode(RandomUtil.randomStringUpper(6));
            invitation.setInvitationNum(0);
            nftPassInvitationService.save(invitation);
        }
        return InvitationCodeRsp.of(invitation);
    }

    @Override
    public void invitation(InvitationReq invitationReq) {
        NftPassInvitation invitation = nftPassInvitationService.getByInvitationCode(invitationReq.getInvitationCode());
        NftPassInvitationRecord record = new NftPassInvitationRecord();
        record.setInviterAddress(invitation.getWalletAddress());
        record.setInviteeAddress(invitationReq.getNewWalletAddress());
        nftPassInvitationRecordService.save(record);
        Integer invitationNum = invitation.getInvitationNum();
        if (invitationNum < 7) {
            passBizService.passExtractedByDays(invitation.getWalletAddress(), 1);
        }
        invitation.setInvitationNum(invitationNum + 1);
        nftPassInvitationService.updateById(invitation);
    }
}
