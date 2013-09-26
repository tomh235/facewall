package facade.modelmapper

import domain.{Team, Person}
import model.{PersonSearchResult, TeamSearchResult, DefaultSearchResultsModel}

class SearchResultsModelMapper {
    def map(persons: List[Person], teams :List[Team]): DefaultSearchResultsModel = DefaultSearchResultsModel(
        persons.map { person => PersonSearchResultMapper.map(person) },
        teams.map { team => TeamSearchResultMapper.map(team) }
    )

    object PersonSearchResultMapper {
        def map(person: Person): PersonSearchResult = PersonSearchResult(
            person.name,
            person.team.fold("") { team => team.name },
            person.picture
        )
    }

    object TeamSearchResultMapper {
        def map(team: Team): TeamSearchResult = TeamSearchResult(team.name)
    }
}
