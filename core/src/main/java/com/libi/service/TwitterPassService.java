package com.libi.service;

import com.libi.bean.TwitterPass;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author libi
 * @since 2022-07-24
 */
public interface TwitterPassService extends IService<TwitterPass> {

    TwitterPass getByMatchCode(String machineCode);

    TwitterPass getByWalletId(String walletId);

    void updateByMachineCode(TwitterPass twitterPass);

    TwitterPass getByMatchCodeAndWalletId(String machineCode, String walletId);
}