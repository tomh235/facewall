package data.dao;

import data.dto.TeamDTO;
import org.neo4j.graphdb.Node;

import java.util.*;

public class TeamDTOs implements Iterable<TeamDTO> {

    private Map<Node, List<Node>> teamsToMembers = new HashMap<>();

    public void addMemberToTeam(Node team, final Node member) {
        if (teamsToMembers.containsKey(team)) {
            teamsToMembers.get(team).add(member);
        } else {
            teamsToMembers.put(
                team,
                new ArrayList<Node>() {{ add(member); }}
            );
        }
    }

    //refactor for safety
    public TeamDTO getSingle() {
        return iterator().next();
    }

    @Override public Iterator<TeamDTO> iterator() {
        List<TeamDTO> dtos = new ArrayList<>();

        for (Map.Entry<Node, List<Node>> entry : teamsToMembers.entrySet()) {
            dtos.add(new TeamDTO(entry.getKey(), entry.getValue()));
        }
        return dtos.iterator();
    }
}
