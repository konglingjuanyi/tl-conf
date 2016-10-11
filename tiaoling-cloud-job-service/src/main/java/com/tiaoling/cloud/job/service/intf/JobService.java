package com.tiaoling.cloud.job.service.intf;

import com.tiaoling.cloud.job.domain.TriggerGroup;

import java.util.List;

/**
 * Created by yhl on 2016/10/11.
 */
public interface JobService {
    List<TriggerGroup> findAllTriggerGroup();
}
