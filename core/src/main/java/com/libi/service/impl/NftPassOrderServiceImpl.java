package com.libi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.libi.bean.NftPassOrder;
import com.libi.dao.NftPassOrderMapper;
import com.libi.service.NftPassOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通行证订单 服务实现类
 * </p>
 *
 * @author libi
 * @since 2022-10-16
 */
@Service
public class NftPassOrderServiceImpl extends ServiceImpl<NftPassOrderMapper, NftPassOrder> implements NftPassOrderService {

    @Override
    public NftPassOrder selectPayingOrder(String walletAddress) {
        QueryWrapper<NftPassOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("wellet_address", walletAddress);
        wrapper.eq("status", 0);
        wrapper.last("limit 1");
        return getOne(wrapper);
    }
}
