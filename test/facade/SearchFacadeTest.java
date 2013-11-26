package facade;

import domain.Person;
import domain.Query;
import domain.Team;
import facade.modelmapper.PersonDetailsModelMapper;
import facade.modelmapper.SearchResultsModelMapper;
import facade.modelmapper.TeamDetailsModelMapper;
import model.DefaultSearchResultsModel;
import model.PersonDetailsModel;
import model.SearchResultsModel;
import model.TeamDetailsModel;
import org.junit.Test;
import data.ScalaRepository;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class SearchFacadeTest {
    ScalaRepository mockRepo = mock(ScalaRepository.class);
    SearchResultsModelMapper mockSearchResultsModelMapper = mock(SearchResultsModelMapper.class);
    PersonDetailsModelMapper mockPersonDetailsModelMapper = mock(PersonDetailsModelMapper.class);
    TeamDetailsModelMapper teamDetailsModelMapper = mock(TeamDetailsModelMapper.class);
    SearchFacade searchFacade = new SearchFacade(mockRepo, mockSearchResultsModelMapper, mockPersonDetailsModelMapper, teamDetailsModelMapper);

    @Test
    public void find_persons_and_teams_query_from_web_then_map_to_search_results_model_test() {
        Query query = mock(Query.class);
        Person mockPerson = mock(Person.class);
        Team mockTeam = mock(Team.class);

        when(mockRepo.queryPersons(query)).thenReturn(new ArrayList<>(Arrays.asList(mockPerson, mockPerson)));
        when(mockRepo.queryTeams(query)).thenReturn(new ArrayList<>(Arrays.asList(mockTeam)));

        DefaultSearchResultsModel expectedResult = mock(DefaultSearchResultsModel.class);
        when(mockSearchResultsModelMapper.map(new ArrayList<>(Arrays.asList(mockPerson, mockPerson)), new ArrayList<>(Arrays.asList(mockTeam))))
                .thenReturn(expectedResult);

        SearchResultsModel result = searchFacade.createSearchResultsModel(query);
        assertEquals("Expected: " + expectedResult + "/nGot: " + result + ".", expectedResult, result);

        verify(mockRepo).queryPersons(query);
        verify(mockRepo).queryTeams(query);
        verify(mockSearchResultsModelMapper).map(new ArrayList<>(Arrays.asList(mockPerson, mockPerson)), new ArrayList<>(Arrays.asList(mockTeam)));
    }

    @Test
    public void one_person_no_teams_result_to_person_details_model_test() {
        Query query = mock(Query.class);
        Person mockPerson = mock(Person.class);

        when(mockRepo.queryPersons(query)).thenReturn(new ArrayList<>(Arrays.asList(mockPerson)));
        when(mockRepo.queryTeams(query)).thenReturn(new ArrayList<Team>());

        PersonDetailsModel expectedResult = mock(PersonDetailsModel.class);
        when(mockPersonDetailsModelMapper.map(mockPerson)).thenReturn(expectedResult);

        SearchResultsModel result = searchFacade.createSearchResultsModel(query);
        assertEquals ("the result should be a person details model, got: " + result.getClass(), expectedResult, result);

        verify(mockRepo).queryPersons(query);
        verify(mockRepo).queryTeams(query);
        verify(mockPersonDetailsModelMapper).map(mockPerson);
    }

    @Test
    public void no_person_one_team_result_to_team_details_model_test() {
        Query query = mock(Query.class);
        Team mockTeam = mock(Team.class);

        when(mockRepo.queryPersons(query)).thenReturn(new ArrayList<Person>());
        when(mockRepo.queryTeams(query)).thenReturn(new ArrayList<>(Arrays.asList(mockTeam)));

        TeamDetailsModel expectedResult = mock(TeamDetailsModel.class);
        when(teamDetailsModelMapper.map(mockTeam)).thenReturn(expectedResult);

        SearchResultsModel result = searchFacade.createSearchResultsModel(query);
        assertEquals ("the result should be a team details model, got: " + result.getClass(), expectedResult, result);
    }
}

