package facade

import domain.{Team, Person, Query}
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar.mock
import model.SearchResultsModel
import org.scalatest.FunSuite
import repository.Repository
import facade.modelmapper.SearchResultsModelMapper


class SearchFacadeTest extends FunSuite {
    val mockRepo = mock[Repository]
    val mockSearchResultsModelMapper = mock[SearchResultsModelMapper]
    val searchFacade = new SearchFacade(mockRepo, mockSearchResultsModelMapper)

    test("should find persons and teams matching a query extracted from a web request and map them into a search results model") {
        val query = mock[Query]
        val mockPerson = mock[Person]
        val mockTeam = mock[Team]

        when(mockRepo.queryPersons(query)).thenReturn(List(mockPerson, mockPerson))
        when(mockRepo.queryTeams(query)).thenReturn(List(mockTeam))

        val expectedResult = mock[SearchResultsModel]
        when(mockSearchResultsModelMapper.map(List(mockPerson, mockPerson), List(mockTeam))).thenReturn(expectedResult)

        val result = searchFacade.createSearchResultsModel(query)
        assert(result == expectedResult, "the result should be whatever the mapper returned")

        verify(mockRepo).queryPersons(query)
        verify(mockRepo).queryTeams(query)
        verify(mockSearchResultsModelMapper).map(List(mockPerson, mockPerson), List(mockTeam))
    }
}
