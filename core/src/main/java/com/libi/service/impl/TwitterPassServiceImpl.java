package com.libi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.libi.bean.TwitterPass;
import com.libi.dao.TwitterPassMapper;
import com.libi.service.TwitterPassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author libi
 * @since 2022-07-24
 */
@Service
public class TwitterPassServiceImpl extends ServiceImpl<TwitterPassMapper, TwitterPass> implements TwitterPassService {

    @Override
    public TwitterPass getByMatchCode(String machineCode) {
        QueryWrapper<TwitterPass> wrapper = new QueryWrapper<>();
        wrapper.eq("machine_code", machineCode);
        return getOne(wrapper);
    }
}
