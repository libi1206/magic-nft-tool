package com.libi.model;

import com.libi.bean.NftPass;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.util.Date;

@Data
public class CheckPassRsp {
    private Boolean checkTag;
    private Date expiredIn;
    private String tokenId;
    private String token;

    public static CheckPassRsp of(NftPass nftPass) {
        CheckPassRsp rsp = new CheckPassRsp();
        if (ObjectUtils.isEmpty(nftPass)) {
            rsp.checkTag = false;
        } else {
            rsp.checkTag = true;
            rsp.expiredIn = nftPass.getLimitTime();
//            rsp.tokenId =;
//            rsp.token =;
        }
        return rsp;
    }
}
