package com.libi.service;

import com.libi.bean.NftPass;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订阅制NFT工具通行证 服务类
 * </p>
 *
 * @author libi
 * @since 2022-10-16
 */
public interface NftPassService extends IService<NftPass> {

    NftPass queryPass(String walletAddress);
}
