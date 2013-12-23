package data;

import data.dto.TeamInformation;
import domain.Person;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MutableTeamTest {

    @Test
    public void name_delegates_to_teamInformation() throws Exception {
        TeamInformation teamInformation = mock(TeamInformation.class);
        when(teamInformation.getName()).thenReturn("hyper");

        MutableTeam mutableTeam = new MutableTeam(teamInformation);

        assertThat(mutableTeam.name(), is("hyper"));
    }

    @Test
    public void colour_delegates_to_teamInformation() throws Exception {
        TeamInformation teamInformation = mock(TeamInformation.class);
        when(teamInformation.getColour()).thenReturn("blue");

        MutableTeam mutableTeam = new MutableTeam(teamInformation);

        assertThat(mutableTeam.colour(), is("blue"));
    }

    @Test
    public void get_set_members() throws Exception {
        List<Person> expectedMembers = mock(List.class);

        MutableTeam mutableTeam = new MutableTeam(mock(TeamInformation.class));

        mutableTeam.setMembers(expectedMembers);

        assertThat(mutableTeam.members(), is(sameInstance(expectedMembers)));
    }
}
