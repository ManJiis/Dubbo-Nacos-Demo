package cn.tlh.ex.common.vo.req.common;


import java.io.Serializable;

/**
 * 分页参数 基类
 * @author TANG
 */
public class PageVo implements Serializable {

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