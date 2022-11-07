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
    private String tokenId;
    private String token;

    public static CheckPassRsp of(NftPass nftPass, WhiteWallet early) {
        CheckPassRsp rsp = new CheckPassRsp();
        if (ObjectUtils.isEmpty(nftPass) && ObjectUtils.isEmpty(early)) {
            rsp.checkTag = false;
        } else {
            rsp.checkTag = true;
            if (!ObjectUtils.isEmpty(early)) {
                rsp.earlyTag = true;
            }else {
                rsp.earlyTag = false;
                rsp.expiredIn = nftPass.getLimitTime();
//            rsp.tokenId =;
//            rsp.token =;
            }
        }
        return rsp;
    }
}
