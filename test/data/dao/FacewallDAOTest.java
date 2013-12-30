package data.dao;

import data.dao.database.FacewallDB;
import data.dao.database.QueryResultRow;
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
import static data.dao.database.QueryResultRowBuilder.blankRow;
import static data.dao.database.QueryResultRowBuilder.defaultRow;
import static data.dao.database.QueryResultsBuilder.results;
import static data.datatype.PersonId.newPersonId;
import static data.datatype.TeamId.newTeamId;
import static data.dto.PersonDTOMatcher.aPersonDTO;
import static data.dto.PersonInformation.newPersonInformation;
import static data.dto.PersonInformationMatcher.aPersonInformation;
import static data.dto.TeamDTOMatcher.aTeamDTO;
import static data.dto.TeamInformation.newTeamInformation;
import static data.dto.TeamInformationMatcher.aTeamInformation;
import static domain.Query.newQuery;
import static domain.datatype.QueryString.newQueryString;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;
import static util.IterableMatchers.containsExhaustivelyInAnyOrder;


// This class has a lot of tests which look very similar. Perhaps we could extract out the
// fetchPersons, fetchTeams, fetchPerson, fetchTeam methods into a separate helper class so that
// we can test this more easily?
@RunWith(MockitoJUnitRunner.class)
public class FacewallDAOTest {

    private static final TeamId someTeamId = newTeamId("blah");
    private static final PersonId somePersonId = newPersonId("bloom");
    private static final Query someQuery = newQuery("query");

    @Mock FacewallDB mockDb;

    private FacewallDAO facewallDAO;

    @Before
    public void setUp() throws Exception {
        facewallDAO = new FacewallDAO(mockDb, new DatabaseQueryFactory(mock(FacewallQueryResultsMapper.class)));
    }

    @Test
    public void fetch_persons_retrieves_person_nodes() {
        stubDb(results().withRows(
                defaultRow().withPerson(newPersonInformation().withId("jim")),
                defaultRow().withPerson(newPersonInformation().withId("bob"))
        ).build());

        Iterable<PersonDTO> result = facewallDAO.fetchPersons();
        assertThat(result, containsExhaustivelyInAnyOrder(
                aPersonDTO().withPerson(aPersonInformation().withId("jim")),
                aPersonDTO().withPerson(aPersonInformation().withId("bob"))
        ));
    }

