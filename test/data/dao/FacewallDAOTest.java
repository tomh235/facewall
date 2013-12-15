package data.dao;

import data.dao.database.FacewallDB;
import data.dao.database.QueryResultRow;
import data.dao.database.query.DatabaseQueryBuilder;
import data.dao.database.query.PersonDatabaseQueryBuilder;
import data.dao.database.query.TeamDatabaseQueryBuilder;
import data.datatype.PersonId;
import data.datatype.TeamId;
import data.dto.PersonDTO;
import data.dto.TeamDTO;
import domain.Query;
import domain.datatype.QueryString;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.neo4j.graphdb.Node;

import static data.dao.MockQueryResultRowFactory.createMockQueryResultRow;
import static data.dao.database.DatabaseQueryBuilderMatcher.aDatabaseQueryBuilder;
import static data.dao.database.DatabaseQueryMatchers.*;
import static data.datatype.PersonId.newPersonId;
import static data.datatype.TeamId.newTeamId;
import static data.dto.PersonDTOMatcher.aPersonDTO;
import static data.dto.TeamDTOMatcher.aTeamDTO;
import static domain.datatype.QueryString.newQueryString;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;
import static util.IterableMatchers.containsExhaustivelyInAnyOrder;
import static util.IterableMatchers.containsExhaustivelyInOrder;

@RunWith(MockitoJUnitRunner.class)
public class FacewallDAOTest {

    private static final TeamId someTeamId = newTeamId("blah");
    private static final PersonId somePersonId = newPersonId("bloom");
    private static final Query someQuery = new Query() {
        @Override public QueryString queryString() {
            return newQueryString("some-query");
        }
    };

    @Mock FacewallDB mockDb;

    @InjectMocks
    private FacewallDAO facewallDAO;

    @Test
    public void fetch_persons_retrieves_person_nodes() {
        Node expectedPersonNode1 = mock(Node.class);
        Node expectedPersonNode2 = mock(Node.class);

        Iterable<QueryResultRow> mockQueryResults = asList(
            createMockQueryResultRow(expectedPersonNode1, mock(Node.class)),
            createMockQueryResultRow(expectedPersonNode2, mock(Node.class))
        );

        when(mockDb.query(any(PersonDatabaseQueryBuilder.class)))
            .thenReturn(mockQueryResults);

        Iterable<PersonDTO> result = facewallDAO.fetchPersons();

        assertThat(result, containsExhaustivelyInAnyOrder(
            aPersonDTO().withPersonNode(sameInstance(expectedPersonNode1)),
            aPersonDTO().withPersonNode(sameInstance(expectedPersonNode2))
        ));
    }

    @Test
    public void fetch_persons_retrieves_team_node() {
        Node expectedTeamNode = mock(Node.class);

        Iterable<QueryResultRow> mockQueryResults = asList(
            createMockQueryResultRow(mock(Node.class), expectedTeamNode)
        );

        when(mockDb.query(any(PersonDatabaseQueryBuilder.class)))
            .thenReturn(mockQueryResults);

        Iterable<PersonDTO> result = facewallDAO.fetchPersons();

        assertThat(result, containsExhaustivelyInOrder(
            aPersonDTO().withTeamNode(
                sameInstance(expectedTeamNode)
            )));
    }

    @Test
    public void fetch_persons_creates_query_for_persons() {
        stubDb();

        facewallDAO.fetchPersons();

        verify(mockDb).query(argThat(is(aDatabaseQueryBuilder()
            .whichBuilds(aQueryForAllPersons())
        )));
    }

    @Test
    public void fetch_teams_retrieves_team_nodes() {
        Node expectedTeamNode1 = mock(Node.class);
        Node expectedTeamNode2 = mock(Node.class);

        Iterable<QueryResultRow> mockQueryResults = asList(
            createMockQueryResultRow(mock(Node.class), expectedTeamNode1),
            createMockQueryResultRow(mock(Node.class), expectedTeamNode2)
        );

        when(mockDb.query(any(TeamDatabaseQueryBuilder.class)))
            .thenReturn(mockQueryResults);

        Iterable<TeamDTO> result = facewallDAO.fetchTeams();

        assertThat(result, containsExhaustivelyInAnyOrder(
            aTeamDTO().withTeamNode(sameInstance(expectedTeamNode1)),
            aTeamDTO().withTeamNode(sameInstance(expectedTeamNode2))
        ));
    }

