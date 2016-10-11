package com.tiaoling.cloud.conf.service.intf;

import com.tiaoling.cloud.conf.domain.ConfNode;

import java.util.List;
import java.util.Map;

/**
 * Created by yhl on 2016/10/10.
 */
public interface ConfNodeService {
    List<ConfNode> pageList(Map<String,Object> params);

    int count(Map<String,Object> params);

    ConfNode getConfNode(String groupKey,String nodeKey);

    int updateConfNode(ConfNode node);

    int insert(ConfNode node);
}
