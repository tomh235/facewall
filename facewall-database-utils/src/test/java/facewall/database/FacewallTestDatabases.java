package facewall.database;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.test.TestGraphDatabaseFactory;

abstract public class FacewallTestDatabases {

    private FacewallTestDatabases() {}

    public static FacewallTestDatabase createImpermanentFacewallTestDatabase() {
        GraphDatabaseService db = new TestGraphDatabaseFactory().newImpermanentDatabase();
        FacewallTestDatabase facewallTestDb = new FacewallTestDatabase(db);

        facewallTestDb.initialise();
        return facewallTestDb;
    }

    public static FacewallTestDatabase createFacewallTestDatabaseWrappingExistingDatabase(GraphDatabaseService db) {
        return new FacewallTestDatabase(db);
    }
}
