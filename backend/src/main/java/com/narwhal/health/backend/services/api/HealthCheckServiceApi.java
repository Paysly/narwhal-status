package com.narwhal.health.backend.services.api;

import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.inject.Inject;
import com.narwhal.basics.core.rest.api.ApiFetchService;
import com.narwhal.basics.core.rest.utils.MicroservicesContext;
import com.narwhal.health.backend.types.HealthStatusType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HealthCheckServiceApi {


    public static final String HEALTH_ENDPOINT = "/health/";
    private MicroservicesContext microservicesContext;
    private ApiFetchService apiFetchService;

    @Inject
    public HealthCheckServiceApi(MicroservicesContext microservicesContext, ApiFetchService apiFetchService) {
        this.microservicesContext = microservicesContext;
        this.apiFetchService = apiFetchService;
    }

    protected List<HTTPHeader> prepareHeaders() {
        List<HTTPHeader> headerList = new ArrayList();
        headerList.add(new HTTPHeader("Content-type", "application/json"));
        return headerList;
    }

    public HealthStatusType pingAdminServer() {
        try {
            String endpoint = microservicesContext.getAdminEndpoint() + HEALTH_ENDPOINT;
            //
            Map<String, String> params = new HashMap<>();
            this.apiFetchService.fetch(endpoint, HTTPMethod.GET, this.prepareHeaders(), params, null);
            return HealthStatusType.ONLINE;
        } catch (Exception e) {
            return HealthStatusType.UNKNOWN;
        }
    }

    public HealthStatusType pingNotificationServer() {
        try {
            String endpoint = microservicesContext.getNotificationsEndpoint() + HEALTH_ENDPOINT;
            //
            Map<String, String> params = new HashMap<>();
            this.apiFetchService.fetch(endpoint, HTTPMethod.GET, this.prepareHeaders(), params, null);
            return HealthStatusType.ONLINE;
        } catch (Exception e) {
            return HealthStatusType.UNKNOWN;
        }
    }

    public HealthStatusType pingAuthorizationServer() {
        try {
            String endpoint = microservicesContext.getAuthorizationEndpoint() + HEALTH_ENDPOINT;
            //
            Map<String, String> params = new HashMap<>();
            this.apiFetchService.fetch(endpoint, HTTPMethod.GET, this.prepareHeaders(), params, null);
            return HealthStatusType.ONLINE;
        } catch (Exception e) {
            return HealthStatusType.UNKNOWN;
        }
    }
}
