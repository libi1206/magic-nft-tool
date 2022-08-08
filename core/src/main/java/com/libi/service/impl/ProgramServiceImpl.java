package com.libi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.libi.bean.Program;
import com.libi.dao.ProgramMapper;
import com.libi.service.ProgramService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * <p>
 * nft小程序版本管理 服务实现类
 * </p>
 *
 * @author libi
 * @since 2022-08-05
 */
@Service
public class ProgramServiceImpl extends ServiceImpl<ProgramMapper, Program> implements ProgramService {

    @Override
    public Program getByNameAndVersion(String programName, String programVersion) {
        QueryWrapper<Program> wrapper = new QueryWrapper<>();
        wrapper.eq("program_name", programName);
        if (!ObjectUtils.isEmpty(programVersion)) {
            wrapper.eq("program_version", programVersion);
        }
        wrapper.orderByDesc("id");
        wrapper.last("limit 1");
        return getOne(wrapper);
    }
}
