package com.narwhal.health.backend.services;

import com.narwhal.basics.core.rest.utils.SharedConstants;
import com.narwhal.basics.integrations.notifications.client.dto.messages.NotificationForceMessageDTO;
import com.narwhal.basics.integrations.notifications.client.dto.users.NotificationUserDTO;
import com.narwhal.basics.integrations.notifications.client.endpoints.NotificationMessageEndpoint;
import com.narwhal.health.backend.dto.HealthCheckDTO;
import com.narwhal.health.backend.endpoint.NotificationUserEndpoint;
import com.narwhal.health.backend.types.HealthStatusType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.narwhal.health.backend.utils.MicroservicesConstants.ADMIN_EMAILS;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class AdminNotificationServiceTest {

    @InjectMocks
    private AdminNotificationService adminNotificationService;

    @Mock
    private NotificationUserEndpoint notificationUserEndpoint;
    @Mock
    private NotificationMessageEndpoint notificationMessageEndpoint;

    @Before
    public void setUp() {
        SharedConstants.junit = true;
        adminNotificationService = new AdminNotificationService();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_sendEmail_AllOnline() {
        HealthCheckDTO healthCheckDTO = new HealthCheckDTO();
        healthCheckDTO.setAdminServer(HealthStatusType.ONLINE);
        healthCheckDTO.setApplicationBetaServer(HealthStatusType.ONLINE);
        healthCheckDTO.setApplicationDevelopmentServer(HealthStatusType.ONLINE);
        healthCheckDTO.setApplicationProductionServer(HealthStatusType.ONLINE);
        healthCheckDTO.setAuthorizationServer(HealthStatusType.ONLINE);
        healthCheckDTO.setLandingServer(HealthStatusType.ONLINE);
        healthCheckDTO.setNotificationServer(HealthStatusType.ONLINE);
        ArrayList<String> adminEmails = new ArrayList<>(Arrays.asList(ADMIN_EMAILS));
        //
        adminNotificationService.sendEmail(healthCheckDTO);
        //
        Mockito.verify(notificationUserEndpoint, never()).getUserByEmails(anyString(), eq(adminEmails));
        Mockito.verify(notificationMessageEndpoint, never()).sendForceMessageNotification(anyString(), any(NotificationForceMessageDTO.class));
    }

    @Test
    public void test_sendEmail() {
        HealthCheckDTO healthCheckDTO = new HealthCheckDTO();
        healthCheckDTO.setAdminServer(HealthStatusType.ONLINE);
        healthCheckDTO.setApplicationBetaServer(HealthStatusType.ONLINE);
        healthCheckDTO.setApplicationDevelopmentServer(HealthStatusType.ONLINE);
        healthCheckDTO.setApplicationProductionServer(HealthStatusType.ONLINE);
        healthCheckDTO.setAuthorizationServer(HealthStatusType.UNKNOWN);
        healthCheckDTO.setLandingServer(HealthStatusType.ONLINE);
        healthCheckDTO.setNotificationServer(HealthStatusType.ONLINE);
        ArrayList<String> adminEmails = new ArrayList<>(Arrays.asList(ADMIN_EMAILS));
        //
        List<NotificationUserDTO> users = new ArrayList<>();
        users.add(new NotificationUserDTO());
        users.add(new NotificationUserDTO());
        //
        when(notificationUserEndpoint.getUserByEmails(anyString(), eq(adminEmails))).thenReturn(users);
        //
        adminNotificationService.sendEmail(healthCheckDTO);
        //
        Mockito.verify(notificationUserEndpoint).getUserByEmails(anyString(), eq(adminEmails));
        Mockito.verify(notificationMessageEndpoint, times(2)).sendForceMessageNotification(anyString(), any(NotificationForceMessageDTO.class));
    }

    @Test
    public void test_sendEmail_UsersEmpty() {
        HealthCheckDTO healthCheckDTO = new HealthCheckDTO();
        healthCheckDTO.setAdminServer(HealthStatusType.ONLINE);
        healthCheckDTO.setApplicationBetaServer(HealthStatusType.ONLINE);
        healthCheckDTO.setApplicationDevelopmentServer(HealthStatusType.ONLINE);
        healthCheckDTO.setApplicationProductionServer(HealthStatusType.ONLINE);
        healthCheckDTO.setAuthorizationServer(HealthStatusType.UNKNOWN);
        healthCheckDTO.setLandingServer(HealthStatusType.ONLINE);
        healthCheckDTO.setNotificationServer(HealthStatusType.ONLINE);
        ArrayList<String> adminEmails = new ArrayList<>(Arrays.asList(ADMIN_EMAILS));
        //
        List<NotificationUserDTO> users = new ArrayList<>();
        //
        when(notificationUserEndpoint.getUserByEmails(anyString(), eq(adminEmails))).thenReturn(users);
        //
        adminNotificationService.sendEmail(healthCheckDTO);
        //
        Mockito.verify(notificationUserEndpoint).getUserByEmails(anyString(), eq(adminEmails));
        Mockito.verify(notificationMessageEndpoint, never()).sendForceMessageNotification(anyString(), any(NotificationForceMessageDTO.class));
    }

}
