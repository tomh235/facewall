package uk.co.o2.facewall.facade;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.co.o2.facewall.data.TeamRepository;
import uk.co.o2.facewall.domain.*;
import uk.co.o2.facewall.facade.modelmapper.OverviewModelMapper;
import uk.co.o2.facewall.facade.modelmapper.TeamDetailsModelMapper;
import uk.co.o2.facewall.model.OverviewEntryModelMatcher;
import uk.co.o2.facewall.model.TeamDetailsWithPersonsModel;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static uk.co.o2.facewall.data.datatype.PersonId.newPersonId;
import static uk.co.o2.facewall.model.OverviewEntryModelMatcher.anOverviewEntryModel;
import static uk.co.o2.facewall.util.IterableMatchers.containsExhaustivelyInOrder;

@RunWith(MockitoJUnitRunner.class)
public class TeamDetailsFacadeTest {

    @Mock TeamRepository teamRepository;

    TeamDetailsFacade teamDetailsFacade;

    @Before
    public void setUp() throws Exception {
        teamDetailsFacade = new TeamDetailsFacade(teamRepository, new TeamDetailsModelMapper(), new OverviewModelMapper());
    }

    @Test
    public void returns_team_when_querying_repository() {
        PersonStub person1 = new PersonStub(newPersonId("1"), "person1", "pic1.img", "email1@testemail.com", "BA", null);
        PersonStub person2 = new PersonStub(newPersonId("2"), "person2", "pic2.img", "email2@testemail.com", "BA", null);
        Team team = new StubbedTeam("test", "blue", new ArrayList<Person>(Arrays.asList(person1, person2)));

        person1.setTeam(team);
        person2.setTeam(team);

        Query name = Query.newExactQuery(team.name());

        when(teamRepository.findTeamByName(name)).thenReturn(team);

        OverviewEntryModelMatcher person1Matcher = anOverviewEntryModel().withTeamHeader("test").named("person1");
        OverviewEntryModelMatcher person2Matcher = anOverviewEntryModel().withTeamHeader("test").named("person2");

        TeamDetailsWithPersonsModel result = teamDetailsFacade.createTeamDetailsModel(name);

        assertThat(result.name, is("test"));
        assertThat(result.colour, is("blue"));
        assertThat(result.size, is(2));
        assertThat(result.entries, containsExhaustivelyInOrder(person1Matcher,person2Matcher));
    }
}
