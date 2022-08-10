package com.libi.service;

import com.libi.bean.WhiteWallet;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * twitterPass的白名单 服务类
 * </p>
 *
 * @author libi
 * @since 2022-08-11
 */
public interface WhiteWalletService extends IService<WhiteWallet> {

    WhiteWallet getByWalletId(String walletId);
}
