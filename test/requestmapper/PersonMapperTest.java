package requestmapper;

import data.ScalaRepository;
import domain.MockTeam;
import domain.Person;
import domain.Query;
import domain.Team;
import model.UserModel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static domain.PersonMatcher.aPerson;
import static domain.QueryMatcher.aQuery;
import static domain.TeamMatcher.aTeam;
import static model.UserModelBuilder.aUserModel;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PersonMapperTest {
    ScalaRepository mockRepository = mock(ScalaRepository.class);
    PersonMapper personMapper = new PersonMapper(mockRepository);

    String imageURL = "http://www.image.jpeg";
    UserModel userModel = aUserModel()
            .named("bob")
            .withImageURL(imageURL)
            .inTeam("ecom")
            .build();

    @Test
    public void user_model_maps_to_person() {
        Person result = personMapper.map(userModel, null);
        assertThat(result, is(aPerson()
                .named("bob")
                .withPicture(imageURL)
        ));
    }

    @Test
    public void user_model_with_accompanying_team_maps_to_person_with_same_team() {
        Team mockTeam = new MockTeam("1", "ecom", "blue", new ArrayList<Person>());

        Person result = personMapper.map(userModel, mockTeam);
        assertThat(result, is(aPerson().inTeam(aTeam().named("ecom").withColour("blue"))));
    }
}