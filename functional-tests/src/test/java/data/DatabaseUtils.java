package data;

import org.neo4j.graphdb.*;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.rest.graphdb.GraphDatabaseFactory;

import static org.neo4j.graphdb.DynamicRelationshipType.withName;

abstract class DatabaseUtils {

    final static RelationshipType MEMBER_OF = withName("TEAMMEMBER_OF");
    final static String personIndexName = "Persons";
    final static String teamIndexName = "Teams";

    private abstract static class DatabaseOperation {
        abstract protected void performOperation(GraphDatabaseService db);

        public final void execute(String databaseUrl) {
            GraphDatabaseService db = GraphDatabaseFactory.databaseFor(databaseUrl);

            Transaction tx = db.beginTx();
            try {
                performOperation(db);

                tx.success();
            } finally {
                tx.finish();
            }
        }
    }

    private static final DatabaseOperation clearDatabaseOperation = new DatabaseOperation() {
        @Override public void performOperation(GraphDatabaseService db) {

            //This is deprecated on the GraphDatabaseService interface,
            // but the alternative is not supported by implementation (RestGraphDatabase)
            for (Node node : db.getAllNodes()) {
                for (Relationship relationship : node.getRelationships()) {
                    relationship.delete();
                }

                boolean notTheRootNode = node.getId() != 0;
                if (notTheRootNode) {
                    node.delete();
                }
            }

            IndexManager indexManager = db.index();
            for (String indexName : indexManager.nodeIndexNames()) {
                indexManager.forNodes(indexName).delete();
            }

            for (String indexName : indexManager.relationshipIndexNames()) {
                indexManager.forRelationships(indexName).delete();
            }
        }
    };

    private static final DatabaseOperation initialiseDatabaseOperation = new DatabaseOperation() {
        @Override protected void performOperation(GraphDatabaseService db) {
            IndexManager indexManager = db.index();

            indexManager.forNodes(personIndexName);
            indexManager.forNodes(teamIndexName);
        }
    };

    static void clearDatabase(String databaseUrl) {
        clearDatabaseOperation.execute(databaseUrl);
    }

    static void initialiseDatabase(String databaseUrl) {
        initialiseDatabaseOperation.execute(databaseUrl);
    }

    static void seedFixtures(String databaseUrl, Fixtures fixtures) {
        createSeedFixturesOperation(fixtures).execute(databaseUrl);
    }

    private static DatabaseOperation createSeedFixturesOperation(final Fixtures fixtures) {
        return new DatabaseOperation() {
            @Override protected void performOperation(GraphDatabaseService db) {
                fixtures.seedDatabase(db);
            }
        };
    }
}