package com.moran.dropwizardtest.data;

import com.hubspot.rosetta.jdbi3.BindWithRosetta;
import com.moran.dropwizardtest.models.TestModel;
import com.moran.dropwizardtest.models.TestModelEgg;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface TestDao {
    @SqlQuery("SELECT * " +
              "FROM my_first_table")
    List<TestModel> getAllTestData();

    @SqlUpdate("INSERT INTO my_first_table " +
               "(first_name, last_name) " +
               "VALUES (:first_name, :last_name)")
    void upsertTestData(@BindWithRosetta TestModelEgg testModel);
}
