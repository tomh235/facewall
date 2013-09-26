package facade

import domain.Query
import model.{SearchResultsModel, DefaultSearchResultsModel}
import repository.Repository
import facade.modelmapper.{PersonDetailsModelMapper, SearchResultsModelMapper}

class SearchFacade(
    val repository: Repository,
    val searchResultsModelMapper: SearchResultsModelMapper,
    val personDetailsModelMapper: PersonDetailsModelMapper
) {
    def createSearchResultsModel(query: Query): SearchResultsModel = {
        val persons = repository.queryPersons(query)
        val teams = repository.queryTeams(query)

        if (persons.size == 1 && teams.isEmpty) {
            return personDetailsModelMapper.map(persons.head)
        }

        if (teams.size == 1 && persons.isEmpty) {

        }

        searchResultsModelMapper.map(persons, teams)
    }
}
