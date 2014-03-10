package uk.co.o2.facewall.facade.modelmapper;

import uk.co.o2.facewall.domain.Team;
import uk.co.o2.facewall.model.OverviewEntryModel;
import uk.co.o2.facewall.model.TeamDetailsModel;
import uk.co.o2.facewall.model.TeamDetailsWithPersonsModel;

import java.util.List;

public class TeamDetailsModelMapper {
    public TeamDetailsModel map(Team team) {
        return new TeamDetailsModel(team.name(), team.colour(), team.members().size());
    }
    public TeamDetailsWithPersonsModel mapWithPersons(Team team, List<OverviewEntryModel> entries ) {
        return new TeamDetailsWithPersonsModel(team.name(), team.colour(), team.members().size(), entries);
    }
}
