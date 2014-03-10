package uk.co.o2.facewall.facade;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.co.o2.facewall.data.TeamRepository;
import uk.co.o2.facewall.domain.Person;
import uk.co.o2.facewall.domain.StubbedTeam;
import uk.co.o2.facewall.domain.Team;
import uk.co.o2.facewall.facade.modelmapper.TeamDetailsModelMapper;
import uk.co.o2.facewall.model.TeamDetailsModelMatcher;
import uk.co.o2.facewall.model.TeamListModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static uk.co.o2.facewall.model.TeamDetailsModelMatcher.aTeamDetailsModel;
import static uk.co.o2.facewall.util.IterableMatchers.containsExhaustivelyInAnyOrder;

@RunWith(MockitoJUnitRunner.class)
public class TeamFacadeTest {
    @Mock
    TeamRepository teamRepository;
    private TeamFacade teamFacade;

    @Before
    public void setUp() throws Exception {
        teamFacade = new TeamFacade(teamRepository, new TeamDetailsModelMapper());
    }

    @Test
    public void map_teams_to_teamlist_test() {
        Person person1 = mock(Person.class);
        Person person2 = mock(Person.class);
        Person person3 = mock(Person.class);
        Person person4 = mock(Person.class);
        Person person5 = mock(Person.class);
        Person person6 = mock(Person.class);

        Team team1 = new StubbedTeam("team1", "blue", new ArrayList<Person>(Arrays.asList(person1)));
        Team team2 = new StubbedTeam("team2", "red", new ArrayList<Person>(Arrays.asList(person2,person3)));
        Team team3 = new StubbedTeam("team3", "green", new ArrayList<Person>(Arrays.asList(person4,person5,person6)));

        List<Team> teams = new ArrayList<Team>(Arrays.asList(team1, team2, team3));

        when(teamRepository.listTeams()).thenReturn(teams);

        TeamDetailsModelMatcher team1Matcher = aTeamDetailsModel().named("team1").withColour("blue").sized(1);
        TeamDetailsModelMatcher team2Matcher = aTeamDetailsModel().named("team2").withColour("red").sized(2);
        TeamDetailsModelMatcher team3Matcher = aTeamDetailsModel().named("team3").withColour("green").sized(3);

        TeamListModel result = teamFacade.createTeamListModel();
        assertThat(result.entries, containsExhaustivelyInAnyOrder(team1Matcher, team2Matcher, team3Matcher));

        verify(teamRepository).listTeams();
    }
}
