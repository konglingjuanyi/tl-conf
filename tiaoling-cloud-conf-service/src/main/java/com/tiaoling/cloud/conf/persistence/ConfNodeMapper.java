package com.tiaoling.cloud.conf.persistence;

import com.tiaoling.cloud.conf.domain.ConfNode;

import java.util.List;
import java.util.Map;

public interface ConfNodeMapper {
    int insert(ConfNode record);

    int insertSelective(ConfNode record);

    List<ConfNode> pageList(Map<String,Object> params);

    int count(Map<String,Object> params);

    ConfNode selectByPrimaryKey(Map<String,Object> params);

    int update(ConfNode node);

    int updateByPrimaryKeySelective(ConfNode node);
}