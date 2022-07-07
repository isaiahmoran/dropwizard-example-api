package com.moran.dropwizardtest.common.modules;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.moran.dropwizardtest.common.objectmapper.ObjectMapperFactory;

public class ObjectMapperModule extends AbstractModule {
    public static final String DEFAULT_OBJECT_MAPPER = "DEFAULT_OBJECT_MAPPER";

    @Provides
    @Singleton
    @Named(DEFAULT_OBJECT_MAPPER)
    public ObjectMapper provideDefaultObjectMapper(){
        return new ObjectMapperFactory().createObjectMapper();
    }
}
