package com.tiaoling.cloud.conf.service.impl;

import com.tiaoling.cloud.conf.domain.ConfGroup;
import com.tiaoling.cloud.conf.persistence.ConfGroupMapper;
import com.tiaoling.cloud.conf.service.intf.ConfGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yhl on 2016/10/10.
 */
@Service
public class ConfGroupServiceImpl implements ConfGroupService{
    @Autowired(required = false)
    private ConfGroupMapper groupMapper;
    @Override
    public List<ConfGroup> findAll() {
        return groupMapper.findAll();
    }

    @Override
    public ConfGroup getGroup(String groupKey) {
        return groupMapper.selectByPrimaryKey(groupKey);
    }

    @Override
    public int addGroup(ConfGroup group) {
        return groupMapper.insert(group);
    }

    @Override
    public int updateGroup(ConfGroup group) {
        return groupMapper.updateByPrimaryKeySelective(group);
    }
}
