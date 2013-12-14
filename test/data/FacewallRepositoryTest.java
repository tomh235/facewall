package data;

import data.dao.FacewallDAO;
import data.dto.PersonDTO;
import data.dto.TeamDTO;
import data.factory.PersonFactory;
import data.factory.TeamFactory;
import data.mapper.PersonNodeMapper;
import domain.Person;
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
    @Mock PersonFactory mockPersonFactory;
    @Mock TeamFactory mockTeamFactory;
    @Mock FacewallDAO mockfacewallDAO;
    @Mock PersonNodeMapper personNodeMapper;

    @InjectMocks
    FacewallRepository repository;

    @Test
    public void fetch_person_delegates_to_factory() {
        List<Person> expectedPersons = mock(List.class);
        when(mockPersonFactory.createPersons(anyList())).thenReturn(expectedPersons);

        List<Person> result = repository.listPersons();
        assertThat(result, is(sameInstance(expectedPersons)));
    }

    @Test
    public void fetch_person_verifyInteractions() {
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
}
