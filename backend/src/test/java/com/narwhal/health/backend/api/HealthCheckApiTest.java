package com.narwhal.health.backend.api;

import com.narwhal.basics.core.rest.exceptions.api.BadRequestException;
import com.narwhal.health.backend.dto.HealthCheckDTO;
import com.narwhal.health.backend.model.HealthCheck;
import com.narwhal.health.backend.services.HealthCheckService;
import com.narwhal.health.backend.types.HealthStatusType;
import com.narwhal.health.backend.types.ServerType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

public class HealthCheckApiTest {

    @InjectMocks
    private HealthCheckApi healthCheckApi;

    @Mock
    private HealthCheckService healthCheckService;

    @Before
    public void setUp() {
        healthCheckApi = new HealthCheckApi();
        //
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_pingServers() {
        //
        HealthCheckDTO healthCheckDTOExpected = new HealthCheckDTO();
        healthCheckDTOExpected.setAdminServer(HealthStatusType.ONLINE);
        healthCheckDTOExpected.setAuthorizationServer(HealthStatusType.ONLINE);
        healthCheckDTOExpected.setNotificationServer(HealthStatusType.ONLINE);
        //
        when(healthCheckService.pingServers()).thenReturn(healthCheckDTOExpected);
        //
        Response response = healthCheckApi.pingServers();
        assertEquals(healthCheckDTOExpected, response.getEntity());
        //
        Mockito.verify(healthCheckService).pingServers();
    }

    @Test
    public void test_getHistorical() {
        //
        ServerType serverTypeExpected = ServerType.AUTHORIZATION;
        Date date = new Date();
        Integer hour = 10;
        //
        List<HealthCheck> list = new ArrayList<>();
        //
        when(healthCheckService.getHistorical(serverTypeExpected, date.getTime(), hour)).thenReturn(list);
        //
        Response response = healthCheckApi.getHistorical(serverTypeExpected, date.getTime(), hour);
        assertEquals(list, response.getEntity());
        //
        Mockito.verify(healthCheckService).getHistorical(serverTypeExpected, date.getTime(), hour);
    }

    @Test(expected = BadRequestException.class)
    public void test_getHistoricalServerIsNull() {
        ServerType serverTypeExpected = null;
        Date date = new Date();
        Integer hour = 10;
        //
        healthCheckApi.getHistorical(serverTypeExpected, date.getTime(), hour);
        //
        Mockito.verify(healthCheckService, never()).getHistorical(serverTypeExpected, date.getTime(), hour);
    }
}
