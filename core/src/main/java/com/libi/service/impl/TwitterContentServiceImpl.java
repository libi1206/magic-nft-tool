package com.libi.service.impl;

import com.libi.bean.TwitterContent;
import com.libi.dao.TwitterContentMapper;
import com.libi.service.TwitterContentService;
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
public class TwitterContentServiceImpl extends ServiceImpl<TwitterContentMapper, TwitterContent> implements TwitterContentService {

}
