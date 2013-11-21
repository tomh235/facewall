package data.factory;

import data.datatype.TeamId;
import data.dto.TeamDTO;
import domain.Person;
import domain.Team;

import java.util.List;

public class TeamFactory {
    final TraversingRepository traversingRepo;

    public TeamFactory(TraversingRepository traversingRepo) {
        this.traversingRepo = traversingRepo;
    }

    public Team createTeam(TeamDTO teamDTO) {
        return new DefaultTeam(new TeamId(teamDTO.id), teamDTO.name, teamDTO.colour);
    }

    private class DefaultTeam implements Team {
        final TeamId id;
        final String name;
        final String colour;

        private DefaultTeam(TeamId id, String name, String colour) {
            this.id = id;
            this.name = name;
            this.colour = colour;
        }

        @Deprecated
        @Override public String id() {
            throw new UnsupportedOperationException("This field is deprecated, and no longer supported");
        }

        @Override public String name() {
            return name;
        }

        @Override public String colour() {
            return colour;
        }

        @Override public List<Person> members() {
            return traversingRepo.findMembersOfTeam(id);
        }
    }
}