    @Test
    public void fetch_teams_retrieves_members_nodes() {
        Node expectedMemberNode1 = mock(Node.class);
        Node expectedMemberNode2 = mock(Node.class);

        Node teamNode = mock(Node.class);

        Iterable<QueryResultRow> mockQueryResults = asList(
            createMockQueryResultRow(expectedMemberNode1, teamNode),
            createMockQueryResultRow(expectedMemberNode2, teamNode)
        );

        when(mockDb.query(any(TeamDatabaseQueryBuilder.class)))
            .thenReturn(mockQueryResults);

        Iterable<TeamDTO> result = facewallDAO.fetchTeams();

        assertThat(result, containsExhaustivelyInOrder(
            aTeamDTO().whereMemberNodes(containsExhaustivelyInOrder(
                sameInstance(expectedMemberNode1),
                sameInstance(expectedMemberNode2)
            ))));
    }

    @Test
    public void fetch_teams_creates_query_for_teams() {
        stubDb();

        facewallDAO.fetchTeams();

        verify(mockDb).query(argThat(is(aDatabaseQueryBuilder()
            .whichBuilds(aQueryForAllTeams())
        )));
    }

    @Test
    public void fetch_team_retrieves_team_node() {
        Node expectedTeamNode1 = mock(Node.class);

        Iterable<QueryResultRow> mockQueryResults = asList(
            createMockQueryResultRow(mock(Node.class), expectedTeamNode1)
        );
        when(mockDb.query(any(TeamDatabaseQueryBuilder.class)))
            .thenReturn(mockQueryResults);

        TeamDTO result = facewallDAO.fetchTeam(someTeamId);

        assertThat(result, is(
            aTeamDTO().withTeamNode(sameInstance(expectedTeamNode1))
        ));
    }

    @Test
    public void fetch_team_retrieves_members_nodes() {
        Node expectedMemberNode1 = mock(Node.class);
        Node expectedMemberNode2 = mock(Node.class);

        Node teamNode = mock(Node.class);

        Iterable<QueryResultRow> mockQueryResults = asList(
            createMockQueryResultRow(expectedMemberNode1, teamNode),
            createMockQueryResultRow(expectedMemberNode2, teamNode)
        );

        when(mockDb.query(any(TeamDatabaseQueryBuilder.class)))
            .thenReturn(mockQueryResults);

        TeamDTO result = facewallDAO.fetchTeam(someTeamId);

        assertThat(result, is(
            aTeamDTO().whereMemberNodes(containsExhaustivelyInOrder(
                sameInstance(expectedMemberNode1),
                sameInstance(expectedMemberNode2)
            ))));
    }

    @Test
    public void fetch_team_creates_query_for_team_with_id() {
        stubDb();

        facewallDAO.fetchTeam(newTeamId("team id"));

        verify(mockDb).query(argThat(is(aDatabaseQueryBuilder()
            .whichBuilds(aQueryForTeamWithId("team id"))
        )));
    }

    @Test
    public void fetch_person_retrieves_person_node() {
        Node expectedPersonNode1 = mock(Node.class);

        Iterable<QueryResultRow> mockQueryResults = asList(
            createMockQueryResultRow(expectedPersonNode1, mock(Node.class))
        );
        when(mockDb.query(any(PersonDatabaseQueryBuilder.class)))
            .thenReturn(mockQueryResults);

        PersonDTO result = facewallDAO.fetchPerson(somePersonId);

        assertThat(result, is(
            aPersonDTO().withPersonNode(sameInstance(expectedPersonNode1))
        ));
    }

    @Test
    public void fetch_person_retrieves_members_nodes() {
        Node expectedTeamNode = mock(Node.class);

        Iterable<QueryResultRow> mockQueryResults = asList(
            createMockQueryResultRow(mock(Node.class), expectedTeamNode)
        );

        when(mockDb.query(any(PersonDatabaseQueryBuilder.class)))
            .thenReturn(mockQueryResults);

        PersonDTO result = facewallDAO.fetchPerson(somePersonId);

        assertThat(result, is(
            aPersonDTO().withTeamNode(sameInstance(expectedTeamNode))
        ));
    }

    @Test
    public void fetch_person_creates_query_for_person_with_id() {
        stubDb();

        facewallDAO.fetchPerson(newPersonId("person-id"));

        verify(mockDb).query(argThat(is(aDatabaseQueryBuilder()
            .whichBuilds(aQueryForPersonWithId("person-id"))
        )));
    }

