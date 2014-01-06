package data.dao;

import data.dao.database.ItemNotFoundException;
import data.dao.database.NodeIndex;
import data.dao.database.PersonNode;
import data.dao.database.RelationshipTypes;
import data.datatype.PersonId;
import data.datatype.TeamId;
import data.dto.PersonInformation;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.graphdb.index.IndexManager;

import java.util.Map;

import static data.dao.database.NodeIndex.Persons;
import static data.dao.database.NodeIndex.Teams;
import static data.dao.database.PersonNode.newPersonNode;

public class AdminDAO {

    private final GraphDatabaseService graphDatabaseService;

    public AdminDAO(GraphDatabaseService graphDatabaseService) {
        this.graphDatabaseService = graphDatabaseService;
    }

    public void savePersonInformation(PersonInformation personInformation) {
        Transaction tx = graphDatabaseService.beginTx();
        try {
            PersonNode personNode = newPersonNode(graphDatabaseService.createNode());
            personNode.setProperties(personInformation);

            Index<Node> personIndex = graphDatabaseService.index().forNodes(Persons.getName());
            personIndex.add(personNode.wrappedNode, Persons.getKey(), personInformation.getId().value);

            tx.success();
        } finally {
            tx.finish();
        }
    }

    public void addPersonToTeam(PersonId personId, TeamId teamId) throws ItemNotFoundException {
        Transaction tx = graphDatabaseService.beginTx();
        try {
            Node personNode = findPersonNode(personId);
            Node teamNode = findTeamNode(teamId);

            personNode.createRelationshipTo(teamNode, RelationshipTypes.TEAMMEMBER_OF);
            tx.success();
        } finally {
            tx.finish();
        }
    }

    private Node findPersonNode(PersonId personId) throws ItemNotFoundException {
        Node personNode = lookupNodeInIndex(Persons, personId.value);
        if (personNode != null) {
            return personNode;
        } else {
            throw new ItemNotFoundException("No such person with id <" + personId.value + ">");
        }
    }

    private Node findTeamNode(TeamId teamId) throws ItemNotFoundException {
        Node teamNode = lookupNodeInIndex(Teams, teamId.value);
        if (teamNode != null) {
            return teamNode;
        } else {
            throw new ItemNotFoundException("No such team with id <" + teamId.value + ">");
        }
    }

    private Node lookupNodeInIndex(NodeIndex index, Object indexedValue) {
        IndexManager indexManager = graphDatabaseService.index();
        IndexHits<Node> indexHits = indexManager.forNodes(index.getName()).get(index.getKey(), indexedValue);

        return indexHits.getSingle();
    }

    public void addPropertiesToNode(Node node, Map<String, String> propertiesList) {
        for (Map.Entry<String, String> property : propertiesList.entrySet()) {
            node.setProperty(property.getKey(), property.getValue());
        }
    }

    public void addRelationshipsToNode(Node startNode, Map<Node, RelationshipTypes> relationshipList) {
        for (Map.Entry<Node, RelationshipTypes> relation : relationshipList.entrySet()) {
            startNode.createRelationshipTo(relation.getKey(), relation.getValue());
        }
    }

    public Node createNode() {
        return graphDatabaseService.createNode();
    }
}
