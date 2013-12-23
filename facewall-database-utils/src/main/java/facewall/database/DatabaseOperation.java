package facewall.database;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;

abstract class DatabaseOperation {

    DatabaseOperation() {}

    abstract protected void performOperation(GraphDatabaseService db);

    public final void execute(GraphDatabaseService db) {
        Transaction tx = db.beginTx();
        try {
            performOperation(db);

            tx.success();
        } finally {
            tx.finish();
        }
    }
}
