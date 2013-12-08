package data.factory.team;

import data.dao.TraversingDAO;
import data.datatype.PersonId;
import data.factory.LazyMutablePersonFactory;
import data.factory.LazyMutableTeamFactory;
import data.mapper.MutablePerson;
import data.mapper.MutableTeam;
import data.mapper.TeamDTOMapper;
import domain.Team;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

    @Mock TraversingDAO mockDAO;
    @Mock LazyMutableTeamFactory mockLazyMutableTeamFactory;
    @Mock TeamDTOMapper mockTeamDTOMapper;
    private LazyMutablePersonFactory lazyMutablePersonFactory;

    @Before
    public void setUp() throws Exception {
        lazyMutablePersonFactory = new LazyMutablePersonFactory(mockDAO, mockLazyMutableTeamFactory, mockTeamDTOMapper);
    }

    @Test
    public void lazy_mutable_persons_team_delegates_to_teamMapper() {
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
        when(mockDAO.fetchTeamForPerson(any(PersonId.class)))
            .thenReturn(expectedTeamNode);

        MutablePerson person = lazyMutablePersonFactory.createMutablePerson();
        person.setId(expectedPersonId);

        person.team();

        verify(mockDAO).fetchTeamForPerson(expectedPersonId);
        verify(mockTeamDTOMapper).map(any(MutableTeam.class), eq(expectedTeamNode));
    }

    @Test
    public void lazy_mutable_persons_team_creates_mutable_team_using_factory() {
        MutableTeam expectedMutableTeam = mock(MutableTeam.class);
        when(mockLazyMutableTeamFactory.createLazyMutableTeam())
            .thenReturn(expectedMutableTeam);

        lazyMutablePersonFactory.createMutablePerson().team();

        verify(mockLazyMutableTeamFactory).createLazyMutableTeam();
        verify(mockTeamDTOMapper).map(eq(expectedMutableTeam), any(Node.class));
    }
}
