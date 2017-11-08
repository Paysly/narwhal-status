package com.narwhal.health.backend.services;

import com.narwhal.basics.core.rest.memcached.MemcachedService;
import com.narwhal.basics.core.rest.utils.ApiPreconditions;
import com.narwhal.health.backend.dao.HealthCheckDao;
import com.narwhal.health.backend.model.HealthCheck;
import com.narwhal.health.backend.types.HealthStatusType;
import com.narwhal.health.backend.types.StatusMemcachedKeys;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Date;

@Singleton
public class HealthCheckSaveEachHourService {

    @Inject
    private HealthCheckDao healthCheckDao;
    @Inject
    private MemcachedService memcachedService;
    @Inject
    private HealthCheckFactoryService healthCheckFactoryService;

    /**
     * Check if we are online for this hour
     *
     * @param healthCheck
     */
    public void saveHealthCheck(HealthCheck healthCheck) {
        ApiPreconditions.checkNotNull(healthCheck, "healthCheck");
        //
        String key = String.format(StatusMemcachedKeys.STATUS_HOURLY_CHECK, healthCheck.getServerType().toString(), healthCheck.getDate(), healthCheck.getHour());
        HealthCheck storedHourlyCheck = (HealthCheck) memcachedService.get(key);
        if (storedHourlyCheck == null) {
            storedHourlyCheck = healthCheckDao.getHourlyByTypeAndDateAndHour(healthCheck.getServerType(),
                    healthCheck.getDate(), healthCheck.getHour());
            //
            if (storedHourlyCheck == null) {
                storedHourlyCheck = healthCheckFactoryService.buildHealthCheckForDayAndHour(healthCheck);
                healthCheckDao.save(storedHourlyCheck);
            }
            //
            memcachedService.put(key, storedHourlyCheck);
        }
        //
        if (HealthStatusType.ONLINE.equals(storedHourlyCheck.getStatusType()) && HealthStatusType.UNKNOWN.equals(healthCheck.getStatusType())) {
            // We have online status stored and now we are down for this server. We should update.
            storedHourlyCheck.setStatusType(HealthStatusType.UNKNOWN);
            storedHourlyCheck.setUpdateAt(new Date());
            healthCheckDao.save(storedHourlyCheck);
            memcachedService.put(key, storedHourlyCheck);
        }
        //
        //If status is already unknown we will not update to avoid hide an error until next hour.
    }
}