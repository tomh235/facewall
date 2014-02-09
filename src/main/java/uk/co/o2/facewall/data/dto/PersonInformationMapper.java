package uk.co.o2.facewall.data.dto;

import org.neo4j.graphdb.Node;

import static uk.co.o2.facewall.data.dto.PersonInformation.newPersonInformation;
import static uk.co.o2.facewall.data.dto.PersonInformation.noPersonInformation;

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

            String email = (String) personNode.getProperty("email");
            if (email != null) {
                personInformation.withEmail(email);
            }

            String role = (String) personNode.getProperty("role");
            if (role != null) {
                personInformation.withRole(role);
            }

            result = personInformation.build();
        }
        return result;
    }
}
