package facade.validators;

import data.ScalaRepository;
import domain.MockTeam;
import domain.Person;
import domain.Query;
import domain.Team;
import org.junit.Test;
import requestmapper.QueryMapper;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TeamValidatorTest {
    ScalaRepository mockScalaRepository = mock(ScalaRepository.class);
    QueryMapper queryMapper = mock(QueryMapper.class);
    TeamValidator teamValidator = new TeamValidator(mockScalaRepository, queryMapper);

    String teamName = "ecom";
    Team mockTeam = new MockTeam("1", "ecom", "blue" , new ArrayList<Person>());

    @Test
    public void validate_returns_true_if_team_matches_query() {
        when(mockScalaRepository.queryTeams(any(Query.class))).thenReturn(new ArrayList<>(Arrays.asList(mockTeam)));

        boolean result = teamValidator.validate(teamName);
        assertTrue(result);
    }

    @Test
    public void validate_returns_false_if_no_team_matches_query() {
        when(mockScalaRepository.queryTeams(any(Query.class))).thenReturn(new ArrayList<Team>());

        boolean result = teamValidator.validate(teamName);
        assertFalse(result);
    }
}
