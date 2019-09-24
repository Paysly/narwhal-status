package com.narwhal.health.backend.services.api;

import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.inject.Inject;
import com.narwhal.basics.core.rest.api.ApiFetchService;
import com.narwhal.basics.core.rest.utils.ToStringUtils;
import com.narwhal.health.backend.types.HealthStatusType;
import com.narwhal.health.backend.utils.BackendMicroserviceContext;
import lombok.extern.java.Log;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log
public class HealthCheckServiceApi {


    public static final String HEALTH_ENDPOINT = "/health/";
    private BackendMicroserviceContext microservicesContext;
    private ApiFetchService apiFetchService;

    @Inject
    public HealthCheckServiceApi(BackendMicroserviceContext microservicesContext, ApiFetchService apiFetchService) {
        this.microservicesContext = microservicesContext;
        this.apiFetchService = apiFetchService;
    }

    protected List<HTTPHeader> prepareHeaders() {
        List<HTTPHeader> headerList = new ArrayList();
        headerList.add(new HTTPHeader("Content-type", "application/json"));
        return headerList;
    }

    public HealthStatusType pingAdminServer() {
        return pingServer(microservicesContext.getAdminEndpoint());
    }

    public HealthStatusType pingNotificationServer() {
        return pingServer(microservicesContext.getNotificationsEndpoint());
    }

    public HealthStatusType pingAuthorizationServer() {
        return pingServer(StringUtils.replace(microservicesContext.getAuthorizationEndpoint(), "/authorization/", ""));
    }

    public HealthStatusType pingLandingServer() {
        return pingPage(microservicesContext.getLandingEndpoint());
    }

    public HealthStatusType pingApplicationDevelopmentServer() {
        return pingServer(microservicesContext.getApplicationDevelopmentEndpoint());
    }

    public HealthStatusType pingApplicationBetaServer() {
        return pingServer(microservicesContext.getApplicationBetaEndpoint());
    }

    public HealthStatusType pingApplicationProductionServer() {
        return pingServer(microservicesContext.getApplicationProductionEndpoint());
    }

    private HealthStatusType pingServer(String endpoint) {
        try {
            endpoint = endpoint + HEALTH_ENDPOINT;
            log.info("Ping Server: " + endpoint);
            //
            Map<String, String> params = new HashMap<>();
            this.apiFetchService.fetch(endpoint, HTTPMethod.GET, this.prepareHeaders(), params, null);
            return HealthStatusType.ONLINE;
        } catch (Exception e) {
            log.info(ToStringUtils.toString(e));
            return HealthStatusType.UNKNOWN;
        }
    }

    private HealthStatusType pingPage(String endpoint) {
        try {
            log.info("Ping page: " + endpoint);
            Map<String, String> params = new HashMap<>();
            this.apiFetchService.fetch(endpoint, HTTPMethod.GET, this.prepareHeaders(), params, null);
            return HealthStatusType.ONLINE;
        } catch (Exception e) {
            log.info(ToStringUtils.toString(e));
            return HealthStatusType.UNKNOWN;
        }
    }
}
