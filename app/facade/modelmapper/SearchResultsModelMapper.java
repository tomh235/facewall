package facade.modelmapper;

import domain.Person;
import domain.Team;
import model.DefaultSearchResultsModel;
import model.PersonSearchResult;
import model.TeamSearchResult;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsModelMapper {
    public DefaultSearchResultsModel map(List<Person> personsList, List<Team> teamsList) {

        List<PersonSearchResult> personSearchResultsList = new ArrayList<>();
        List<TeamSearchResult> teamSearchResultsList = new ArrayList<>();

        for(Person person: personsList) {
            personSearchResultsList.add(PersonSearchResultMapper.map(person));
        }

        for(Team team: teamsList) {
            teamSearchResultsList.add(TeamSearchResultMapper.map(team));
        }

        return new DefaultSearchResultsModel(personSearchResultsList, teamSearchResultsList);
    }

    private static class PersonSearchResultMapper {
        public static PersonSearchResult map(Person person) {
            return new PersonSearchResult(person.name(),person.team().name(), person.picture());
        }
    }

    private static class TeamSearchResultMapper {
        public static TeamSearchResult map(Team team) {
            return new TeamSearchResult(team.name());
        }

    }
}
