package data.factory;

import data.dto.PersonDTO;
import domain.Person;
import domain.Team;

import java.util.List;

public class PersonFactory {
    public List<Person> createPersons(List<PersonDTO> dto) {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    private Person createPerson(PersonDTO dto) {
        return null;
    }

    private class DefaultPersonBuilder extends PersonBuilder {

        @Override public Person build() {
            return new Person() {

                final String name = DefaultPersonBuilder.this.name;
                final String picture = DefaultPersonBuilder.this.picture;

                @Override public String id() {
                    return null;  //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override public String name() {
                    return null;  //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override public String picture() {
                    return null;  //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override public Team team() {
                    return null;  //To change body of implemented methods use File | Settings | File Templates.
                }
            };
        }
    }
}
