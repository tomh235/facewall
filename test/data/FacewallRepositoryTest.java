package data;

import data.dao.FacewallDAO;
import data.datatype.PersonId;
import data.dto.PersonDTO;
import data.dto.TeamDTO;
import data.factory.PersonFactory;
import data.factory.TeamFactory;
import data.mapper.PersonNodeMapper;
import domain.Person;
import domain.Query;
import domain.Team;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static data.datatype.PersonId.newPersonId;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FacewallRepositoryTest {
    private static final Query someQuery = mock(Query.class);
    private static final PersonId somePersonId = newPersonId("some-id");

    @Mock PersonFactory mockPersonFactory;
    @Mock TeamFactory mockTeamFactory;
    @Mock FacewallDAO mockfacewallDAO;
    @Mock PersonNodeMapper personNodeMapper;

    @InjectMocks
    FacewallRepository repository;

    @Test
    public void fetch_persons_delegates_to_factory() {
        List<Person> expectedPersons = mock(List.class);
        when(mockPersonFactory.createPersons(anyList())).thenReturn(expectedPersons);

        List<Person> result = repository.listPersons();
        assertThat(result, is(sameInstance(expectedPersons)));
    }

    @Test
    public void fetch_persons_verifyInteractions() {
        List<PersonDTO> expectedDTOs = mock(List.class);
        when(mockfacewallDAO.fetchPersons()).thenReturn(expectedDTOs);

        repository.listPersons();

        verify(mockfacewallDAO).fetchPersons();
        verify(mockPersonFactory).createPersons(expectedDTOs);
    }

    @Test
    public void fetch_team_delegates_to_factory() {
        List<Team> expectedTeams = mock(List.class);
        when(mockTeamFactory.createTeams(anyList())).thenReturn(expectedTeams);

        List<Team> result = repository.listTeams();
        assertThat(result, is(sameInstance(expectedTeams)));
    }

    @Test
    public void fetch_team_verifyInteractions() {
        List<TeamDTO> expectedDTOs = mock(List.class);
        when(mockfacewallDAO.fetchTeams()).thenReturn(expectedDTOs);

        repository.listTeams();

        verify(mockfacewallDAO).fetchTeams();
        verify(mockTeamFactory).createTeams(expectedDTOs);
    }

    @Test
    public void query_persons_delegates_to_factory() {
        List<Person> expectedPersons = mock(List.class);
        when(mockPersonFactory.createPersons(anyList())).thenReturn(expectedPersons);

        List<Person> result = repository.queryPersons(someQuery);
        assertThat(result, is(sameInstance(expectedPersons)));
    }

    @Test
    public void query_persons_verifyInteractions() {
        List<PersonDTO> expectedDTOs = mock(List.class);
        when(mockfacewallDAO.queryPersons(any(Query.class))).thenReturn(expectedDTOs);

        Query query = mock(Query.class);
        repository.queryPersons(query);

        verify(mockfacewallDAO).queryPersons(query);
        verify(mockPersonFactory).createPersons(expectedDTOs);
    }

    @Test
    public void query_teams_delegates_to_factory() {
        List<Team> expectedTeams = mock(List.class);
        when(mockTeamFactory.createTeams(anyList())).thenReturn(expectedTeams);

        List<Team> result = repository.queryTeams(someQuery);
        assertThat(result, is(sameInstance(expectedTeams)));
    }

    @Test
    public void query_team_verifyInteractions() {
        List<TeamDTO> expectedDTOs = mock(List.class);
        when(mockfacewallDAO.queryTeams(any(Query.class))).thenReturn(expectedDTOs);

        Query query = mock(Query.class);
        repository.queryTeams(query);

        verify(mockfacewallDAO).queryTeams(query);
        verify(mockTeamFactory).createTeams(expectedDTOs);
    }

    @Test
    public void fetch_person_by_id_delegates_to_factory() {
        stubDao();

        Person expectedPerson = mock(Person.class);
        when(mockPersonFactory.createPerson(any(PersonDTO.class))).thenReturn(expectedPerson);

        Person result = repository.findPersonById(somePersonId);
        assertThat(result, is(sameInstance(expectedPerson)));
    }

    @Test
    public void fetch_person_queries_for_person_with_that_id_using_dao() {
        repository.findPersonById(newPersonId("expected-id"));

        verify(mockfacewallDAO).fetchPerson(newPersonId("expected-id"));
    }

    @Test
    public void fetch_person_builds_person_from_person_dto_using_factory() {
        PersonDTO expectedPersonDTO = mock(PersonDTO.class);
        when(mockfacewallDAO.fetchPerson(any(PersonId.class))).thenReturn(expectedPersonDTO);

        repository.findPersonById(somePersonId);

        verify(mockPersonFactory).createPerson(expectedPersonDTO);
    }

    private void stubDao() {
        when(mockfacewallDAO.fetchPerson(any(PersonId.class))).thenReturn(mock(PersonDTO.class));
    }
}
