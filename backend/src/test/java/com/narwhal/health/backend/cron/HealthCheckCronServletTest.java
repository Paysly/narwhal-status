package com.narwhal.health.backend.cron;

import com.narwhal.basics.core.rest.utils.ToStringUtils;
import com.narwhal.health.backend.dto.HealthCheckDTO;
import com.narwhal.health.backend.services.HealthCheckSaveEachFiveMinutesService;
import com.narwhal.health.backend.services.HealthCheckService;
import com.narwhal.health.backend.types.HealthStatusType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.mockito.Mockito.when;

public class HealthCheckCronServletTest {

    @InjectMocks
    private HealthCheckFiveMinutesCronServlet healthCheckFiveMinutesCronServlet;

    @Mock
    private Logger logger;
    @Mock
    private HealthCheckService healthCheckService;
    @Mock
    private HealthCheckSaveEachFiveMinutesService healthCheckSaveEachFiveMinutesService;

    @Before
    public void setUp() {
        healthCheckFiveMinutesCronServlet = new HealthCheckFiveMinutesCronServlet();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_doGet() throws ServletException, IOException {
        //
        HealthCheckDTO healthCheckDTOExpected = new HealthCheckDTO();
        healthCheckDTOExpected.setAdminServer(HealthStatusType.ONLINE);
        healthCheckDTOExpected.setAuthorizationServer(HealthStatusType.ONLINE);
        healthCheckDTOExpected.setNotificationServer(HealthStatusType.ONLINE);
        //
        when(healthCheckService.pingServers()).thenReturn(healthCheckDTOExpected);
        //
        healthCheckFiveMinutesCronServlet.doGet(null, null);
        //
        Mockito.verify(healthCheckSaveEachFiveMinutesService).saveHealthCheck(healthCheckDTOExpected);
        Mockito.verify(logger).log(Level.INFO, "Server status: " + ToStringUtils.toString(healthCheckDTOExpected));
        Mockito.verify(logger).log(Level.INFO, "Pinging server status every five minutes");
    }
}
