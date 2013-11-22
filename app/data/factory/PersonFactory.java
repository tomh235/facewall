package data.factory;

import data.datatype.PersonId;
import data.dto.PersonDTO;
import domain.Person;
import domain.Team;

public class PersonFactory {
    final TraversingTeamRepository traversingTeamRepo;

    public PersonFactory(TraversingTeamRepository traversingTeamRepo) {
        this.traversingTeamRepo = traversingTeamRepo;
    }

    public Person createPerson(PersonDTO personDTO) {
        return new DefaultPerson(new PersonId(personDTO.id), personDTO.name, personDTO.picture);
    }

    private class DefaultPerson extends AbstractPerson {
        private DefaultPerson(PersonId id, String name, String picture) {
            super(id, name, picture);
        }

        @Override public Team team() {
            return traversingTeamRepo.findTeamForPerson(id);
        }
    }
}
