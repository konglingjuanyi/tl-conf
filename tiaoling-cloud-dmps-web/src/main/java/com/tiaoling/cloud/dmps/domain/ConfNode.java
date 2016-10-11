package com.tiaoling.cloud.dmps.domain;

/**
 * Created by yhl on 2016/10/10.
 */
public class ConfNode {
    private String nodeGroup;

    private String nodeKey;

    private String nodeValue;

    private String nodeDesc;

    private Boolean isdelete;

    private String groupKey;		// key of prop [in zk]

    private String nodeValueReal; 	// value of prop [in zk]

    public String getNodeGroup() {
        return nodeGroup;
    }

    public void setNodeGroup(String nodeGroup) {
        this.nodeGroup = nodeGroup;
    }

    public String getNodeKey() {
        return nodeKey;
    }

    public void setNodeKey(String nodeKey) {
        this.nodeKey = nodeKey;
    }

    public String getNodeValue() {
        return nodeValue;
    }

    public void setNodeValue(String nodeValue) {
        this.nodeValue = nodeValue;
    }

    public String getNodeDesc() {
        return nodeDesc;
    }

    public void setNodeDesc(String nodeDesc) {
        this.nodeDesc = nodeDesc;
    }

    public Boolean getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Boolean isdelete) {
        this.isdelete = isdelete;
    }

    public String getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getNodeValueReal() {
        return nodeValueReal;
    }

    public void setNodeValueReal(String nodeValueReal) {
        this.nodeValueReal = nodeValueReal;
    }
}
