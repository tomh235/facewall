package uk.co.o2.facewall.data;

import uk.co.o2.facewall.data.datatype.PersonId;
import uk.co.o2.facewall.data.dto.PersonInformation;
import uk.co.o2.facewall.domain.Team;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static uk.co.o2.facewall.data.datatype.PersonId.*;

public class MutablePersonTest {

    private PersonInformation personInformation;
    private MutablePerson mutablePerson;

    @Test
    public void get_set_team() throws Exception {
        Team expectedTeam = mock(Team.class);

        mutablePerson = new MutablePerson(mock(PersonInformation.class));
        mutablePerson.setTeam(expectedTeam);

        assertThat(mutablePerson.team(), is(sameInstance(expectedTeam)));
    }

    @Test
    public void name_delegates_to_personInformation() throws Exception {
        personInformation = mock(PersonInformation.class);
        when(personInformation.getName()).thenReturn("hewett");

        mutablePerson = new MutablePerson(personInformation);

        assertThat(mutablePerson.name(), is("hewett"));
    }

    @Test
    public void colour_delegates_to_personInformation() throws Exception {
        personInformation = mock(PersonInformation.class);
        when(personInformation.getPicture()).thenReturn("img");

        mutablePerson = new MutablePerson(personInformation);

        assertThat(mutablePerson.picture(), is("img"));
    }

    @Test
    public void email_delegates_to_personInformation() throws Exception {
        personInformation = mock(PersonInformation.class);
        PersonId expectedId = newPersonId("email@testemail.com");
        when(personInformation.getId()).thenReturn(expectedId);

        mutablePerson = new MutablePerson(personInformation);

        assertThat(mutablePerson.getId(), is(expectedId));
    }

    @Test
    public void role_delegates_to_personInformation() throws Exception {
        personInformation = mock(PersonInformation.class);
        when(personInformation.getRole()).thenReturn("BA");

        mutablePerson = new MutablePerson(personInformation);

        assertThat(mutablePerson.role(), is("BA"));
    }
}
