package data.dto;

import org.neo4j.graphdb.Node;

import static data.dto.TeamInformation.newTeamInformation;
import static data.dto.TeamInformation.noTeamInformation;

public class TeamInformationMapper {

    public TeamInformation map(Node teamNode) {
        TeamInformation result = noTeamInformation();

        if (teamNode != null) {
            TeamInformation.Builder teamInformation = newTeamInformation();

            String id = (String) teamNode.getProperty("id");
            if (id != null) {
                teamInformation.withId(id);
            }

            String name = (String) teamNode.getProperty("name");
            if (name != null) {
                teamInformation.named(name);
            }

            String colour = (String) teamNode.getProperty("colour");
            if (colour != null) {
                teamInformation.coloured(colour);
            }

            result = teamInformation.build();
        }
        return result;
    }
}
