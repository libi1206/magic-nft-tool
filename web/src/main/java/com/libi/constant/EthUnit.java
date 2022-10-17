package com.libi.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author libi@hyperchain.cn
 * @date 2022/10/17 9:35 PM
 */
public enum EthUnit {
    wei,         // wei
    gwei,        // 10^9 wei
    ether,       // 10^18 wei
    ;

    public static EthUnit getByName(String name) {
        for (EthUnit value : values()) {
            if (name.equalsIgnoreCase(value.name())) {
                return value;
            }
        }
        throw new RuntimeException("不支持这样的货币单位： " + name);
    }

}
