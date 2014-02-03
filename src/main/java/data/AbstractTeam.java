package data;

import data.dto.TeamInformation;
import domain.Team;

public abstract class AbstractTeam implements Team {

    protected final TeamInformation teamInformation;

    protected AbstractTeam(TeamInformation teamInformation) {
        this.teamInformation = teamInformation;
    }

    @Override
    public final String name() {
        return teamInformation.getName();
    }

    @Override
    public final String colour() {
        return teamInformation.getColour();
    }
}
