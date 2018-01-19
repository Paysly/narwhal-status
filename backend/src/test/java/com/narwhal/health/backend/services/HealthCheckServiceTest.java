package com.narwhal.health.backend.services;

import com.narwhal.basics.core.rest.exceptions.api.BadRequestException;
import com.narwhal.health.backend.dao.HealthCheckDao;
import com.narwhal.health.backend.dto.HealthCheckDTO;
import com.narwhal.health.backend.model.HealthCheck;
import com.narwhal.health.backend.services.api.HealthCheckServiceApi;
import com.narwhal.health.backend.types.HealthCheckType;
import com.narwhal.health.backend.types.HealthStatusType;
import com.narwhal.health.backend.types.ServerType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

public class HealthCheckServiceTest {

    @InjectMocks
    private HealthCheckService service;

    @Mock
    private HealthCheckServiceApi serviceApi;

    @Mock
    private HealthCheckDao healthCheckDao;

    @Before
    public void setUp() {
        service = new HealthCheckService();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_pingServers_mixed() {
        HealthCheckDTO expected = new HealthCheckDTO();
        //
        expected.setAdminServer(HealthStatusType.UNKNOWN);
        expected.setAuthorizationServer(HealthStatusType.ONLINE);
        expected.setNotificationServer(HealthStatusType.UNKNOWN);
        //
        when(serviceApi.pingAdminServer()).thenReturn(HealthStatusType.UNKNOWN);
        when(serviceApi.pingAuthorizationServer()).thenReturn(HealthStatusType.ONLINE);
        when(serviceApi.pingNotificationServer()).thenReturn(HealthStatusType.UNKNOWN);
        //
        HealthCheckDTO result = service.pingServers();
        //
        assertEquals(expected, result);
        //
        Mockito.verify(serviceApi).pingAdminServer();
        Mockito.verify(serviceApi).pingAuthorizationServer();
        Mockito.verify(serviceApi).pingNotificationServer();
    }

    @Test
    public void test_pingServers_unknown() {
        HealthCheckDTO expected = new HealthCheckDTO();
        //
        expected.setAdminServer(HealthStatusType.UNKNOWN);
        expected.setAuthorizationServer(HealthStatusType.UNKNOWN);
        expected.setNotificationServer(HealthStatusType.UNKNOWN);
        //
        when(serviceApi.pingAdminServer()).thenReturn(HealthStatusType.UNKNOWN);
        when(serviceApi.pingAuthorizationServer()).thenReturn(HealthStatusType.UNKNOWN);
        when(serviceApi.pingNotificationServer()).thenReturn(HealthStatusType.UNKNOWN);
        //
        HealthCheckDTO result = service.pingServers();
        //
        assertEquals(expected, result);
        //
        Mockito.verify(serviceApi).pingAdminServer();
        Mockito.verify(serviceApi).pingAuthorizationServer();
        Mockito.verify(serviceApi).pingNotificationServer();
    }

    @Test(expected = BadRequestException.class)
    public void test_getHistorical_null_serverType() {
        service.getHistorical(null, new Date().getTime(), 5);
        //
        Mockito.verify(healthCheckDao, never()).getByTypeOneDay((ServerType) any());
        Mockito.verify(healthCheckDao, never()).getByTypeOneHour((ServerType) any(), (Long) any());
        Mockito.verify(healthCheckDao, never()).getByTypeFiveMinutes((ServerType) any(), (Long) any(), anyInt());
    }

    @Test
    public void test_getHistorical_success_fiveMinutesRequest() {
        Date date = new Date();
        //
        HealthCheck object1 = new HealthCheck();
        object1.setHour(5);
        object1.setMinute(15);
        object1.setHealthCheckType(HealthCheckType.FIVE_MINUTES);
        object1.setServerType(ServerType.ADMIN_PRODUCTION);
        object1.setStatusType(HealthStatusType.UNKNOWN);
        //
        HealthCheck object2 = new HealthCheck();
        object2.setHour(5);
        object2.setMinute(30);
        object2.setHealthCheckType(HealthCheckType.FIVE_MINUTES);
        object2.setServerType(ServerType.ADMIN_PRODUCTION);
        object2.setStatusType(HealthStatusType.UNKNOWN);
        //
        HealthCheck object3 = new HealthCheck();
        object3.setHour(5);
        object3.setMinute(45);
        object3.setHealthCheckType(HealthCheckType.FIVE_MINUTES);
        object3.setServerType(ServerType.ADMIN_PRODUCTION);
        object3.setStatusType(HealthStatusType.UNKNOWN);
        //
        List<HealthCheck> expected = new ArrayList<>();
        expected.add(object1);
        expected.add(object2);
        expected.add(object3);
        //
        when(healthCheckDao.getByTypeFiveMinutes(ServerType.ADMIN_PRODUCTION, date.getTime(), 5)).thenReturn(expected);
        //
        List<HealthCheck> result = service.getHistorical(ServerType.ADMIN_PRODUCTION, date.getTime(), 5);
        //
        assertEquals(result.get(0).getHealthCheckType(), expected.get(0).getHealthCheckType());
        assertEquals(result.get(1).getHealthCheckType(), expected.get(1).getHealthCheckType());
        assertEquals(result.get(2).getHealthCheckType(), expected.get(2).getHealthCheckType());
        //
        assertEquals(result.get(0).getServerType(), expected.get(0).getServerType());
        assertEquals(result.get(1).getServerType(), expected.get(1).getServerType());
        assertEquals(result.get(2).getServerType(), expected.get(2).getServerType());
        //
        assertEquals(result.get(0).getDate(), expected.get(0).getDate());
        assertEquals(result.get(1).getDate(), expected.get(1).getDate());
        assertEquals(result.get(2).getDate(), expected.get(2).getDate());
        //
        assertEquals(result.get(0).getHour(), expected.get(0).getHour());
        assertEquals(result.get(1).getHour(), expected.get(1).getHour());
        assertEquals(result.get(2).getHour(), expected.get(2).getHour());
        //
        Mockito.verify(healthCheckDao).getByTypeFiveMinutes(ServerType.ADMIN_PRODUCTION, date.getTime(), 5);
        //
        Mockito.verify(healthCheckDao, never()).getByTypeOneDay((ServerType) any());
        Mockito.verify(healthCheckDao, never()).getByTypeOneHour((ServerType) any(), anyLong());
    }
}
