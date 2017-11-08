package com.narwhal.health.backend.guice;

import com.narwhal.basics.core.rest.guice.SubModule;
import com.narwhal.basics.core.rest.model.BaseModel;
import com.narwhal.health.backend.api.HealthCheckApi;
import com.narwhal.health.backend.cron.HealthCheckCronServlet;
import com.narwhal.health.backend.model.HealthCheck;

import javax.servlet.http.HttpServlet;
import java.util.ArrayList;
import java.util.List;

public class BackendModule extends SubModule {
    @Override
    public List<Class<? extends HttpServlet>> cronClasses() {
        List<Class<? extends HttpServlet>> list = new ArrayList<>();
        //
        list.add(HealthCheckCronServlet.class);
        //
        return list;
    }

    @Override
    public List<Class<?>> apiClasses() {
        List<Class<?>> list = new ArrayList<>();
        //
        list.add(HealthCheckApi.class);
        //
        return list;
    }

    @Override
    public List<Class<? extends BaseModel>> objectifyClasses() {
        List<Class<? extends BaseModel>> list = new ArrayList<>();
        //
        list.add(HealthCheck.class);
        //
        return list;
    }
}
