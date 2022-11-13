package com.libi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.libi.bean.NftPassInvitation;
import com.libi.dao.NftPassInvitationMapper;
import com.libi.service.NftPassInvitationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通行证邀请码 服务实现类
 * </p>
 *
 * @author libi
 * @since 2022-11-01
 */
@Service
public class NftPassInvitationServiceImpl extends ServiceImpl<NftPassInvitationMapper, NftPassInvitation> implements NftPassInvitationService {

    @Override
    public NftPassInvitation getByWalletAddress(String walletAddress) {
        QueryWrapper<NftPassInvitation> wrapper = new QueryWrapper<>();
        wrapper.eq("wallet_address", walletAddress);
        return getOne(wrapper);
    }

    @Override
    public NftPassInvitation getByInvitationCode(String invitationCode) {
        QueryWrapper<NftPassInvitation> wrapper = new QueryWrapper<>();
        wrapper.eq("invitation_code", invitationCode);
        return getOne(wrapper);
    }
}
