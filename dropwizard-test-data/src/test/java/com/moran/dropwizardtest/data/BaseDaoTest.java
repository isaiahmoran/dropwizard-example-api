package com.moran.dropwizardtest.data;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.hubspot.rosetta.Rosetta;
import com.hubspot.rosetta.jdbi3.RosettaRowMapperFactory;
import com.moran.dropwizardtest.common.objectmapper.ObjectMapperFactory;
import io.dropwizard.jdbi3.NamePrependingTemplateEngine;
import io.zonky.test.db.postgres.embedded.LiquibasePreparer;
import io.zonky.test.db.postgres.junit.EmbeddedPostgresRules;
import io.zonky.test.db.postgres.junit.PreparedDbRule;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.SqlStatements;
import org.jdbi.v3.core.statement.TemplateEngine;
import org.jdbi.v3.core.transaction.SerializableTransactionRunner;
import org.jdbi.v3.guava.GuavaPlugin;
import org.jdbi.v3.postgres.PostgresPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.junit.Before;
import org.junit.Rule;

import java.sql.SQLException;

public class BaseDaoTest {
    private Jdbi jdbi;
    @Rule
    public PreparedDbRule db =
            EmbeddedPostgresRules.preparedDatabase(
                    LiquibasePreparer.forClasspathLocation("./migrations.xml"));

    @Before
    public void setupDbSchema() {
        this.jdbi = Jdbi.create(db.getTestDatabase())
                    .installPlugin(new SqlObjectPlugin())
                    .installPlugin(new GuavaPlugin())
                    .installPlugin(new PostgresPlugin())
                    .registerRowMapper(new RosettaRowMapperFactory())
                    .setTransactionHandler(new SerializableTransactionRunner());
        Rosetta.getMapper().setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        Rosetta.setMapper(new ObjectMapperFactory().createObjectMapper());
        TemplateEngine original = jdbi.getConfig(SqlStatements.class).getTemplateEngine();
        jdbi.setTemplateEngine(new NamePrependingTemplateEngine(original));
    }

    public <T> T getDao(Class<T> dao) {return this.jdbi.onDemand(dao);}
}
