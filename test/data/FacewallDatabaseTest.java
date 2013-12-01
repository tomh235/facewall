package data;

import data.util.FacewallTestDatabase;
import org.junit.After;
import org.junit.Before;

import static data.util.FacewallTestDatabase.createImpermanentFacewallTestDatabase;

public class FacewallDatabaseTest {

    protected FacewallTestDatabase db;

    @Before
    final public void createAndInitialiseImpermanentDatabase() throws Exception {
        db = createImpermanentFacewallTestDatabase();
    }

    @After
    final public void destroyDatabase() throws Exception {
        db.shutdown();
    }
}
