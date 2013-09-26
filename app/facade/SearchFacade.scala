package facade

import domain.Query
import model.SearchResultsModel
import repository.Repository
import facade.modelmapper.SearchResultsModelMapper

class SearchFacade(val repository: Repository, val searchResultsModelMapper: SearchResultsModelMapper) {

    def createSearchResultsModel(query: Query): SearchResultsModel = {
        val persons = repository.queryPersons(query)
        val teams = repository.queryTeams(query)

        if (persons.size == 1 && teams.isEmpty) {

        }

        if (teams.size == 1 && persons.isEmpty) {

        }

        searchResultsModelMapper.map(persons, teams)
    }
}
