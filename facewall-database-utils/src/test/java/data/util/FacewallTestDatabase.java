package data.util;

import data.fixture.Fixtures;
import data.fixture.PersonData;
import data.fixture.TeamData;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.test.TestGraphDatabaseFactory;

import java.util.Map;

import static data.util.DatabaseOperations.clearDatabaseOperation;
import static data.util.DatabaseOperations.initialiseDatabaseOperation;
import static data.util.FacewallDatabaseConfiguration.*;

public class FacewallTestDatabase extends GraphDatabaseAdapter {

    private FacewallTestDatabase(GraphDatabaseService backingDatabase) {
        super(backingDatabase);
    }

    public static FacewallTestDatabase createImpermanentFacewallTestDatabase() {
        GraphDatabaseService db = new TestGraphDatabaseFactory().newImpermanentDatabase();
        IndexManager indexManager = db.index();

        indexManager.forNodes(personIndexName);
        indexManager.forNodes(teamIndexName);

        return new FacewallTestDatabase(db);
    }

    public static FacewallTestDatabase createFacewallTestDatabaseWrappingExistingDatabase(GraphDatabaseService db) {
        return new FacewallTestDatabase(db);
    }

    public void clear() {
        clearDatabaseOperation.execute(this);
    }

    public void initialise() {
        initialiseDatabaseOperation.execute(this);
    }

    public void seedFixtures(final Fixtures fixtures) {
        DatabaseOperation seedFixturesOperation = new DatabaseOperation() {
            @Override protected void performOperation(GraphDatabaseService db) {

                IndexManager indexManager = db.index();
                Index<Node> personIndex = indexManager.forNodes(personIndexName);
                Index<Node> teamIndex = indexManager.forNodes(teamIndexName);


                for (TeamData teamData : fixtures.teams) {
                    Node teamNode = db.createNode();
                    copyData(teamNode, teamData);

                    teamIndex.add(teamNode, indexKey, teamNode.getProperty(indexKey));

                    for (PersonData personData : teamData.members) {
                        Node personNode = db.createNode();
                        copyData(personNode, personData);

                        personNode.createRelationshipTo(teamNode, MEMBER_OF);
                        personIndex.add(personNode, indexKey, personNode.getProperty(indexKey));
                    }
                }
            }

            private void copyData(Node node, Map<String, Object> data) {
                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    node.setProperty(entry.getKey(), entry.getValue());
                }
            }
        };

        seedFixturesOperation.execute(this);
    }
}
