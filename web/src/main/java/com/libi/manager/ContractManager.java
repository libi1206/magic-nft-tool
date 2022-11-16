package com.libi.manager;

import com.libi.contract.NftToolPass;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;

/**
 * @author libi@hyperchain.cn
 * @date 2022/11/16 18:56
 */
@Component
@Data
public class ContractManager {
    @Autowired
    private Web3j web3j;

    @Autowired
    private Credentials adminUserAccount;

    @Autowired
    private NftToolPass nftToolPass;
}
