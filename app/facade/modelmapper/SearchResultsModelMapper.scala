package facade.modelmapper

import domain.{Team, Person}
import model.{PersonSearchResult, TeamSearchResult, DefaultSearchResultsModel}
import scala.collection.JavaConverters._

class SearchResultsModelMapper {
    def map(persons: List[Person], teams :List[Team]): DefaultSearchResultsModel = new DefaultSearchResultsModel(
        persons.map { person => PersonSearchResultMapper.map(person) }.asJava,
        teams.map { team => TeamSearchResultMapper.map(team) }.asJava
    )

    object PersonSearchResultMapper {
        def map(person: Person): PersonSearchResult = new PersonSearchResult(
            person.name,
            person.team.name(),
            person.picture
        )
    }

    object TeamSearchResultMapper {
        def map(team: Team): TeamSearchResult = new TeamSearchResult(team.name)
    }
}
