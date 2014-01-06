package facade.validators;

import data.TeamRepository;
import data.dto.PersonInformation;
import domain.Query;
import domain.Team;
import model.UserModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static data.datatype.PersonId.noPersonId;
import static data.dto.PersonInformationMatcher.aPersonInformation;
import static domain.QueryMatcher.aQuery;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserModelValidatorTest {

    @Mock
    private TeamRepository mockTeamRepository;

    @InjectMocks
    UserModelValidator userModelValidator;

    @Test
    public void creates_personInformation_name_from_valid_user_model_name() throws Exception {
        stubTeamRepository();

        UserModel userModel = new UserModel();
        userModel.name = "Charlie";

        PersonInformation result = userModelValidator.validate(userModel).getPersonInformation();

        assertThat(result, is(aPersonInformation()
                .named("Charlie")
        ));
    }

    @Test
    public void creates_personInformation_picture_from_valid_user_model_picture() throws Exception {
        stubTeamRepository();

        UserModel userModel = new UserModel();
        userModel.imgURL = "img.url";

        PersonInformation result = userModelValidator.validate(userModel).getPersonInformation();

        assertThat(result, is(aPersonInformation()
                .withPicture("img.url")
        ));
    }

    @Test
    public void creates_unique_personInformation_id_from_valid_user_model() throws Exception {
        stubTeamRepository();

        PersonInformation result = userModelValidator.validate(new UserModel()).getPersonInformation();

        assertThat(result.getId(), is(not(noPersonId())));
    }

    @Test
    public void creates_team_from_user_model() throws Exception {
        UserModel userModel = new UserModel();
        userModel.team = "teamName";

        Team expectedTeam = mock(Team.class);
        when(mockTeamRepository.queryTeams(any(Query.class))).thenReturn(asList(expectedTeam));

        ValidatedUserModel result = userModelValidator.validate(userModel);
        assertThat(result.getTeam(), is(expectedTeam));

        verify(mockTeamRepository).queryTeams(argThat(is(aQuery().withQueryString("teamName"))));
    }

    private void stubTeamRepository() {
        when(mockTeamRepository.queryTeams(any(Query.class))).thenReturn(asList(mock(Team.class)));
    }
}
