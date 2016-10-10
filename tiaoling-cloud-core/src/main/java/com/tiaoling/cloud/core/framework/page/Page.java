package com.tiaoling.cloud.core.framework.page;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yhl on 2016/9/30.
 */
public class Page<T> implements Serializable {
    private static final long serialVersionUID = -8123039538589235324L;

    /** 当前页 */
    protected int currentPage = 1;

    /** 每页记录数，默认10条 */
    protected int pageSize = 10;

    /** 记录总数 */
    protected long pageCount;

    /** 页数 */
    protected int pages;

    /** 当前页的起始索引,从1开始 */
    protected int start = 1;

    /** mysql 分页 */
    protected int limit=10;
    /**
     * 查询列表
     */
    private List<T> list;

    /**
     * 获取当前页
     *
     * @return 当前页
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * 设置当前页
     *
     * @param currentPage
     *            当前页
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * 获取每页记录数
     *
     * @return 每页记录数
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页记录数
     *
     * @param pageSize
     *            每页记录数
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 获取记录总数
     *
     * @return 记录总数
     */
    public long getPageCount() {
        return pageCount;
    }

    /**
     * 设置记录总数
     *
     * @param pageCount
     *            记录总数
     */
    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;

        if (pageCount > 0) {
            // 计算页数
            this.pages = (int) (this.pageCount / this.pageSize);

            if (this.pageCount % this.pageSize > 0) {
                this.pages++;
            }

            // 调整当前页
            if (this.currentPage > this.pages) {
                this.currentPage = this.pages;
            }

            // 计算当前页的索引
            this.start = (this.currentPage - 1) * this.pageSize;
            this.limit=this.pageSize;
        }
    }

    /**
     * 获取页数
     *
     * @return 页数
     */
    public int getPages() {
        return pages;
    }

    /**
     * @Return the List<T> list
     */
    public List<T> getList() {
        return list;
    }

    /**
     * @Param List<T> list to set
     */
    public void setList(List<T> list) {
        this.list = list;
    }

    /**
     * @Return the int start
     */
    public int getStart() {
        return start;
    }

    /**
     * @Param int start to set
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * @Return the int limit
     */
    public int getLimit() {
        return limit;
    }

    /**
     * @Param int limit to set
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }
}
