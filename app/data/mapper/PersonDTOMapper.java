package data.mapper;

import domain.Person;
import org.neo4j.graphdb.Node;

public class PersonDTOMapper {
    public Person map(MutablePerson mutablePerson, Node personNode) {
        mutablePerson.setName((String) personNode.getProperty("name"));
        mutablePerson.setPicture((String) personNode.getProperty("picture"));

        return mutablePerson;
    }
}
