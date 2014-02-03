package data.dao;

import data.dao.database.query.DatabaseQueryBuilder;
import data.dao.database.query.DatabaseQueryFactory;
import data.dao.database.query.FacewallQueryResultsMapper;
import data.datatype.PersonId;
import data.datatype.TeamId;
import data.dto.PersonDTO;
import data.dto.TeamDTO;
import domain.Query;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static data.dao.database.DatabaseQueryBuilderMatcher.aDatabaseQueryBuilder;
import static data.dao.database.DatabaseQueryMatchers.*;
import static data.datatype.PersonId.newPersonId;
import static data.datatype.TeamId.newTeamId;
import static domain.Query.newCaseSensitiveQuery;
import static domain.datatype.QueryString.newQueryString;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FacewallDAOTest {

    private static final Query someQuery = newCaseSensitiveQuery("blah");
    private static final TeamId someTeamId = newTeamId("some-team-id");
    private static final PersonId somePersonId = newPersonId("some-person-id");
    
    @Mock private QueryingDAO mockQueryingDAO;

    private FacewallDAO facewallDAO;

    @Before
    public void setUp() throws Exception {
        facewallDAO = new FacewallDAO(mockQueryingDAO, new DatabaseQueryFactory(mock(FacewallQueryResultsMapper.class)));
    }

    @Test
    public void fetch_team_retrieves_team_dto() {
        TeamDTO expectedTeamDTO = mock(TeamDTO.class);
        when(mockQueryingDAO.queryTeams(any(DatabaseQueryBuilder.class)))
                .thenReturn(asList(expectedTeamDTO));
        
        TeamDTO result = facewallDAO.fetchTeam(someTeamId);

        assertThat(result, is(
                sameInstance(expectedTeamDTO)
        ));
    }
    
    @Test
    public void fetch_team_queries_for_id() {
        stubDAO();

        facewallDAO.fetchTeam(newTeamId("team-id"));
        
        verify(mockQueryingDAO).queryTeams(argThat(is(aDatabaseQueryBuilder()
            .whichBuilds(aQueryForTeamWithId("team-id"))
        )));
    }
    
    @Test
    public void fetch_person_retrieves_person_dto() {
        PersonDTO expectedPersonDTO = mock(PersonDTO.class);
        when(mockQueryingDAO.queryPersons(any(DatabaseQueryBuilder.class)))
                .thenReturn(asList(expectedPersonDTO));
        
        PersonDTO result = facewallDAO.fetchPerson(somePersonId);

        assertThat(result, is(
                sameInstance(expectedPersonDTO)
        ));
    }
    
    @Test
    public void fetch_person_queries_for_id() {
        stubDAO();

        facewallDAO.fetchPerson(newPersonId("person-id"));
        
        verify(mockQueryingDAO).queryPersons(argThat(is(aDatabaseQueryBuilder()
            .whichBuilds(aQueryForPersonWithId("person-id"))
        )));
    }

    @Test
    public void fetch_persons_retrieves_persons() {
        Iterable<PersonDTO> expectedPersons = asList(mock(PersonDTO.class), mock(PersonDTO.class));
        when(mockQueryingDAO.queryPersons(any(DatabaseQueryBuilder.class)))
                .thenReturn(expectedPersons);

        Iterable<PersonDTO> result = facewallDAO.fetchPersons();

        assertThat(result, is(
                sameInstance(expectedPersons)
        ));
    }

    @Test
    public void fetch_persons_creates_query_for_persons() {
        facewallDAO.fetchPersons();

        verify(mockQueryingDAO).queryPersons(argThat(is(aDatabaseQueryBuilder()
                .whichBuilds(aQueryForAllPersons())
        )));
    }

    @Test
    public void fetch_teams_retrieves_teams() {
        Iterable<TeamDTO> expectedTeams = asList(mock(TeamDTO.class), mock(TeamDTO.class));
        when(mockQueryingDAO.queryTeams(any(DatabaseQueryBuilder.class)))
                .thenReturn(expectedTeams);

        Iterable<TeamDTO> result = facewallDAO.fetchTeams();

        assertThat(result, is(
                sameInstance(expectedTeams)
        ));
    }

    @Test
    public void fetch_teams_creates_query_for_teams() {
        facewallDAO.fetchTeams();

        verify(mockQueryingDAO).queryTeams(argThat(is(aDatabaseQueryBuilder()
                .whichBuilds(aQueryForAllTeams())
        )));
    }

    @Test
    public void query_persons_retrieves_persons() {
        Iterable<PersonDTO> expectedPersons = asList(mock(PersonDTO.class), mock(PersonDTO.class));
        when(mockQueryingDAO.queryPersons(any(DatabaseQueryBuilder.class)))
                .thenReturn(expectedPersons);

        Iterable<PersonDTO> result = facewallDAO.queryPersons(someQuery);

        assertThat(result, is(
                sameInstance(expectedPersons)
        ));
    }

    @Test
    public void query_persons_creates_query_for_person_name() {
        Query query = mock(Query.class);
        when(query.queryString()).thenReturn(newQueryString("imogen"));

        facewallDAO.queryPersons(query);

        verify(mockQueryingDAO).queryPersons(argThat(is(aDatabaseQueryBuilder()
                .whichBuilds(aQueryForPersonsNamed("imogen"))
        )));
    }

    @Test
    public void query_teams_retrieves_teams() {
        Iterable<TeamDTO> expectedTeams = asList(mock(TeamDTO.class), mock(TeamDTO.class));
        when(mockQueryingDAO.queryTeams(any(DatabaseQueryBuilder.class)))
                .thenReturn(expectedTeams);

        Iterable<TeamDTO> result = facewallDAO.queryTeams(someQuery);

        assertThat(result, is(
                sameInstance(expectedTeams)
        ));
    }

    @Test
    public void query_teams_creates_query_for_team_name() {
        Query query = mock(Query.class);
        when(query.queryString()).thenReturn(newQueryString("imogen"));

        facewallDAO.queryTeams(query);

        verify(mockQueryingDAO).queryTeams(argThat(is(aDatabaseQueryBuilder()
                .whichBuilds(aQueryForTeamsWithName("imogen"))
        )));
    }

    private void stubDAO() {
        when(mockQueryingDAO.queryPersons(any(DatabaseQueryBuilder.class)))
                .thenReturn(asList(mock(PersonDTO.class)));

        when(mockQueryingDAO.queryTeams(any(DatabaseQueryBuilder.class)))
                .thenReturn(asList(mock(TeamDTO.class)));
    }
}
