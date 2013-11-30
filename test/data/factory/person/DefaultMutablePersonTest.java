package data.factory.person;

import data.factory.DefaultMutablePerson;
import domain.Team;
import org.junit.Test;

import static data.factory.DefaultMutablePerson.newMutablePersonInTeam;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.mockito.Mockito.mock;

public class DefaultMutablePersonTest {
    @Test
    public void get_set_team() {
        Team expectedTeam = mock(Team.class);
        DefaultMutablePerson defaultMutablePerson = newMutablePersonInTeam(expectedTeam);

        assertThat(defaultMutablePerson.team(), is(sameInstance(expectedTeam)));
    }
}
