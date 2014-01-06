package facewall.database;

import facewall.database.fixture.Fixtures;
import facewall.database.fixture.PersonData;
import facewall.database.fixture.TeamData;
import facewall.database.util.ForwardingGraphDatabaseService;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.rest.graphdb.query.QueryEngine;

import java.util.Map;
import java.util.NoSuchElementException;

import static facewall.database.DatabaseOperations.clearDatabaseOperation;
import static facewall.database.DatabaseOperations.initialiseDatabaseOperation;
import static facewall.database.config.FacewallDatabaseConfiguration.IndexConfiguration.Persons;
import static facewall.database.config.FacewallDatabaseConfiguration.IndexConfiguration.Teams;
import static facewall.database.config.FacewallDatabaseConfiguration.MEMBER_OF;
import static facewall.database.util.QueryEngineAdaptor.createQueryEngineAdaptor;

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
                Index<Node> personIdIndex = indexManager.forNodes(Persons.indexName);
                Index<Node> teamIdIndex = indexManager.forNodes(Teams.indexName);

                for (TeamData teamData : fixtures.teams) {
                    Node teamNode = db.createNode();
                    copyData(teamData, teamNode);

                    teamIdIndex.add(teamNode, Teams.key, teamData.get(Teams.key));

                    for (PersonData personData : teamData.members) {
                        Node personNode = db.createNode();
                        copyData(personData, personNode);

                        personNode.createRelationshipTo(teamNode, MEMBER_OF);
                        personIdIndex.add(personNode, Persons.key, personData.get(Persons.key));
                    }
                }

                for (PersonData personData : fixtures.teamlessPersons) {
                    Node personNode = db.createNode();
                    copyData(personData, personNode);

                    personIdIndex.add(personNode, Persons.key, personData.get(Persons.key));
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

    public QueryEngine<Map<String, Object>> createQueryEngine() {
        ExecutionEngine executionEngine = new ExecutionEngine(this);
        return createQueryEngineAdaptor(executionEngine);
    }

    public Node findPersonById(String personId) {
        IndexHits<Node> hits = index().forNodes(Persons.indexName).query(Persons.key, personId);
        Node personNode = hits.getSingle();

        if (personNode != null) {
            return personNode;
        } else {
            throw new NoSuchElementException("no person with that id");
        }
    }
}
