package com.moran.dropwizardtest.data;

import com.moran.dropwizardtest.models.TestModel;
import com.moran.dropwizardtest.models.TestModelEgg;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class TestDaoTest extends BaseDaoTest{
    TestDao testDao;
    private TestModelEgg testModelEgg;
    private static final String CREATED_AT = "createdAt";
    private static final String UPDATED_AT = "updatedAt";
    private static final String ID = "id";


    @Before
    public void setup() {
        this.testDao = getDao(TestDao.class);
        this.testModelEgg = TestModelEgg.builder()
                .setFirstName("Isaiah")
                .setLastName("Moran")
                .build();
        testDao.upsertTestData(testModelEgg);
    }

    @Test
    public void getAllTestModels() {
        List<TestModel> testModels = testDao.getAllTestData();
        assertThat(testModels.get(0)).isEqualToIgnoringGivenFields(testModelEgg, CREATED_AT, UPDATED_AT, ID);
    }
}
