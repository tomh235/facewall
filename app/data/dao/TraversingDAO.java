package data.dao;

import data.dao.database.FacewallDB;
import data.dao.database.IndexQuery;
import data.datatype.PersonId;
import data.datatype.TeamId;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import java.util.Collections;
import java.util.List;

import static data.dao.database.FacewallDB.NodeIndex.Persons;
import static data.dao.database.FacewallDB.NodeIndex.Teams;
import static data.dao.database.IndexQuery.anIndexLookup;

public class TraversingDAO {

    private final FacewallDB db;

    public TraversingDAO(FacewallDB db) {
        this.db = db;
    }

    public List<Node> fetchTeamMembers(TeamId id) {
        List<Node> result = Collections.emptyList();

        Transaction tx = db.beginTransaction();
        try {
            IndexQuery query = anIndexLookup()
                .onIndex(Teams)
                .forValue(id.value)
                .build();

            Node teamNode = db.lookupSingleNodeInIndex(query);
            result = db.findRelatedNodes(teamNode);

            tx.success();
        } finally {
            tx.finish();
        }

        return result;
    }

    public Node fetchTeamForPerson(PersonId personId) {
        Node result;

        Transaction tx = db.beginTransaction();
        try {
            IndexQuery query = anIndexLookup()
                .onIndex(Persons)
                .forValue(personId.value)
                .build();

            Node teamNode = db.lookupSingleNodeInIndex(query);
            result = db.findSingleRelatedNode(teamNode);

            tx.success();
        } finally {
            tx.finish();
        }

        return result;
    }
}
