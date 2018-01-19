package com.narwhal.health.backend.cron;

import com.narwhal.basics.core.rest.guice.Cron;
import com.narwhal.basics.core.rest.guice.RelativePath;
import com.narwhal.basics.core.rest.utils.ToStringUtils;
import com.narwhal.health.backend.dto.HealthCheckDTO;
import com.narwhal.health.backend.services.AdminNotificationService;
import com.narwhal.health.backend.services.HealthCheckSaveEachFiveMinutesService;
import com.narwhal.health.backend.services.HealthCheckService;
import lombok.extern.java.Log;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;

@Log
@Singleton
@Cron
@RelativePath("/health/checkfiveminutes")
public class HealthCheckFiveMinutesCronServlet extends HttpServlet {

    @Inject
    private HealthCheckService healthCheckService;
    @Inject
    private AdminNotificationService adminNotificationService;
    @Inject
    private HealthCheckSaveEachFiveMinutesService healthCheckSaveEachFiveMinutesService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        log.log(Level.INFO, "Pinging server status every five minutes");
        HealthCheckDTO healthCheckDTO = healthCheckService.pingServers();
        //
        healthCheckSaveEachFiveMinutesService.saveHealthCheck(healthCheckDTO);
        //
        adminNotificationService.sendEmail(healthCheckDTO);
        //
        log.log(Level.INFO, "Server status: " + ToStringUtils.toString(healthCheckDTO));

    }
}
