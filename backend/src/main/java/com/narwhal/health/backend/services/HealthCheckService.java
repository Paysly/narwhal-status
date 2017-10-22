package com.narwhal.health.backend.services;

import com.narwhal.health.backend.dto.HealthCheckDTO;
import com.narwhal.health.backend.services.api.HealthCheckServiceApi;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class HealthCheckService {

    @Inject
    private HealthCheckServiceApi serviceApi;

    public HealthCheckDTO pingServers() {
        HealthCheckDTO healthCheckDTO = new HealthCheckDTO();
        //
        healthCheckDTO.setAdminServer(serviceApi.pingAdminServer());
        healthCheckDTO.setAuthorizationServer(serviceApi.pingAuthorizationServer());
        healthCheckDTO.setNotificationServer(serviceApi.pingNotificationServer());
        //
        return healthCheckDTO;
    }
}
