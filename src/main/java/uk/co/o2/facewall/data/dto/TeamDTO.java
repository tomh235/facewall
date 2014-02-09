package uk.co.o2.facewall.data.dto;

import java.util.List;

public class TeamDTO {
    public final TeamInformation teamInformation;
    public final List<PersonInformation> memberInformation;

    public TeamDTO(TeamInformation teamInformation, List<PersonInformation> memberInformation) {
        this.teamInformation = teamInformation;
        this.memberInformation = memberInformation;
    }
}