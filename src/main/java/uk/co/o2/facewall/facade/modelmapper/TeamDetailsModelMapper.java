package uk.co.o2.facewall.facade.modelmapper;

import uk.co.o2.facewall.domain.Team;
import uk.co.o2.facewall.model.TeamDetailsModel;

public class TeamDetailsModelMapper {
    public TeamDetailsModel map(Team team) {
        return new TeamDetailsModel(team.name(), team.colour());
    }
}
