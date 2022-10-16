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
 * nft支付等级
 * </p>
 *
 * @author libi
 * @since 2022-10-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class NftPassRank extends Model<NftPassRank> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Date insertTime;

    private Date updateTime;

    @TableLogic
    private String removeTag;

    /**
     * 通行证天数
     */
    private Integer days;

    /**
     * 以太币的数量
     */
    private BigDecimal ethNum;

    /**
     * 以太币单位
     */
    private String ethUnit;

    private String descp;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
