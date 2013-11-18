package facade;

import domain.Person;
import domain.Query;
import domain.Team;
import facade.modelmapper.PersonDetailsModelMapper;
import facade.modelmapper.SearchResultsModelMapper;
import model.DefaultSearchResultsModel;
import model.PersonDetailsModel;
import model.SearchResultsModel;
import org.junit.Test;
import data.ScalaRepository;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class SearchFacadeTest {
    ScalaRepository mockRepo = mock(ScalaRepository.class);
    SearchResultsModelMapper mockSearchResultsModelMapper = mock(SearchResultsModelMapper.class);
    PersonDetailsModelMapper mockPersonDetailsModelMapper = mock(PersonDetailsModelMapper.class);
    SearchFacade searchFacade = new SearchFacade(mockRepo, mockSearchResultsModelMapper, mockPersonDetailsModelMapper);

    // should find persons and teams matching a query extracted from a web request and map them into a search results model
    @Test
    public void FindPersonsAndTeamsQueryFromWebThenMapToSearchResultsModelTest() {
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
     // should return person details model if query returns only one person and no teams
    @Test
    public void OnePersonNoTeamsResultToPersonDetailsModelTest() {
        Query query = mock(Query.class);
        Person mockPerson = mock(Person.class);

        when(mockRepo.queryPersons(query)).thenReturn(new ArrayList<Person>(Arrays.asList(mockPerson)));
        when(mockRepo.queryTeams(query)).thenReturn(new ArrayList<Team>());

        PersonDetailsModel expectedResult = mock(PersonDetailsModel.class);
        when(mockPersonDetailsModelMapper.map(mockPerson)).thenReturn(expectedResult);

        SearchResultsModel result = searchFacade.createSearchResultsModel(query);
        assertEquals ("the result should be a person details model, got:" + result.getClass(), expectedResult, result);

        verify(mockRepo).queryPersons(query);
        verify(mockRepo).queryTeams(query);
        verify(mockPersonDetailsModelMapper).map(mockPerson);
    }
}

