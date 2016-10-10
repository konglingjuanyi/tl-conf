package com.tiaoling.cloud.conf.service.impl;

import com.tiaoling.cloud.conf.domain.ConfNode;
import com.tiaoling.cloud.conf.persistence.ConfNodeMapper;
import com.tiaoling.cloud.conf.service.intf.ConfNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
