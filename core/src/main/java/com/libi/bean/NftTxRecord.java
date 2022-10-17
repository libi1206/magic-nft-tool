package com.libi.bean;

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
 * 记录转账给目标账户的所有交易流水，方便查账
 * </p>
 *
 * @author libi
 * @since 2022-10-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class NftTxRecord extends Model<NftTxRecord> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Date insertTime;

    private Date updateTime;

    @TableLogic
    private String removeTag;

    /**
     * 交易哈希
     */
    private String txHash;

    /**
     * 转账来源
     */
    private String fromAddress;

    /**
     * 转账目标
     */
    private String toAddress;

    /**
     * 交易的原始信息，以JSON的格式保存
     */
    private String txJson;

    /**
     * 转账数量
     */
    private String ethNum;

    /**
     * 转账单位
     */
    private String ethUnit;

    /**
     * 订单id
     */
    private Long orderId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
