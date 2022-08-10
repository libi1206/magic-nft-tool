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
 * twitterPass的白名单
 * </p>
 *
 * @author libi
 * @since 2022-08-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WhiteWallet extends Model<WhiteWallet> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Date insertTime;

    private Date updateTime;

    @TableLogic
    private String removeTag;

    /**
     * 白名单的钱包id
     */
    private String walletId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
