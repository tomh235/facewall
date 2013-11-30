package data.factory.team;

import data.TraversingRepository;
import data.datatype.PersonId;
import data.mapper.MutablePerson;
import domain.Team;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static data.datatype.PersonId.newPersonId;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LazyMutablePersonFactoryTest {

    @Mock TraversingRepository mockRepo;
    private LazyMutablePersonFactory lazyMutablePersonFactory;

    @Before
    public void setUp() throws Exception {
        lazyMutablePersonFactory = new LazyMutablePersonFactory(mockRepo);
    }

    @Test
    public void lazy_mutable_persons_team_delegates_to_repo() {
        Team expectedTeam = mock(Team.class);
        when(mockRepo.findTeamForPerson(any(PersonId.class))).thenReturn(expectedTeam);

        Team result = lazyMutablePersonFactory.createLazyMutablePerson().team();

        assertThat(result, is(sameInstance(expectedTeam)));
    }

    @Test
    public void lazy_mutable_persons_team_verifyInteractions() {
        PersonId expectedPersonId = newPersonId("expected person id");

        MutablePerson person = lazyMutablePersonFactory.createLazyMutablePerson();
        person.setId(expectedPersonId);

        person.team();

        verify(mockRepo).findTeamForPerson(expectedPersonId);
    }
}
