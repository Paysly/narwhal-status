package com.narwhal.health.backend.cron;

import com.narwhal.basics.core.rest.guice.Cron;
import com.narwhal.basics.core.rest.guice.RelativePath;
import com.narwhal.basics.core.rest.utils.ToStringUtils;
import com.narwhal.health.backend.dto.HealthCheckDTO;
import com.narwhal.health.backend.services.HealthCheckSaveEachFiveMinutesService;
import com.narwhal.health.backend.services.HealthCheckService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
@Cron
@RelativePath("/health/check")
public class HealthCheckCronServlet extends HttpServlet {
    @Inject
    private Logger logger;
    @Inject
    private HealthCheckService healthCheckService;
    @Inject
    private HealthCheckSaveEachFiveMinutesService healthCheckSaveEachFiveMinutesService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.log(Level.INFO, "Pinging server status every five minutes");
        HealthCheckDTO healthCheckDTO = healthCheckService.pingServers();
        //
        healthCheckSaveEachFiveMinutesService.saveHealthCheck(healthCheckDTO);
        //
        logger.log(Level.INFO, "Server status: " + ToStringUtils.toString(healthCheckDTO));
    }
}
