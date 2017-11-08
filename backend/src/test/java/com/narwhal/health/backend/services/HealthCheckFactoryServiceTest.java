package com.narwhal.health.backend.services;

import com.narwhal.health.backend.model.HealthCheck;
import com.narwhal.health.backend.types.HealthCheckType;
import com.narwhal.health.backend.types.HealthStatusType;
import com.narwhal.health.backend.types.ServerType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.Calendar;
import java.util.Date;

import static junit.framework.TestCase.assertEquals;

public class HealthCheckFactoryServiceTest {


    private HealthCheckFactoryService healthCheckFactoryService;

    @Before
    public void setUp() {
        healthCheckFactoryService = new HealthCheckFactoryService();
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void test_saveHealthCheckByServer() {
        Calendar cal = Calendar.getInstance();
        Date date = com.narwhal.basics.core.rest.utils.DateUtils.todayNoTime();
        cal.setTime(date);
        cal.set(Calendar.MILLISECOND, 0);
        //
        Calendar calMin = Calendar.getInstance();
        Date dateMin = new Date();
        calMin.set(Calendar.MINUTE, dateMin.getMinutes());
        //
        Calendar calHour = Calendar.getInstance();
        Date dateHour = new Date();
        calHour.set(Calendar.HOUR_OF_DAY, dateHour.getHours());
        //
        ServerType serverTypeExpected = ServerType.AUTHORIZATION;
        HealthStatusType healthStatusTypeExpected = HealthStatusType.ONLINE;
        HealthCheckType healthCheckTypeExpected = HealthCheckType.ONE_HOUR;
        Date dateExpected = cal.getTime();
        Integer hourExpected = calHour.get(Calendar.HOUR_OF_DAY);
        Integer minExpected = calMin.get(Calendar.MINUTE);
        //
        HealthCheck healthCheckExpected = new HealthCheck();
        healthCheckExpected.setHealthCheckType(healthCheckTypeExpected);
        healthCheckExpected.init(serverTypeExpected, dateExpected.getTime(), hourExpected, minExpected);
        healthCheckExpected.setStatusType(healthStatusTypeExpected);
        //
        HealthCheck healthCheck = healthCheckFactoryService.buildHealthCheck(serverTypeExpected, healthStatusTypeExpected, dateExpected, hourExpected, minExpected);
        assertEquals(healthCheckExpected.getHealthCheckType(), healthCheck.getHealthCheckType());
        assertEquals(healthCheckExpected.getDate(), healthCheck.getDate());
        assertEquals(healthCheckExpected.getHour(), healthCheck.getHour());
        assertEquals(healthCheckExpected.getMinute(), healthCheck.getMinute());
        assertEquals(healthCheckExpected.getServerType(), healthCheck.getServerType());
        assertEquals(healthCheckExpected.getStatusType(), healthCheck.getStatusType());
    }
}
