package facade.modelmapper;

import domain.Person;
import domain.StubbedTeam;
import model.TeamDetailsModel;
import org.junit.Test;

import java.util.Arrays;

import static model.TeamDetailsModelMatcher.aTeamDetailsModel;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TeamDetailsModelMapperTest {
    TeamDetailsModelMapper teamDetailsModelMapper = new TeamDetailsModelMapper();

    @Test
    public void should_map_person() {
        StubbedTeam stubbedTeam = new StubbedTeam("eCom", "red", Arrays.<Person>asList());
        TeamDetailsModel result = teamDetailsModelMapper.map(stubbedTeam);
        assertThat(result, is(aTeamDetailsModel().named("eCom").withColour("red")));
    }
}
