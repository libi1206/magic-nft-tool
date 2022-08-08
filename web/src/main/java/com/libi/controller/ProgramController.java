package com.libi.controller;

import com.libi.model.ProgGetRsp;
import com.libi.response.BaseResult;
import com.libi.service.ProgramBizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author libi@hyperchain.cn
 * @date 2022/8/5 5:50 PM
 */
@RestController
@RequestMapping("/prog")
public class ProgramController {
    @Autowired
    private ProgramBizService programBizService;

    @PostMapping(path = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public BaseResult upload(
            @RequestParam(required = true) MultipartFile programFile,
            @RequestParam(required = true) String programName,
            @RequestParam(required = false) String programVersion
    ) {
        return programBizService.updateProg(programFile, programName, programVersion);
    }

    @GetMapping("/get")
    public BaseResult<ProgGetRsp> get(
            @RequestParam(required = true) String programName,
            @RequestParam(required = false) String programVersion
    ) {
        return programBizService.getProg(programName, programVersion);
    }
}
