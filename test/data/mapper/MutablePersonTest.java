package data.mapper;

import data.datatype.PersonId;
import domain.Team;
import org.junit.Test;

import static data.datatype.PersonId.newPersonId;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MutablePersonTest {

    private TestMutablePerson mutablePerson = new TestMutablePerson();

    class TestMutablePerson extends MutablePerson {
        @Override public Team team() {
            throw new UnsupportedOperationException();
        }

        public PersonId getId() {
            return id;
        }
    }

    @Test
    public void get_set_name() {
        mutablePerson.setName("Hyun");

        assertThat(mutablePerson.name(), is("Hyun"));
    }

    @Test
    public void get_set_colour() {
        mutablePerson.setPicture("img of Hyun");

        assertThat(mutablePerson.picture(), is("img of Hyun"));
    }

    @Test
    public void get_set_id() {
        mutablePerson.setId(newPersonId("personId"));

        assertThat(mutablePerson.getId(), is(newPersonId("personId")));
    }
}
