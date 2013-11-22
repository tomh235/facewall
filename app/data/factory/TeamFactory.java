package data.factory;

import data.datatype.TeamId;
import data.dto.TeamDTO;
import domain.Person;
import domain.Team;

import java.util.List;

public class TeamFactory {
    final TraversingPersonRepository traversingPersonRepo;

    public TeamFactory(TraversingPersonRepository traversingPersonRepo) {
        this.traversingPersonRepo = traversingPersonRepo;
    }

    public Team createTeam(TeamDTO teamDTO) {
        return new DefaultTeam(new TeamId(teamDTO.id), teamDTO.name, teamDTO.colour);
    }

    private class DefaultTeam extends AbstractTeam {

        protected DefaultTeam(TeamId id, String name, String colour) {
            super(id, name, colour);
        }

        @Override public List<Person> members() {
            return traversingPersonRepo.findMembersOfTeam(id, this);
        }
    }
}
