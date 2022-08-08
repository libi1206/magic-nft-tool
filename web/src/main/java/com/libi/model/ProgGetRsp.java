package com.libi.model;

import com.libi.bean.Program;
import lombok.Data;

/**
 * @author libi@hyperchain.cn
 * @date 2022/8/8 3:01 PM
 */
@Data
public class ProgGetRsp {
    private String downloadUrl;
    private String version;

    public static ProgGetRsp of(Program program,String downloadUrl) {
        ProgGetRsp progGetRsp = new ProgGetRsp();
        progGetRsp.downloadUrl = downloadUrl;
        progGetRsp.version = program.getProgramVersion();
        return progGetRsp;
    }
}
