package com.tiaoling.cloud.job.service.impl;

import com.tiaoling.cloud.job.domain.TriggerGroup;
import com.tiaoling.cloud.job.domain.TriggerInfo;
import com.tiaoling.cloud.job.persistence.TriggerGroupMapper;
import com.tiaoling.cloud.job.persistence.TriggerInfoMapper;
import com.tiaoling.cloud.job.service.intf.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by yhl on 2016/10/11.
 */
@Service
public class JobServiceImpl implements JobService {
    @Autowired
    private TriggerGroupMapper triggerGroupMapper;
    @Autowired
    private TriggerInfoMapper triggerInfoMapper;

    @Override
    public List<TriggerGroup> findAllTriggerGroup() {
        return triggerGroupMapper.findAllTriggerGroup();
    }

    @Override
    public List<TriggerInfo> findTriggerInfoPageList(Map<String, Object> params) {
        return triggerInfoMapper.selectPageList(params);
    }

    @Override
    public int findTriggerInfoPageListCount(Map<String, Object> params) {
        return triggerInfoMapper.selectPageListCount(params);
    }
}
