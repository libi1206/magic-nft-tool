package com.libi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.libi.bean.WhiteWallet;
import com.libi.dao.WhiteWalletMapper;
import com.libi.service.WhiteWalletService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * twitterPass的白名单 服务实现类
 * </p>
 *
 * @author libi
 * @since 2022-08-11
 */
@Service
public class WhiteWalletServiceImpl extends ServiceImpl<WhiteWalletMapper, WhiteWallet> implements WhiteWalletService {

    @Override
    public WhiteWallet getByWalletId(String walletId) {
        QueryWrapper<WhiteWallet> wrapper = new QueryWrapper<>();
        wrapper.eq("wallet_id", walletId);
        wrapper.orderByDesc("id");
        wrapper.last("limit 1");
        return getOne(wrapper);
    }
}
