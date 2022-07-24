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
public class TwitterContent extends Model<TwitterContent> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String userId;

    private String browserSerial;

    private String address;

    private String followBlogger;

    private String followMentions;

    private String tagFriend;

    private String needLike;

    private String reply;

    private String retweet;

    private String quoteRetweet;

    private String tagMethod;

    private String contentFrom;

    private String contentStatus;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
