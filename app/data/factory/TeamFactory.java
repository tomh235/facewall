package data.factory;

import data.dto.TeamDTO;
import data.mapper.TeamDTOMapper;
import domain.Person;
import domain.Team;

import java.util.ArrayList;
import java.util.List;

import static data.factory.DefaultMutableTeam.newMutableTeamWithMembers;

public class TeamFactory {

    private final TeamDTOMapper teamDTOMapper;
    private final MembersFactory membersFactory;

    public TeamFactory(TeamDTOMapper teamDTOMapper, MembersFactory membersFactory) {
        this.teamDTOMapper = teamDTOMapper;
        this.membersFactory = membersFactory;
    }

    public List<Team> createTeams(List<TeamDTO> dtos) {
        List<Team> result = new ArrayList<>();

        for (TeamDTO dto : dtos) {
            result.add(createTeam(dto));
        }
        return result;
    }

    private Team createTeam(TeamDTO dto) {
        List<Person> lazyMembers = membersFactory.createMembers(dto.memberNodes);
        DefaultMutableTeam defaultMutableTeam = newMutableTeamWithMembers(lazyMembers);

        return teamDTOMapper.map(defaultMutableTeam, dto.teamNode);
    }
}
