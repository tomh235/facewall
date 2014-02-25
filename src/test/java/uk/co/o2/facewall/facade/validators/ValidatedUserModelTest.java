package uk.co.o2.facewall.facade.validators;

import org.junit.BeforeClass;
import org.junit.Test;
import uk.co.o2.facewall.data.dto.PersonInformation;
import uk.co.o2.facewall.domain.Person;
import uk.co.o2.facewall.domain.StubbedTeam;
import uk.co.o2.facewall.domain.Team;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static uk.co.o2.facewall.data.dto.PersonInformation.newPersonInformation;
import static uk.co.o2.facewall.data.dto.PersonInformation.noPersonInformation;
import static uk.co.o2.facewall.domain.NoTeam.noTeam;

public class ValidatedUserModelTest {
    private static Validator validator;
    private static final Team VALID_TEAM = new StubbedTeam("The Goonies", "Red", new ArrayList<Person>());
    private static final PersonInformation VALID_PERSONINFORMATION = newPersonInformation()
        .named("Jim Ribber")
        .withRole("Developer")
        .withEmail("AnEmail@Email.com")
        .withPicture("http:/www.aUrl.net").build();


    @BeforeClass
    public static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void two_invalid_personinformation_fields_are_invalid_and_have_two_violations() {
        Team team = VALID_TEAM;
        PersonInformation PersonInformation = newPersonInformation()
                .named("Jim")
                .withEmail("NotAnEmail")
                .withPicture("NotAUrl")
                .withRole("Job").build();
        ValidatedUserModel validatedUserModel = new ValidatedUserModel(PersonInformation, team);

        Set<ConstraintViolation<ValidatedUserModel>> constraintViolations = validator.validate(validatedUserModel);
        assertEquals(2, constraintViolations.size());
    }

    @Test
    public void valid_person_information_is_valid() {
        Team team = VALID_TEAM;
        PersonInformation PersonInformation = VALID_PERSONINFORMATION;
        ValidatedUserModel validatedUserModel = new ValidatedUserModel(PersonInformation, team);

        Set<ConstraintViolation<ValidatedUserModel>> constraintViolations = validator.validate(validatedUserModel);
        assertEquals(0, constraintViolations.size());
    }

    @Test
    public void noTeam_is_invalid() {
        Team team = noTeam();
        PersonInformation mockPersonInformation = mock(PersonInformation.class);
        ValidatedUserModel validatedUserModel = new ValidatedUserModel(mockPersonInformation, team);

        Set<ConstraintViolation<ValidatedUserModel>> constraintViolations = validator.validateProperty(validatedUserModel, "team");
        assert (constraintViolations.size() > 0);
    }

    @Test
    public void valid_team_is_valid() {
        Team team = VALID_TEAM;
        PersonInformation mockPersonInformation = mock(PersonInformation.class);
        ValidatedUserModel validatedUserModel = new ValidatedUserModel(mockPersonInformation, team);

        Set<ConstraintViolation<ValidatedUserModel>> constraintViolations = validator.validateProperty(validatedUserModel, "team");
        assertEquals(0, constraintViolations.size());

    }

    @Test
    public void get_errors_returns_hashmap_containing_path_and_message() {
        Team team = VALID_TEAM;
        PersonInformation personInformation = newPersonInformation()
                .named("Jim")
                .withEmail("NotAnEmail")
                .withPicture("http://www.animage.com")
                .withRole("Job").build();
        ValidatedUserModel validatedUserModel = new ValidatedUserModel(personInformation, team);


        Map<String, String> resultMap = validatedUserModel.getErrors();
        assertThat(resultMap.get("personInformation.email"), containsString("email address"));
    }

    @Test
    public void has_errors_returns_true_when_invalid() {
        Team invalidTeam = noTeam();
        PersonInformation invalidPersonInformation = noPersonInformation();
        ValidatedUserModel validatedUserModel = new ValidatedUserModel(invalidPersonInformation, invalidTeam);

        assertTrue(validatedUserModel.hasErrors());
    }

    @Test
    public void has_errors_returns_false_when_valid() {
        Team validTeam = VALID_TEAM;
        PersonInformation validPersonInformation = VALID_PERSONINFORMATION;
        ValidatedUserModel validatedUserModel = new ValidatedUserModel(validPersonInformation, validTeam);

        assertFalse(validatedUserModel.hasErrors());
    }


}
