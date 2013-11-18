package data.factory;

import data.datatype.PersonId;
import data.dto.PersonDTO;
import domain.Person;
import domain.Team;

public class PersonFactory {
    final TraversingRepository traversingRepo;

    public PersonFactory(TraversingRepository traversingRepo) {
        this.traversingRepo = traversingRepo;
    }

    public Person createPerson(PersonDTO personDTO) {
        return new DefaultPerson(new PersonId(personDTO.id), personDTO.name, personDTO.picture);
    }

    private class DefaultPerson implements Person {
        final PersonId id;
        final String name;
        final String picture;

        private DefaultPerson(PersonId id, String name, String picture) {
            this.id = id;
            this.name = name;
            this.picture = picture;
        }

        @Override public String id() {
            throw new UnsupportedOperationException("This field is deprecated, and no longer supported");
        }

        @Override public String name() {
            return name;
        }

        @Override public String picture() {
            return picture;
        }

        @Override public Team team() {
            return traversingRepo.findTeamForPerson(id);
        }
    }
}
