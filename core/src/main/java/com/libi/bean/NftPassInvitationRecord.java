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
 * 通行证邀请记录
记录谁，什么时候，邀请了谁
 * </p>
 *
 * @author libi
 * @since 2022-11-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class NftPassInvitationRecord extends Model<NftPassInvitationRecord> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Date insertTime;

    private Date updateTime;

    @TableLogic
    private String removeTag;

    /**
     * 邀请人的地址
     */
    private String inviterAddress;

    /**
     * 被邀请者的地址
     */
    private String inviteeAddress;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
