package data.dao;

import data.dao.database.FacewallDB;
import data.dao.database.IndexQuery;
import data.dao.database.RelationshipTypes;
import data.dto.PersonDTO;
import data.dto.TeamDTO;
import domain.Query;
import data.mapper.PersonNodeMapper;
import domain.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.IndexHits;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static data.dao.database.IndexQueryMatcher.anIndexQuery;
import static data.dto.PersonDTOMatcher.aPersonDTO;
import static data.dto.TeamDTOMatcher.aTeamDTO;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;
import static util.CollectionMatcher.containsExhaustively;

@RunWith(MockitoJUnitRunner.class)
public class FacewallDAOTest extends DAOTest {

    private static final Query someQuery = mock(Query.class);

    @Mock FacewallDB mockDb;
    @Mock PersonNodeMapper mockPersonNodeMapper;
    private FacewallDAO facewallDAO;

    @Before
    public void setUp() throws Exception {
        facewallDAO = new FacewallDAO(mockDb, mockPersonNodeMapper);
        when(mockDb.beginTransaction()).thenReturn(mockTransaction);
    }

    @Test
    public void fetch_persons_starts_and_finishes_transactions() {
        stubOutMocks();
        facewallDAO.fetchPersons();

        verifyTransactionComplete();
    }

    @Test
    public void fetch_teams_starts_and_finishes_transactions() {
        stubOutMocks();
        facewallDAO.fetchTeams();

        verifyTransactionComplete();
    }

    @Test
    public void add_person_starts_and_finishes_transactions() {
        Person mockPerson = mock(Person.class);
        stubOutMocks();
        facewallDAO.addPerson(mockPerson);

        verifyTransactionComplete();
    }

    @Test
    public void fetch_persons_creates_index_query() {
        stubOutMocks();
        facewallDAO.fetchPersons();

        verify(mockDb).lookupNodesInIndex(
            argThat(is(anIndexQuery()
                .queryingOnAnIndexNamed("Persons_Id")
                .queryingOnTheKey("id")
                .queryingForAllValues()
            ))
        );
    }

    @Test
    public void fetch_teams_creates_index_query() {
        stubOutMocks();
        facewallDAO.fetchTeams();

        verify(mockDb).lookupNodesInIndex(
            argThat(is(anIndexQuery()
                .queryingOnAnIndexNamed("Teams_Id")
                .queryingOnTheKey("id")
                .queryingForAllValues()
            ))
        );
    }

    @Test
    public void fetch_person_retrieves_person_nodes() {
        Node expectedPersonNode1 = mock(Node.class);
        Node expectedPersonNode2 = mock(Node.class);

        IndexHits<Node> personNodeHits = createMockIndexHits(
            expectedPersonNode1, expectedPersonNode2
        );
        when(mockDb.lookupNodesInIndex(any(IndexQuery.class)))
            .thenReturn(personNodeHits);

        List<PersonDTO> result = facewallDAO.fetchPersons();

        verify(mockDb).findSingleRelatedNode(expectedPersonNode1);
        verify(mockDb).findSingleRelatedNode(expectedPersonNode2);

        assertThat(result, containsExhaustively(
            aPersonDTO().withPersonNode(
                    sameInstance(expectedPersonNode1)),
            aPersonDTO().withPersonNode(
                    sameInstance(expectedPersonNode2)
            )
        ));
    }

    @Test
    public void fetch_person_retrieves_team_nodes() {
        Node expectedTeamNode1 = mock(Node.class);
        Node expectedTeamNode2 = mock(Node.class);

        IndexHits<Node> personNodeHits = createMockIndexHits(
            expectedTeamNode1, expectedTeamNode2
        );
        when(mockDb.lookupNodesInIndex(any(IndexQuery.class)))
            .thenReturn(personNodeHits);

        when(mockDb.findSingleRelatedNode(any(Node.class)))
            .thenReturn(expectedTeamNode1)
            .thenReturn(expectedTeamNode2);

        List<PersonDTO> result = facewallDAO.fetchPersons();

        assertThat(result, containsExhaustively(
            aPersonDTO().withTeamNode(
                sameInstance(expectedTeamNode1)),
            aPersonDTO().withTeamNode(
                sameInstance(expectedTeamNode2)
            )
        ));
    }

