package com.libi.service;

import com.libi.model.SaveTwitterPassReq;
import com.libi.model.TwitterPassRsp;
import com.libi.response.BaseResult;

public interface TwitterBizService {
    BaseResult<TwitterPassRsp> twitterPass(String machineCode, String walletId);

    BaseResult saveTwitterPass(SaveTwitterPassReq reqData);
}
