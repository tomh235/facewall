package data.dao;

import data.dto.PersonInformation;
import data.dto.TeamDTO;
import data.dto.TeamInformation;

import java.util.*;

public class TeamDTOs implements Iterable<TeamDTO> {

    private Map<TeamInformation, List<PersonInformation>> teamsToMembers = new HashMap<>();

    public void addMemberToTeam(TeamInformation team, final PersonInformation member) {
        if (teamsToMembers.containsKey(team)) {
            teamsToMembers.get(team).add(member);
        } else {
            teamsToMembers.put(
                team,
                new ArrayList<PersonInformation>() {{ add(member); }}
            );
        }
    }

    //refactor for safety
    public TeamDTO getSingle() {
        return iterator().next();
    }

    @Override public Iterator<TeamDTO> iterator() {
        List<TeamDTO> dtos = new ArrayList<>();

        for (Map.Entry<TeamInformation, List<PersonInformation>> entry : teamsToMembers.entrySet()) {
            dtos.add(new TeamDTO(entry.getKey(), entry.getValue()));
        }
        return dtos.iterator();
    }
}
