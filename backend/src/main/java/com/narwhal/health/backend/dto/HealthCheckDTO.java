package com.narwhal.health.backend.dto;

import com.narwhal.basics.core.rest.exceptions.api.BadRequestException;
import com.narwhal.health.backend.types.HealthStatusType;
import com.narwhal.health.backend.types.ServerType;
import org.apache.commons.lang.builder.EqualsBuilder;

import java.io.Serializable;

public class HealthCheckDTO implements Serializable {

    private HealthStatusType adminServer;
    private HealthStatusType notificationServer;
    private HealthStatusType authorizationServer;

    public HealthStatusType getAdminServer() {
        return adminServer;
    }

    public void setAdminServer(HealthStatusType adminServer) {
        this.adminServer = adminServer;
    }

    public HealthStatusType getNotificationServer() {
        return notificationServer;
    }

    public void setNotificationServer(HealthStatusType notificationServer) {
        this.notificationServer = notificationServer;
    }

    public HealthStatusType getAuthorizationServer() {
        return authorizationServer;
    }

    public void setAuthorizationServer(HealthStatusType authorizationServer) {
        this.authorizationServer = authorizationServer;
    }

    public HealthStatusType getServerStatus(ServerType serverType) {
        switch (serverType) {
            case ADMIN:
                return getAdminServer();
            case NOTIFICATION:
                return getNotificationServer();
            case AUTHORIZATION:
                return getAuthorizationServer();
        }
        throw new BadRequestException("Bad server type: " + serverType);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
