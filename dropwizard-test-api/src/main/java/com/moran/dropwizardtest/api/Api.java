package com.moran.dropwizardtest.api;

import com.google.inject.AbstractModule;
import com.moran.dropwizardtest.common.baseApi.BaseApi;
import com.moran.dropwizardtest.data.DropwizardTestDataModule;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Environment;

import java.util.List;

import static com.moran.dropwizardtest.data.DropwizardTestDataModule.JDBI_FACTORY_NAME;

public class Api extends BaseApi<ApiConfiguration> {
    public static void main(String[] args) throws Exception {
        new Api().run(args);
    }

    @Override
    public void run(ApiConfiguration apiConfiguration, Environment environment) {
        super.run(apiConfiguration, environment);
    }

    @Override
    protected ConfiguredBundle<? super ApiConfiguration> getDatasourceFactoryForMigrations() {
        return super.setDataSourceForMigrations(new DropwizardTestDataModule(), JDBI_FACTORY_NAME);
    }

    @Override
    protected List<AbstractModule> getApiModules() {
        return List.of(new ApiModule());
    }
}
