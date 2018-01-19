package com.narwhal.authorization.web.guice;

import com.google.inject.Provides;
import com.narwhal.authorization.web.index.IndexServlet;
import com.narwhal.authorization.web.utils.AppMicroservicesContext;
import com.narwhal.authorization.web.utils.MicroservicesConstants;
import com.narwhal.basics.core.rest.guice.BaseWebServletModule;
import com.narwhal.basics.core.rest.guice.SubModule;
import com.narwhal.basics.core.rest.utils.MicroservicesContext;
import com.narwhal.basics.core.rest.utils.SharedConstants;
import com.narwhal.health.backend.utils.BackendMicroserviceContext;

import javax.inject.Singleton;

import static com.narwhal.authorization.web.utils.MicroservicesConstants.*;

/**
 * @author Tomas de Priede
 */
public class WebServletModule extends BaseWebServletModule {

    private AppMicroservicesContext microservicesContext;

    public WebServletModule(SubModule... servletModuleList) {
        super(servletModuleList);
        //
        initMicroserviceContext();
    }

    private void initMicroserviceContext() {
        microservicesContext = new AppMicroservicesContext();
        //
        microservicesContext.setAdminEndpoint(ADMIN_ENDPOINT);
        microservicesContext.setNotificationsEndpoint(NOTIFICATIONS_ENDPOINT);
        microservicesContext.setAuthorizationEndpoint(AUTHORIZATION_ENDPOINT);
        //
        microservicesContext.setLandingEndpoint(LANDING_ENDPOINT);
        //
        microservicesContext.setApplicationDevelopmentEndpoint(APPLICATION_DEVELOPMENT_ENDPOINT);
        microservicesContext.setApplicationDevelopmentEndpoint(APPLICATION_BETA_ENDPOINT);
        microservicesContext.setApplicationDevelopmentEndpoint(APPLICATION_PRODUCTION_ENDPOINT);
        //
        if (SharedConstants.isRunningOnAppEngine()) {
            microservicesContext.getClientIdSecret().put(MicroservicesConstants.Credentials.Production.clientId,
                    MicroservicesConstants.Credentials.Production.clientSecret);
        } else {
            microservicesContext.getClientIdSecret().put(MicroservicesConstants.Credentials.Development.clientId,
                    MicroservicesConstants.Credentials.Development.clientSecret);
        }
        //
    }

    public MicroservicesContext provideMicroserviceContext() {
        return microservicesContext;
    }

    @Provides
    @Singleton
    public BackendMicroserviceContext provideBackendMicroserviceContext() {
        return microservicesContext;
    }

    @Override
    protected void doAdditionalSubmoduleBinding(SubModule subModule) {
    }

    @Override
    protected void configureCustomServlets() {
        serve("/").with(IndexServlet.class);
    }
}
