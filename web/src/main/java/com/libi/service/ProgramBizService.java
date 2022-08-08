package com.libi.service;

import com.libi.model.ProgGetRsp;
import com.libi.response.BaseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author libi@hyperchain.cn
 * @date 2022/8/8 2:41 PM
 */
public interface ProgramBizService {
    /**
     * 上传文件
     * @param programFile
     * @param programName
     * @param programVersion
     * @return
     */
    BaseResult updateProg(MultipartFile programFile, String programName, String programVersion);

    /**
     * 获取文件
     * @param programName
     * @param programVersion
     * @return
     */
    BaseResult<ProgGetRsp> getProg(String programName, String programVersion);
}
