package com.narwhal.health.backend.services;

import com.narwhal.health.backend.model.HealthCheck;
import com.narwhal.health.backend.types.HealthStatusType;
import com.narwhal.health.backend.types.ServerType;

import javax.inject.Singleton;
import java.util.Date;

@Singleton
public class HealthCheckFactoryService {
    public HealthCheck buildHealthCheck(ServerType serverType, HealthStatusType healthStatusType, Date date, Integer hour, Integer minute) {
        //
        HealthCheck healthCheck = new HealthCheck();
        //
        healthCheck.init(serverType, date.getTime(), hour, minute);
        healthCheck.setStatusType(healthStatusType);
        //
        return healthCheck;
    }

    public HealthCheck buildHealthCheckForDayAndHour(HealthCheck healthCheckToClone) {
        HealthCheck healthCheck = new HealthCheck();
        //
        healthCheck.init(healthCheckToClone.getServerType(), healthCheckToClone.getDate(),
                healthCheckToClone.getHour());
        healthCheck.setStatusType(healthCheckToClone.getStatusType());
        //
        return healthCheck;
    }

    public HealthCheck buildHealthCheckForDay(HealthCheck healthCheckToClone) {
        HealthCheck healthCheck = new HealthCheck();
        //
        healthCheck.init(healthCheckToClone.getServerType(), healthCheckToClone.getDate());
        healthCheck.setStatusType(healthCheckToClone.getStatusType());
        //
        return healthCheck;
    }
}
