package data.factory;

import data.dao.FacewallDAO;
import data.dto.TeamDTO;
import data.mapper.MutablePerson;
import data.mapper.MutableTeam;
import data.mapper.PersonDTOMapper;
import domain.Person;

import java.util.List;

import static data.factory.DefaultMutablePerson.newMutablePersonInTeam;

public class LazyMutableTeamFactory {
    private final FacewallDAO facewallDAO;
    private final PersonDTOMapper personDTOMapper;

    public LazyMutableTeamFactory(FacewallDAO facewallDAO, PersonDTOMapper personDTOMapper) {
        this.facewallDAO = facewallDAO;
        this.personDTOMapper = personDTOMapper;
    }

    public MutableTeam createLazyMutableTeam() {
        return new LazyMutableTeam();
    }

    private class LazyMutableTeam extends MutableTeam {

        MembersFactory membersFactory = new MembersFactory(personDTOMapper, new DefaultMutablePersonFactory());

        @Override public List<Person> members() {
            TeamDTO teamDTO = facewallDAO.fetchTeam(id);

            return membersFactory.createMembers(teamDTO);
        }

        private class DefaultMutablePersonFactory implements MutablePersonFactory {
            @Override public MutablePerson createMutablePerson() {
                return newMutablePersonInTeam(LazyMutableTeam.this);
            }
        }
    }
}
