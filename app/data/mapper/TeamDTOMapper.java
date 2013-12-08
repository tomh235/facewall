package data.mapper;

import domain.Team;
import org.neo4j.graphdb.Node;

import javax.annotation.Nullable;

import static domain.NoTeam.noTeam;

public class TeamDTOMapper {
    public Team map(MutableTeam team, @Nullable Node teamNode) {
        Team result = noTeam();

        if (teamNode != null) {
            team.setName((String) teamNode.getProperty("name"));
            team.setColour((String) teamNode.getProperty("colour"));

            result = team;
        }
        return result;
    }
}
