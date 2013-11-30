package data.factory;

import data.mapper.MutablePerson;
import data.mapper.PersonMapper;
import domain.Person;
import org.neo4j.graphdb.Node;

import java.util.ArrayList;
import java.util.List;

public class MembersFactory {

    private final PersonMapper personMapper;
    private final MutablePersonFactory mutablePersonFactory;

    public MembersFactory(PersonMapper personMapper, MutablePersonFactory mutablePersonFactory) {
        this.personMapper = personMapper;
        this.mutablePersonFactory = mutablePersonFactory;
    }

    public List<Person> createMembers(List<Node> personNodes) {
        List<Person> members = new ArrayList<>();

        for (Node personNode : personNodes) {
            MutablePerson mutablePerson = mutablePersonFactory.createMutablePerson();

            members.add(personMapper.map(mutablePerson, personNode));
        }
        return members;
    }
}
