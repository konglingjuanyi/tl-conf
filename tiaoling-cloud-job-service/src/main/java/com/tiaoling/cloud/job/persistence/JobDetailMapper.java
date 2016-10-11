package com.tiaoling.cloud.job.persistence;

import com.tiaoling.cloud.job.domain.JobDetail;
import com.tiaoling.cloud.job.domain.JobDetailKey;

public interface JobDetailMapper {
    int deleteByPrimaryKey(JobDetailKey key);

    int insert(JobDetail record);

    int insertSelective(JobDetail record);

    JobDetail selectByPrimaryKey(JobDetailKey key);

    int updateByPrimaryKeySelective(JobDetail record);

    int updateByPrimaryKeyWithBLOBs(JobDetail record);

    int updateByPrimaryKey(JobDetail record);
}