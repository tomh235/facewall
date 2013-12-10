package data.dao;

import data.dao.database.FacewallDB;
import data.dao.database.IndexQuery;
import data.datatype.PersonId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.neo4j.graphdb.Node;

import java.util.List;

import static data.dao.database.IndexQueryMatcher.anIndexQuery;
import static data.datatype.PersonId.newPersonId;
import static data.datatype.TeamId.newTeamId;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TraversingDAOTest extends DAOTest {

    @Mock FacewallDB mockDb;
    private TraversingDAO dao;

    @Before
    public void setUp() throws Exception {
        dao = new TraversingDAO(mockDb);

        when(mockDb.beginTransaction()).thenReturn(mockTransaction);
    }

    @Test
    public void fetch_team_members_verify_transaction() {
        dao.fetchTeamMembers(newTeamId("some id"));

        verifyTransactionComplete();
    }

    @Test
    public void fetch_team_members_creates_index_query() {
        dao.fetchTeamMembers(newTeamId("some id"));

        verify(mockDb).lookupSingleNodeInIndex(argThat(is(anIndexQuery()
            .queryingOnAnIndexNamed("Teams")
            .queryingOnTheKey("id")
            .queryingForTheValue("some id")
        )));
    }

    @Test
    public void fetch_team_members_finds_related_nodes() {
        Node expectedTeamNode = mock(Node.class);
        when(mockDb.lookupSingleNodeInIndex(any(IndexQuery.class))).thenReturn(expectedTeamNode);

        dao.fetchTeamMembers(newTeamId("some id"));

        verify(mockDb).findRelatedNodes(
            argThat(is(sameInstance(expectedTeamNode)
        )));
    }

    @Test
    public void fetch_team_members_from_related_nodes() {
        List<Node> expectedMembersNodes = mock(List.class);
        when(mockDb.findRelatedNodes(any(Node.class))).thenReturn(expectedMembersNodes);

        List<Node> result = dao.fetchTeamMembers(newTeamId("some id"));

        assertThat(result, is(sameInstance(expectedMembersNodes)));
    }

    @Test
    public void fetch_team_for_person_verify_transaction() {
        dao.fetchTeamForPerson(newPersonId("some id"));

        verifyTransactionComplete();
    }

    @Test
    public void fetch_team_for_person_creates_index_query() {
        dao.fetchTeamForPerson(newPersonId("some id"));

        verify(mockDb).lookupSingleNodeInIndex(argThat(is(anIndexQuery()
            .queryingOnAnIndexNamed("Persons")
            .queryingOnTheKey("id")
            .queryingForTheValue("some id")
        )));
    }

    @Test
    public void fetch_team_for_person_finds_related_nodes() {
        Node expectedPersonNode = mock(Node.class);
        when(mockDb.lookupSingleNodeInIndex(any(IndexQuery.class))).thenReturn(expectedPersonNode);

        dao.fetchTeamForPerson(newPersonId("some id"));

        verify(mockDb).findSingleRelatedNode(
            argThat(is(sameInstance(expectedPersonNode)
        )));
    }

    @Test
    public void fetch_team_for_person_from_related_node() {
        Node expectedTeamNode = mock(Node.class);
        when(mockDb.findSingleRelatedNode(any(Node.class))).thenReturn(expectedTeamNode);

        Node result = dao.fetchTeamForPerson(newPersonId("some id"));

        assertThat(result, is(sameInstance(expectedTeamNode)));
    }

}
