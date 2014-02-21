package uk.co.o2.facewall.facade.validators;

import uk.co.o2.facewall.data.TeamRepository;
import uk.co.o2.facewall.data.dto.PersonInformation;
import uk.co.o2.facewall.domain.Query;
import uk.co.o2.facewall.domain.Team;
import uk.co.o2.facewall.model.UserModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static uk.co.o2.facewall.data.datatype.PersonId.noPersonId;
import static uk.co.o2.facewall.data.dto.PersonInformationMatcher.aPersonInformation;
import static uk.co.o2.facewall.domain.QueryMatcher.aQuery;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserModelValidatorTest {

    @Mock
    private TeamRepository mockTeamRepository;

    @InjectMocks
    private UserModelValidator userModelValidator;

    private UserModel userModel;
    private PersonInformation result;

    @Before
    public void beforeTest() {
        stubTeamRepository();
        userModel = new UserModel();
    }

    @Test
    public void creates_personInformation_name_from_valid_user_model_name() throws Exception {
        userModel.name = "Charlie";

        result = userModelValidator.validate(userModel).getPersonInformation();

        assertThat(result, is(aPersonInformation()
                .named("Charlie")
        ));
    }

    @Test
    public void creates_personInformation_picture_from_valid_user_model_picture() throws Exception {
        userModel.imgUrl = "img.url";

        result = userModelValidator.validate(userModel).getPersonInformation();

        assertThat(result, is(aPersonInformation()
                .withPicture("img.url")
        ));
    }

    @Test
    public void creates_personInformation_email_from_valid_user_model_email() throws Exception {
        userModel.email = "email@testemail.com";

        result = userModelValidator.validate(userModel).getPersonInformation();

        assertThat(result, is(aPersonInformation()
                .withEmail("email@testemail.com")
        ));
    }

    @Test
    public void creates_personInformation_role_from_valid_user_model_email() throws Exception {
        userModel.role = "BA";

        result = userModelValidator.validate(userModel).getPersonInformation();

        assertThat(result, is(aPersonInformation()
                .withRole("BA")
        ));
    }

    @Test
    public void creates_unique_personInformation_id_from_valid_user_model() throws Exception {
        result = userModelValidator.validate(new UserModel()).getPersonInformation();

        assertThat(result.getId(), is(not(noPersonId())));
    }

    @Test
    public void creates_team_from_user_model() throws Exception {
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
