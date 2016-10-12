package com.tiaoling.cloud.job.service.intf;

import com.tiaoling.cloud.job.domain.TriggerGroup;
import com.tiaoling.cloud.job.domain.TriggerInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by yhl on 2016/10/11.
 */
public interface JobService {
    List<TriggerGroup> findAllTriggerGroup();

    List<TriggerInfo> findTriggerInfoPageList(Map<String,Object> params);

    int findTriggerInfoPageListCount(Map<String,Object> params);
}
