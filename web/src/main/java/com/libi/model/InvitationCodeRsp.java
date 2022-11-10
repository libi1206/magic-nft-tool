package com.libi.model;

import com.libi.bean.NftPassInvitation;
import lombok.Data;

/**
 * @author libi@hyperchain.cn
 * @date 2022/11/10 10:38 AM
 */
@Data
public class InvitationCodeRsp {
    private String invitationCode;

    public static InvitationCodeRsp of(NftPassInvitation invitation) {
        return null;
    }
}
