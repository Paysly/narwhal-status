package com.narwhal.health.backend.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.narwhal.basics.core.rest.utils.ApiPreconditions;
import com.narwhal.health.backend.dto.HealthCheckDTO;
import com.narwhal.health.backend.model.HealthCheck;
import com.narwhal.health.backend.services.HealthCheckService;
import com.narwhal.health.backend.types.ServerType;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
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

    @GET
    @Path("/{server}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHistorical(@PathParam("server") ServerType serverType,
                                  @QueryParam("date") Long date,
                                  @QueryParam("hour") Integer hour) {
        ApiPreconditions.checkNotNull(serverType, "serverType");
        //
        List<HealthCheck> list = healthCheckService.getHistorical(serverType, date, hour);
        //
        return Response.ok(list).build();
    }
}
