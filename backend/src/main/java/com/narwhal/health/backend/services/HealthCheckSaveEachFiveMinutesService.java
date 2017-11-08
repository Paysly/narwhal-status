package com.narwhal.health.backend.services;

import com.narwhal.basics.core.rest.utils.DateUtils;
import com.narwhal.health.backend.dao.HealthCheckDao;
import com.narwhal.health.backend.dto.HealthCheckDTO;
import com.narwhal.health.backend.model.HealthCheck;
import com.narwhal.health.backend.types.ServerType;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Singleton
public class HealthCheckSaveEachFiveMinutesService {

    @Inject
    private HealthCheckDao healthCheckDao;
    @Inject
    private HealthCheckSaveEachDayService healthCheckSaveEachDayService;
    @Inject
    private HealthCheckSaveEachHourService healthCheckSaveEachHourService;
    @Inject
    private HealthCheckFactoryService healthCheckFactoryService;


    public void saveHealthCheck(HealthCheckDTO healthCheckDTO) {
        //
        Calendar cal = Calendar.getInstance();
        //
        Date date = DateUtils.todayNoTime();
        cal.setTime(date);
        //
        GregorianCalendar now = new GregorianCalendar();
        //
        for (ServerType serverType : ServerType.values()) {
            HealthCheck healthCheck = healthCheckFactoryService.buildHealthCheck(serverType, healthCheckDTO.getServerStatus(serverType),
                    cal.getTime(), now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE));
            healthCheckDao.save(healthCheck);
            //
            // check the hourly report
            healthCheckSaveEachHourService.saveHealthCheck(healthCheck);
            //
            // check the daily report
            healthCheckSaveEachDayService.saveHealthCheck(healthCheck);
        }
    }
}