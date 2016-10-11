package com.tiaoling.cloud.job.persistence;

import com.tiaoling.cloud.job.domain.Calendar;
import com.tiaoling.cloud.job.domain.CalendarKey;

public interface CalendarMapper {
    int deleteByPrimaryKey(CalendarKey key);

    int insert(Calendar record);

    int insertSelective(Calendar record);

    Calendar selectByPrimaryKey(CalendarKey key);

    int updateByPrimaryKeySelective(Calendar record);

    int updateByPrimaryKeyWithBLOBs(Calendar record);
}