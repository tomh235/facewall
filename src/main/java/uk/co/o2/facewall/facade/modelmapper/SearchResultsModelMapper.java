package uk.co.o2.facewall.facade.modelmapper;

import uk.co.o2.facewall.domain.Person;
import uk.co.o2.facewall.domain.Team;
import uk.co.o2.facewall.model.DefaultSearchResultsModel;
import uk.co.o2.facewall.model.PersonSearchResult;
import uk.co.o2.facewall.model.TeamSearchResult;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsModelMapper {
    public DefaultSearchResultsModel map(List<Person> personsList, List<Team> teamsList) {

        List<PersonSearchResult> personSearchResultsList = new ArrayList<>();
        List<TeamSearchResult> teamSearchResultsList = new ArrayList<>();

        for (Person person : personsList) {
            personSearchResultsList.add(PersonSearchResultMapper.map(person));
        }

        for (Team team : teamsList) {
            teamSearchResultsList.add(TeamSearchResultMapper.map(team));
        }

        return new DefaultSearchResultsModel(personSearchResultsList, teamSearchResultsList);
    }

    private static class PersonSearchResultMapper {
        public static PersonSearchResult map(Person person) {
            return new PersonSearchResult(person.name(), person.team().name(), person.picture(), person.email(), person.role());
        }
    }

    private static class TeamSearchResultMapper {
        public static TeamSearchResult map(Team team) {
            return new TeamSearchResult(team.name());
        }
    }
}