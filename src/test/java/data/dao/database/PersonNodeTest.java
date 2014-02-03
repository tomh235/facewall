package data.dao.database;

import data.dto.PersonInformation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.neo4j.graphdb.Node;

import static data.dto.PersonInformation.newPersonInformation;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PersonNodeTest {

    @Mock private Node wrappedNode;

    @InjectMocks
    private PersonNode personNode;

    @Test
    public void setProperties_copies_id_from_person_information() throws Exception {
        PersonInformation personInformation = newPersonInformation()
                .withId("some-id")
                .build();

        personNode.setProperties(personInformation);
        verify(wrappedNode).setProperty("id", "some-id");
    }

    @Test
    public void setProperties_copies_name_from_person_information() throws Exception {
        PersonInformation personInformation = newPersonInformation()
                .named("billy howell")
                .build();

        personNode.setProperties(personInformation);
        verify(wrappedNode).setProperty("name", "billy howell");
    }

    @Test
    public void setProperties_copies_picture_from_person_information() throws Exception {
        PersonInformation personInformation = newPersonInformation()
                .withPicture("billy.img")
                .build();

        personNode.setProperties(personInformation);
        verify(wrappedNode).setProperty("picture", "billy.img");
    }

    @Test
    public void setProperties_copies_email_from_person_information() throws Exception {
        PersonInformation personInformation = newPersonInformation()
                .withEmail("email@testemail.com")
                .build();

        personNode.setProperties(personInformation);
        verify(wrappedNode).setProperty("email", "email@testemail.com");
    }

    @Test
    public void setProperties_copies_role_from_person_information() throws Exception {
        PersonInformation personInformation = newPersonInformation()
                .withRole("BA")
                .build();

        personNode.setProperties(personInformation);
        verify(wrappedNode).setProperty("role", "BA");
    }

}
