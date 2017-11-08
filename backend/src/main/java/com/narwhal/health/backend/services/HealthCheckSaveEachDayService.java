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
public class HealthCheckSaveEachDayService {

    @Inject
    private HealthCheckDao healthCheckDao;
    @Inject
    private HealthCheckFactoryService healthCheckFactoryService;
    @Inject
    private MemcachedService memcachedService;

    /**
     * Check if we are online for this hour
     *
     * @param healthCheck
     */
    public void saveHealthCheck(HealthCheck healthCheck) {
        ApiPreconditions.checkNotNull(healthCheck, "healthCheck");
        //
        String key = String.format(StatusMemcachedKeys.STATUS_DAILY_CHECK, healthCheck.getServerType().toString(), healthCheck.getDate());
        HealthCheck storedDailyCheck = (HealthCheck) memcachedService.get(key);
        if (storedDailyCheck == null) {
            storedDailyCheck = healthCheckDao.getDailyByTypeAndDate(healthCheck.getServerType(), healthCheck.getDate());
            //
            if (storedDailyCheck == null) {
                storedDailyCheck = healthCheckFactoryService.buildHealthCheckForDay(healthCheck);
                healthCheckDao.save(storedDailyCheck);
            }
            //
            memcachedService.put(key, storedDailyCheck);
        }
        //
        if (HealthStatusType.ONLINE.equals(storedDailyCheck.getStatusType()) && HealthStatusType.UNKNOWN.equals(healthCheck.getStatusType())) {
            // We have online status stored and now we are down for this server. We should update.
            storedDailyCheck.setStatusType(HealthStatusType.UNKNOWN);
            healthCheckDao.save(storedDailyCheck);
            storedDailyCheck.setUpdateAt(new Date());
            memcachedService.put(key, storedDailyCheck);
        }
        //
        //If status is already unknown we will not update to avoid hide an error until next hour.
    }
}