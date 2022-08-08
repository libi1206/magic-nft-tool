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
 * nft小程序版本管理
 * </p>
 *
 * @author libi
 * @since 2022-08-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Program extends Model<Program> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Date insertTime;

    private Date updateTime;

    @TableLogic
    private String removeTag;

    /**
     * 小程序名称
     */
    private String programName;

    /**
     * 小程序版本
     */
    private String programVersion;

    /**
     * 在minion中对象名字
     */
    private String minioObjectName;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
