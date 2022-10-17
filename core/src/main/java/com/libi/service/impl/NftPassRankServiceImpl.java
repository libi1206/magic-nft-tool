package com.libi.service.impl;

import com.libi.bean.NftPassRank;
import com.libi.dao.NftPassRankMapper;
import com.libi.service.NftPassRankService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * nft支付等级 服务实现类
 * </p>
 *
 * @author libi
 * @since 2022-10-16
 */
@Service
public class NftPassRankServiceImpl extends ServiceImpl<NftPassRankMapper, NftPassRank> implements NftPassRankService {

    @Override
    public List<NftPassRank> getAllRank() {
        return list();
    }
}
