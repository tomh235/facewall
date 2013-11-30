package data.mapper;

import data.datatype.TeamId;
import domain.Person;
import org.junit.Test;

import java.util.List;

import static data.datatype.TeamId.newTeamId;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MutableTeamTest {

    private TestMutableTeam mutableTeam = new TestMutableTeam();

    class TestMutableTeam extends MutableTeam {
        @Override public List<Person> members() {
            throw new UnsupportedOperationException();
        }

        public TeamId getId() {
            return id;
        }
    }

    @Test
    public void get_set_name() {
        mutableTeam.setName("Ars");

        assertThat(mutableTeam.name(), is("Ars"));
    }

    @Test
    public void get_set_colour() {
        mutableTeam.setColour("blue");

        assertThat(mutableTeam.colour(), is("blue"));
    }

    @Test
    public void get_set_id() {
        mutableTeam.setId(newTeamId("teamId"));

        assertThat(mutableTeam.getId(), is(newTeamId("teamId")));
    }
}
