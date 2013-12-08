package data.factory;

import data.mapper.MutablePerson;
import data.mapper.PersonDTOMapper;
import domain.Person;
import org.neo4j.graphdb.Node;

import java.util.ArrayList;
import java.util.List;

public class MembersFactory {

    private final PersonDTOMapper personDTOMapper;
    private final MutablePersonFactory mutablePersonFactory;

    public MembersFactory(PersonDTOMapper personDTOMapper, MutablePersonFactory mutablePersonFactory) {
        this.personDTOMapper = personDTOMapper;
        this.mutablePersonFactory = mutablePersonFactory;
    }

    public List<Person> createMembers(List<Node> personNodes) {
        List<Person> members = new ArrayList<>();

        for (Node personNode : personNodes) {
            MutablePerson mutablePerson = mutablePersonFactory.createMutablePerson();

            members.add(personDTOMapper.map(mutablePerson, personNode));
        }
        return members;
    }
}
