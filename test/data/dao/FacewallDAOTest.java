package data.dao;

import data.dao.database.FacewallDB;
import data.dao.database.IndexQuery;
import data.dto.PersonDTO;
import data.dto.PersonDTOMatcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.IndexHits;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static data.dao.database.IndexQueryMatcher.anIndexQuery;
import static data.dto.PersonDTOMatcher.aPersonDTO;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static util.CollectionMatcher.contains;

@RunWith(MockitoJUnitRunner.class)
public class FacewallDAOTest {

    @Mock FacewallDB mockDb;

    @Mock Transaction mockTransaction;
    @Mock IndexHits<Node> stubNodeIndexHits;

    private FacewallDAO facewallDAO;

    @Before
    public void setUp() throws Exception {
        facewallDAO = new FacewallDAO(mockDb);
        when(mockDb.beginTransaction()).thenReturn(mockTransaction);

        when(stubNodeIndexHits.iterator()).thenReturn(Collections.<Node>emptyList().iterator());
    }

    @Test
    public void fetch_persons_starts_and_finishes_transactions() {
        stubOutMocks();
        facewallDAO.fetchPersons();

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

        assertThat(result, contains(
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

        assertThat(result, contains(
            aPersonDTO().withTeamNode(
                sameInstance(expectedTeamNode1)),
            aPersonDTO().withTeamNode(
                sameInstance(expectedTeamNode2)
            )
        ));
    }

    private void stubOutMocks() {
        when(mockDb.lookupNodesInIndex(any(IndexQuery.class))).thenReturn(stubNodeIndexHits);
    }

    private void verifyTransactionComplete() {
        verify(mockDb).beginTransaction();
        verify(mockTransaction).success();
        verify(mockTransaction).finish();
    }
}
