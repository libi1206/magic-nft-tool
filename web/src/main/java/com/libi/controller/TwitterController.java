package com.libi.controller;

import com.libi.model.SaveTwitterPassReq;
import com.libi.model.TwitterPassRsp;
import com.libi.response.BaseResult;
import com.libi.service.TwitterBizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author libi
 */
@RestController
@RequestMapping("/twitter")
public class TwitterController {
    @Autowired
    private TwitterBizService twitterBizService;

    @GetMapping("/pass")
    public BaseResult<TwitterPassRsp> twitterPass(
            @RequestParam(required = false) String machineCode,
            @RequestParam(required = false) String walletId
    ){
        return twitterBizService.twitterPass(machineCode,walletId);
    }

    @PostMapping("/pass")
    public BaseResult saveTwitterPass(
            @RequestBody SaveTwitterPassReq reqData
    ){
        return twitterBizService.saveTwitterPass(reqData);
    }
}
