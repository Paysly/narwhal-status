package com.narwhal.health.backend.model;

import com.googlecode.objectify.annotation.*;
import com.googlecode.objectify.condition.IfDefault;
import com.narwhal.basics.core.rest.model.BaseModel;
import com.narwhal.basics.core.rest.utils.ApiPreconditions;
import com.narwhal.basics.core.rest.utils.ToStringUtils;
import com.narwhal.health.backend.types.HealthCheckType;
import com.narwhal.health.backend.types.HealthStatusType;
import com.narwhal.health.backend.types.ServerType;

import java.util.Date;

@Entity
@Index
public class HealthCheck implements BaseModel {

    /**
     * If is a day only
     * SERVER_TYPE{}date
     * <p>
     * If is a each hour
     * SERVER_TYPE{}date{}hour
     * If is a five minutes
     * SERVER_TYPE{}date{}hour{}minute
     */
    @Id
    private String id;

    private HealthStatusType statusType;

    private ServerType serverType;

    private HealthCheckType healthCheckType;

    /**
     * This is only the date. There is no time (hours/minutes/seconds) in this field.
     */
    private long date;

    @IgnoreSave(IfDefault.class)
    private Integer hour;

    @IgnoreSave(IfDefault.class)
    @Unindex
    private Integer minute;

    private Date createdAt;

    @Unindex
    private Date updateAt;

    public HealthStatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(HealthStatusType statusType) {
        this.statusType = statusType;
    }

    public ServerType getServerType() {
        return serverType;
    }

    public void setServerType(ServerType serverType) {
        this.serverType = serverType;
    }

    public HealthCheckType getHealthCheckType() {
        return healthCheckType;
    }

    public void setHealthCheckType(HealthCheckType healthCheckType) {
        this.healthCheckType = healthCheckType;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public void init(ServerType serverType, long date) {
        init(serverType, date, null, null);
    }

    public void init(ServerType serverType, long date, Integer hour) {
        init(serverType, date, hour, null);
    }

    public void init(ServerType serverType, long date, Integer hour, Integer minute) {
        ApiPreconditions.checkNotNull(serverType, "serverType");
        ApiPreconditions.checkNotNull(date, "date");
        //
        this.id = serverType.toString() + "{}" + date;
        this.healthCheckType = HealthCheckType.ONE_DAY;
        //
        if (hour != null) {
            this.id += "{}" + hour;
            this.healthCheckType = HealthCheckType.ONE_HOUR;
            //
            if (minute != null) {
                this.healthCheckType = HealthCheckType.FIVE_MINUTES;
                this.id += "{}" + minute;
            }
        }
        //
        this.serverType = serverType;
        this.date = date;
        this.hour = hour;
        this.minute = minute;
        //
        this.createdAt = new Date();
        this.updateAt = this.createdAt;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return ToStringUtils.toString(this);
    }
}
