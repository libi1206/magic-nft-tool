package com.libi.model;

import com.libi.bean.NftPass;
import com.libi.bean.WhiteWallet;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.util.Date;

@Data
public class CheckPassRsp {
    private Boolean checkTag;
    private Date expiredIn;
    private Boolean earlyTag;
    private Boolean tokenTag;
    private String token;

    public static CheckPassRsp of(NftPass nftPass, WhiteWallet early) {
        CheckPassRsp rsp = new CheckPassRsp();
        if (ObjectUtils.isEmpty(nftPass) && ObjectUtils.isEmpty(early)) {
            rsp.checkTag = false;
        } else {
            if (!ObjectUtils.isEmpty(early)) {
                rsp.earlyTag = true;
            }
            if (!ObjectUtils.isEmpty(nftPass)) {
                rsp.checkTag = true;
                rsp.expiredIn = nftPass.getLimitTime();
//            rsp.tokenId =;
//            rsp.token =;
            }

        }
        return rsp;
    }
}
