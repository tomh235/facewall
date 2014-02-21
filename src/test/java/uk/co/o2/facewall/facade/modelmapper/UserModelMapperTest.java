package uk.co.o2.facewall.facade.modelmapper;

import org.junit.Test;
import uk.co.o2.facewall.model.UserModel;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static uk.co.o2.facewall.model.UserModelMatcher.aUserModel;

public class UserModelMapperTest {
    UserModelMapper userModelMapper = new UserModelMapper();

    private static final String NAME = "Fred";
    private static final String EMAIL = "email@email.net";
    private static final String IMGURL = "http://www.fakeimage.co.uk";
    private static final String TEAM = "The Musketeers";
    private static final String SCRUM = "Bronies";
    private static final String ROLE = "BA";
    private static final String LOCATION = "Bath Road";

    @Test
    public void should_map_user_model() {
        UserModel result = userModelMapper.map(NAME, IMGURL, EMAIL, TEAM, SCRUM, ROLE, LOCATION);

        assertThat(result, is(aUserModel()
                .named(NAME)
                .withEmail(EMAIL)
                .withImgUrl(IMGURL)
                .withTeamName(TEAM)
                .withScrumName(SCRUM)
                .withRole(ROLE)
                .withLocation(LOCATION)));
    }
}
