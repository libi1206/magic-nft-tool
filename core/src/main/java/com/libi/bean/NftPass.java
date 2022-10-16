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
 * 订阅制NFT工具通行证
 * </p>
 *
 * @author libi
 * @since 2022-10-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class NftPass extends Model<NftPass> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Date insertTime;

    private Date updateTime;

    @TableLogic
    private String removeTag;

    /**
     * 用户钱包地址
     */
    private String walletAddress;

    /**
     * 是否为永久通行证
     */
    private Integer permanentTag;

    /**
     * 到期时间
     */
    private Date limitTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
