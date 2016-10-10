package com.tiaoling.cloud.conf.domain;

/**
 * Created by yhl on 2016/10/9.
 */
public class ConfGroup {
    private String groupName;
    private String groupKey;
    private Boolean isdelete;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public Boolean getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Boolean isdelete) {
        this.isdelete = isdelete;
    }
}
