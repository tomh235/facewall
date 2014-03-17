package uk.co.o2.facewall.facade;

import uk.co.o2.facewall.data.TeamRepository;
import uk.co.o2.facewall.domain.Person;
import uk.co.o2.facewall.domain.Persons;
import uk.co.o2.facewall.domain.Query;
import uk.co.o2.facewall.domain.Team;
import uk.co.o2.facewall.facade.modelmapper.OverviewModelMapper;
import uk.co.o2.facewall.facade.modelmapper.TeamDetailsModelMapper;
import uk.co.o2.facewall.model.OverviewEntryModel;
import uk.co.o2.facewall.model.TeamDetailsWithPersonsModel;

import java.util.ArrayList;
import java.util.List;

public class TeamDetailsFacade {

    private final TeamRepository teamRepository;
    private final TeamDetailsModelMapper teamDetailsModelMapper;
    private final OverviewModelMapper overviewModelMapper;

    public TeamDetailsFacade(TeamRepository teamRepository, TeamDetailsModelMapper teamDetailsModelMapper, OverviewModelMapper overviewModelMapper) {
        this.teamRepository = teamRepository;
        this.teamDetailsModelMapper = teamDetailsModelMapper;
        this.overviewModelMapper = overviewModelMapper;
    }

    public TeamDetailsWithPersonsModel createTeamDetailsModel(Query name) {
        List<OverviewEntryModel> overviewEntryModels = new ArrayList<>();
        Team team = teamRepository.findTeamByName(name);

        Persons persons = Persons.newPersons(team.members());
        persons.sortByTeamNameThenName();

        for(Person person : persons) {
            overviewEntryModels.add(overviewModelMapper.map(person));
        }

        return teamDetailsModelMapper.mapWithPersons(team, overviewEntryModels);
    }

}
