package facade.modelmapper;

import domain.Team;
import model.TeamDetailsModel;

public class TeamDetailsModelMapper {
    public TeamDetailsModel map(Team team) {
        return new TeamDetailsModel(team.name(), team.colour());
    }
}
