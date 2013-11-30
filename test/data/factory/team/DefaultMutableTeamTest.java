package data.factory.team;

import data.factory.DefaultMutableTeam;
import domain.Person;
import org.junit.Test;

import java.util.List;

import static data.factory.DefaultMutableTeam.newMutableTeamWithMembers;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.mockito.Mockito.mock;

public class DefaultMutableTeamTest {

    @Test
    public void get_members() {
        List<Person> expectedMembers = mock(List.class);
        DefaultMutableTeam defaultMutableTeam = newMutableTeamWithMembers(expectedMembers);

        assertThat(defaultMutableTeam.members(), is(sameInstance(expectedMembers)));
    }

}
