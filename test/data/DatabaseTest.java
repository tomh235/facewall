package data;

import org.junit.After;
import org.junit.Before;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.test.TestGraphDatabaseFactory;

import static org.neo4j.graphdb.DynamicRelationshipType.withName;

public class DatabaseTest {

    //a lot of the stuff here is needed in the functional tests as well - we should pull this all into a common library
    final static RelationshipType MEMBER_OF = withName("TEAMMEMBER_OF");
    final static String personIndexName = "Persons";
    final static String teamIndexName = "Teams";

    protected GraphDatabaseService db;

    @Before
    final public void createAndInitialiseImpermanentDatabase() throws Exception {
        db = new TestGraphDatabaseFactory().newImpermanentDatabase();
        IndexManager indexManager = db.index();

        indexManager.forNodes(personIndexName);
        indexManager.forNodes(teamIndexName);
    }

    @After
    final public void destroyDatabase() throws Exception {
        db.shutdown();
    }
}
