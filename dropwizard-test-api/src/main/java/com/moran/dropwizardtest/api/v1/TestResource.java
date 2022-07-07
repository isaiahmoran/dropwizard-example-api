package com.moran.dropwizardtest.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.moran.dropwizardtest.models.Pong;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static com.moran.dropwizardtest.common.modules.ObjectMapperModule.DEFAULT_OBJECT_MAPPER;


@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/v1/test")
public class TestResource {
    private final ObjectMapper objectMapper;
    private final String applicationName;
    private static final String TEST_APPLICATION_NAME = "TEST_APPLICATION_NAME";
    private static final String PONG = "Pong";

    @Inject
    public TestResource(@Named(DEFAULT_OBJECT_MAPPER) ObjectMapper objectMapper,
                        // We're passing the application name in via environment variables,
                        // This named annotation proves this code is working
                        @Named(TEST_APPLICATION_NAME) String applicationName) {
        this.objectMapper = objectMapper;
        this.applicationName = applicationName;
    }

    @GET
    public Pong ping() {
        return Pong.builder().setPong(PONG).build();
    }
}
