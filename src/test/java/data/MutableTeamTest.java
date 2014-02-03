package data;

import data.dao.AdminDAO;
import data.dao.database.ItemNotFoundException;
import data.datatype.PersonId;
import data.datatype.TeamId;
import data.dto.TeamInformation;
import domain.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static data.dto.TeamInformation.noTeamInformation;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MutableTeamTest {

    @Mock private AdminDAO mockAdminDAO;

    private final TeamInformation teamInformation = mock(TeamInformation.class);
    private MutableTeam mutableTeam;

    @Test
    public void name_delegates_to_teamInformation() throws Exception {
        when(teamInformation.getName()).thenReturn("hyper");

        mutableTeam = new MutableTeam(mockAdminDAO, teamInformation);

        assertThat(mutableTeam.name(), is("hyper"));
    }

    @Test
    public void colour_delegates_to_teamInformation() throws Exception {
        when(teamInformation.getColour()).thenReturn("blue");

        mutableTeam = new MutableTeam(mockAdminDAO, teamInformation);

        assertThat(mutableTeam.colour(), is("blue"));
    }

    @Test
    public void get_set_members() throws Exception {
        List<Person> expectedMembers = mock(List.class);

        mutableTeam = new MutableTeam(mockAdminDAO, mock(TeamInformation.class));

        mutableTeam.setMembers(expectedMembers);

        assertThat(mutableTeam.members(), is(sameInstance(expectedMembers)));
    }

    @Test(expected = RuntimeException.class)
    public void add_member_throws_runtime_exception_when_team_or_person_not_found() throws Exception {
        mutableTeam = new MutableTeam(mockAdminDAO, noTeamInformation());

        doThrow(new ItemNotFoundException(""))
                .when(mockAdminDAO).addPersonToTeam(any(PersonId.class), any(TeamId.class));

        mutableTeam.addMember(mock(Person.class));
    }
}
