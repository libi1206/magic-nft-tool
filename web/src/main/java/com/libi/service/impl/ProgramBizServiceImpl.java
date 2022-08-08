package com.libi.service.impl;

import com.libi.bean.Program;
import com.libi.compoment.MinioManager;
import com.libi.exception.BusinessException;
import com.libi.model.ProgGetRsp;
import com.libi.response.BaseResult;
import com.libi.response.BaseResultFactory;
import com.libi.service.ProgramBizService;
import com.libi.service.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.TimeUnit;

/**
 * @author libi@hyperchain.cn
 * @date 2022/8/8 3:05 PM
 */
@Service
public class ProgramBizServiceImpl implements ProgramBizService {
    @Autowired
    private ProgramService programService;
    @Autowired
    private MinioManager minioManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult updateProg(MultipartFile programFile, String programName, String programVersion) {
        if (ObjectUtils.isEmpty(programVersion)) {
            programVersion = "v1.0";
        }
        Program program = programService.getByNameAndVersion(programName, programVersion);
        if (!ObjectUtils.isEmpty(program)) {
            throw new BusinessException(9, "名字为" + programName + "，版本为" + programVersion + "的小程序文件已经存在");
        }
        String objectName = programName + "-" + programVersion + "-" + programFile.getOriginalFilename();
        minioManager.uploadFile(objectName, programFile);

        program = new Program();
        program.setProgramName(programName);
        program.setProgramVersion(programVersion);
        program.setMinioObjectName(objectName);
        programService.save(program);
        return BaseResultFactory.produceSuccessEmpty();
    }

    @Override
    public BaseResult<ProgGetRsp> getProg(String programName, String programVersion) {
        Program program = programService.getByNameAndVersion(programName, programVersion);
        if (ObjectUtils.isEmpty(program)) {
            throw new BusinessException(9, "名字为" + programName + "，版本为" + programVersion + "的小程序文件不存在");
        }
        String fileUrl = minioManager.getFileUrl(program.getMinioObjectName());
        return BaseResultFactory.produceSuccess(ProgGetRsp.of(program, fileUrl));
    }
}
