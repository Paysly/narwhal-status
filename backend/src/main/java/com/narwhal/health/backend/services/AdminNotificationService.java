package com.narwhal.health.backend.services;

import com.google.inject.Inject;
import com.narwhal.basics.integrations.notifications.client.dto.messages.NotificationForceMessageDTO;
import com.narwhal.basics.integrations.notifications.client.dto.users.NotificationUserDTO;
import com.narwhal.basics.integrations.notifications.client.endpoints.NotificationMessageEndpoint;
import com.narwhal.basics.integrations.notifications.client.endpoints.NotificationUserEndpoint;
import com.narwhal.health.backend.dto.HealthCheckDTO;
import com.narwhal.health.backend.types.HealthStatusType;
import com.narwhal.health.backend.utils.AppClientConstants;
import com.narwhal.health.backend.utils.MicroservicesConstants;

import java.util.HashMap;
import java.util.List;

public class AdminNotificationService {

    @Inject
    private NotificationMessageEndpoint notificationMessageEndpoint;

    @Inject
    private NotificationUserEndpoint notificationUserEndpoint;

    public void sendEmail(HealthCheckDTO healthCheckDTO) {
        //
        HashMap<String, Object> model = new HashMap<>();
        model.put("healthCheckDTO", healthCheckDTO);
        //
        NotificationForceMessageDTO forceMessageDTO = new NotificationForceMessageDTO();
        //
        forceMessageDTO.setVersion(MicroservicesConstants.NOTIFICATIONS_VERSION);
        forceMessageDTO.setNotificationKey(MicroservicesConstants.NOTIFICATIONS_KEY_SERVER_STATUS);
        forceMessageDTO.setTemplateName(MicroservicesConstants.NOTIFICATIONS_TEMPLATE);
        forceMessageDTO.setModel(model);
        //
        if (healthCheckDTO.getAdminServer() == HealthStatusType.UNKNOWN ||
                healthCheckDTO.getAuthorizationServer() == HealthStatusType.UNKNOWN ||
                healthCheckDTO.getNotificationServer() == HealthStatusType.UNKNOWN ||
                //
                healthCheckDTO.getApplicationDevelopmentServer() == HealthStatusType.UNKNOWN ||
                healthCheckDTO.getApplicationBetaServer() == HealthStatusType.UNKNOWN ||
                healthCheckDTO.getApplicationProductionServer() == HealthStatusType.UNKNOWN ||
                //
                healthCheckDTO.getLandingServer() == HealthStatusType.UNKNOWN) {
            //
            List<NotificationUserDTO> list = notificationUserEndpoint.getUsers(AppClientConstants.getAdminClientId(), 100, null, null).getResultList();
            //
            for (NotificationUserDTO l : list) {
                forceMessageDTO.setUserTo(l.getId());
                notificationMessageEndpoint.sendForceMessageNotification(AppClientConstants.getAdminClientId(), forceMessageDTO);
            }
        }
    }
}
