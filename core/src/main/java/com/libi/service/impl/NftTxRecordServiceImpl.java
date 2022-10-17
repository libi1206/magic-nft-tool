package com.libi.service.impl;

import com.libi.bean.NftTxRecord;
import com.libi.dao.NftTxRecordMapper;
import com.libi.service.NftTxRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 记录转账给目标账户的所有交易流水，方便查账 服务实现类
 * </p>
 *
 * @author libi
 * @since 2022-10-17
 */
@Service
public class NftTxRecordServiceImpl extends ServiceImpl<NftTxRecordMapper, NftTxRecord> implements NftTxRecordService {

}
