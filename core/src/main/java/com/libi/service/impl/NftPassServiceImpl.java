package com.libi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.libi.bean.NftPass;
import com.libi.bean.NftPassRank;
import com.libi.dao.NftPassMapper;
import com.libi.service.NftPassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 订阅制NFT工具通行证 服务实现类
 * </p>
 *
 * @author libi
 * @since 2022-10-16
 */
@Service
public class NftPassServiceImpl extends ServiceImpl<NftPassMapper, NftPass> implements NftPassService {

    @Override
    public NftPass queryPass(String walletAddress) {
        QueryWrapper<NftPass> wrapper = new QueryWrapper<>();
        wrapper.eq("wallet_address", walletAddress)
                .and(innerWrapper ->
                        innerWrapper.eq("permanent_tag", 1)
                        .or().ge("limit_time",new Date())
                );
        wrapper.orderByDesc("id");
        wrapper.last("limit 1");
        return getOne(wrapper);
    }
}
