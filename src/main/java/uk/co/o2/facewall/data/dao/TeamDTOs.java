package uk.co.o2.facewall.data.dao;

import uk.co.o2.facewall.data.dto.PersonInformation;
import uk.co.o2.facewall.data.dto.TeamDTO;
import uk.co.o2.facewall.data.dto.TeamInformation;

import java.util.*;

public class TeamDTOs implements Iterable<TeamDTO> {

    private Map<TeamInformation, List<PersonInformation>> teamsToMembers = new HashMap<>();

    public void addMemberToTeam(TeamInformation team, final PersonInformation member) {
        if (teamsToMembers.containsKey(team)) {
            teamsToMembers.get(team).add(member);
        } else {
            teamsToMembers.put(
                    team,
                    members(member)
            );
        }
    }

    @Override public Iterator<TeamDTO> iterator() {
        List<TeamDTO> dtos = new ArrayList<>();

        for (Map.Entry<TeamInformation, List<PersonInformation>> entry : teamsToMembers.entrySet()) {
            dtos.add(new TeamDTO(entry.getKey(), entry.getValue()));
        }
        return dtos.iterator();
    }

    private ArrayList<PersonInformation> members(final PersonInformation member) {
        return new ArrayList<PersonInformation>() {{ add(member); }};
    }
}
