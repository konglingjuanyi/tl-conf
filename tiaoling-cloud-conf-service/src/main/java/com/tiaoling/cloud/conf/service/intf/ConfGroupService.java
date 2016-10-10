package com.tiaoling.cloud.conf.service.intf;

import com.tiaoling.cloud.conf.domain.ConfGroup;

import java.util.List;

/**
 * Created by yhl on 2016/10/10.
 */
public interface ConfGroupService {
    List<ConfGroup>  findAll();
    ConfGroup getGroup(String groupKey);
    int addGroup(ConfGroup group);
}
