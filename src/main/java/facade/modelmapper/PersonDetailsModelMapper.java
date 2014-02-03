package facade.modelmapper;

import domain.Person;
import model.PersonDetailsModel;

public class PersonDetailsModelMapper {
    public PersonDetailsModel map(Person person) {
        return new PersonDetailsModel(person.name(), person.team().name(), person.picture(), person.email(), person.role());
    }
}
