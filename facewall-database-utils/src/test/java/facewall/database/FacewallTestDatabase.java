package facewall.database;

import facewall.database.config.IndexConfiguration;
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

    public void seedFixtures(final Fixtures.Builder fixturesBuilder) {
        DatabaseOperation seedFixturesOperation = new DatabaseOperation() {
            @Override protected void performOperation(GraphDatabaseService db) {
                Fixtures fixtures = fixturesBuilder.build();
                IndexManager indexManager = db.index();
                Index<Node> personIdIndex = indexManager.forNodes(Persons_Id.name);
                Index<Node> teamIdIndex = indexManager.forNodes(Teams_Id.name);


                for (TeamData teamData : fixtures.teams) {
                    Node teamNode = db.createNode();
                    copyData(teamData, teamNode);

                    teamIdIndex.add(teamNode, Teams_Id.key, teamData.get(Teams_Id.key));

                    for (PersonData personData : teamData.members) {
                        Node personNode = db.createNode();
                        copyData(personData, personNode);

                        personNode.createRelationshipTo(teamNode, MEMBER_OF);
                        personIdIndex.add(personNode, Persons_Id.key, personData.get(Persons_Id.key));
                    }
                }

                for (PersonData personData : fixtures.teamlessPersons) {
                    Node personNode = db.createNode();
                    copyData(personData, personNode);

                    personIdIndex.add(personNode, Persons_Id.key, personData.get(Persons_Id.key));
                }
            }

            private void copyData(Map<String, Object> data, Node node) {
                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    node.setProperty(entry.getKey(), entry.getValue());
                }
            }
        };

        seedFixturesOperation.execute(this);
    }
}
