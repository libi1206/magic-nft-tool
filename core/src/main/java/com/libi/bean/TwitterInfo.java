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
public class TwitterInfo extends Model<TwitterInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String userId;

    private String browserSerial;

    private String twitterUsername;

    private String twitterPassword;

    private String twitterEmail;

    private String twitterPhone;

    private String twitterLoginStatus;

    private String twitterNickname;

    private String twitterBio;

    private String twitterLocation;

    private String twitterAvatar;

    private String twitterBkg;

    private String twitterNewUsername;

    private String twitterNewPassword;

    private String twitterUsernameStatus;

    private String twitterPasswordStatus;

    private String twitterProfileStatus;

    private String lastTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
