package com.libi.bean;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 通行证订单
 * </p>
 *
 * @author libi
 * @since 2022-10-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class NftPassOrder extends Model<NftPassOrder> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Date insertTime;

    private Date updateTime;

    @TableLogic
    private String removeTag;

    /**
     * 用户钱包id
     */
    private String walletAddress;

    /**
     * 用户需要想其支付的目标地址
     */
    private String targetAddress;

    /**
     * 目标数量
     */
    private BigDecimal targetNum;

    /**
     * 单位
     */
    private String unit;

    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 已经支付的价格
     */
    private BigDecimal payedNum;

    /**
     * 购买的等级id
     */
    private Long rankId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
