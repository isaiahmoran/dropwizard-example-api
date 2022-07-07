package com.moran.dropwizardtest.data;

import com.codahale.metrics.MetricRegistry;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.moran.dropwizardtest.common.jdbi.JdbiFactory;
import io.dropwizard.db.DataSourceFactory;
import org.jdbi.v3.core.Jdbi;

public class DropwizardTestDataModule extends AbstractModule {
    public static final String JDBI_NAME = "dropwizardtest.jdbi";
    public static final String JDBI_FACTORY_NAME = "moran.postgres.dropwizardtest.datasourcefactory";
    private static final String APPLICATION_NAME = "DropwizardTest";
    private static final String POSTGRES_DRIVER = "org.postgresql.Driver";

    @Provides
    @Singleton
    @Named(JDBI_FACTORY_NAME)
    DataSourceFactory provideDataSourceFactory(@Named("PGHOST") String pgHost,
                                               @Named("PGPORT") String pgPort,
                                               @Named("PGDATABASE") String pgDatabase,
                                               @Named("PGUSER") String pgUser,
                                               @Named("PGPASSWORD") String pgPassword) {
        DataSourceFactory dataSourceFactory = new DataSourceFactory();
        dataSourceFactory.setDriverClass(POSTGRES_DRIVER);
        String dbUrl = String.format("jdbc:postgresql://%s:%s/%s?stringtype=unspecified&ApplicationName=%s",
                pgHost,
                pgPort,
                pgDatabase,
                APPLICATION_NAME);
        dataSourceFactory.setUrl(dbUrl);
        dataSourceFactory.setUser(pgUser);
        dataSourceFactory.setPassword(pgPassword);
        return dataSourceFactory;
    }

    @Provides
    @Singleton
    @Named(JDBI_NAME)
    Jdbi provideJdbi(MetricRegistry metricRegistry,
                     JdbiFactory jdbiFactory,
                     @Named(JDBI_FACTORY_NAME) DataSourceFactory dataSourceFactory) {
        return jdbiFactory.buildNewJdbiInstance(metricRegistry, dataSourceFactory, APPLICATION_NAME);
    }
}
