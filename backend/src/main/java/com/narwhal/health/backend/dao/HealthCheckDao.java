package com.narwhal.health.backend.dao;

import com.google.inject.ImplementedBy;
import com.narwhal.basics.core.rest.daos.BaseDao;
import com.narwhal.health.backend.model.HealthCheck;
import com.narwhal.health.backend.types.ServerType;

import java.util.List;

@ImplementedBy(HealthCheckDaoObjectifyImpl.class)
public interface HealthCheckDao extends BaseDao<HealthCheck> {

    List<HealthCheck> getByTypeFiveMinutes(ServerType type, long date, int hour);

    List<HealthCheck> getByTypeOneHour(ServerType type, long date);

    List<HealthCheck> getByTypeOneDay(ServerType type);

    HealthCheck getDailyByTypeAndDate(ServerType serverType, long date);

    HealthCheck getHourlyByTypeAndDateAndHour(ServerType serverType, long date, int hour);
}
