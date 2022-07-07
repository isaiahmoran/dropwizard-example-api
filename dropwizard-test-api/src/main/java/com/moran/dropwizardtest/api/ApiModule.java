package com.moran.dropwizardtest.api;

import com.google.inject.AbstractModule;
import com.moran.dropwizardtest.api.v1.TestResource;
import com.moran.dropwizardtest.data.DropwizardTestDataModule;

public class ApiModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(TestResource.class);
        install(new DropwizardTestDataModule());
    }
}
