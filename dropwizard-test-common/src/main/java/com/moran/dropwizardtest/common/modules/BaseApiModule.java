package com.moran.dropwizardtest.common.modules;

import com.google.inject.AbstractModule;

public class BaseApiModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new ConfigurationPropertyModule());
        install(new ObjectMapperModule());
    }

}
