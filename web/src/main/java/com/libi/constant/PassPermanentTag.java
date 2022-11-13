package com.libi.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author libi@hyperchain.cn
 * @date 2022/10/31 3:29 PM
 * 通行证是否是永久通行证
 */
@Getter
@AllArgsConstructor
public enum PassPermanentTag {
    NORMAL(0, "普通定时到期通行证"),
    PERMANENT(1, "永久通行证"),
    ;
    private final Integer code;
    private final String msg;
}
