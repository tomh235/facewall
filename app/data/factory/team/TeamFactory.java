package data.factory.team;

import data.dto.TeamDTO;
import data.mapper.MutablePerson;
import data.mapper.PersonMapper;
import data.mapper.TeamMapper;
import domain.Person;
import domain.Team;
import org.neo4j.graphdb.Node;

import java.util.ArrayList;
import java.util.List;

import static data.factory.team.DefaultMutableTeam.newMutableTeamWithMembers;

//This class is doing quite a lot in terms of iterating through lists, which is making the corresponding test class rather
//bloated. Perhaps some of it could be extracted into another class?
public class TeamFactory {

    private final TeamMapper teamMapper;
    private final PersonMapper personMapper;
    private final LazyMutablePersonFactory lazyMutablePersonFactory;

    public TeamFactory(TeamMapper teamMapper, PersonMapper personMapper, LazyMutablePersonFactory lazyMutablePersonFactory) {
        this.teamMapper = teamMapper;
        this.personMapper = personMapper;
        this.lazyMutablePersonFactory = lazyMutablePersonFactory;
    }

    public List<Team> createTeams(List<TeamDTO> dtos) {
        List<Team> result = new ArrayList<>();

        for (TeamDTO dto : dtos) {
            result.add(createTeam(dto));
        }
        return result;
    }

    private Team createTeam(TeamDTO dto) {
        List<Person> lazyMembers = createMembers(dto.memberNodes);
        DefaultMutableTeam defaultMutableTeam = newMutableTeamWithMembers(lazyMembers);

        return teamMapper.map(defaultMutableTeam, dto.teamNode);
    }

    private List<Person> createMembers(List<Node> personNodes) {
        List<Person> members = new ArrayList<>();

        for (Node personNode : personNodes) {
            MutablePerson mutablePerson = lazyMutablePersonFactory.createLazyMutablePerson();

            members.add(personMapper.map(mutablePerson, personNode));
        }
        return members;
    }
}
