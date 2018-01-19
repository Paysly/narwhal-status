package com.narwhal.health.backend.services;

import com.narwhal.health.backend.dao.HealthCheckDao;
import com.narwhal.health.backend.dto.HealthCheckDTO;
import com.narwhal.health.backend.model.HealthCheck;
import com.narwhal.health.backend.types.HealthCheckType;
import com.narwhal.health.backend.types.HealthStatusType;
import com.narwhal.health.backend.types.ServerType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Calendar;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

public class HealthCheckSaveEachFiveMinutesServiceTest {

    @InjectMocks
    private HealthCheckSaveEachFiveMinutesService healthCheckSaveEachFiveMinutesService;

    @Mock
    private HealthCheckDao healthCheckDao;
    @Mock
    private HealthCheckSaveEachDayService healthCheckSaveEachDayService;
    @Mock
    private HealthCheckSaveEachHourService healthCheckSaveEachHourService;
    @Mock
    private HealthCheckFactoryService healthCheckFactoryService;

    @Before
    public void setUp() {
        healthCheckSaveEachFiveMinutesService = new HealthCheckSaveEachFiveMinutesService();
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void test_saveHealthCheck_Hour() {
        //
        HealthCheckDTO healthCheckDTOExpected = new HealthCheckDTO();
        healthCheckDTOExpected.setAdminServer(HealthStatusType.ONLINE);
        healthCheckDTOExpected.setAuthorizationServer(HealthStatusType.ONLINE);
        healthCheckDTOExpected.setNotificationServer(HealthStatusType.ONLINE);
        //
        HealthCheckType healthCheckTypeExpected = HealthCheckType.ONE_HOUR;
        //
        Calendar cal = Calendar.getInstance();
        Date date = com.narwhal.basics.core.rest.utils.DateUtils.todayNoTime();
        cal.setTime(date);
        cal.set(Calendar.MILLISECOND, 0);
        //
        Calendar calMin = Calendar.getInstance();
        //
        Calendar calHour = Calendar.getInstance();
        Date dateHour = new Date();
        calHour.set(Calendar.HOUR_OF_DAY, dateHour.getHours());
        //
        ServerType serverTypeExpected = ServerType.AUTHORIZATION;
        HealthStatusType healthStatusTypeExpected = HealthStatusType.ONLINE;
        Date dateExpected = cal.getTime();
        Integer hourExpected = calHour.get(Calendar.HOUR_OF_DAY);
        Integer minExpected = calMin.get(Calendar.MINUTE);
        //
        HealthCheck healthCheckExpected = new HealthCheck();
        healthCheckExpected.setHealthCheckType(healthCheckTypeExpected);
        healthCheckExpected.init(serverTypeExpected, dateExpected.getTime(), hourExpected, minExpected);
        healthCheckExpected.setStatusType(healthStatusTypeExpected);
        //
        healthCheckSaveEachFiveMinutesService.saveHealthCheck(healthCheckDTOExpected);
        //
        Mockito.verify(healthCheckDao, times(7)).save((HealthCheck) any());
    }

    @Test
    public void test_saveHealthCheck_Minutes() {
        //
        HealthCheckDTO healthCheckDTOExpected = new HealthCheckDTO();
        healthCheckDTOExpected.setAdminServer(HealthStatusType.ONLINE);
        healthCheckDTOExpected.setAuthorizationServer(HealthStatusType.ONLINE);
        healthCheckDTOExpected.setNotificationServer(HealthStatusType.ONLINE);
        //
        HealthCheckType healthCheckTypeExpected = HealthCheckType.ONE_HOUR;
        //
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
        //
        ServerType serverTypeExpected = ServerType.AUTHORIZATION;
        HealthStatusType healthStatusTypeExpected = HealthStatusType.ONLINE;
        Date dateExpected = cal.getTime();
        Integer hourExpected = calHour.get(Calendar.HOUR_OF_DAY);
        Integer minExpected = calMin.get(Calendar.MINUTE);
        //
        HealthCheck healthCheckExpected = new HealthCheck();
        healthCheckExpected.setHealthCheckType(healthCheckTypeExpected);
        healthCheckExpected.init(serverTypeExpected, dateExpected.getTime(), hourExpected, minExpected);
        healthCheckExpected.setStatusType(healthStatusTypeExpected);
        //
        healthCheckSaveEachFiveMinutesService.saveHealthCheck(healthCheckDTOExpected);
        //
        Mockito.verify(healthCheckDao, times(7)).save((HealthCheck) any());
    }

    @Test
    public void test_saveHealthCheck() {
        //
        HealthCheckDTO healthCheckDTOExpected = new HealthCheckDTO();
        healthCheckDTOExpected.setAdminServer(HealthStatusType.ONLINE);
        healthCheckDTOExpected.setAuthorizationServer(HealthStatusType.ONLINE);
        healthCheckDTOExpected.setNotificationServer(HealthStatusType.ONLINE);
        //
        HealthCheckType healthCheckTypeExpected = HealthCheckType.ONE_HOUR;
        //
        Calendar cal = Calendar.getInstance();
        Date date = com.narwhal.basics.core.rest.utils.DateUtils.todayNoTime();
        cal.setTime(date);
        cal.set(Calendar.MILLISECOND, 0);
        //
        Calendar calMin = Calendar.getInstance();
        //
        Calendar calHour = Calendar.getInstance();
        //
        ServerType serverTypeExpected = ServerType.AUTHORIZATION;
        HealthStatusType healthStatusTypeExpected = HealthStatusType.ONLINE;
        Date dateExpected = cal.getTime();
        Integer hourExpected = calHour.get(Calendar.HOUR_OF_DAY);
        Integer minExpected = calMin.get(Calendar.MINUTE);
        //
        HealthCheck healthCheckExpected = new HealthCheck();
        healthCheckExpected.setHealthCheckType(healthCheckTypeExpected);
        healthCheckExpected.init(serverTypeExpected, dateExpected.getTime(), hourExpected, minExpected);
        healthCheckExpected.setStatusType(healthStatusTypeExpected);
        //
        healthCheckSaveEachFiveMinutesService.saveHealthCheck(healthCheckDTOExpected);
        //
        Mockito.verify(healthCheckDao, times(7)).save((HealthCheck) any());
    }
}
