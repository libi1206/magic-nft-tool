package com.libi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
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
 * @since 2022-08-04
 */
@Service
public class TwitterPassServiceImpl extends ServiceImpl<TwitterPassMapper, TwitterPass> implements TwitterPassService {

    @Override
    public TwitterPass getByMatchCode(String machineCode) {
        QueryWrapper<TwitterPass> wrapper = new QueryWrapper<>();
        wrapper.eq("machine_code", machineCode);
        return getOne(wrapper);
    }

    @Override
    public TwitterPass getByWalletId(String walletId) {
        QueryWrapper<TwitterPass> wrapper = new QueryWrapper<>();
        wrapper.eq("wallet_id", walletId);
        return getOne(wrapper);
    }

    @Override
    public void updateByMachineCode(TwitterPass twitterPass) {
        UpdateWrapper<TwitterPass> wrapper = new UpdateWrapper<>();
        wrapper.set("machine_code", twitterPass.getMachineCode());
        wrapper.eq("wallet_id", twitterPass.getWalletId());
        update(wrapper);
    }

    @Override
    public TwitterPass getByMatchCodeAndWalletId(String machineCode, String walletId) {
        QueryWrapper<TwitterPass> wrapper = new QueryWrapper<>();
        if (!ObjectUtils.isEmpty(walletId)) {
            wrapper.eq("wallet_id", walletId);
        }
        if (!ObjectUtils.isEmpty(machineCode)) {
            wrapper.eq("machine_code", machineCode);
        }
        return getOne(wrapper);
    }
}
