package facade

import domain.{Team, Person, Query}
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar.mock
import model.{PersonDetailsModel, DefaultSearchResultsModel}
import org.scalatest.FunSuite
import repository.Repository
import facade.modelmapper.{PersonDetailsModelMapper, SearchResultsModelMapper}


class SearchFacadeTest extends FunSuite {
    val mockRepo = mock[Repository]
    val mockSearchResultsModelMapper = mock[SearchResultsModelMapper]
    var mockPersonDetailsModelMapper = mock[PersonDetailsModelMapper]
    val searchFacade = new SearchFacade(mockRepo, mockSearchResultsModelMapper, mockPersonDetailsModelMapper)

    test("should find persons and teams matching a query extracted from a web request and map them into a search results model") {
        val query = mock[Query]
        val mockPerson = mock[Person]
        val mockTeam = mock[Team]

        when(mockRepo.queryPersons(query)).thenReturn(List(mockPerson, mockPerson))
        when(mockRepo.queryTeams(query)).thenReturn(List(mockTeam))

        val expectedResult = mock[DefaultSearchResultsModel]
        when(mockSearchResultsModelMapper.map(List(mockPerson, mockPerson), List(mockTeam))).thenReturn(expectedResult)

        val result = searchFacade.createSearchResultsModel(query)
        assert(result == expectedResult, "the result should be whatever the mapper returned")

        verify(mockRepo).queryPersons(query)
        verify(mockRepo).queryTeams(query)
        verify(mockSearchResultsModelMapper).map(List(mockPerson, mockPerson), List(mockTeam))
    }

    test("should return person details model if query returns only one person and no teams") {
        val query = mock[Query]
        val mockPerson = mock[Person]

        when(mockRepo.queryPersons(query)).thenReturn(List(mockPerson))
        when(mockRepo.queryTeams(query)).thenReturn(Nil)

        val expectedResult = mock[PersonDetailsModel]
        when(mockPersonDetailsModelMapper.map(mockPerson)).thenReturn(expectedResult)

        val result = searchFacade.createSearchResultsModel(query)
        assert(result == expectedResult, "the result should be whatever the mapper returned")

        verify(mockPersonDetailsModelMapper).map(mockPerson)
    }
}
