package data.dto;

import data.dto.PersonInformation;
import org.neo4j.graphdb.Node;

import static data.dto.PersonInformation.newPersonInformation;
import static data.dto.PersonInformation.noPersonInformation;

public class PersonInformationMapper {

    public PersonInformation map(Node personNode) {
        PersonInformation result = noPersonInformation();

        if (personNode != null) {
            PersonInformation.Builder personInformation = newPersonInformation();

            String id = (String) personNode.getProperty("id");
            if (id != null) {
                personInformation.withId(id);
            }

            String name = (String) personNode.getProperty("name");
            if (name != null) {
                personInformation.named(name);
            }

            String picture = (String) personNode.getProperty("picture");
            if (picture != null) {
                personInformation.withPicture(picture);
            }

            result = personInformation.build();
        }
        return result;
    }
}
