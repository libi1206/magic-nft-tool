package com.libi.bean;

import com.baomidou.mybatisplus.extension.activerecord.Model;
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
public class TwitterPass extends Model<TwitterPass> {

    private static final long serialVersionUID = 1L;

    private String machineCode;

    private String walletId;


    @Override
    protected Serializable pkVal() {
        return this.machineCode;
    }

}
