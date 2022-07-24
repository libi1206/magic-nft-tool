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
public class BrowserInfo extends Model<BrowserInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String userId;

    private String browserSerial;

    private String bscAddr;

    private String bscWords;

    private String bscPwd;

    private String solAddr;

    private String solWords;

    private String solPwd;

    private String proxyAddr;

    private String proxyPort;

    private String proxyUsername;

    private String proxyPwd;

    private String browserStatus;

    private String lastTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
