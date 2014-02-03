package requestmapper;

import domain.Person;
import domain.StubbedTeam;
import domain.Team;
import model.UserModel;
import org.junit.Test;

import java.util.ArrayList;

import static domain.PersonMatcher.aPerson;
import static domain.TeamMatcher.aTeam;
import static model.UserModelBuilder.aUserModel;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PersonMapperTest {

    PersonMapper personMapper = new PersonMapper();

    String imageURL = "http://www.image.jpeg";
    String email = "email@testemail.com";
    UserModel userModel = aUserModel()
            .named("bob")
            .withImageURL(imageURL)
            .inTeam("ecom")
            .withEmail(email)
            .build();

    @Test
    public void user_model_maps_to_person() {
        Person result = personMapper.map(userModel, null);
        assertThat(result, is(aPerson()
                .named("bob")
                .withPicture(imageURL)
                .withEmail(email)
        ));
    }

    @Test
    public void user_model_with_accompanying_team_maps_to_person_with_same_team() {
        Team mockTeam = new StubbedTeam("ecom", "blue", new ArrayList<Person>());

        Person result = personMapper.map(userModel, mockTeam);
        assertThat(result, is(aPerson().inTeam(aTeam().named("ecom").withColour("blue"))));
    }
}