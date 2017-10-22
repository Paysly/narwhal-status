package com.narwhal.health.backend.guice;

import com.narwhal.basics.core.rest.guice.SubModule;
import com.narwhal.health.backend.api.HealthCheckApi;

import java.util.ArrayList;
import java.util.List;

public class BackendModule extends SubModule {

    @Override
    public List<Class<?>> apiClasses() {
        List<Class<?>> list = new ArrayList<>();
        //
        list.add(HealthCheckApi.class);
        //
        return list;
    }
}
