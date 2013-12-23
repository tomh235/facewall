package data;

import data.dto.PersonInformation;
import domain.Team;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MutablePersonTest {

    @Test
    public void get_set_team() throws Exception {
        Team expectedTeam = mock(Team.class);
        
        MutablePerson mutablePerson = new MutablePerson(mock(PersonInformation.class));
        mutablePerson.setTeam(expectedTeam);
        
        assertThat(mutablePerson.team(), is(sameInstance(expectedTeam)));
    }
    
    @Test
    public void name_delegates_to_personInformation() throws Exception {
        PersonInformation personInformation = mock(PersonInformation.class);
        when(personInformation.getName()).thenReturn("hewett");

        MutablePerson mutablePerson = new MutablePerson(personInformation);

        assertThat(mutablePerson.name(), is("hewett"));
    }

    @Test
    public void colour_delegates_to_personInformation() throws Exception {
        PersonInformation personInformation = mock(PersonInformation.class);
        when(personInformation.getPicture()).thenReturn("img");

        MutablePerson mutablePerson = new MutablePerson(personInformation);

        assertThat(mutablePerson.picture(), is("img"));
    }
}
