package data.mapper;

import data.datatype.TeamId;
import domain.Team;
import org.neo4j.graphdb.Node;

import javax.annotation.Nullable;

import static data.datatype.TeamId.newTeamId;
import static domain.NoTeam.noTeam;

public class TeamDTOMapper {
    public Team map(MutableTeam team, @Nullable Node teamNode) {
        Team result = noTeam();

        if (teamNode != null) {
            TeamId id = newTeamId((String) teamNode.getProperty("id"));
            team.setId(id);

            String name = (String) teamNode.getProperty("name");
            team.setName(name);

            String colour = (String) teamNode.getProperty("colour");
            team.setColour(colour);

            result = team;
        }
        return result;
    }
}