    @Test
    public void query_persons_retrieves_person_nodes() {
        Node expectedPersonNode1 = mock(Node.class);
        Node expectedPersonNode2 = mock(Node.class);

        Iterable<QueryResultRow> mockQueryResults = asList(
            createMockQueryResultRow(expectedPersonNode1, mock(Node.class)),
            createMockQueryResultRow(expectedPersonNode2, mock(Node.class))
        );

        when(mockDb.query(any(PersonDatabaseQueryBuilder.class)))
            .thenReturn(mockQueryResults);

        Iterable<PersonDTO> result = facewallDAO.queryPersons(someQuery);

        assertThat(result, containsExhaustivelyInAnyOrder(
            aPersonDTO().withPersonNode(sameInstance(expectedPersonNode1)),
            aPersonDTO().withPersonNode(sameInstance(expectedPersonNode2))
        ));
    }

    @Test
    public void query_persons_retrieves_team_node() {
        Node expectedTeamNode = mock(Node.class);

        Iterable<QueryResultRow> mockQueryResults = asList(
            createMockQueryResultRow(mock(Node.class), expectedTeamNode)
        );

        when(mockDb.query(any(PersonDatabaseQueryBuilder.class)))
            .thenReturn(mockQueryResults);

        Iterable<PersonDTO> result = facewallDAO.queryPersons(someQuery);

        assertThat(result, containsExhaustivelyInOrder(
            aPersonDTO().withTeamNode(
                sameInstance(expectedTeamNode)
            )));
    }

    @Test
    public void query_persons_creates_query_for_person_name() {
        stubDb();

        Query query = mock(Query.class);
        when(query.queryString()).thenReturn(newQueryString("imogen"));

        facewallDAO.queryPersons(query);

        verify(mockDb).query(argThat(is(aDatabaseQueryBuilder()
            .whichBuilds(aQueryForPersonsNamed("imogen"))
        )));
    }

    @Test
    public void query_teams_retrieves_team_nodes() {
        Node expectedTeamNode1 = mock(Node.class);
        Node expectedTeamNode2 = mock(Node.class);

        Iterable<QueryResultRow> mockQueryResults = asList(
            createMockQueryResultRow(mock(Node.class), expectedTeamNode1),
            createMockQueryResultRow(mock(Node.class), expectedTeamNode2)
        );

        when(mockDb.query(any(TeamDatabaseQueryBuilder.class)))
            .thenReturn(mockQueryResults);

        Iterable<TeamDTO> result = facewallDAO.queryTeams(someQuery);

        assertThat(result, containsExhaustivelyInAnyOrder(
            aTeamDTO().withTeamNode(sameInstance(expectedTeamNode1)),
            aTeamDTO().withTeamNode(sameInstance(expectedTeamNode2))
        ));
    }

    @Test
    public void query_teams_retrieves_members_nodes() {
        Node expectedMemberNode1 = mock(Node.class);
        Node expectedMemberNode2 = mock(Node.class);

        Node teamNode = mock(Node.class);

        Iterable<QueryResultRow> mockQueryResults = asList(
            createMockQueryResultRow(expectedMemberNode1, teamNode),
            createMockQueryResultRow(expectedMemberNode2, teamNode)
        );

        when(mockDb.query(any(TeamDatabaseQueryBuilder.class)))
            .thenReturn(mockQueryResults);

        Iterable<TeamDTO> result = facewallDAO.queryTeams(someQuery);

        assertThat(result, containsExhaustivelyInOrder(
            aTeamDTO().whereMemberNodes(containsExhaustivelyInOrder(
                sameInstance(expectedMemberNode1),
                sameInstance(expectedMemberNode2)
            ))));
    }

    @Test
    public void query_teams_creates_query_for_teams_with_name() {
        stubDb();

        Query query = mock(Query.class);
        when(query.queryString()).thenReturn(newQueryString("bobby"));

        facewallDAO.queryTeams(query);

        verify(mockDb).query(argThat(is(aDatabaseQueryBuilder()
            .whichBuilds(aQueryForTeamsWithName("bobby"))
        )));
    }

    private void stubDb() {
        when(mockDb.query(any(DatabaseQueryBuilder.class))).thenReturn(
            asList(mock(QueryResultRow.class))
        );
    }
}
