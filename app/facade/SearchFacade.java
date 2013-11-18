package facade;

import domain.Person;
import domain.Query;
import domain.Team;
import facade.modelmapper.PersonDetailsModelMapper;
import facade.modelmapper.SearchResultsModelMapper;
import model.SearchResultsModel;
import data.ScalaRepository;
import java.util.List;

public class SearchFacade {
<<<<<<< Updated upstream
    ScalaRepository repository;
    SearchResultsModelMapper searchResultsModelMapper;
    PersonDetailsModelMapper personDetailsModelMapper;
=======
    private final Repository repository;
    private final SearchResultsModelMapper searchResultsModelMapper;
    private final PersonDetailsModelMapper personDetailsModelMapper;
>>>>>>> Stashed changes

    public SearchFacade(ScalaRepository repository, SearchResultsModelMapper searchResultsModelMapper, PersonDetailsModelMapper personDetailsModelMapper) {
        this.repository = repository;
        this.searchResultsModelMapper = searchResultsModelMapper;
        this.personDetailsModelMapper = personDetailsModelMapper;
    }

    public SearchResultsModel createSearchResultsModel(Query query) {
        List<Person> persons = repository.queryPersons(query);
        List<Team> teams = repository.queryTeams(query);

        if (persons.size() == 1 && teams.isEmpty()) {
            return personDetailsModelMapper.map(persons.get(0));
        }

        if (teams.size() == 1 && persons.isEmpty()) {
        }

        return searchResultsModelMapper.map(persons, teams);
    }
}
