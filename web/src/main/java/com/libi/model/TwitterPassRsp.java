package com.libi.model;

import com.libi.bean.TwitterPass;
import lombok.Data;

@Data
public class TwitterPassRsp {
    private String walletId;

    public static TwitterPassRsp of(TwitterPass twitterPass) {
        TwitterPassRsp rsp = new TwitterPassRsp();
        rsp.walletId = twitterPass.getWalletId();
        return rsp;
    }
}