    @Test
    public void fetch_teams_retrieves_team_nodes() {
        Node expectedTeamNode1 = mock(Node.class);
        Node expectedTeamNode2 = mock(Node.class);

        IndexHits<Node> teamNodeHits = createMockIndexHits(
            expectedTeamNode1, expectedTeamNode2
        );
        when(mockDb.lookupNodesInIndex(any(IndexQuery.class)))
            .thenReturn(teamNodeHits);

        List<TeamDTO> result = facewallDAO.fetchTeams();

        verify(mockDb).findRelatedNodes(expectedTeamNode1);
        verify(mockDb).findRelatedNodes(expectedTeamNode2);

        assertThat(result, containsExhaustively(
            aTeamDTO().withTeamNode(
                    sameInstance(expectedTeamNode1)),
            aTeamDTO().withTeamNode(
                sameInstance(expectedTeamNode2)
            )
        ));
    }

    @Test
    public void fetch_teams_retrieves_members_nodes() {
        IndexHits<Node> mockIndexHits = createMockIndexHits(mock(Node.class));
        when(mockDb.lookupNodesInIndex(any(IndexQuery.class)))
            .thenReturn(mockIndexHits);

        Node expectedMemberNode1 = mock(Node.class);
        Node expectedMemberNode2 = mock(Node.class);

        when(mockDb.findRelatedNodes(any(Node.class))).thenReturn(asList(
            expectedMemberNode1, expectedMemberNode2
        ));

        List<TeamDTO> result = facewallDAO.fetchTeams();

        assertThat(result, containsExhaustively(
            aTeamDTO().whereMemberNodes(containsExhaustively(
                sameInstance(expectedMemberNode1),
                sameInstance(expectedMemberNode2)
            )
            )));
    }

    @Test
    public void create_person_delegates_to_create_node() {
        Person mockPerson = mock(Person.class);
        facewallDAO.addPerson(mockPerson);

        verify(mockDb).createNode();
    }

    @Test
    public void create_person_delegates_to_property_mapper() {
        Person mockPerson = mock(Person.class);
        facewallDAO.addPerson(mockPerson);

        verify(mockPersonNodeMapper).mapNodeProperties(mockPerson);

    }

    @Test
    public void create_person_delegates_to_relationship_mapper() {
        Person mockPerson = mock(Person.class);
        facewallDAO.addPerson(mockPerson);

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

        facewallDAO.addPerson(mockPerson);
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

        facewallDAO.addPerson(mockPerson);
        verify(mockDb).addRelationshipsToNode(mockPersonNode, stubRelations);
    }

    @Test
    public void query_persons_starts_and_finishes_transaction() {
        stubOutMocks();

        facewallDAO.queryPersons(someQuery);

        verifyTransactionComplete();
    }

    @Test
    public void query_persons_should_build_indexQuery_on_name() {
        stubOutMocks();

        Query query = mock(Query.class);
        when(query.toRegEx()).thenReturn("search-term");

        facewallDAO.queryPersons(query);

        verify(mockDb).lookupNodesInIndex(argThat(is(anIndexQuery()
            .queryingOnAnIndexNamed("Persons_Name")
            .queryingOnTheKey("name")
            .queryingForTheValue("search-term")
        )));
    }

    @Test
    public void query_persons_fetches_person_nodes() {
        Node expectedPersonNode1 = mock(Node.class);
        Node expectedPersonNode2 = mock(Node.class);

        IndexHits<Node> mockIndexHits = createMockIndexHits(
            expectedPersonNode1,
            expectedPersonNode2
        );
        when(mockDb.lookupNodesInIndex(any(IndexQuery.class)))
            .thenReturn(mockIndexHits);

        List<PersonDTO> result = facewallDAO.queryPersons(someQuery);

        assertThat(result, containsExhaustively(
           aPersonDTO().withPersonNode(sameInstance(expectedPersonNode1)),
           aPersonDTO().withPersonNode(sameInstance(expectedPersonNode2))
        ));
    }

