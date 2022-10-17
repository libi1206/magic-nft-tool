package com.libi.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    CREATE(0),
    PAYING(1),
    COMPLETED(2)
    ;
    private final Integer code;

}
