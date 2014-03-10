package uk.co.o2.facewall.facade;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.co.o2.facewall.data.TeamRepository;
import uk.co.o2.facewall.facade.modelmapper.OverviewModelMapper;
import uk.co.o2.facewall.facade.modelmapper.TeamDetailsModelMapper;

@RunWith(MockitoJUnitRunner.class)
public class TeamDetailsFacadeTest {

    @Mock TeamRepository teamRepository;
    @Mock TeamDetailsModelMapper teamDetailsModelMapper;
    @Mock OverviewModelMapper overviewModelMapper;

    @InjectMocks
    TeamDetailsFacade teamDetailsFacade;

    @Test
    public void returns_team_when_querying_repository() {

//TODO - help with this
//        PersonStub person1 = new PersonStub(newPersonId("1"), "person1", "pic1.img", "email1@testemail.com", "BA", null);
//        PersonStub person2 = new PersonStub(newPersonId("2"), "person2", "pic2.img", "email2@testemail.com", "BA", null);
//        Team team = new StubbedTeam("test", "blue", new ArrayList<Person>(Arrays.asList(person1,person2)));
//
//        List<Person> persons = new ArrayList<Person>(Arrays.asList(person1, person2));
//
//        person1.setTeam(team);
//        person2.setTeam(team);
//
//        List<OverviewEntryModel> entries = new ArrayList<>();
//
//        entries.add(overviewModelMapper.map(person1));
//        entries.add(overviewModelMapper.map(person2));
//
//        when(teamRepository.findTeamByName(Query.newExactQuery(team.name()))).thenReturn(team);
//
//        TeamDetailsWithPersonsModel teamDetailsWithPersonsModel = teamDetailsFacade.createTeamDetailsModel(Query.newExactQuery(team.name()));
//
//        assertThat(teamDetailsWithPersonsModel,is(teamDetailsModelMapper.mapWithPersons(team, entries)));
    }
}
