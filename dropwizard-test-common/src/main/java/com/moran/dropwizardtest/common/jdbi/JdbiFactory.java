package com.moran.dropwizardtest.common.jdbi;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.google.inject.Inject;
import com.hubspot.guice.transactional.TransactionalDataSource;
import com.hubspot.rosetta.Rosetta;
import com.hubspot.rosetta.jdbi3.RosettaRowMapperFactory;
import com.moran.dropwizardtest.common.modules.ObjectMapperModule;
import com.moran.dropwizardtest.common.objectmapper.ObjectMapperFactory;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.ManagedDataSource;
import io.dropwizard.jdbi3.NamePrependingTemplateEngine;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.SqlStatements;
import org.jdbi.v3.core.statement.TemplateEngine;
import org.jdbi.v3.guava.GuavaPlugin;
import org.jdbi.v3.postgres.PostgresPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public class JdbiFactory {
    private final MetricRegistry metricRegistry;

    @Inject
    public JdbiFactory(MetricRegistry metricRegistry){
        this.metricRegistry = metricRegistry;
    }

    public Jdbi buildNewJdbiInstance(MetricRegistry metricRegistry, DataSourceFactory dataSourceFactory, String applicationName) {
        ManagedDataSource dataSource = dataSourceFactory.build(metricRegistry, applicationName + "-postgresql");
        TransactionalDataSource transactionalDataSource = new TransactionalDataSource(dataSource);
        Jdbi jdbi = Jdbi.create(transactionalDataSource);
        jdbi.installPlugin(new SqlObjectPlugin());
        jdbi.installPlugin(new GuavaPlugin());
        jdbi.installPlugin(new PostgresPlugin());
        jdbi.registerRowMapper(new RosettaRowMapperFactory());
        Rosetta.setMapper(new ObjectMapperFactory().createObjectMapper());
        if (dataSourceFactory.isAutoCommentsEnabled()) {
            TemplateEngine original = jdbi.getConfig(SqlStatements.class).getTemplateEngine();
            jdbi.setTemplateEngine(new NamePrependingTemplateEngine(original));
        }
        return jdbi;
    }
}
