package com.libi.service;

import com.libi.bean.NftPassRank;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * nft支付等级 服务类
 * </p>
 *
 * @author libi
 * @since 2022-10-16
 */
public interface NftPassRankService extends IService<NftPassRank> {

    List<NftPassRank> getAllRank();
}
