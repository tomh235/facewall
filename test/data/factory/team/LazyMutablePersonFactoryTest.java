package data.factory.team;

import data.dao.FacewallDAO;
import data.datatype.PersonId;
import data.dto.PersonDTO;
import data.factory.LazyMutablePersonFactory;
import data.factory.LazyMutableTeamFactory;
import data.mapper.MutablePerson;
import data.mapper.MutableTeam;
import data.mapper.TeamDTOMapper;
import domain.Team;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.neo4j.graphdb.Node;

import static data.datatype.PersonId.newPersonId;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LazyMutablePersonFactoryTest {

    @Mock FacewallDAO mockFacewallDAO;
    @Mock LazyMutableTeamFactory mockLazyMutableTeamFactory;
    @Mock TeamDTOMapper mockTeamDTOMapper;

    @InjectMocks
    private LazyMutablePersonFactory lazyMutablePersonFactory;

    @Test
    public void lazy_mutable_persons_team_delegates_to_teamMapper() {
        stubDao();

        Team expectedTeam = mock(Team.class);
        when(mockTeamDTOMapper.map(any(MutableTeam.class), any(Node.class)))
            .thenReturn(expectedTeam);

        Team result = lazyMutablePersonFactory.createMutablePerson().team();

        assertThat(result, is(sameInstance(expectedTeam)));
    }

    @Test
    public void lazy_mutable_persons_team_fetches_teamNode_using_dao() {
        PersonId expectedPersonId = newPersonId("expected person id");

        Node expectedTeamNode = mock(Node.class);
        when(mockFacewallDAO.fetchPerson(any(PersonId.class))).thenReturn(
            new PersonDTO(mock(Node.class), expectedTeamNode)
        );

        MutablePerson person = lazyMutablePersonFactory.createMutablePerson();
        person.setId(expectedPersonId);

        person.team();

        verify(mockFacewallDAO).fetchPerson(expectedPersonId);
        verify(mockTeamDTOMapper).map(any(MutableTeam.class), eq(expectedTeamNode));
    }

    @Test
    public void lazy_mutable_persons_team_creates_mutable_team_using_factory() {
        stubDao();

        MutableTeam expectedMutableTeam = mock(MutableTeam.class);
        when(mockLazyMutableTeamFactory.createLazyMutableTeam())
            .thenReturn(expectedMutableTeam);

        lazyMutablePersonFactory.createMutablePerson().team();

        verify(mockLazyMutableTeamFactory).createLazyMutableTeam();
        verify(mockTeamDTOMapper).map(eq(expectedMutableTeam), any(Node.class));
    }

    private void stubDao() {
        when(mockFacewallDAO.fetchPerson(any(PersonId.class))).thenReturn(
            new PersonDTO(mock(Node.class), mock(Node.class))
        );
    }
}
