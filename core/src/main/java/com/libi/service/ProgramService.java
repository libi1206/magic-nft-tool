package com.libi.service;

import com.libi.bean.Program;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * nft小程序版本管理 服务类
 * </p>
 *
 * @author libi
 * @since 2022-08-05
 */
public interface ProgramService extends IService<Program> {

    Program getByNameAndVersion(String programName, String programVersion);
}
