package com.narwhal.health.backend.dto;

import com.narwhal.basics.core.rest.exceptions.api.BadRequestException;
import com.narwhal.health.backend.types.HealthStatusType;
import com.narwhal.health.backend.types.ServerType;
import lombok.Data;
import lombok.extern.java.Log;
import org.apache.commons.lang.builder.EqualsBuilder;

import java.io.Serializable;

@Data
public class HealthCheckDTO implements Serializable {

    private HealthStatusType adminServer;
    private HealthStatusType notificationServer;
    private HealthStatusType authorizationServer;
    //
    private HealthStatusType applicationDevelopmentServer;
    private HealthStatusType applicationBetaServer;
    private HealthStatusType applicationProductionServer;
    //
    private HealthStatusType landingServer;
    //

    public HealthStatusType getServerStatus(ServerType serverType) {
        switch (serverType) {
            case ADMIN_PRODUCTION:
                return getAdminServer();
            case NOTIFICATION:
                return getNotificationServer();
            case AUTHORIZATION:
                return getAuthorizationServer();
            case APPLICATION_DEVELOPMENT:
                return getApplicationDevelopmentServer();
            case APPLICATION_BETA:
                return getApplicationBetaServer();
            case APPLICATION_PRODUCTION:
                return getApplicationProductionServer();
            case LANDING:
                return getLandingServer();
        }
        throw new BadRequestException("Bad server type: " + serverType);
    }

}
