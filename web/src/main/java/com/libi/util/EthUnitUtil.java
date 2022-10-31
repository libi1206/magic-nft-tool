package com.libi.util;

import com.libi.constant.EthUnit;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author libi@hyperchain.cn
 * @date 2022/10/17 9:35 PM
 */
public class EthUnitUtil {
    /**
     * 将不同的货币单位转为 wei
     *
     * @param num
     * @param unit
     * @return
     */
    public static BigInteger castToWei(BigDecimal num, String unit) {
        EthUnit ethUnit = EthUnit.getByName(unit);
        switch (ethUnit) {
            case wei:
                return new BigInteger(num.toString());
            case gwei:
                num = num.multiply(BigDecimal.valueOf(1000000L)).setScale(0);
                return new BigInteger(num.toString());
            case eth:
                num = num.multiply(BigDecimal.valueOf(1000000L)).multiply(BigDecimal.valueOf(1000000L)).setScale(0);
                return new BigInteger(num.toString());
            default:
                throw new RuntimeException("还不支持这样的单位" + ethUnit.name());
        }
    }

    public static void main(String[] args) {
        System.out.println(castToWei(new BigDecimal("0.000020000000000000"), "gwei"));
    }
}
