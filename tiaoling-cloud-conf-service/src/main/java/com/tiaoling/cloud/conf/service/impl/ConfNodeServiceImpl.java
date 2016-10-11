package com.tiaoling.cloud.conf.service.impl;

import com.tiaoling.cloud.conf.domain.ConfNode;
import com.tiaoling.cloud.conf.persistence.ConfNodeMapper;
import com.tiaoling.cloud.conf.service.intf.ConfNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yhl on 2016/10/10.
 */
@Service
public class ConfNodeServiceImpl implements ConfNodeService{
    @Autowired(required = false)
    private ConfNodeMapper nodeMapper;

    @Override
    public List<ConfNode> pageList(Map<String, Object> params) {
        return nodeMapper.pageList(params);
    }

    @Override
    public int count(Map<String, Object> params) {
        return nodeMapper.count(params);
    }

    @Override
    public ConfNode getConfNode(String groupKey, String nodeKey) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("nodeGroup",groupKey);
        params.put("nodeKey",nodeKey);
        return nodeMapper.selectByPrimaryKey(params);
    }

    @Override
    public int updateConfNode(ConfNode node) {
        return nodeMapper.updateByPrimaryKeySelective(node);
    }

    @Override
    public int insert(ConfNode node) {
        return nodeMapper.insert(node);
    }
}