    @Test
    public void fetch_persons_retrieves_team_node() {

        stubDb(results().withRows(
                defaultRow().withTeam(newTeamInformation().withId("reds"))
        ).build());

        Iterable<PersonDTO> result = facewallDAO.fetchPersons();

        assertThat(result, containsExhaustivelyInAnyOrder(
                aPersonDTO().withTeam(aTeamInformation()
                        .withId("reds")
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
        stubDb(results().withRows(
                defaultRow().withTeam(newTeamInformation().withId("blues")),
                defaultRow().withTeam(newTeamInformation().withId("light blues"))
        ).build());

        Iterable<TeamDTO> result = facewallDAO.fetchTeams();

        assertThat(result, containsExhaustivelyInAnyOrder(
                aTeamDTO().withTeamInformation(aTeamInformation().withId("blues")),
                aTeamDTO().withTeamInformation(aTeamInformation().withId("light blues"))
        ));
    }

    @Test
    public void fetch_teams_retrieves_members_nodes() {
        stubDb(results().withRows(
                blankRow()
                        .withPerson(newPersonInformation().withId("henry"))
                        .withTeam(newTeamInformation().withId("first team")),
                blankRow()
                        .withPerson(newPersonInformation().withId("percy"))
                        .withTeam(newTeamInformation().withId("first team")),
                blankRow()
                        .withPerson(newPersonInformation().withId("edward"))
                        .withTeam(newTeamInformation().withId("second team"))
        ).build());

        Iterable<TeamDTO> result = facewallDAO.fetchTeams();

        assertThat(result, containsExhaustivelyInAnyOrder(
                aTeamDTO().withTeamInformation(aTeamInformation().withId("first team"))
                        .whereMemberInformation(containsExhaustivelyInAnyOrder(
                                aPersonInformation().withId("henry"),
                                aPersonInformation().withId("percy")
                        )),
                aTeamDTO().withTeamInformation(aTeamInformation().withId("second team"))
                        .whereMemberInformation(containsExhaustivelyInAnyOrder(
                                aPersonInformation().withId("edward")
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
        stubDb(results().withRows(
                defaultRow().withTeam(newTeamInformation().withId("team"))
        ).build());

        TeamDTO result = facewallDAO.fetchTeam(someTeamId);

        assertThat(result, is(
                aTeamDTO().withTeamInformation(aTeamInformation().withId("team"))
        ));
    }

    @Test
    public void fetch_team_retrieves_members_nodes() {
        stubDb(results().withRows(
                blankRow()
                        .withPerson(newPersonInformation().withId("henry"))
                        .withTeam(newTeamInformation().withId("first team")),
                blankRow()
                        .withPerson(newPersonInformation().withId("percy"))
                        .withTeam(newTeamInformation().withId("first team"))
        ).build());

        TeamDTO result = facewallDAO.fetchTeam(someTeamId);

        assertThat(result, is(
                aTeamDTO().whereMemberInformation(containsExhaustivelyInAnyOrder(
                        aPersonInformation().withId("henry"),
                        aPersonInformation().withId("percy")
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
        stubDb(results().withRows(
                defaultRow().withPerson(newPersonInformation().withId("jim"))
        ).build());

        PersonDTO result = facewallDAO.fetchPerson(somePersonId);

        assertThat(result, is(
                aPersonDTO().withPerson(aPersonInformation().withId("jim"))
        ));
    }

    @Test
    public void fetch_person_retrieves_members_nodes() {
        stubDb(results().withRows(
                defaultRow().withTeam(newTeamInformation().withId("jim's team"))
        ).build());

        PersonDTO result = facewallDAO.fetchPerson(somePersonId);

        assertThat(result, is(
                aPersonDTO().withTeam(aTeamInformation().withId("jim's team"))
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
        stubDb(results().withRows(
                defaultRow().withPerson(newPersonInformation().withId("jim")),
                defaultRow().withPerson(newPersonInformation().withId("bob"))
        ).build());

        Iterable<PersonDTO> result = facewallDAO.queryPersons(someQuery);

        assertThat(result, containsExhaustivelyInAnyOrder(
                aPersonDTO().withPerson(aPersonInformation().withId("jim")),
                aPersonDTO().withPerson(aPersonInformation().withId("bob"))
        ));
    }

    @Test
    public void query_persons_retrieves_team_node() {
        stubDb(results().withRows(
                defaultRow().withTeam(newTeamInformation().withId("bob's team"))
        ).build());

        Iterable<PersonDTO> result = facewallDAO.queryPersons(someQuery);

        assertThat(result, containsExhaustivelyInAnyOrder(
                aPersonDTO().withTeam(
                        aTeamInformation().withId("bob's team")
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
        stubDb(results().withRows(
                defaultRow().withTeam(newTeamInformation().withId("blues")),
                defaultRow().withTeam(newTeamInformation().withId("light blues"))
        ).build());

        Iterable<TeamDTO> result = facewallDAO.queryTeams(someQuery);

        assertThat(result, containsExhaustivelyInAnyOrder(
                aTeamDTO().withTeamInformation(aTeamInformation().withId("blues")),
                aTeamDTO().withTeamInformation(aTeamInformation().withId("light blues"))
        ));
    }

    @Test
    public void query_teams_retrieves_members_nodes() {
        stubDb(results().withRows(
                blankRow()
                        .withPerson(newPersonInformation().withId("henry"))
                        .withTeam(newTeamInformation().withId("first team")),
                blankRow()
                        .withPerson(newPersonInformation().withId("percy"))
                        .withTeam(newTeamInformation().withId("first team")),
                blankRow()
                        .withPerson(newPersonInformation().withId("edward"))
                        .withTeam(newTeamInformation().withId("second team"))
        ).build());

        Iterable<TeamDTO> result = facewallDAO.queryTeams(someQuery);

        assertThat(result, containsExhaustivelyInAnyOrder(
                aTeamDTO().withTeamInformation(aTeamInformation().withId("first team"))
                        .whereMemberInformation(containsExhaustivelyInAnyOrder(
                                aPersonInformation().withId("henry"),
                                aPersonInformation().withId("percy")
                        )),
                aTeamDTO().withTeamInformation(aTeamInformation().withId("second team"))
                        .whereMemberInformation(containsExhaustivelyInAnyOrder(
                                aPersonInformation().withId("edward")
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

    private void stubDb(Iterable<QueryResultRow> queryResults) {
        when(mockDb.query(any(DatabaseQueryBuilder.class)))
                .thenReturn(queryResults);
    }
}
