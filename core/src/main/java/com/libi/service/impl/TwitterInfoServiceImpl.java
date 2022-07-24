package com.libi.service.impl;

import com.libi.bean.TwitterInfo;
import com.libi.dao.TwitterInfoMapper;
import com.libi.service.TwitterInfoService;
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
public class TwitterInfoServiceImpl extends ServiceImpl<TwitterInfoMapper, TwitterInfo> implements TwitterInfoService {

}
