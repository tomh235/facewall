package data.factory.team;

import data.dto.TeamDTO;
import data.mapper.MutablePerson;
import data.mapper.MutableTeam;
import data.mapper.PersonMapper;
import data.mapper.TeamMapper;
import domain.Person;
import domain.Team;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.neo4j.graphdb.Node;

import java.util.Collections;
import java.util.List;

import static data.factory.team.MutableTeamMatcher.aMutableTeam;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static util.CollectionMatcher.contains;

@RunWith(MockitoJUnitRunner.class)
public class TeamFactoryTest {

    @Mock TeamMapper mockTeamMapper;
    @Mock PersonMapper mockPersonMapper;
    @Mock LazyMutablePersonFactory mockLazyMutablePersonFactory;

    private TeamFactory teamFactory;

    @Before
    public void setUp() throws Exception {
        teamFactory = new TeamFactory(mockTeamMapper, mockPersonMapper, mockLazyMutablePersonFactory);
    }

    @Test
    public void create_team_delegates_to_teamMapper() {
        Team expectedTeam1 = mock(Team.class);
        Team expectedTeam2 = mock(Team.class);

        when(mockTeamMapper.map(any(MutableTeam.class), any(Node.class)))
            .thenReturn(expectedTeam1)
            .thenReturn(expectedTeam2);
        List<TeamDTO> dtos = asList(
            new TeamDTO(mock(Node.class), Collections.<Node>emptyList()),
            new TeamDTO(mock(Node.class), Collections.<Node>emptyList())
        );
        List<Team> result = teamFactory.createTeams(dtos);

        assertThat(result, contains(
            sameInstance(expectedTeam1),
            sameInstance(expectedTeam2)
        ));
    }

    @Test
    public void create_team_maps_team_details() {
        Node expectedTeamNode1 = mock(Node.class);
        Node expectedTeamNode2 = mock(Node.class);

        List<TeamDTO> dtos = asList(
            new TeamDTO(expectedTeamNode1, Collections.<Node>emptyList()),
            new TeamDTO(expectedTeamNode2, Collections.<Node>emptyList())
        );
        teamFactory.createTeams(dtos);

        verify(mockTeamMapper).map(any(DefaultMutableTeam.class), eq(expectedTeamNode1));
        verify(mockTeamMapper).map(any(DefaultMutableTeam.class), eq(expectedTeamNode2));
    }

    @Test
    public void create_team_maps_members() {
        Node expectedPersonNode1 = mock(Node.class);
        List<Node> expectedPersonNodesList1 = asList(expectedPersonNode1);

        Node expectedPersonNode2 = mock(Node.class);
        Node expectedPersonNode3 = mock(Node.class);
        List<Node> expectedPersonNodesList2 = asList(expectedPersonNode2, expectedPersonNode3);

        List<TeamDTO> dtos = asList(
            new TeamDTO(mock(Node.class), expectedPersonNodesList1),
            new TeamDTO(mock(Node.class), expectedPersonNodesList2)
        );
        teamFactory.createTeams(dtos);

        verify(mockLazyMutablePersonFactory, times(3)).createLazyMutablePerson();

        verify(mockPersonMapper).map(any(MutablePerson.class), eq(expectedPersonNode1));

        verify(mockPersonMapper).map(any(MutablePerson.class), eq(expectedPersonNode2));
        verify(mockPersonMapper).map(any(MutablePerson.class), eq(expectedPersonNode3));
    }

    @Test
    public void create_team_sets_team_on_defaultTeamBuilder() {
        Person expectedPerson1 = mock(Person.class);
        Person expectedPerson2 = mock(Person.class);
        when(mockPersonMapper.map(any(MutablePerson.class), any(Node.class)))
            .thenReturn(expectedPerson1)
            .thenReturn(expectedPerson2);

        List<TeamDTO> dtos = asList(
            new TeamDTO(mock(Node.class), asList(
                mock(Node.class), mock(Node.class)
            )
            ));
        teamFactory.createTeams(dtos);

        verify(mockTeamMapper).map(
            argThat(is(aMutableTeam()
                .whoseMembers(contains(
                        sameInstance(expectedPerson1),
                        sameInstance(expectedPerson2)
                    )))
            ), any(Node.class)
        );
    }
}
