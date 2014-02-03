package facade;

import data.PersonRepository;
import data.TeamRepository;
import data.dao.AdminDAO;
import data.datatype.PersonId;
import data.dto.PersonInformation;
import domain.Person;
import domain.StubbedTeam;
import domain.Team;
import domain.TeamMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static data.datatype.PersonId.newPersonId;
import static data.dto.PersonInformation.newPersonInformation;
import static domain.TeamMatcher.aTeam;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static util.IterableMatchers.containsExhaustivelyInOrder;

@RunWith(MockitoJUnitRunner.class)
public class SignUpFacadeTest {

    private static final Team someTeam = mock(Team.class);

    @Mock private PersonRepository mockPersonRepository;
    @Mock private TeamRepository mockTeamRepository;
    @Mock private AdminDAO adminDAO;

    @InjectMocks
    private SignUpFacade signUpFacade;


    @Test
    public void register_person_saves_person_to_the_database() throws Exception {
        PersonInformation expectedPersonInformation = newPersonInformation().build();
        signUpFacade.registerPerson(expectedPersonInformation, someTeam);

        verify(adminDAO).savePersonInformation(expectedPersonInformation);
    }

    @Test
    public void register_person_retrieves_saved_person_and_adds_to_team() throws Exception {
        PersonInformation personInformation = newPersonInformation()
                .withId("person-id")
                .build();

        Person expectedPerson = mock(Person.class);
        when(mockPersonRepository.findPersonById(any(PersonId.class))).thenReturn(expectedPerson);

        Team mockTeam = mock(Team.class);
        signUpFacade.registerPerson(personInformation, mockTeam);

        verify(mockPersonRepository).findPersonById(newPersonId("person-id"));
        verify(mockTeam).addMember(expectedPerson);
    }

    @Test
    public void get_available_sorted_teams_sorts_correctly() throws Exception {
        List<Person> emptyPersonList = new ArrayList<>();
        Team stubbedTeam1 = new StubbedTeam("zCommerce", "blue", emptyPersonList);
        Team stubbedTeam2 = new StubbedTeam("eCom", "red", emptyPersonList);
        Team stubbedTeam3 = new StubbedTeam("Ars", "green", emptyPersonList);
        Team stubbedTeam4 = new StubbedTeam("Portal", "grey", emptyPersonList);

        when(mockTeamRepository.listTeams()).thenReturn(
                asList(stubbedTeam1, stubbedTeam2, stubbedTeam3, stubbedTeam4));

        List<Team> result = signUpFacade.getSortedAvailableTeams();

        TeamMatcher expectedTeam1 = aTeam().named("Ars").withColour("green");
        TeamMatcher expectedTeam2 = aTeam().named("eCom").withColour("red");
        TeamMatcher expectedTeam3 = aTeam().named("Portal").withColour("grey");
        TeamMatcher expectedTeam4 = aTeam().named("zCommerce").withColour("blue");

        assertThat(result, containsExhaustivelyInOrder(
                expectedTeam1, expectedTeam2, expectedTeam3, expectedTeam4));
    }

    @Test
    public void get_sorted_available_team_names_returns_list_of_expected_names() throws Exception {
        List<Person> emptyPersonList = new ArrayList<>();
        Team stubbedTeam1 = new StubbedTeam("Ars", "green", emptyPersonList);
        Team stubbedTeam2 = new StubbedTeam("eCom", "red", emptyPersonList);
        Team stubbedTeam3 = new StubbedTeam("Portal", "grey", emptyPersonList);
        Team stubbedTeam4 = new StubbedTeam("zCommerce", "blue", emptyPersonList);

        when(mockTeamRepository.listTeams()).thenReturn(
                asList(stubbedTeam1, stubbedTeam2, stubbedTeam3, stubbedTeam4));

        List<String> expectedResult = new ArrayList<>(asList("Ars", "eCom", "Portal", "zCommerce"));

        List<String> result = signUpFacade.getSortedAvailableTeamNames();
        assertEquals(expectedResult, result);

    }

    @Test
    public void get_sortedAvailable_teams_delegates_to_team_repository() throws Exception {
        signUpFacade.getSortedAvailableTeams();
        verify(mockTeamRepository).listTeams();
    }
}
