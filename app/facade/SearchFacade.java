package facade;

import domain.Person;
import domain.Query;
import domain.Team;
import facade.modelmapper.PersonDetailsModelMapper;
import facade.modelmapper.SearchResultsModelMapper;
import facade.modelmapper.TeamDetailsModelMapper;
import model.SearchResultsModel;
import data.ScalaRepository;
import java.util.List;

public class SearchFacade {
    private final ScalaRepository repository;
    private final SearchResultsModelMapper searchResultsModelMapper;
    private final PersonDetailsModelMapper personDetailsModelMapper;
    private final TeamDetailsModelMapper teamDetailsModelMapper;

    public SearchFacade(ScalaRepository repository, SearchResultsModelMapper searchResultsModelMapper, PersonDetailsModelMapper personDetailsModelMapper, TeamDetailsModelMapper teamDetailsModelMapper) {
        this.repository = repository;
        this.searchResultsModelMapper = searchResultsModelMapper;
        this.personDetailsModelMapper = personDetailsModelMapper;
        this.teamDetailsModelMapper = teamDetailsModelMapper;
    }

    public SearchResultsModel createSearchResultsModel(Query query) {
        SearchResultsModel resultsModel;
        List<Person> persons = repository.queryPersons(query);
        List<Team> teams = repository.queryTeams(query);

        if (persons.size() == 1 && teams.isEmpty()) {
            resultsModel = personDetailsModelMapper.map(persons.get(0));
        } else if (teams.size() == 1 && persons.isEmpty()) {
            resultsModel = teamDetailsModelMapper.map(teams.get(0));
        } else {
            resultsModel = searchResultsModelMapper.map(persons, teams);
        }
        return resultsModel;

    }
}
