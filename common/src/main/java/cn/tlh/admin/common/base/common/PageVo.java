package cn.tlh.admin.common.base.common;


import java.io.Serializable;

/**
 * 分页参数 基类
 *
 * @author TANG
 */
public class PageVo implements Serializable {

    private static final long serialVersionUID = 3469184515892456937L;
    private int pageNum = 1;

    private int pageSize = 20;

    public int getCurrentPage() {
        return pageNum;
    }

    public void setCurrentPage(int currentPage) {
        this.pageNum = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}