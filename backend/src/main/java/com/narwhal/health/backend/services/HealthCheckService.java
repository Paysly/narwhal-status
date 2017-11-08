package com.narwhal.health.backend.services;

import com.narwhal.basics.core.rest.utils.ApiPreconditions;
import com.narwhal.health.backend.dao.HealthCheckDao;
import com.narwhal.health.backend.dto.HealthCheckDTO;
import com.narwhal.health.backend.model.HealthCheck;
import com.narwhal.health.backend.services.api.HealthCheckServiceApi;
import com.narwhal.health.backend.types.ServerType;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class HealthCheckService {

    @Inject
    private HealthCheckServiceApi serviceApi;
    @Inject
    private HealthCheckDao healthCheckDao;

    public HealthCheckDTO pingServers() {
        HealthCheckDTO healthCheckDTO = new HealthCheckDTO();
        //
        healthCheckDTO.setAdminServer(serviceApi.pingAdminServer());
        healthCheckDTO.setAuthorizationServer(serviceApi.pingAuthorizationServer());
        healthCheckDTO.setNotificationServer(serviceApi.pingNotificationServer());
        //
        return healthCheckDTO;
    }

    public List<HealthCheck> getHistorical(ServerType type, Long date, Integer hour) {
        ApiPreconditions.checkNotNull(type, "type");
        //
        List<HealthCheck> historical = new ArrayList<>();
        if (date != null) {
            if (hour != null) {
                historical = healthCheckDao.getByTypeFiveMinutes(type, date, hour);
            } else {
                historical = healthCheckDao.getByTypeOneHour(type, date);
            }
        } else {
            historical = healthCheckDao.getByTypeOneDay(type);
        }
        //
        return historical;
    }
}
