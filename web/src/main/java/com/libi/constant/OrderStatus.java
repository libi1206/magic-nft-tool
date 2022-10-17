package com.libi.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    CREATE(0),
    COMPLETED(1),
    CANCELED(2)
    ;
    private final Integer code;

}
