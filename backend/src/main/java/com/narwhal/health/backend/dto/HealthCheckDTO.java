package com.narwhal.health.backend.dto;

import com.narwhal.health.backend.types.HealthStatusType;

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
}
