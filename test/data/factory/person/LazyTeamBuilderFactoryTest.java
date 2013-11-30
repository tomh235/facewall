package data.factory.person;

import data.TraversingRepository;
import data.datatype.TeamId;
import domain.Person;
import domain.Team;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static data.datatype.TeamId.newTeamId;
import static domain.TeamMatcher.aTeam;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LazyTeamBuilderFactoryTest {

    @Mock private TraversingRepository mockRepo;
    private LazyTeamBuilderFactory lazyTeamBuilderFactory;

    @Before
    public void setUp() throws Exception {
        lazyTeamBuilderFactory = new LazyTeamBuilderFactory(mockRepo);
    }

    @Test
    public void build_team_with_name() {
        Team result = lazyTeamBuilderFactory.newLazyTeam()
            .named("Zero velocity")
            .build();

        assertThat(result, is(aTeam()
            .named("Zero velocity")
        ));
    }

    @Test
    public void build_team_with_colour() {
        Team result = lazyTeamBuilderFactory.newLazyTeam()
            .coloured("broken-build red")
            .build();

        assertThat(result, is(aTeam()
            .withColour("broken-build red")
        ));
    }

    @Test
    public void build_team_where_members_delegates_to_repo() {
        Team result = lazyTeamBuilderFactory.newLazyTeam()
            .build();

        List<Person> expectedList = mock(List.class);
        when(mockRepo.findPersonsForTeam(any(TeamId.class))).thenReturn(expectedList);

        assertThat(result, is(aTeam()
            .whereMembers(sameInstance(expectedList))
        ));
    }

    @Test
    public void build_team_where_members_delegates_to_repo_verifyInteractions() {
        Team expectedTeam = lazyTeamBuilderFactory.newLazyTeam()
            .withId("team id")
            .build();

        expectedTeam.members();

        verify(mockRepo).findPersonsForTeam(newTeamId("team id"));
    }
}
