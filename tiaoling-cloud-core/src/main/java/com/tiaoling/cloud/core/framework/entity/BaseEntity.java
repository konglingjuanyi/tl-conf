package com.tiaoling.cloud.core.framework.entity;

import java.io.Serializable;

/**
 * Created by yhl on 2016/9/30.
 */
public abstract class BaseEntity  implements Serializable, Cloneable {
    private static final long serialVersionUID = -8171808119275704150L;

    private Integer start;
    private Integer limit;

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
