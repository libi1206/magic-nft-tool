package com.libi.service.impl;

import com.libi.bean.TwitterPass;
import com.libi.exception.BusinessException;
import com.libi.model.SaveTwitterPassReq;
import com.libi.model.TwitterPassRsp;
import com.libi.response.BaseResult;
import com.libi.response.BaseResultFactory;
import com.libi.service.TwitterBizService;
import com.libi.service.TwitterPassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class TwitterBizServiceImpl implements TwitterBizService {
    @Autowired
    private TwitterPassService twitterPassService;

    @Override
    public BaseResult<TwitterPassRsp> twitterPass(String machineCode, String walletId) {
        if (ObjectUtils.isEmpty(machineCode) && ObjectUtils.isEmpty(walletId)) {
            throw new BusinessException(9,"对应的machineCode未找到");
        }
        TwitterPass twitterPass = twitterPassService.getByMatchCodeAndWalletId(machineCode, walletId);
        if (ObjectUtils.isEmpty(twitterPass)) {
            throw new BusinessException(9,"对应的machineCode未找到");
        }
        return BaseResultFactory.produceSuccess(TwitterPassRsp.of(twitterPass));
    }

    @Override
    public BaseResult saveTwitterPass(SaveTwitterPassReq reqData) {
        TwitterPass twitterPass = twitterPassService.getByWalletId(reqData.getMachineCode());
        if (!ObjectUtils.isEmpty(twitterPass)) {
            twitterPass.setMachineCode(reqData.getMachineCode());
            twitterPassService.updateByMachineCode(twitterPass);
            return BaseResultFactory.produceSuccessEmpty();
        }
        twitterPass = new TwitterPass();
        twitterPass.setWalletId(reqData.getWalletId());
        twitterPass.setMachineCode(reqData.getMachineCode());
        twitterPassService.save(twitterPass);
        return BaseResultFactory.produceSuccessEmpty();
    }
}
