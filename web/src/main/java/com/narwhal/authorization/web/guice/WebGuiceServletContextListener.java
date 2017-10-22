package com.narwhal.authorization.web.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.narwhal.basics.core.health.guice.HealthStatusModule;
import com.narwhal.basics.core.jobs.guice.JobStatusModule;
import com.narwhal.health.backend.guice.BackendModule;

/**
 * @author Tomas de Priede
 */
public class WebGuiceServletContextListener extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(
                new WebServletModule( //
                        new JobStatusModule(),
                        new HealthStatusModule(),
                        new BackendModule()
                ));
    }
}
