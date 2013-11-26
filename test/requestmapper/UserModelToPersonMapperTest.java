package requestmapper;

import domain.Person;
import domain.Team;
import model.UserModel;
import org.junit.Test;

import static domain.PersonMatcher.aPerson;
import static domain.TeamMatcher.aTeam;
import static model.UserModelBuilder.aUserModel;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.hamcrest.Matchers.is;

public class UserModelToPersonMapperTest {
    UserModelToPersonMapper userModelToPersonMapper = new UserModelToPersonMapper();

    @Test
    public void user_model_maps_to_person() {
        String imageURL = "http://upload.wikimedia.org/wikipedia/commons/thumb/0/0c/Cow_female_black_white.jpg/800px-Cow_female_black_white.jpg";
        Team team = mock(Team.class);
        UserModel userModel = aUserModel()
                .named("bob")
                .withImageURL(imageURL)
                .inTeam("ecom")
                .inScrum("shop")
                .LocatedIn("bath_road")
                .withEmail("bob@telefonica.com")
                .withRole("developer")
                .build();

        Person result = userModelToPersonMapper.map(userModel);

        assertThat(result, is(aPerson().named("bob").withPicture(imageURL).inTeam(aTeam())));
    }
}
