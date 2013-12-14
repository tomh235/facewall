package data.mapper;

import data.datatype.PersonId;
import domain.Person;
import org.neo4j.graphdb.Node;

import static data.datatype.PersonId.newPersonId;

public class PersonDTOMapper {
    public Person map(MutablePerson mutablePerson, Node personNode) {
        PersonId id = newPersonId((String) personNode.getProperty("id"));
        mutablePerson.setId(id);

        String name = (String) personNode.getProperty("name");
        mutablePerson.setName(name);

        String picture = (String) personNode.getProperty("picture");
        mutablePerson.setPicture(picture);

        return mutablePerson;
    }
}
