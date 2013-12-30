package facade;

import data.AdminRepository;
import data.TeamRepository;
import domain.MockTeam;
import domain.Person;
import domain.Query;
import domain.Team;
import facade.validators.TeamValidator;
import model.UserModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import requestmapper.PersonMapper;

import java.util.ArrayList;
import java.util.Arrays;

import static domain.TeamMatcher.aTeam;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SignUpFacadeTest {

    @Mock AdminRepository mockAdminRepository;
    @Mock TeamRepository mockRepository;
    @Mock TeamValidator mockTeamValidator;
    @Mock PersonMapper mockPersonMapper;

    @InjectMocks
    SignUpFacade signUpFacade;

    @Test
    public void delegateNewUserToRepository_delegates_correctly() {
        UserModel mockUserModel = mock(UserModel.class);
        Person mockPerson = mock(Person.class);
        Team mockTeam = mock(Team.class);

        when(mockRepository.queryTeams(any(Query.class))).thenReturn(new ArrayList<>(Arrays.asList(mockTeam)));
        when(mockPersonMapper.map(any(UserModel.class), any(Team.class))).thenReturn(mockPerson);
        signUpFacade.delegateNewUserToRepository(mockUserModel);

        verify(mockAdminRepository).addPerson(mockPerson);
    }

    @Test
    public void validateModelsTeamExists_delegates_correctly() {
        UserModel userModel = new UserModel();
        userModel.team = "aName";

        when(mockTeamValidator.validate(anyString())).thenReturn(true);
        signUpFacade.validateModelsTeamExists(userModel);

        verify(mockTeamValidator).validate(userModel.team);
    }

    @Test
    public void getUserModelTeamFromRepository_returns_the_first_team_matching_the_querystring() {
        UserModel userModel = new UserModel();
        userModel.team = "ecom";

        Team mockTeam = new MockTeam("ecom", "blue", new ArrayList<Person>());

        when(mockRepository.queryTeams(any(Query.class))).thenReturn(new ArrayList<>(Arrays.asList(mockTeam)));
        Team result = signUpFacade.getUserModelTeamFromRepository(userModel);

        assertThat(result, is(aTeam().named("ecom").withColour("blue")));
    }
}
