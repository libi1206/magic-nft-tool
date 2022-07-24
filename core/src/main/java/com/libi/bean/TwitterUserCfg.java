package com.libi.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author libi
 * @since 2022-07-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TwitterUserCfg extends Model<TwitterUserCfg> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String userId;

    private String userDataDir;

    private String maxNum;

    private Boolean willSwitch;

    private String retryTime;

    private String twitterTxt;

    private String twitterPic;

    private String commentTxt;

    private String quoteTxt;

    private Boolean loginBan;

    private Boolean loginValid;

    private Boolean loginUnlogin;

    private Boolean loginBlank;

    private Boolean keepBak;

    private Boolean releaseIp;

    private String avaPic;

    private String bakPic;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
