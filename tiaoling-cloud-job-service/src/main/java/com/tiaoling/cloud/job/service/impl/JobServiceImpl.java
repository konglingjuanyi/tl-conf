package com.tiaoling.cloud.job.service.impl;

import com.tiaoling.cloud.job.domain.TriggerGroup;
import com.tiaoling.cloud.job.persistence.TriggerGroupMapper;
import com.tiaoling.cloud.job.service.intf.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yhl on 2016/10/11.
 */
@Service
public class JobServiceImpl implements JobService {
    @Autowired
    private TriggerGroupMapper groupMapper;

    @Override
    public List<TriggerGroup> findAllTriggerGroup() {
        return null;
    }
}
