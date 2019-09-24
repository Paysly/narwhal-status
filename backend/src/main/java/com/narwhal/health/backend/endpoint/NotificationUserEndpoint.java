package com.narwhal.health.backend.endpoint;

import com.google.inject.Inject;
import com.narwhal.basics.core.rest.api.ApiFetchService;
import com.narwhal.basics.core.rest.utils.ApiPreconditions;
import com.narwhal.basics.core.rest.utils.MicroservicesContext;
import com.narwhal.basics.integrations.authorization.client.api.BaseNarwhalApi;
import com.narwhal.basics.integrations.authorization.client.services.AuthorizationService;
import com.narwhal.basics.integrations.notifications.client.dto.users.NotificationUserDTO;
import com.narwhal.basics.integrations.notifications.client.exceptions.UserNotificationUnavailable;

import java.util.ArrayList;
import java.util.List;

public class NotificationUserEndpoint extends BaseNarwhalApi {
    private final String NOTIFICATION_URL;
    private MicroservicesContext microservicesContext;

    @Inject
    public NotificationUserEndpoint(MicroservicesContext microservicesContext, ApiFetchService apiFetchService, AuthorizationService authorizationService) {
        this.microservicesContext = microservicesContext;
        this.apiFetchService = apiFetchService;
        this.authorizationService = authorizationService;
        this.NOTIFICATION_URL = microservicesContext.getNotificationsEndpoint() + "/users/";
    }

    public List<NotificationUserDTO> getUserByEmails(String clientId, ArrayList<String> adminEmails) {
        ApiPreconditions.checkNotNull(clientId, "clientId");
        ApiPreconditions.checkNotNull(adminEmails, "adminEmails");
        String url = this.NOTIFICATION_URL + "emails/";
        try {
            return this.securedPut(clientId, url, adminEmails, ArrayList.class);
        } catch (Exception var6) {
            throw new UserNotificationUnavailable("Failed to fetch Notification NotificationUserDTO", var6);
        }
    }
}
