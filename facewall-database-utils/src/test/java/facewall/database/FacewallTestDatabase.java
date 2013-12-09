package facewall.database;

import facewall.database.fixture.Fixtures;
import facewall.database.fixture.PersonData;
import facewall.database.fixture.TeamData;
import facewall.database.util.ForwardingGraphDatabaseService;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.IndexManager;

import java.util.Map;

import static facewall.database.DatabaseOperations.clearDatabaseOperation;
import static facewall.database.DatabaseOperations.initialiseDatabaseOperation;
import static facewall.database.FacewallIndices.newFacewallIndices;
import static facewall.database.config.FacewallDatabaseConfiguration.IndexConfiguration.*;
import static facewall.database.config.FacewallDatabaseConfiguration.MEMBER_OF;

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

                FacewallIndices personIndices = newFacewallIndices(indexManager,
                    Persons_Id,
                    Persons_Name
                );

                FacewallIndices teamIndices = newFacewallIndices(indexManager,
                    Teams_Id,
                    Teams_Name
                );

                for (TeamData teamData : fixtures.teams) {
                    Node teamNode = db.createNode();

                    copyData(teamData, teamNode);
                    teamIndices.addNode(teamNode, teamData);

                    for (PersonData personData : teamData.members) {
                        Node personNode = db.createNode();
                        personNode.createRelationshipTo(teamNode, MEMBER_OF);

                        copyData(personData, personNode);
                        personIndices.addNode(personNode, personData);
                    }
                }

                for (PersonData personData : fixtures.teamlessPersons) {
                    Node personNode = db.createNode();

                    copyData(personData, personNode);
                    personIndices.addNode(personNode, personData);
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
