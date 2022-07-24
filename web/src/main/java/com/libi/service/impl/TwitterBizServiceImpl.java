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
    public BaseResult<TwitterPassRsp> twitterPass(String machineCode) {
        TwitterPass twitterPass = twitterPassService.getByMatchCode(machineCode);
        if (ObjectUtils.isEmpty(twitterPass)) {
            throw new BusinessException(9,"对应的machineCode未找到");
        }
        return BaseResultFactory.produceSuccess(TwitterPassRsp.of(twitterPass));
    }

    @Override
    public BaseResult saveTwitterPass(SaveTwitterPassReq reqData) {
        TwitterPass twitterPass = twitterPassService.getByMatchCode(reqData.getMachineCode());
        if (!ObjectUtils.isEmpty(twitterPass)) {
            throw new BusinessException(9, "machineCode已存在");
        }
        twitterPass = new TwitterPass();
        twitterPass.setWalletId(reqData.getWalletId());
        twitterPass.setMachineCode(reqData.getMachineCode());
        twitterPassService.save(twitterPass);
        return BaseResultFactory.produceSuccessEmpty();
    }
}
