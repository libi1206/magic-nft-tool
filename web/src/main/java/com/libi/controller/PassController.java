package com.libi.controller;

import com.libi.model.*;
import com.libi.response.BaseResult;
import com.libi.service.PassBizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pass")
public class PassController {
    @Autowired
    private PassBizService passBizService;

    @PostMapping("/checkPass")
    public BaseResult<CheckPassRsp> checkPass(
            @RequestBody CheckPassReq checkPassReq
    ){
        return passBizService.checkPass(checkPassReq);
    }

    @PostMapping("/preOrder")
    public BaseResult<PreOrderRsp> preOrder(
            @RequestBody PreOrderReq preOrderReq
    ) {
        return passBizService.preOrder(preOrderReq);
    }

    @PostMapping("/txCheck")
    public BaseResult<TxCheckRsp> txCheck(
            @RequestBody TxCheckReq txCheckReq
    ) {
        return passBizService.txCheck(txCheckReq);
    }

    @GetMapping("/passRank")
    public BaseResult<PassRankRsp> passRank(
    ) {
        return passBizService.passRank();
    }
}
