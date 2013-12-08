package data.factory;

import data.dao.TraversingDAO;
import data.mapper.MutablePerson;
import data.mapper.MutableTeam;
import data.mapper.PersonDTOMapper;
import domain.Person;
import org.neo4j.graphdb.Node;

import java.util.List;

import static data.factory.DefaultMutablePerson.newMutablePersonInTeam;

public class LazyMutableTeamFactory {
    private final TraversingDAO dao;
    private final PersonDTOMapper personDTOMapper;

    public LazyMutableTeamFactory(TraversingDAO traversingDAO, PersonDTOMapper personDTOMapper) {
        this.dao = traversingDAO;
        this.personDTOMapper = personDTOMapper;
    }

    public MutableTeam createLazyMutableTeam() {
        return new LazyMutableTeam();
    }

    private class LazyMutableTeam extends MutableTeam {

        MembersFactory membersFactory = new MembersFactory(personDTOMapper, new DefaultMutablePersonFactory());

        @Override public List<Person> members() {
            List<Node> memberNodes = dao.fetchTeamMembers(id);

            return membersFactory.createMembers(memberNodes);
        }

        private class DefaultMutablePersonFactory implements MutablePersonFactory {
            @Override public MutablePerson createMutablePerson() {
                return newMutablePersonInTeam(LazyMutableTeam.this);
            }
        }
    }
}
