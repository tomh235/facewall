package data.mapper;

import domain.Team;
import org.neo4j.graphdb.Node;

public class TeamDTOMapper {
    public Team map(MutableTeam team, Node teamNode) {
        team.setName((String) teamNode.getProperty("name"));
        team.setColour((String) teamNode.getProperty("colour"));

        return team;
    }
}
