package cn.tlh.admin.common.base.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 自定义分页对象
 *
 * @author TANG
 */
@Getter
@Setter
@NoArgsConstructor
public class Page implements Serializable {
    private static final long serialVersionUID = -7947472768702672931L;
    /**
     * 当页数
     */
    private Integer pageNum = 1;
    /**
     * 分页大小
     */
    private Integer pageSize = 20;
    /**
     * 起始位置
     */
    private Integer start;
    /**
     * 结束位置
     */
    private Integer end;

    public Page(int pageNum, int pageSize) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.start = (pageNum - 1) * pageSize;
        this.end = pageNum * pageSize;
    }

}
