package com.moran.dropwizardtest.common.baseApi;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import com.moran.dropwizardtest.common.modules.BaseApiModule;
import com.moran.dropwizardtest.common.modules.ConfigurationPropertyModule;
import com.moran.dropwizardtest.common.objectmapper.ObjectMapperFactory;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jackson.AnnotationSensitivePropertyNamingStrategy;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import ru.vyarus.dropwizard.guice.GuiceBundle;

import java.util.List;
public abstract class BaseApi<T extends Configuration> extends Application<T> {
    GuiceBundle guiceBundle;
    @Override
    public void initialize(Bootstrap<T> bootstrap) {
        super.initialize(bootstrap);
        GuiceBundle.Builder guiceBundleBuilder = GuiceBundle.builder()
                .enableAutoConfig(getClass().getPackage().getName())
                .modules(new BaseApiModule());

        for(AbstractModule module : getApiModules()) {
            guiceBundleBuilder.modules(module);
        }
        guiceBundle = guiceBundleBuilder.build();
        bootstrap.addBundle(guiceBundle);
        bootstrap.addBundle(getDatasourceFactoryForMigrations());
        bootstrap.setObjectMapper(new ObjectMapperFactory().createObjectMapper());
        bootstrap.getObjectMapper().setPropertyNamingStrategy(new AnnotationSensitivePropertyNamingStrategy());
    }

    @Override
    public void run(T apiConfiguration, Environment environment) {
        environment.getObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    }

    protected List<AbstractModule> getApiModules() {
        return List.of();
    }

    protected ConfiguredBundle<? super T> getDatasourceFactoryForMigrations() {
        return null;
    }

    protected ConfiguredBundle<? super T> setDataSourceForMigrations(AbstractModule module, String factoryName) {
        return new MigrationsBundle<>() {
            @Override
            public DataSourceFactory getDataSourceFactory(T apiConfiguration) {
                return getDataSourceFactoryWithGuice(module, factoryName);
            }
        };
    }

    private DataSourceFactory getDataSourceFactoryWithGuice(AbstractModule module, String factoryName) {
        Injector injector = Guice.createInjector(new ConfigurationPropertyModule(), module);
        DataSourceFactory dataSourceFactory = injector.getInstance(Key.get(DataSourceFactory.class, Names.named(factoryName)));
        return dataSourceFactory;
    }
}
