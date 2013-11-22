package data.factory;

import data.datatype.TeamId;
import data.dto.TeamDTO;
import domain.Person;
import domain.Team;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static data.dto.TeamDTOBuilder.newTeamDTO;
import static domain.TeamMatcher.aTeam;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TeamFactoryTest {
    @Mock
    TraversingPersonRepository mockRepository;
    private TeamFactory teamFactory;

    @Before
    public void setUp() throws Exception {
        teamFactory = new TeamFactory(mockRepository);
    }

    @Test
    public void map_name() {
        TeamDTO dto = newTeamDTO()
            .named("facewall team")
            .build();

        Team result = teamFactory.createTeam(dto);
        assertThat(result, is(aTeam()
            .named("facewall team")
        ));
    }

    @Test
    public void map_picture() {
        TeamDTO dto = newTeamDTO()
            .coloured("blue")
            .build();

        Team result = teamFactory.createTeam(dto);
        assertThat(result, is(aTeam()
            .withColour("blue")
        ));
    }

    @Test
    public void team_members_delegates_to_repository() {
        List<Person> expectedMembers = mock(List.class);
        when(mockRepository.findMembersOfTeam(any(TeamId.class), any(Team.class))).thenReturn(expectedMembers);

        Team result = teamFactory.createTeam(newTeamDTO().build());
        assertThat(result.members(), sameInstance(expectedMembers));
    }

    @Test
    public void persons_team_verify_interactions() {
        TeamDTO dto = newTeamDTO()
            .withId("expected id")
            .build();

        Team team = teamFactory.createTeam(dto);
        team.members();

        verify(mockRepository).findMembersOfTeam(new TeamId("expected id"), team);
    }
}
