package com.narwhal.health.backend.dao;

import com.googlecode.objectify.cmd.Query;
import com.narwhal.basics.core.rest.daos.BaseDaoObjectifyImpl;
import com.narwhal.basics.core.rest.daos.OfyService;
import com.narwhal.basics.core.rest.exceptions.EntityNotFoundException;
import com.narwhal.basics.core.rest.utils.ApiPreconditions;
import com.narwhal.basics.core.rest.utils.DateUtils;
import com.narwhal.health.backend.model.HealthCheck;
import com.narwhal.health.backend.types.HealthCheckType;
import com.narwhal.health.backend.types.ServerType;

import javax.inject.Singleton;
import java.util.List;


@Singleton
public class HealthCheckDaoObjectifyImpl extends BaseDaoObjectifyImpl<HealthCheck> implements HealthCheckDao {

    private static final Integer PAGE_SIZE = 100;

    @Override
    public List<HealthCheck> getByTypeFiveMinutes(ServerType type, long date, int hour) {
        ApiPreconditions.checkNotNull(type, "type");
        //
        Query<HealthCheck> query = OfyService.ofy().load().type(HealthCheck.class);
        //
        query = query.filter("date", date);
        query = query.filter("hour", hour);
        query = query.filter("serverType", type);
        query = query.filter("healthCheckType", HealthCheckType.FIVE_MINUTES);
        //
        return getPagingResult(query, PAGE_SIZE, null).getResultList();
    }

    @Override
    public List<HealthCheck> getByTypeOneHour(ServerType type, long date) {
        ApiPreconditions.checkNotNull(type, "type");
        //
        Query<HealthCheck> query = OfyService.ofy().load().type(HealthCheck.class);
        //
        query = query.filter("date", date);
        query = query.filter("serverType", type);
        query = query.filter("healthCheckType", HealthCheckType.ONE_HOUR);
        //
        return getPagingResult(query, PAGE_SIZE, null).getResultList();
    }

    @Override
    public List<HealthCheck> getByTypeOneDay(ServerType type) {
        ApiPreconditions.checkNotNull(type, "type");
        //
        Query<HealthCheck> query = OfyService.ofy().load().type(HealthCheck.class);
        //
        query = query.filter("date >=", DateUtils.getDayAMonthAgo().getTime());
        query = query.filter("serverType", type);
        query = query.filter("healthCheckType", HealthCheckType.ONE_DAY);
        //
        return getPagingResult(query, PAGE_SIZE, null).getResultList();
    }

    @Override
    public HealthCheck getDailyByTypeAndDate(ServerType serverType, long date) {
        ApiPreconditions.checkNotNull(serverType, "serverType");
        //
        try {
            return this.get(HealthCheck.class, serverType.toString() + "{}" + date);
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    @Override
    public HealthCheck getHourlyByTypeAndDateAndHour(ServerType serverType, long date, int hour) {
        ApiPreconditions.checkNotNull(serverType, "serverType");
        //
        try {
            return this.get(HealthCheck.class, serverType.toString() + "{}" + date + "{}" + hour);
        } catch (EntityNotFoundException e) {
            return null;
        }
    }
}
