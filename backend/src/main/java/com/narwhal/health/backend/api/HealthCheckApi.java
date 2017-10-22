package com.narwhal.health.backend.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.narwhal.health.backend.dto.HealthCheckDTO;
import com.narwhal.health.backend.services.HealthCheckService;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

@Singleton
@Path("/v1/health/check/")
public class HealthCheckApi {
    private Logger logger = Logger.getLogger(HealthCheckApi.class.getSimpleName());

    @Inject
    private HealthCheckService healthCheckService;

    @GET
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response pingServers() {
        //
        HealthCheckDTO healthCheckDTO = healthCheckService.pingServers();
        //
        return Response.ok(healthCheckDTO).build();
    }
}
