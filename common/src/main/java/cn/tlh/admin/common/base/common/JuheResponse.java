package cn.tlh.admin.common.base.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author musui
 * @description 聚合api响应数据格式
 */
@Data
public class JuheResponse implements Serializable {
    private static final long serialVersionUID = 2347059227849421617L;
    private String reason;
    private Object result;
    private Integer error_code;
}
