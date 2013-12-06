package facewall.database;

import facewall.database.fixture.Fixtures;
import facewall.database.fixture.PersonData;
import facewall.database.fixture.TeamData;
import facewall.database.util.ForwardingGraphDatabaseService;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexManager;

import java.util.Map;

import static facewall.database.DatabaseOperations.clearDatabaseOperation;
import static facewall.database.DatabaseOperations.initialiseDatabaseOperation;
import static facewall.database.config.FacewallDatabaseConfiguration.*;

public class FacewallTestDatabase extends ForwardingGraphDatabaseService {

    FacewallTestDatabase(GraphDatabaseService backingDatabase) {
        super(backingDatabase);
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

                    teamIndex.add(teamNode, indexKey, teamData.get(indexKey));

                    for (PersonData personData : teamData.members) {
                        Node personNode = db.createNode();
                        copyData(personNode, personData);

                        personNode.createRelationshipTo(teamNode, MEMBER_OF);
                        personIndex.add(personNode, indexKey, personData.get(indexKey));
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
