package data.dao;

import data.dao.database.AdminDB;
import data.dao.database.RelationshipTypes;
import data.mapper.PersonNodeMapper;
import domain.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.neo4j.graphdb.Node;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AdminDAOTest {

    @Mock AdminDB mockDb;
    @Mock PersonNodeMapper mockPersonNodeMapper;

    @InjectMocks
    private AdminDAO adminDAO;

    @Test
    public void create_person_delegates_to_create_node() {
        Person mockPerson = mock(Person.class);
        adminDAO.addPerson(mockPerson);

        verify(mockDb).createNode();
    }

    @Test
    public void create_person_delegates_to_property_mapper() {
        Person mockPerson = mock(Person.class);
        adminDAO.addPerson(mockPerson);

        verify(mockPersonNodeMapper).mapNodeProperties(mockPerson);

    }

    @Test
    public void create_person_delegates_to_relationship_mapper() {
        Person mockPerson = mock(Person.class);
        adminDAO.addPerson(mockPerson);

        verify(mockPersonNodeMapper).mapNodeRelationships(any(Node.class));
    }

    @Test
    public void create_person_delegates_to_db_add_properties_to_node() {
        Person mockPerson = mock(Person.class);
        Node mockPersonNode = mock(Node.class);

        Map<String, String> stubProperties = new HashMap<>();
        stubProperties.put("name", "bob");

        when(mockDb.createNode()).thenReturn(mockPersonNode);
        when(mockPersonNodeMapper.mapNodeProperties(mockPerson)).thenReturn(stubProperties);

        adminDAO.addPerson(mockPerson);
        verify(mockDb).addPropertiesToNode(mockPersonNode, stubProperties);
    }

    @Test
    public void create_person_delegates_to_db_add_relationships_to_node() {
        Person mockPerson = mock(Person.class);
        Node mockPersonNode = mock(Node.class);
        Node mockTeamNode = mock(Node.class);

        Map<Node, RelationshipTypes> stubRelations = new HashMap<>();
        stubRelations.put(mockTeamNode, RelationshipTypes.TEAMMEMBER_OF);

        when(mockDb.createNode()).thenReturn(mockPersonNode);
        when(mockPersonNodeMapper.mapNodeRelationships(any(Node.class))).thenReturn(stubRelations);

        adminDAO.addPerson(mockPerson);
        verify(mockDb).addRelationshipsToNode(mockPersonNode, stubRelations);
    }
}

