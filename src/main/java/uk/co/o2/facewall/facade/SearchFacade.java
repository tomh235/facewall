package uk.co.o2.facewall.facade;

import uk.co.o2.facewall.data.PersonRepository;
import uk.co.o2.facewall.data.TeamRepository;
import uk.co.o2.facewall.domain.Person;
import uk.co.o2.facewall.domain.Query;
import uk.co.o2.facewall.domain.Team;
import uk.co.o2.facewall.facade.modelmapper.PersonDetailsModelMapper;
import uk.co.o2.facewall.facade.modelmapper.SearchResultsModelMapper;
import uk.co.o2.facewall.facade.modelmapper.TeamDetailsModelMapper;
import uk.co.o2.facewall.model.SearchResultsModel;

import java.util.List;

// A bit too many dependencies here. Perhaps we can split up the logic pertaining to resolving the type of model
// that needs to be mapped from retrieving the data that needs to go into the model.
public class SearchFacade {
    private final PersonRepository personRepository;
    private final TeamRepository teamRepository;
    private final SearchResultsModelMapper searchResultsModelMapper;
    private final PersonDetailsModelMapper personDetailsModelMapper;
    private final TeamDetailsModelMapper teamDetailsModelMapper;

    public SearchFacade(
            PersonRepository personRepository,
            TeamRepository teamRepository,
            SearchResultsModelMapper searchResultsModelMapper,
            PersonDetailsModelMapper personDetailsModelMapper,
            TeamDetailsModelMapper teamDetailsModelMapper
    ) {
        this.personRepository = personRepository;
        this.teamRepository = teamRepository;
        this.searchResultsModelMapper = searchResultsModelMapper;
        this.personDetailsModelMapper = personDetailsModelMapper;
        this.teamDetailsModelMapper = teamDetailsModelMapper;
    }

    public SearchResultsModel createSearchResultsModel(Query query) {
        SearchResultsModel resultsModel;
        List<Person> persons = personRepository.queryPersons(query);
        List<Team> teams = teamRepository.queryTeams(query);

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
