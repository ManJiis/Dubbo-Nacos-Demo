package top.b0x0.admin.common.vo;


import lombok.Data;

import java.io.Serializable;

/**
 * 分页参数 基类
 *
 * @author TANG
 */
@Data
public class PageVo implements Serializable {
    private static final long serialVersionUID = 3469184515892456937L;

    public Integer pageNum = 1;

    public Integer pageSize = 20;

}