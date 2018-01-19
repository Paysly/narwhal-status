package com.narwhal.health.backend.model;

import com.googlecode.objectify.annotation.*;
import com.googlecode.objectify.condition.IfDefault;
import com.narwhal.basics.core.rest.model.BaseModel;
import com.narwhal.basics.core.rest.utils.ApiPreconditions;
import com.narwhal.basics.core.rest.utils.ToStringUtils;
import com.narwhal.health.backend.types.HealthCheckType;
import com.narwhal.health.backend.types.HealthStatusType;
import com.narwhal.health.backend.types.ServerType;
import lombok.Data;

import java.util.Date;

@Data
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

}
