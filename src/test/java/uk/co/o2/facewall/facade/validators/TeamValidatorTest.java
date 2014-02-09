package uk.co.o2.facewall.facade.validators;

import uk.co.o2.facewall.data.TeamRepository;
import uk.co.o2.facewall.domain.Person;
import uk.co.o2.facewall.domain.Query;
import uk.co.o2.facewall.domain.StubbedTeam;
import uk.co.o2.facewall.domain.Team;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TeamValidatorTest {
    @Mock TeamRepository mockRepository;

    @InjectMocks
    TeamValidator teamValidator;

    String teamName = "ecom";
    Team mockTeam = new StubbedTeam("ecom", "blue" , new ArrayList<Person>());

    @Test
    public void validate_returns_true_if_team_matches_query() {
        when(mockRepository.queryTeams(any(Query.class))).thenReturn(new ArrayList<>(Arrays.asList(mockTeam)));

        boolean result = teamValidator.validate(teamName);
        assertTrue(result);
    }

    @Test
    public void validate_returns_false_if_no_team_matches_query() {
        when(mockRepository.queryTeams(any(Query.class))).thenReturn(new ArrayList<Team>());

        boolean result = teamValidator.validate(teamName);
        assertFalse(result);
    }
}
