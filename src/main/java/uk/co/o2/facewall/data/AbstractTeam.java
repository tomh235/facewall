package uk.co.o2.facewall.data;

import uk.co.o2.facewall.data.dto.TeamInformation;
import uk.co.o2.facewall.domain.Team;

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
