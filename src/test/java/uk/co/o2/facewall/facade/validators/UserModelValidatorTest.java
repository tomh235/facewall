package uk.co.o2.facewall.facade.validators;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.co.o2.facewall.data.TeamRepository;
import uk.co.o2.facewall.data.dto.PersonInformation;
import uk.co.o2.facewall.domain.NoTeam;
import uk.co.o2.facewall.domain.Person;
import uk.co.o2.facewall.domain.Query;
import uk.co.o2.facewall.domain.StubbedTeam;
import uk.co.o2.facewall.domain.Team;
import uk.co.o2.facewall.model.UserModel;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static uk.co.o2.facewall.data.dto.PersonInformationMatcher.aPersonInformation;
import static uk.co.o2.facewall.domain.TeamMatcher.aTeam;
import static uk.co.o2.facewall.model.UserModelBuilder.aUserModel;

@RunWith(MockitoJUnitRunner.class)
public class UserModelValidatorTest {
    @Mock UserModel mockUserModel;
    @Mock TeamRepository mockTeamRepository;

    @InjectMocks
    private UserModelValidator userModelValidator;

    @Test
    public void validate_returns_validated_user_model_with_correct_person_information() {
        UserModel userModel = aUserModel()
                .withEmail("email@email.com")
                .withImageURL("http://www.image.com")
                .named("fred")
                .withRole("pilot").build();

        when(mockTeamRepository.queryTeams(any(Query.class)))
                .thenReturn(Collections.<Team>emptyList());

        ValidatedUserModel validatedUserModel = userModelValidator.validate(userModel);
        PersonInformation result = validatedUserModel.getPersonInformation();

        assertThat(result, is(aPersonInformation()
                .withId("email@email.com")
                .withPicture("http://www.image.com")
                .named("fred")
                .withRole("pilot")));
    }

    @Test
    public void validate_returns_validated_user_model_with_correct_team() {
        Team stubbedTeam = new StubbedTeam("eCom", "red", Collections.<Person>emptyList());
        when(mockTeamRepository.queryTeams(any(Query.class)))
                .thenReturn(Arrays.asList(stubbedTeam));

        ValidatedUserModel result = userModelValidator.validate(mockUserModel);
        assertThat(result.getTeam(), is(aTeam().named("eCom").withColour("red")));
    }

    @Test
    public void validate_returns_validated_user_model_with_noteam() {
        when(mockTeamRepository.queryTeams(any(Query.class)))
                .thenReturn(Collections.<Team>emptyList());

        ValidatedUserModel result = userModelValidator.validate(mockUserModel);
        assertThat(result.getTeam(), is(instanceOf(NoTeam.class)));
    }
}