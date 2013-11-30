package data.factory.person;

import data.TraversingRepository;
import data.datatype.TeamId;
import data.mapper.MutableTeam;
import domain.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static data.datatype.TeamId.newTeamId;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LazyMutableTeamFactoryTest {

    @Mock TraversingRepository mockTraversingRepository;
    private LazyMutableTeamFactory lazyMutableTeamFactory;

    @Before
    public void setUp() throws Exception {
        lazyMutableTeamFactory = new LazyMutableTeamFactory(mockTraversingRepository);
    }

    @Test
    public void lazy_mutable_team_members_delegates_to_repo() {
        List<Person> expectedList = mock(List.class);
        when(mockTraversingRepository.findPersonsForTeam(any(TeamId.class))).thenReturn(expectedList);

        List<Person> result = lazyMutableTeamFactory.createLazyMutableTeam().members();

        assertThat(result, is(sameInstance(expectedList)));
    }

    @Test
    public void lazy_mutable_team_members_verifyInteractions() {
        MutableTeam mutableTeam = lazyMutableTeamFactory.createLazyMutableTeam();
        TeamId expectedTeamId = newTeamId("expected id");
        mutableTeam.setId(expectedTeamId);

        mutableTeam.members();

        verify(mockTraversingRepository).findPersonsForTeam(expectedTeamId);
    }
}
