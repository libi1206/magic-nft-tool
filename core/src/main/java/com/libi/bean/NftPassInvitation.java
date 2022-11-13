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
 * 通行证邀请码
 * </p>
 *
 * @author libi
 * @since 2022-11-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class NftPassInvitation extends Model<NftPassInvitation> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Date insertTime;

    private Date updateTime;

    @TableLogic
    private String removeTag;

    /**
     * 钱包地址
     */
    private String walletAddress;

    /**
     * 邀请码
     */
    private String invitationCode;

    /**
     * 邀请数量
     */
    private Integer invitationNum;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