    @Test
    public void query_persons_fetches_persons_team_nodes() {
        IndexHits<Node> mockIndexHits = createMockIndexHits(
            mock(Node.class), mock(Node.class)
        );
        when(mockDb.lookupNodesInIndex(any(IndexQuery.class)))
            .thenReturn(mockIndexHits);

        Node expectedTeamNode1 = mock(Node.class);
        Node expectedTeamNode2 = mock(Node.class);

        when(mockDb.findSingleRelatedNode(any(Node.class)))
            .thenReturn(expectedTeamNode1)
            .thenReturn(expectedTeamNode2);

        List<PersonDTO> result = facewallDAO.queryPersons(someQuery);

        assertThat(result, containsExhaustively(
           aPersonDTO().withTeamNode(sameInstance(expectedTeamNode1)),
           aPersonDTO().withTeamNode(sameInstance(expectedTeamNode2))
        ));
    }

    @Test
    public void query_persons_looks_up_team_node_for_person() {
        Node personNode1 = mock(Node.class);
        Node personNode2 = mock(Node.class);

        IndexHits<Node> mockIndexHits = createMockIndexHits(
            personNode1, personNode2
        );
        when(mockDb.lookupNodesInIndex(any(IndexQuery.class)))
            .thenReturn(mockIndexHits);

        facewallDAO.queryPersons(someQuery);

        verify(mockDb).findSingleRelatedNode(personNode1);
        verify(mockDb).findSingleRelatedNode(personNode2);
    }

    @Test
    public void query_teams_starts_and_finishes_transaction() {
        stubOutMocks();

        facewallDAO.queryTeams(someQuery);

        verifyTransactionComplete();
    }

    @Test
    public void query_teams_builds_indexQuery_on_name() {
        stubOutMocks();

        Query query = mock(Query.class);
        when(query.toRegEx()).thenReturn("search-term");

        facewallDAO.queryTeams(query);

        verify(mockDb).lookupNodesInIndex(argThat(is(anIndexQuery()
            .queryingOnAnIndexNamed("Teams_Name")
            .queryingOnTheKey("name")
            .queryingForTheValue("search-term")
        )));
    }

    @Test
    public void query_teams_fetches_team_nodes() {
        Node expectedTeamNode1 = mock(Node.class);
        Node expectedTeamNode2 = mock(Node.class);

        IndexHits<Node> mockIndexHits = createMockIndexHits(
            expectedTeamNode1,
            expectedTeamNode2
        );
        when(mockDb.lookupNodesInIndex(any(IndexQuery.class)))
            .thenReturn(mockIndexHits);

        List<TeamDTO> result = facewallDAO.queryTeams(someQuery);

        assertThat(result, containsExhaustively(
            aTeamDTO().withTeamNode(sameInstance(expectedTeamNode1)),
            aTeamDTO().withTeamNode(sameInstance(expectedTeamNode2))
        ));
    }

    @Test
    public void query_teams_fetches_teams_member_nodes() {
        IndexHits<Node> mockIndexHits = createMockIndexHits(mock(Node.class));
        when(mockDb.lookupNodesInIndex(any(IndexQuery.class)))
            .thenReturn(mockIndexHits);

        Node expectedPersonNode1 = mock(Node.class);
        Node expectedPersonNode2 = mock(Node.class);

        when(mockDb.findRelatedNodes(any(Node.class))).thenReturn(asList(
            expectedPersonNode1, expectedPersonNode2)
        );

        List<TeamDTO> result = facewallDAO.queryTeams(someQuery);

        assertThat(result, containsExhaustively(
            aTeamDTO().whereMemberNodes(containsExhaustively(
                sameInstance(expectedPersonNode1),
                sameInstance(expectedPersonNode2)
            ))));
    }

    @Test
    public void query_teams_looks_up_member_nodes_for_team() {
        Node teamNode1 = mock(Node.class);

        IndexHits<Node> mockIndexHits = createMockIndexHits(teamNode1);
        when(mockDb.lookupNodesInIndex(any(IndexQuery.class)))
            .thenReturn(mockIndexHits);

        facewallDAO.queryTeams(someQuery);

        verify(mockDb).findRelatedNodes(teamNode1);
    }

    private void stubOutMocks() {
        when(mockDb.lookupNodesInIndex(any(IndexQuery.class))).thenReturn(stubNodeIndexHits);
    }

    private static IndexHits<Node> createMockIndexHits(Node... nodes) {
        IndexHits<Node> mockIndexHits = mock(IndexHits.class);
        when(mockIndexHits.iterator()).thenReturn(asList(nodes).iterator());

        return mockIndexHits;
    }
}
