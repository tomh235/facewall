package data.factory;

import data.dto.TeamDTO;
import data.mapper.TeamMapper;
import domain.Person;
import domain.Team;

import java.util.ArrayList;
import java.util.List;

import static data.factory.DefaultMutableTeam.newMutableTeamWithMembers;

public class TeamFactory {

    private final TeamMapper teamMapper;
    private final MembersFactory membersFactory;

    public TeamFactory(TeamMapper teamMapper, MembersFactory membersFactory) {
        this.teamMapper = teamMapper;
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

        return teamMapper.map(defaultMutableTeam, dto.teamNode);
    }
}
