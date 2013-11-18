package data;

import data.dao.FacewallDAO;
import data.datatype.PersonId;
import data.datatype.TeamId;
import data.dto.PersonDTO;
import data.dto.TeamDTO;
import data.factory.PersonFactory;
import data.factory.TeamFactory;
import domain.Person;
import domain.Team;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FacewallRepositoryTest {
    @Mock PersonFactory mockPersonFactory;
    @Mock TeamFactory mockTeamFactory;
    @Mock FacewallDAO mockDao;
    private FacewallRepository repository;

    @Before
    public void setUp() throws Exception {
        repository = new FacewallRepository(mockPersonFactory, mockTeamFactory, mockDao);
    }

    @Test
    public void fetch_person_delegates_to_factory() {
        Person expectedPerson = mock(Person.class);
        when(mockPersonFactory.createPerson(any(PersonDTO.class))).thenReturn(expectedPerson);

        Person result = repository.findPerson(new PersonId("person"));
        assertThat(result, is(sameInstance(expectedPerson)));
    }

    @Test
    public void fetch_person_verifyInteractions() {
        PersonDTO expectedDTO = mock(PersonDTO.class);
        PersonId expectedId = new PersonId("expected id");
        when(mockDao.fetchPerson(any(PersonId.class))).thenReturn(expectedDTO);

        repository.findPerson(expectedId);

        verify(mockDao).fetchPerson(expectedId);
        verify(mockPersonFactory).createPerson(expectedDTO);
    }

    @Test
    public void fetch_team_delegates_to_factory() {
        Team expectedTeam = mock(Team.class);
        when(mockTeamFactory.createTeam(any(TeamDTO.class))).thenReturn(expectedTeam);

        Team result = repository.findTeam(new TeamId("team"));
        assertThat(result, is(sameInstance(expectedTeam)));
    }

    @Test
    public void fetch_team_verifyInteractions() {
        TeamDTO expectedDTO = mock(TeamDTO.class);
        TeamId expectedId = new TeamId("expected id");
        when(mockDao.fetchTeam(any(TeamId.class))).thenReturn(expectedDTO);

        repository.findTeam(expectedId);

        verify(mockDao).fetchTeam(expectedId);
        verify(mockTeamFactory).createTeam(expectedDTO);
    }
}
