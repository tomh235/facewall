package uk.co.o2.facewall.facade.modelmapper;

import uk.co.o2.facewall.domain.Person;
import uk.co.o2.facewall.model.PersonDetailsModel;

public class PersonDetailsModelMapper {
    public PersonDetailsModel map(Person person) {
        return new PersonDetailsModel(person.name(), person.team().name(), person.picture(), person.email(), person.role());
    }
}
