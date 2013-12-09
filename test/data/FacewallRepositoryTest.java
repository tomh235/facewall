package data;

import data.dao.FacewallDAO;
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

    @Mock PersonFactory mockPersonFactory;
    @Mock TeamFactory mockTeamFactory;
    @Mock FacewallDAO mockDao;
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
        when(mockDao.fetchPersons()).thenReturn(expectedDTOs);

        repository.listPersons();

        verify(mockDao).fetchPersons();
        verify(mockPersonFactory).createPersons(expectedDTOs);
    }

    @Test
    public void fetch_teams_delegates_to_factory() {
        List<Team> expectedTeams = mock(List.class);
        when(mockTeamFactory.createTeams(anyList())).thenReturn(expectedTeams);

        List<Team> result = repository.listTeams();
        assertThat(result, is(sameInstance(expectedTeams)));
    }

    @Test
    public void fetch_teams_verifyInteractions() {
        List<TeamDTO> expectedDTOs = mock(List.class);
        when(mockDao.fetchTeams()).thenReturn(expectedDTOs);

        repository.listTeams();

        verify(mockDao).fetchTeams();
        verify(mockTeamFactory).createTeams(expectedDTOs);
    }

    @Test
    public void query_persons_passes_query_to_dao() {
        Query expectedQuery = mock(Query.class);

        repository.queryPersons(expectedQuery);
        verify(mockDao).queryPersons(expectedQuery);
    }

    @Test
    public void query_persons_delegates_to_factory() {
        List<Person> expectedPersons = mock(List.class);
        when(mockPersonFactory.createPersons(anyList())).thenReturn(expectedPersons);

        List<Person> result = repository.queryPersons(someQuery);
        assertThat(result, is(sameInstance(expectedPersons)));
    }

    @Test
    public void query_persons_passes_dtos_to_factory() {
        List<PersonDTO> expectedDTOs = mock(List.class);
        when(mockDao.queryPersons(any(Query.class))).thenReturn(expectedDTOs);

        repository.queryPersons(someQuery);

        verify(mockPersonFactory).createPersons(expectedDTOs);
    }

    @Test
    public void query_teams_passes_query_to_dao() {
        Query expectedQuery = mock(Query.class);

        repository.queryTeams(expectedQuery);
        verify(mockDao).queryTeams(expectedQuery);
    }

    @Test
    public void query_teams_delegates_to_factory() {
        List<Team> expectedTeams = mock(List.class);
        when(mockTeamFactory.createTeams(anyList())).thenReturn(expectedTeams);

        List<Team> result = repository.queryTeams(someQuery);
        assertThat(result, is(sameInstance(expectedTeams)));
    }

    @Test
    public void query_teams_passes_dtos_to_factory() {
        List<TeamDTO> expectedDTOs = mock(List.class);
        when(mockDao.queryTeams(any(Query.class))).thenReturn(expectedDTOs);

        repository.queryTeams(someQuery);

        verify(mockTeamFactory).createTeams(expectedDTOs);
    }

    @Test
    public void add_person_interacts_with_facewall_dao() {
        Person mockPerson = mock(Person.class);

        repository.addPerson(mockPerson);
        verify(mockDao).addPerson(mockPerson);
    }
}
