package com.narwhal.health.backend.utils;

import com.narwhal.basics.core.rest.utils.MicroservicesContext;
import lombok.Data;

@Data
public abstract class BackendMicroserviceContext extends MicroservicesContext {

    private String landingEndpoint;
    private String applicationDevelopmentEndpoint;
    private String applicationBetaEndpoint;
    private String applicationProductionEndpoint;
}
