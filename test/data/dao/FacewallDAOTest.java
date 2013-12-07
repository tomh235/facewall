package data.dao;

import data.dao.database.FacewallDB;
import data.dao.database.IndexQuery;
import data.dto.PersonDTO;
import data.dto.TeamDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.IndexHits;

import java.util.List;

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

    @Mock FacewallDB mockDb;
    private FacewallDAO facewallDAO;

    @Before
    public void setUp() throws Exception {
        facewallDAO = new FacewallDAO(mockDb);
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
    public void fetch_persons_creates_index_query() {
        stubOutMocks();
        facewallDAO.fetchPersons();

        verify(mockDb).lookupNodesInIndex(
            argThat(is(anIndexQuery()
                .queryingOnAnIndexNamed("Persons")
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
                .queryingOnAnIndexNamed("Teams")
                .queryingOnTheKey("id")
                .queryingForAllValues()
            ))
        );
    }

    @Test
    public void fetch_person_retrieves_person_nodes() {
        IndexHits<Node> personNodeHits = mock(IndexHits.class);
        when(mockDb.lookupNodesInIndex(any(IndexQuery.class)))
            .thenReturn(personNodeHits);

        Node expectedPersonNode1 = mock(Node.class);
        Node expectedPersonNode2 = mock(Node.class);
        when(personNodeHits.iterator()).thenReturn(asList(
            expectedPersonNode1, expectedPersonNode2
        ).iterator());

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
        IndexHits<Node> personNodeHits = mock(IndexHits.class);
        when(mockDb.lookupNodesInIndex(any(IndexQuery.class)))
            .thenReturn(personNodeHits);

        when(personNodeHits.iterator()).thenReturn(asList(
            mock(Node.class), mock(Node.class)
        ).iterator());

        Node expectedTeamNode1 = mock(Node.class);
        Node expectedTeamNode2 = mock(Node.class);

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
        IndexHits<Node> teamNodeHits = mock(IndexHits.class);
        when(mockDb.lookupNodesInIndex(any(IndexQuery.class)))
            .thenReturn(teamNodeHits);

        Node expectedTeamNode1 = mock(Node.class);
        Node expectedTeamNode2 = mock(Node.class);
        when(teamNodeHits.iterator()).thenReturn(asList(
            expectedTeamNode1, expectedTeamNode2
        ).iterator());

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
        IndexHits<Node> teamNodeHits = mock(IndexHits.class);
        when(mockDb.lookupNodesInIndex(any(IndexQuery.class)))
            .thenReturn(teamNodeHits);

        when(teamNodeHits.iterator()).thenReturn(asList(
            mock(Node.class)
        ).iterator());

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

    private void stubOutMocks() {
        when(mockDb.lookupNodesInIndex(any(IndexQuery.class))).thenReturn(stubNodeIndexHits);
    }
}
