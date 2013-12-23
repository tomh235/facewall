package data.dto;

import org.neo4j.graphdb.Node;

import java.util.List;

public class TeamDTO {
    public final TeamInformation teamInformation;
    public final List<PersonInformation> memberInformation;

    public TeamDTO(TeamInformation teamInformation, List<PersonInformation> memberInformation) {
        this.teamInformation = teamInformation;
        this.memberInformation = memberInformation;
    }
}